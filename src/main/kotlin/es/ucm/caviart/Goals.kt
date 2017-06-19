package es.ucm.caviart

import com.microsoft.z3.*

/**
 * Classes and functions involving goals
 *
 * Created by manuel on 14/06/17.
 */


val debug = true

class Goal(val name: String,
           assumptions: List<Assertion>,
           conclusion: Assertion,
           environment: Map<String, Type>,
           val kappas: Map<String, Kappa>,
           declarationMap: Map<String, UninterpretedFunctionType>) {

    val namedKappas: Set<String>
    val rhsKappa: String?

    private val kappaQualifiers: Map<String, List<BoolExpr>>
    private val z3DeclarationMap: Map<String, FuncDecl>
    private val ctx: Context = Context()
    private val solver: Solver

    init {
        solver = ctx.mkSolver()
        z3DeclarationMap = declarationMap.map { (k, v) -> k to v.toFuncDecl(k, ctx) }.toMap()
        namedKappas = searchAppliedPredicates(assumptions, kappas.keys) union searchAppliedPredicates(listOf(conclusion), kappas.keys)
        rhsKappa = when (conclusion) {
            is PredicateApplication -> if (conclusion.name in kappas.keys) conclusion.name else null
            else -> null
        }
        kappaQualifiers = kappas.mapValues { (_, kappa) ->
            val environment = kappa.arguments.map { it.varName to it.type.toZ3Sort(ctx) }.toMap()
            val symbolMap = kappa.arguments.map { it.varName to ctx.mkSymbol(it.varName) }.toMap()

            kappa.availableQualifiers.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment) }
        }
        val z3Symbols = environment.mapValues { (k, _) -> ctx.mkSymbol(k) }
        val z3Environment = environment.map { (k, t) -> k to t.toZ3Sort(ctx) }.toMap()
        val z3Assumptions = assumptions.map { it.toZ3BoolExpr(ctx, z3Symbols, z3DeclarationMap, z3Environment) }
        val z3Conclusion = conclusion.toZ3BoolExpr(ctx, z3Symbols, z3DeclarationMap, z3Environment)

        z3Assumptions.forEach { solver.add(it) }
        solver.add(ctx.mkNot(z3Conclusion))

        if (debug) {
            println("Created goal " + name + ".")
            println("Given:")
            z3Assumptions.forEach {
                println(it.sExpr)
            }
            println("We want to prove:")
            println(z3Conclusion.sExpr)
        }
    }

    fun check(solution: Solution): GoalSolveResult {
        println("Solving " + name)
        solver.push()

        val lhsKappas = if (rhsKappa == null) namedKappas else (namedKappas - rhsKappa)

        lhsKappas.forEach {
            val z3Equivalence = kappaDefinitionToZ3(it, solution)
            if (debug) {
                println("where " + z3Equivalence.sExpr)
            }
            solver.add(z3Equivalence)
        }

        solver.push()
        rhsKappa?.let {
            val z3Equivalence = kappaDefinitionToZ3(it, solution)
            if (debug) {
                println("where " + z3Equivalence.sExpr)
            }
            solver.add(z3Equivalence)
        }
        val wholeFormulaVerdict = solver.check()
        solver.pop()

        val result: GoalSolveResult

        when {
            wholeFormulaVerdict == Status.UNSATISFIABLE ->
                result = Correct()
            rhsKappa != null -> {
                weakenKappa(solution, rhsKappa)
                result = KappaWeakened(rhsKappa)
            }
            else ->
                result = CannotWeaken()
        }

        solver.pop()
        return result
    }

    private fun weakenKappa(solution: Solution, rhsKappa: String) {
        if (debug) println("Weakening $rhsKappa...")
        val qualifiersInSolution: MutableSet<Int> = solution.kappas[rhsKappa]!!.toCollection(mutableSetOf())
        val acceptedQualifiers = mutableSetOf<Int>()
        qualifiersInSolution.forEach {
            solution.kappas[rhsKappa] = mutableSetOf(it)
            val z3Equivalence = kappaDefinitionToZ3(rhsKappa, solution)
            solver.push()
            solver.add(z3Equivalence)
            val status = solver.check()
            if (status == Status.UNSATISFIABLE) {
                acceptedQualifiers += it
            }
            solver.pop()
        }
        solution.kappas[rhsKappa] = acceptedQualifiers
    }

    private fun kappaDefinitionToZ3(kappaName: String, solution: Solution): Quantifier {
        val kappa = kappas[kappaName]!!
        val qualifiers = kappaQualifiers[kappaName]!!
        val symbolSorts = kappa.arguments.map { ctx.mkSymbol(it.varName) to it.type.toZ3Sort(ctx) }

        val z3Equivalence = ctx.mkForall(kappa.arguments.map { ctx.mkConst(it.varName, it.type.toZ3Sort(ctx)) }.toTypedArray(),
                ctx.mkIff(
                        ctx.mkApp(z3DeclarationMap[kappaName],
                                *symbolSorts.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray()
                        ) as BoolExpr,
                        ctx.mkAnd(*solution.kappas[kappaName]!!.map { n -> qualifiers[n] }.toTypedArray())
                ),
                0, null, null, null, null)
        return z3Equivalence
    }

    private fun searchAppliedPredicates(assertions: List<Assertion>, predicateNames: Set<String>): Set<String> {
        val result = mutableSetOf<String>()

        fun traverseAssertion(assertion: Assertion) {
            when (assertion) {
                is PredicateApplication ->
                    if (assertion.name in predicateNames) {
                        result.add(assertion.name)
                    }
                is Not -> traverseAssertion(assertion.assertion)
                is And -> assertion.conjuncts.forEach { traverseAssertion(it) }
                is Or -> assertion.disjuncts.forEach { traverseAssertion(it) }
                is Implication -> assertion.operands.forEach { traverseAssertion(it) }
                is Iff -> assertion.operands.forEach { traverseAssertion(it) }
                is ForAll -> traverseAssertion(assertion.assertion)
                is Exists -> traverseAssertion(assertion.assertion)
                is LetAssertion -> traverseAssertion(assertion.mainAssertion)
                is CaseAssertion -> {
                    assertion.branches.forEach { traverseAssertion(it.assertion) }
                    assertion.defaultBranch?.let { traverseAssertion(it.assertion) }
                }
            }
        }

        assertions.forEach { traverseAssertion(it) }

        return result
    }

}


abstract class GoalSolveResult
class Correct : GoalSolveResult()
class CannotWeaken : GoalSolveResult()
data class KappaWeakened(val varName: String) : GoalSolveResult()


class InvalidGoal(message: String) : RuntimeException(message)