/*
    Copyright (c) 2018 Manuel Montenegro

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/
package es.ucm.caviart.iterativeweakening.z3

/**
 * This module implements the iterative weakening procedure with the
 * help of Z3 goals.
 */


import com.microsoft.z3.*
import es.ucm.caviart.ast.*
import es.ucm.caviart.iterativeweakening.*
import es.ucm.caviart.utils.FormulaCounter


/**
 * This class represents a goal translated into Z3's structures and
 * provides methods for checking whether a given solution satisfies
 * the goal, and providing a weakened solution in case the solution
 * does not satisfy the goal.
 *
 * @param name Name of the goal
 * @param description A text explaining why this goal was generated
 * @param assumptions A list of given assumptions
 * @param conclusion The fact to be proven
 * @param environment A map containing the type of each variable in the goal
 * @param kappas Definition of each kappa
 * @param mus Definition of each mu
 * @param declarationMap A map associating each uninterpreted function to its type
 */
class Z3Goal(val name: String,
             val description: String,
             assumptions: List<Assertion>,
             conclusion: Assertion,
             environment: Map<String, HMType>,
             val kappas: Map<String, Kappa>,
             val mus: Map<String, Mu>,
             kappaIndices: Map<String, Set<Int>>,
             muIndices: Map<String, Set<Int>>,
             declarationMap: Map<String, UninterpretedFunctionType>,
             val pruneTrivialLHS: Boolean,
             val timeout: Long) {

    /**
     * The MuInfo, KappaInfo, QEQualifier, QEEQualifier structures are similar to the Mu class,
     * but they represent assertions as Z3 BoolExpr instances, instead of Assertion instances
     */
    class MuInfo(val arguments: List<Pair<Symbol, Sort>>,
                 val lenSymbols: List<Symbol>,
                 val boundVar1: Symbol,
                 val boundVar2: Symbol,
                 val qIQualifiers: List<BoolExpr>,
                 val qEQualifiers: List<QEQualifier>,
                 val qIIQualifiers: List<BoolExpr>,
                 val qEEQualifiers: List<QEEQualifier>,
                 val qLenQualifiers: List<BoolExpr>)


    class QEQualifier(val qualifier: BoolExpr, val arrays: List<Symbol>)
    class QEEQualifier(val qualifier: BoolExpr, val arrays1: List<Symbol>, val arrays2: List<Symbol>)


    class KappaInfo(val arguments: List<Pair<Symbol, Sort>>,
                    val lenSymbols: List<Symbol>,
                    val qQualifiers: List<BoolExpr>)

    /**
     * The set of kappas appearing in the assumptions or conclusion
     */
    val mentionedKappas: Set<String>

    /**
     * The set of mus appearing in the assumptions or conclusion
     */
    val mentionedMus: Set<String>

    /**
     * The top-level kappa application appearing in the conclusion (if any)
     */
    val rhsKappa: String?

    /**
     * The top-level mu application appearing in the conclusion (if any)
     */
    val rhsMu: String?

    /**
     * The set of Z3 qualifiers corresponding to each Kappa
     */
    private val kappaQualifiers: Map<String, KappaInfo>

    /**
     * The set of Z3 qualifiers corresponding to each Mu
     */
    private val muQualifiers: Map<String, MuInfo>

    /**
     * A translation of the declarationMap parameter but as a FuncDecl
     * instead of an UninterpretedFunctionType
     */
    private val z3DeclarationMap: Map<String, FuncDecl<out Sort>>

    /**
     * The context in which all expressions will be created
     */
    private val ctx: Context = Context(mapOf("timeout" to timeout.toString()))

    /**
     * The solver which will be used in that context
     */
    private val solver: Solver = ctx.mkSolver("AUFLIA")


    override fun toString(): String {
        return solver.toString()
    }


    init {
        // For each kappa and mu, we created its type as if it were an uninterpreted function
        val kappaTypes = kappas.mapValues { (_, kappa) -> UninterpretedFunctionType(kappa.arguments.map { it.HMType }, boolType) }
        val muTypes = mus.mapValues { (_, mu) -> UninterpretedFunctionType(mu.arguments.map { it.HMType }, boolType) }

        // We create a function declaration for each uninterpreted function type
        z3DeclarationMap = (declarationMap + kappaTypes + muTypes).map { (k, v) -> k to v.toFuncDecl(k, ctx) }.toMap()


        // We find all the kappa and mu applications in the goal and fill in the
        // namedXXXX and rhsXXXX fields
        val kappaNames = kappas.keys
        val muNames = mus.keys

        val appliedPredicates = findAppliedPredicates(assumptions + conclusion)
        mentionedKappas = appliedPredicates.intersect(kappaNames)
        mentionedMus = appliedPredicates.intersect(muNames)
        rhsKappa = if (conclusion is PredicateApplication && conclusion.name in kappaNames) conclusion.name else null
        rhsMu = if (conclusion is PredicateApplication && conclusion.name in muNames) conclusion.name else null


        // For each kappa received, we convert its QSet into a Z3 Assertion in order to obtain
        // a map from kappa names to KappaInfo structures (instead of Kappa structures)
        kappaQualifiers = kappas.mapValues { (_, kappa) ->
            val z3Arguments = kappa.arguments.map { ctx.mkSymbol(it.varName) to it.HMType.toZ3Sort(ctx) }
            val currentKappaIndices = kappaIndices[kappa.name]!!
            val lenSymbols = z3Arguments
                    .filterIndexed { index, _ -> index in currentKappaIndices }
                    .map { (symbol, _) -> symbol }
            val symbolMap = kappa.arguments.zip(z3Arguments).map { (kappaArg, z3Arg) -> kappaArg.varName to z3Arg.first }.toMap()
            val argumentsEnvironment = kappa.arguments.zip(z3Arguments).map { (kappaArg, z3Arg) -> kappaArg.varName to z3Arg.second }.toMap()

            val qQualifiers = kappa.qStar.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment) }

            KappaInfo(z3Arguments, lenSymbols, qQualifiers)
        }

        // The same as above, but for mus
        muQualifiers = mus.mapValues { (_, mu) ->

            val z3Arguments = mu.arguments.map { ctx.mkSymbol(it.varName) to it.HMType.toZ3Sort(ctx) }

            val currentMuIndices = muIndices[mu.name]!!

            val lenSymbols = z3Arguments
                    .filterIndexed { index, _ -> index in currentMuIndices }
                    .map { (name, _) -> name }

            val boundVar1Symbol = ctx.mkSymbol(mu.boundVar1)
            val boundVar2Symbol = ctx.mkSymbol(mu.boundVar2)

            val symbolMap = mu.arguments.zip(z3Arguments).map { (muArg, z3Arg) -> muArg.varName to z3Arg.first }.toMap() +
                    mapOf(mu.boundVar1 to boundVar1Symbol, mu.boundVar2 to boundVar2Symbol)

            val argumentsEnvironment = mu.arguments.zip(z3Arguments).map { (muArg, z3Arg) -> muArg.varName to z3Arg.second }.toMap() +
                    mapOf(mu.boundVar1 to ctx.mkIntSort(), mu.boundVar2 to ctx.mkIntSort())


            val qIQualifiers = mu.qI.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment) }

            val qEQualifiers = mu.qE.map {
                val qualifier = it.qualifier.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment)
                val arrayAccesses = it.arrayNames.map { symbolMap[it]!! }
                QEQualifier(qualifier, arrayAccesses)
            }

            val qIIQualifiers = mu.qII.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment) }

            val qEEQualifiers = mu.qEE.map {
                val qualifier = it.qualifier.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment)
                val arrayAccesses1 = it.arrayNames1.map { symbolMap[it]!! }
                val arrayAccesses2 = it.arrayNames2.map { symbolMap[it]!! }
                QEEQualifier(qualifier, arrayAccesses1, arrayAccesses2)
            }

            val qLenQualifiers = mu.qLen.map { it.toZ3BoolExpr(ctx, symbolMap, z3DeclarationMap, argumentsEnvironment) }

            MuInfo(
                    arguments = z3Arguments,
                    lenSymbols = lenSymbols,
                    boundVar1 = boundVar1Symbol,
                    boundVar2 = boundVar2Symbol,
                    qIQualifiers = qIQualifiers,
                    qEQualifiers = qEQualifiers,
                    qIIQualifiers = qIIQualifiers,
                    qEEQualifiers = qEEQualifiers,
                    qLenQualifiers = qLenQualifiers
            )
        }

        val z3Symbols = environment.mapValues { (k, _) -> ctx.mkSymbol(k) }
        val z3Environment = environment.map { (k, t) -> k to t.toZ3Sort(ctx) }.toMap()
        val z3Assumptions = assumptions.map { it.toZ3BoolExpr(ctx, z3Symbols, z3DeclarationMap, z3Environment) }
        val z3Conclusion = conclusion.toZ3BoolExpr(ctx, z3Symbols, z3DeclarationMap, z3Environment)

        z3Assumptions.forEach { solver.add(it) }
        solver.add(ctx.mkNot(z3Conclusion))
    }


    /**
     * It checks whether the `this` goal satisfies the Solution given as parameter, applying
     * iterative weakening when necessary. In this case, it mutates the solution given as parameters
     * so the goal becomes satisfied.
     *
     * Only the kappa or mu in the right-hand side of the formula can be weakened.
     *
     * @param solution Solution to check. It will be mutated in order to make the goal valid (if possible)
     * @param debugMessages Optional string buffer used as logger
     *
     * @return A GoalSolverResult object that specifies whether the goal has been satisfied without weakening
     *          (Correct), if it has not been satisfied and cannot be weakened (CannotWeaken), or if it has been
     *          made valid by weakening a kappa or mu (KappaWeakened, MuWeakened).
     *
     */
    fun check(solution: Solution, debugMessages: StringBuffer? = null): GoalSolveResult {
        debugMessages?.append("*Checking goal* `$name`: $description\n\n")

        // The assumptions and the negation of the conclusion in the goal are already
        // in the solver. We save them

        solver.push()

        // Now we add the definitions of those mus and kappas not occurring
        // in the right hand side. These are held constant for all the weakening process

        val lhsKappas = if (rhsKappa == null) mentionedKappas else (mentionedKappas - rhsKappa)
        val lhsMus = if (rhsMu == null) mentionedMus else (mentionedMus - rhsMu)

        lhsKappas.forEach {
            val z3Equivalence = kappaDefinitionToZ3(it, solution)
            solver.add(z3Equivalence)
        }

        lhsMus.forEach {
            val z3Equivalence = muDefinitionToZ3(it, solution)
            solver.add(z3Equivalence)
        }

        // We save the current state (assumptions + ¬goal + kappas and mus only in the lhs)
        solver.push()

        // Now we add the definitions the mu or the kappa at the right hand side

        rhsKappa?.let {
            val z3Equivalence = kappaDefinitionToZ3(it, solution)
            solver.add(z3Equivalence)
        }
        rhsMu?.let {
            val z3Equivalence = muDefinitionToZ3(it, solution)
            solver.add(z3Equivalence)
        }

        // And we are done! We check the validity of the formula
        val wholeFormulaVerdict: Status = checkStatus(debugMessages)

        // We discard the latest definition, so
        // we restore the state to (assumptions + ¬goal + kappas and mus only in the lhs)
        solver.pop()

        val result = when {

        // If the formula is correct, we are done
            wholeFormulaVerdict == Status.UNSATISFIABLE ->
                Correct()

        // If it is not, but we have a kappa in the conclusion of
        // the formula, we weaken its definition
            rhsKappa != null -> {
                weakenKappa(solution, rhsKappa, debugMessages)
                KappaWeakened(rhsKappa)
            }

        // Now the case in which we have a mu in the conclusion of
        // the formula
            rhsMu != null -> {
                weakenMu(solution, rhsMu, debugMessages)
                MuWeakened(rhsMu)
            }

        // If there is neither kappa nor mu in the conclusion, we cannot weaken.
            else ->
                CannotWeaken()
        }

        // After possibly weakening, we discard the definitions of the mus and kappas in
        // the left-hand side, so
        // we restore the state to (assumptions + ¬goal)
        solver.pop()

        return result
    }

    private fun checkStatus(debugMessages: StringBuffer?): Status {
        FormulaCounter.increment()
        System.out.flush()
        debugMessages?.let {
            it.append("```lisp\n")
            it.append(solver)
            it.append("```\n\n")
        }
        try {
            val verdict: Status = solver.check()
            print(".")
            debugMessages?.append("*Result:* $verdict\n\n")
            return verdict
        } catch (e: Z3Exception) {
            print("T")
            debugMessages?.append("*Timeout*\n\n")
            return Status.UNKNOWN
        }
    }

    /**
     * Assuming that the current goal is not valid by the given solution due to the
     * kappa whose name is pased to the second parameter, it mutates the solution
     * (in particular, the binding corressponding to rhsKappa), so the formula becomes
     * valid.
     */
    private fun weakenKappa(solution: Solution, rhsKappa: String, debugMessages: StringBuffer?) {
        debugMessages?.append("### Weakening `$rhsKappa`\n\n")

        // We get the qualifiers corresponding to the current solution
        val qualifiersInSolution: Set<Int> = solution.kappas[rhsKappa]!!.toSet()


        // For each qualifier, we change the definition of kappa so it *only* includes
        // this qualifier. If the goal is valid, we include in the `acceptedQualifers` set.

        val acceptedQualifiers = qualifiersInSolution.filter {
            solution.kappas[rhsKappa] = mutableSetOf(it)
            val z3Equivalence = kappaDefinitionToZ3(rhsKappa, solution)
            debugMessages?.append("  Trying: `${z3Equivalence.sExpr}`\n\n")
            solver.push()
            solver.add(z3Equivalence)
            val status = checkStatus(debugMessages)
            solver.pop()
            status == Status.UNSATISFIABLE
        }

        // Now the solution is modified with only the qualifiers that have made the goal valid
        solution.kappas[rhsKappa] = acceptedQualifiers.toMutableSet()
    }

    /**
     * Assuming that the current goal is not valid by the given solution due to the
     * mu whose name is pased to the second parameter, it mutates the solution
     * (in particular, the binding corressponding to rhsMu), so the formula becomes
     * valid.
     */
    private fun weakenMu(solution: Solution, rhsMu: String, debugMessages: StringBuffer?) {
        debugMessages?.append("### Weakening `$rhsMu`\n\n")

        val muSolutionToWeaken = solution.mus[rhsMu]!!

        debugMessages?.append("#### Clasifying single refinements\n\n")
        // For each singly-quantified refinement, we build a solution containing only that
        // refinement and check whether the goal is valid. If it is, the refinement goes
        // to the `acceptedSingleRefinements` list. Otherwise, it comes into `rejectedSingleRefinements`.
        val (acceptedSingleRefinements, rejectedSingleRefinements) =
                classifyRefinements(solution, rhsMu, muSolutionToWeaken.singleRefinements, { MuSolution(it, listOf(), setOf()) }, debugMessages)


        val numberOfQI = muQualifiers[rhsMu]!!.qIQualifiers.size

        // For each single refinement, we strengthen its antecedent. As a result we can get many refinements
        // whose left-hand sides are not comparable. We add them all to the newSigleRefinements
        val newSingleRefinements = rejectedSingleRefinements.flatMap {
            debugMessages?.append("#### Weakening rejected refinement\n\n")
            weakenRefinement(it, numberOfQI, solution, rhsMu, { MuSolution(it, listOf(), setOf()) }, debugMessages)
        }


        // Now the weakened refinements are added to the solution
        acceptedSingleRefinements += if (pruneTrivialLHS) {
            newSingleRefinements.filterNot { isTrivialSingleRefinement(rhsMu, it, debugMessages) }
        } else {
            newSingleRefinements
        }

        // We do the same with the doubly-quantified refinements
        debugMessages?.append("#### Clasifying double refinements\n\n")
        val (acceptedDoubleRefinements, rejectedDoubleRefinements) =
                classifyRefinements(solution, rhsMu, muSolutionToWeaken.doubleRefinements, { MuSolution(listOf(), it, setOf()) }, debugMessages)

        val numberOfQII = muQualifiers[rhsMu]!!.qIIQualifiers.size

        val newDoubleRefinements = rejectedDoubleRefinements.flatMap {
            debugMessages?.append("#### Weakening rejected refinement\n\n")
            weakenRefinement(it, numberOfQII, solution, rhsMu, { MuSolution(listOf(), it, setOf()) }, debugMessages)
        }

        acceptedDoubleRefinements += if (pruneTrivialLHS) {
            newDoubleRefinements.filterNot { isTrivialDoubleRefinement(rhsMu, it, debugMessages) }
        } else {
            newDoubleRefinements
        }

        // Now we try each length refinement, and take only those which make the formula valid
        // It is similar to the kappa
        val acceptedLenRefinements = muSolutionToWeaken.qLen.filter {
            solution.mus[rhsMu] = MuSolution(listOf(), listOf(), setOf(it))
            val z3Equivalence = muDefinitionToZ3(rhsMu, solution)
            debugMessages?.append("  Weakening length refinement:\n\n ```lisp\n${z3Equivalence.sExpr}\n```\n\n")
            solver.push()
            solver.add(z3Equivalence)
            val status = checkStatus(debugMessages)
            debugMessages?.append("    Result: $status\n")
            solver.pop()
            status == Status.UNSATISFIABLE
        }

        solution.mus[rhsMu] = MuSolution(acceptedSingleRefinements, acceptedDoubleRefinements, acceptedLenRefinements.toSet())
    }

    /**
     * Given a list of refinements, it traverses the list so that a solution is
     * built with each refinement and the goal is checked under that solution.
     *
     * It returns two lists, one with those refinements which make the formula valid,
     * and another with those who did not.
     *
     */
    private fun classifyRefinements(solution: Solution,
                                    muVar: String,
                                    refinements: List<Refinement>,
                                    builder: (List<Refinement>) -> MuSolution,
                                    debugMessages: StringBuffer?): Pair<MutableList<Refinement>, MutableList<Refinement>> {
        val accepted = mutableListOf<Refinement>()
        val rejected = mutableListOf<Refinement>()

        refinements.forEach {
            // We update the solution so it only contains the given refinement
            solution.mus[muVar] = builder(listOf(it))
            val z3Equivalence = muDefinitionToZ3(muVar, solution)

            // Is the formula still valid?
            solver.push()
            solver.add(z3Equivalence)
            val status = checkStatus(debugMessages)
            solver.pop()

            // Depending on whether it is valid or not, the refinement goes to the set `accepted` or `rejected`.
            if (status == Status.UNSATISFIABLE) {
                accepted += it
            } else {
                rejected += it
            }
        }

        return Pair(accepted, rejected)
    }


    /**
     * Given a singly or doubly-quantified refinement, it tries to strenghten its left-hand
     * side so the goals becomes valid. If there are many uncomparable strenghtenings that make
     * the formula valid, then a refinement is returned for each one of them
     */
    private fun weakenRefinement(refinement: Refinement,
                                 numberOfLHSQualifiers: Int,
                                 solution: Solution,
                                 rhsMu: String,
                                 builder: (List<Refinement>) -> MuSolution,
                                 debugMessages: StringBuffer?): List<Refinement> {


        val result = mutableListOf<Refinement>()

        // First we try the formula under the strongest possible refinement
        val strongestLhs = (0 until numberOfLHSQualifiers).toSet()
        solution.mus[rhsMu] = builder(listOf(Refinement(strongestLhs, refinement.rhs)))
        solver.push()
        val z3Definition = muDefinitionToZ3(rhsMu, solution)
        solver.add(z3Definition)
        debugMessages?.append("Trying strongest mapping\n\n")
        val status = checkStatus(debugMessages)
        solver.pop()

        // If it does not hold, then we return the empty list, so that refinement is
        // discarded
        if (status != Status.UNSATISFIABLE) {
            return listOf()
        }

        // Assuming that the formula holds, now we iterate over all the supersets of the
        // current left-hand side of the refinement, up to the strongest refinement possible.
        var (currentLhs, setIterator) = iterateSupersetsOf(refinement.lhs, (0 until numberOfLHSQualifiers).toSet())

        var superSetNumber = 0

        while (currentLhs != null) {
            // We build a refinement with the current superset and update
            // the solution
            val currentRefinement = Refinement(currentLhs.toSet(), refinement.rhs)
            solution.mus[rhsMu] = builder(listOf(currentRefinement))

            // Is the goal valid?
            solver.push()
            val updatedDefinition = muDefinitionToZ3(rhsMu, solution)
            solver.add(updatedDefinition)
            debugMessages?.append("Trying new superset.\n\n")
            val updatedStatus = checkStatus(debugMessages)
            solver.pop()


            currentLhs = if (updatedStatus == Status.UNSATISFIABLE) {
                // If the goal is valid, then we add the current refinement to the list
                // of valid ones (list that will eventually be returned).
                result.add(currentRefinement)

                // We go to the next subset, but we no longer consider supersets of the
                // current one
                setIterator.next(false)
            } else {
                // We go to the next subset, but starting from the supersets of the
                // current one
                setIterator.next(true)
            }

            superSetNumber += 1
        }
        return result
    }

    /**
     * It generates a Z3 definition of the given kappa, according to the given solution.
     *
     * @param kappaName name of the kappa from which the definition will be generated
     * @param solution Solution under which the kappa will be generated
     *
     * @return A Z3 formula with the definition of the given kappa
     */
    private fun kappaDefinitionToZ3(kappaName: String, solution: Solution): Quantifier {
        val kappa = kappaQualifiers[kappaName]!!
        val qualifiers = kappa.qQualifiers

        // For each variable denoting the length of the array, we add the
        // constraint that the length must be positive.
        val positiveLenAssertions = kappa.lenSymbols.map {
            ctx.mkGe(ctx.mkIntConst(it), ctx.mkInt(0))
        }


        return ctx.mkForall(kappa.arguments.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray(),
                ctx.mkIff(
                        ctx.mkApp(
                                z3DeclarationMap[kappaName],
                                *kappa.arguments.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray()
                        ) as BoolExpr,
                        ctx.mkAnd(*positiveLenAssertions.toTypedArray(), *solution.kappas[kappaName]!!.map { n -> qualifiers[n] }.toTypedArray())
                ),
                0, null, null, null, null)
    }

    /**
     * It generates a Z3 definition of the given mu, according to the given solution.
     *
     * @param muName name of the mu from which the definition will be generated
     * @param solution Solution under which the mu will be generated
     *
     * @return A Z3 formula with the definition of the given mu
     */
    private fun muDefinitionToZ3(muName: String, solution: Solution): Quantifier {
        val mu = muQualifiers[muName]!!

        // For each variable denoting the length of the array, we add the
        // constraint that the length must be positive.
        val positiveLenAssertions = mu.lenSymbols.map {
            ctx.mkGe(ctx.mkIntConst(it), ctx.mkInt(0))
        }

        // We will have a formula of the form
        //
        //  mu(x1, x2, ...) <-> refinement1 /\ refinement2 /\ ...

        // We start from the left-hand side of the <->
        val equivLhs = ctx.mkApp(z3DeclarationMap[muName], *mu.arguments.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray())

        // Now with the right hand side. First, the singly quantified refinements
        val singleAssertions = solution.mus[muName]!!.singleRefinements.map {

            val boundVarAsConst = ctx.mkIntConst(mu.boundVar1)
            // The formula has the shape:
            // forall boundvar. elementsOfQI --> elementsOfQE
            ctx.mkForall(arrayOf(boundVarAsConst),
                    ctx.mkImplies(
                            // Left hand side: elements of QI and index range conditions
                            buildSingleLHS(boundVarAsConst, it, muName),

                            // Right hand side: elements of QE
                            ctx.mkAnd(
                                    *it.rhs.map { muQualifiers[muName]!!.qEQualifiers[it].qualifier }.toTypedArray()
                            )
                    ), 0, null, null, null, null
            )
        }

        // Similarly as above, but with the doubly quantified refinements
        val doubleAssertions = solution.mus[muName]!!.doubleRefinements.map {

            val boundVar1AsConst = ctx.mkIntConst(mu.boundVar1)
            val boundVar2AsConst = ctx.mkIntConst(mu.boundVar2)
            // The formula has the shape:
            // forall boundvar1, boundvar2. elementsOfQII --> elementsOfQEE
            ctx.mkForall(arrayOf(boundVar1AsConst, boundVar2AsConst),
                    ctx.mkImplies(
                            // Left hand side: elements of QII and index range conditions
                            buildDoubleLHS(boundVar1AsConst, boundVar2AsConst, it, muName),
                            // Right hand side: elements of QEE and index range conditions
                            ctx.mkAnd(
                                    *it.rhs.map { muQualifiers[muName]!!.qEEQualifiers[it].qualifier }.toTypedArray()
                            )
                    ), 0, null, null, null, null
            )
        }


        // And finally, with the assertions regarding the length of the array
        val lenAssertions = solution.mus[muName]!!.qLen.map {
            muQualifiers[muName]!!.qLenQualifiers[it]
        }

        val equivRhs = ctx.mkAnd(
                *positiveLenAssertions.toTypedArray(),
                *singleAssertions.toTypedArray(),
                *doubleAssertions.toTypedArray(),
                *lenAssertions.toTypedArray()
        )

        return ctx.mkForall(
                mu.arguments.map { (symbol, sort) -> ctx.mkConst(symbol, sort) }.toTypedArray(),
                ctx.mkIff(equivLhs as BoolExpr, equivRhs),
                0, null, null, null, null
        )
    }

    private fun buildSingleLHS(boundVarAsConst: IntExpr?, it: Refinement, muName: String): BoolExpr {
        return ctx.mkAnd(
                // The constraint 0 <= boundvar
                ctx.mkGe(boundVarAsConst, ctx.mkInt(0)),

                // For each element of QE appearing in the solution, we generate
                // the constraint boundvar < array_len
                *it.rhs.flatMap {
                    muQualifiers[muName]!!.qEQualifiers[it].arrays.map {
                        ctx.mkLt(boundVarAsConst, ctx.mkIntConst(it))
                    }
                }.toTypedArray(),

                // And, finally, the elements of QI itself
                *it.lhs.map { muQualifiers[muName]!!.qIQualifiers[it] }.toTypedArray()
        )
    }

    private fun buildDoubleLHS(boundVar1AsConst: IntExpr, boundVar2AsConst: IntExpr, refinement: Refinement, muName: String): BoolExpr {
        return ctx.mkAnd(
                // 0 <= boundvar1, 0 <= boundvar2
                ctx.mkGe(boundVar1AsConst, ctx.mkInt(0)), ctx.mkGe(boundVar2AsConst, ctx.mkInt(0)),
                // For every array x indexed by boundvar1 in QEE, boundvar1 < array_len
                *refinement.rhs.flatMap {
                    muQualifiers[muName]!!.qEEQualifiers[it].arrays1.map {
                        ctx.mkLt(boundVar1AsConst, ctx.mkIntConst(it))
                    }
                }.toTypedArray(),
                // For every array x indexed by boundvar2 in QEE, boundvar2 <= array_len
                *refinement.rhs.flatMap {
                    muQualifiers[muName]!!.qEEQualifiers[it].arrays2.map {
                        ctx.mkLt(boundVar2AsConst, ctx.mkIntConst(it))
                    }
                }.toTypedArray(),
                // and the elements of QII
                *refinement.lhs.map { muQualifiers[muName]!!.qIIQualifiers[it] }.toTypedArray()
        )
    }

    private fun isTrivialSingleRefinement(muName: String, refinement: Refinement, debugMessages: StringBuffer?): Boolean {
        // We have to build a formula of the form
        // forall i. (lhs_1 /\ lhs_2 /\ ... /\ lhs_n == false)
        //
        // equivalently: forall i. ¬(lhs_1 /\ lhs_2 /\ ... /\ lhs_n)

        val muInfo = muQualifiers[muName]!!

        val boundVar1AsConst = ctx.mkIntConst(muInfo.boundVar1)

        val allLhs = ctx.mkForall(arrayOf(boundVar1AsConst),
                ctx.mkNot(
                        buildSingleLHS(boundVar1AsConst, refinement, muName)
                ), 0, null, null, null, null)


        solver.push()
        solver.add(ctx.mkNot(allLhs))
        debugMessages?.append("Checking whether LHS is trivial\n\n")
        val status = checkStatus(debugMessages)
        solver.pop()

        return status == Status.UNSATISFIABLE
    }


    private fun isTrivialDoubleRefinement(muName: String, refinement: Refinement, debugMessages: StringBuffer?): Boolean {
        // We have to build a formula of the form
        // forall i, j. (lhs_1 /\ lhs_2 /\ ... /\ lhs_n == false)
        //
        // equivalently: forall i, j. ¬(lhs_1 /\ lhs_2 /\ ... /\ lhs_n)

        val muInfo = muQualifiers[muName]!!

        val boundVar1AsConst = ctx.mkIntConst(muInfo.boundVar1)
        val boundVar2AsConst = ctx.mkIntConst(muInfo.boundVar2)

        val allLhs = ctx.mkForall(arrayOf(boundVar1AsConst, boundVar2AsConst),
                ctx.mkNot(
                        buildDoubleLHS(boundVar1AsConst, boundVar2AsConst, refinement, muName)
                ), 0, null, null, null, null)


        solver.push()
        solver.add(ctx.mkNot(allLhs))
        debugMessages?.append("Checking whether LHS is trivial\n\n")
        val status = checkStatus(debugMessages)
        solver.pop()

        return status == Status.UNSATISFIABLE
    }


}


abstract class GoalSolveResult
class Correct : GoalSolveResult()
class CannotWeaken : GoalSolveResult()
data class KappaWeakened(val varName: String) : GoalSolveResult()
data class MuWeakened(val varName: String) : GoalSolveResult()


