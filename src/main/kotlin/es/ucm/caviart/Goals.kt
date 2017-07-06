package es.ucm.caviart

import com.microsoft.z3.*
import java.util.function.Predicate

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
           val mus: Map<String, Mu>,
           declarationMap: Map<String, UninterpretedFunctionType>) {

    class MuInfo(val boundVar1: Symbol,
                 val boundVar2: Symbol,
                 val qIQualifiers: List<BoolExpr>,
                 val qEQualifiers: List<QEQualifier>,
                 val qIIQualifiers: List<BoolExpr>,
                 val qEEQualifiers: List<QEEQualifier>,
                 val qLenQualifiers: List<BoolExpr>)

    class QEQualifier(val qualifier: BoolExpr, val arrays: List<Pair<Symbol, Sort>>)
    class QEEQualifier(val qualifier: BoolExpr, val arrays1: List<Pair<Symbol, Sort>>, val arrays2: List<Pair<Symbol, Sort>>)

    val namedKappas: Set<String>
    val namedMus: Set<String>
    val rhsKappa: String?
    val rhsMu: String?

    private val kappaQualifiers: Map<String, List<BoolExpr>>
    private val muQualifiers: Map<String, MuInfo>
    private val z3DeclarationMap: Map<String, FuncDecl>
    private val ctx: Context = Context()
    private val solver: Solver

    init {
        solver = ctx.mkSolver()
        z3DeclarationMap = declarationMap.map { (k, v) -> k to v.toFuncDecl(k, ctx) }.toMap()
        val kappaNames = kappas.keys
        val muNames = mus.keys
        namedKappas = searchAppliedPredicates(assumptions, kappaNames) union searchAppliedPredicates(listOf(conclusion), kappaNames)
        namedMus = searchAppliedPredicates(assumptions, muNames) union searchAppliedPredicates(listOf(conclusion), muNames);
        rhsKappa = if (conclusion is PredicateApplication && conclusion.name in kappaNames) conclusion.name else null
        rhsMu = if (conclusion is PredicateApplication && conclusion.name in muNames) conclusion.name else null
        kappaQualifiers = kappas.mapValues { (_, kappa) ->
            val environment = kappa.arguments.map { it.varName to it.type.toZ3Sort(ctx) }.toMap()
            val symbolMap = kappa.arguments.map { it.varName to ctx.mkSymbol(it.varName) }.toMap()

            kappa.qStar.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment) }
        }
        muQualifiers = mus.mapValues { (_, mu) ->
            val boundVar1Symbol = ctx.mkSymbol(mu.boundVar1)
            val boundVar2Symbol = ctx.mkSymbol(mu.boundVar2)
            val environment = (mu.arguments.map { it.varName to it.type.toZ3Sort(ctx) }
                    + listOf(mu.boundVar1 to ctx.mkIntSort(), mu.boundVar2 to ctx.mkIntSort())).toMap()
            val symbolMap = (mu.arguments.map { it.varName to ctx.mkSymbol(it.varName) }
                    + listOf(mu.boundVar1 to boundVar1Symbol, mu.boundVar2 to boundVar2Symbol)).toMap()
            MuInfo(boundVar1Symbol,
                    boundVar2Symbol,
                    mu.qI.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment) },
                    mu.qE.map {
                        QEQualifier(
                                it.qualifier.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment),
                                it.arrayNames.map { (name, type) ->
                                    Pair(symbolMap[name]!!, type.toZ3Sort(ctx))
                                }
                        )
                    },
                    mu.qII.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment) },
                    mu.qEE.map {
                        QEEQualifier(it.qualifier.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment),
                                it.arrayNames1.map { (name, type) -> Pair(symbolMap[name]!!, type.toZ3Sort(ctx)) },
                                it.arrayNames2.map { (name, type) -> Pair(symbolMap[name]!!, type.toZ3Sort(ctx)) })
                    },
                    mu.qLen.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, environment) }
            )
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
        val lhsMus = if (rhsMu == null) namedMus else (namedMus - rhsMu)

        lhsKappas.forEach {
            val z3Equivalence = kappaDefinitionToZ3(it, solution)
            if (debug) {
                println("where " + z3Equivalence.sExpr)
            }
            solver.add(z3Equivalence)
        }

        lhsMus.forEach {
            val z3Equivalence = muDefinitionToZ3(it, solution)
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
        rhsMu?.let {
            val z3Equivalence = muDefinitionToZ3(it, solution)
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
            rhsMu != null -> {
                weakenMu(solution, rhsMu)
                result = MuWeakened(rhsMu)
            }
            else ->
                result = CannotWeaken()
        }

        solver.pop()
        return result
    }

    private fun weakenKappa(solution: Solution, rhsKappa: String) {
        if (debug) println("Weakening $rhsKappa...")
        val qualifiersInSolution: Set<Int> = solution.kappas[rhsKappa]!!.toSet()
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

    private fun weakenMu(solution: Solution, rhsMu: String) {
        if (debug) println("Weakening $rhsMu...")
        val muSolutionToWeaken = solution.mus[rhsMu]!!

        val numberOfQI = muQualifiers[rhsMu]!!.qIQualifiers.size

        val acceptedSingleRefinements = mutableListOf<Refinement>()
        val rejectedSingleRefinements = mutableListOf<Refinement>()

        muSolutionToWeaken.singleRefinements.forEach {
            solution.mus[rhsMu] = MuSolution(listOf(it), listOf(), setOf())
            val z3Equivalence = muDefinitionToZ3(rhsMu, solution)
            solver.push()
            solver.add(z3Equivalence)
            val status = solver.check()
            solver.pop()
            if (status == Status.UNSATISFIABLE) {
                acceptedSingleRefinements += it
            } else {
                rejectedSingleRefinements += it
            }
        }

        rejectedSingleRefinements.forEach {
            val strongestLhs = (0 until numberOfQI).toSet()
            solution.mus[rhsMu] = MuSolution(
                    listOf(Refinement(strongestLhs, it.rhs)),
                    listOf(),
                    setOf()
            )
            solver.push()
            val z3Definition = muDefinitionToZ3(rhsMu, solution)
            solver.add(z3Definition)
            val status = solver.check()
            solver.pop()
            if (debug) {
                println("Strongest mapping\n${z3Definition.sExpr}\nResult: $status")
            }
            if (status == Status.UNSATISFIABLE) {
                var (currentLhs, setIterator) = iterateSupersetsOf(it.lhs, (0 until numberOfQI).toSet())

                while(currentLhs != null) {
                    val currentRefinement = Refinement(currentLhs.toSet(), it.rhs)
                    solution.mus[rhsMu] = MuSolution(
                            listOf(currentRefinement),
                            listOf(),
                            setOf()
                    )
                    solver.push()
                    val z3Definition = muDefinitionToZ3(rhsMu, solution)
                    solver.add(z3Definition)
                    val status = solver.check()
                    solver.pop()
                    if (debug) {
                        println("Superset\n${z3Definition.sExpr}\nResult: $status")
                    }
                    if (status == Status.UNSATISFIABLE) {
                        acceptedSingleRefinements.add(currentRefinement)
                        currentLhs = setIterator.next(false)
                    } else {
                        currentLhs = setIterator.next(true)
                    }
                }
            }
        }

        // TODO: Weaken double refinements
        // TODO: Weaken qLen

        solution.mus[rhsMu] = MuSolution(acceptedSingleRefinements, listOf(), setOf())
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

    private fun muDefinitionToZ3(muName: String, solution: Solution): Quantifier {
        val mu = mus[muName]!!

        val symbolSorts = mu.arguments.map { ctx.mkSymbol(it.varName) to it.type.toZ3Sort(ctx) }

        val equivLhs = ctx.mkApp(z3DeclarationMap[muName], *symbolSorts.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray())

        val singleAssertions = solution.mus[muName]!!.singleRefinements.map {

            val boundVarAsConst = ctx.mkIntConst(mu.boundVar1)
            ctx.mkForall(arrayOf(boundVarAsConst),
                    ctx.mkImplies(
                            ctx.mkAnd(
                                    ctx.mkGe(boundVarAsConst, ctx.mkInt(0)),
                                    *it.lhs.map { muQualifiers[muName]!!.qIQualifiers[it]!! }.toTypedArray(),
                                    *it.rhs.flatMap {
                                        muQualifiers[muName]!!.qEQualifiers[it]!!.arrays.map { (symbol, sort) ->
                                            val letFunction = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), sort), ctx.mkIntSort())
                                            ctx.mkLt(boundVarAsConst, ctx.mkApp(letFunction, ctx.mkConst(symbol, ctx.mkArraySort(ctx.mkIntSort(), sort))) as ArithExpr)
                                        }
                                    }.toTypedArray()
                            ),
                            ctx.mkAnd(
                                    *it.rhs.map { muQualifiers[muName]!!.qEQualifiers[it]!!.qualifier }.toTypedArray()
                            )
                    ), 0, null, null, null, null
            )
        }

        val doubleAssertions = solution.mus[muName]!!.doubleRefinements.map {

            val boundVar1AsConst = ctx.mkIntConst(mu.boundVar1)
            val boundVar2AsConst = ctx.mkIntConst(mu.boundVar2)
            ctx.mkForall(arrayOf(boundVar1AsConst, boundVar2AsConst),
                    ctx.mkImplies(
                            ctx.mkAnd(
                                    ctx.mkGe(boundVar1AsConst, ctx.mkInt(0)), ctx.mkGe(boundVar2AsConst, ctx.mkInt(0)),
                                    *it.lhs.map { muQualifiers[muName]!!.qIIQualifiers[it]!! }.toTypedArray(),
                                    *it.rhs.flatMap {
                                        muQualifiers[muName]!!.qEEQualifiers[it]!!.arrays1.map { (symbol, sort) ->
                                            val lenFunction = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), sort), ctx.mkIntSort())
                                            ctx.mkLt(boundVar1AsConst, ctx.mkApp(lenFunction, ctx.mkConst(symbol, ctx.mkArraySort(ctx.mkIntSort(), sort))) as ArithExpr)
                                        }
                                    }.toTypedArray(),
                                    *it.rhs.flatMap {
                                        muQualifiers[muName]!!.qEEQualifiers[it]!!.arrays2.map { (symbol, sort) ->
                                            val lenFunction = ctx.mkFuncDecl("len", ctx.mkArraySort(ctx.mkIntSort(), sort), ctx.mkIntSort())
                                            ctx.mkLt(boundVar2AsConst, ctx.mkApp(lenFunction, ctx.mkConst(symbol, ctx.mkArraySort(ctx.mkIntSort(), sort))) as ArithExpr)
                                        }
                                    }.toTypedArray()
                            ),
                            ctx.mkAnd(
                                    *it.rhs.map { muQualifiers[muName]!!.qEEQualifiers[it]!!.qualifier }.toTypedArray()
                            )
                    ), 0, null, null, null, null
            )
        }

        // TODO: Generate qLen qualifiers

        val equivRhs = ctx.mkAnd(*singleAssertions.toTypedArray(), *doubleAssertions.toTypedArray())

        val z3Equivalence = ctx.mkForall(
                mu.arguments.map { ctx.mkConst(it.varName, it.type.toZ3Sort(ctx)) }.toTypedArray(),
                ctx.mkIff(equivLhs as BoolExpr, equivRhs),
                0, null, null, null, null
        )
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
data class MuWeakened(val varName: String) : GoalSolveResult()


class InvalidGoal(message: String) : RuntimeException(message)