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

package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.goal.Goal
import es.ucm.caviart.goal.generateForDefinition
import es.ucm.caviart.iterativeweakening.*
import es.ucm.caviart.iterativeweakening.z3.*
import es.ucm.caviart.qstar.instantiateVerificationUnit
import es.ucm.caviart.utils.asDarkGray
import es.ucm.caviart.utils.asGreen
import es.ucm.caviart.utils.asRed
import kotlin.math.min
import com.xenomachina.argparser.ArgParser
import com.xenomachina.argparser.DefaultHelpFormatter
import com.xenomachina.argparser.default
import com.xenomachina.argparser.mainBody
import es.ucm.caviart.typecheck.*
import es.ucm.caviart.utils.FormulaCounter
import java.io.*


/**
 * Class for representing command line arguments
 */
class CLIArguments(parser: ArgParser) {
    val inputFile: File by
    parser.positional("INPUTFILE", help = "input CLIR file") { File(this) }

    val onlyExpand: String? by
    parser.storing("-e", "--expand-only", help = "expand Q-Sets and produce a CLIR output without inferring liquid types", argName = "FILE_CLIR").default { null }

    val flushGoals: String? by
    parser.storing("-g", "--goals", help = "export goals to a given file", argName = "FILE_MD").default { null }

    val trace: String? by
    parser.storing("-t", "--trace", help = "flush the steps of iterative weakening to a file", argName = "FILE_MD").default { null }

    val outputFile: String? by
    parser.storing("-o", "--output", help = "write output to file", argName = "FILE_MD").default { null }
}

/**
 * Main function
 */
fun main(args: Array<String>) = mainBody("liquidarrays") {

    // We set up the help formatter in case the user has specified the -h option
    val prologue =
            "It receives a verification unit in the CLIR format, decorates its " +
                    "function definitions with template variables, and applies the iterative " +
                    "weakening algorithm in order to infer the qualifiers assigned to each " +
                    "template variable"

    val epilogue =
            "Example: liquidarrays insert_sort.clir"
    val argParser = ArgParser(args, helpFormatter = DefaultHelpFormatter(prologue, epilogue))

    // Parse the command line arguments
    val parsedArgs = CLIArguments(argParser)

    try {
        val reader = FileReader(parsedArgs.inputFile)

        // Parse the input file
        val verificationUnit = runParser(reader)

        // Check whether the verification unit is well-typed according to HM-type
        // rules, and without taking Liquid Types into account. We obtain an environment
        // with the types of all the function definitions.
        val newEnvironment = runTypeChecker(verificationUnit)

        // Traverse all the function definitions in the verification unit in order to check
        // whether their input and output parameters have qualified types. In those cases when
        // there are no qualified types, a fresh template variable (kappa or mu) is generated and
        // assigned as a qualifier for that type.
        val verificationUnitTemplates = generateTemplates(verificationUnit, newEnvironment)

        // Traverse all the kappa and mu definitions. In those cases where they do not
        // have an specific Q-Set (namely, Q, QI, QE, QII, QEE, QLen) it generates one by
        // using the global Q*-Sets (respectively, Q*, QI*, QE*, QII*, QEE*, QLen*).
        val verificationUnitQStar = generateQStarSets(verificationUnitTemplates)

        // If we only wanted to generate the expanded CLIR, we write it into a file and stop here
        if (parsedArgs.onlyExpand != null) {
            writeVerificationUnit(parsedArgs.onlyExpand, verificationUnitQStar)
            println("Generated CLIR: ${parsedArgs.onlyExpand}")
            System.exit(0)
        }

        // Rename variables, so all the variables in the verification unit become unique, in order
        // to avoid name conflicts when generating goals later
        val verificationUnitRenamed = renameIdentifiers(verificationUnitQStar)

        // Generate the verification conditions
        val goals = generateGoals(verificationUnitRenamed, newEnvironment, parsedArgs.inputFile, parsedArgs.flushGoals)

        // Convert KappaDefinition and MuDefinition into Kappa and Mu instances, respectively
        // The latter contain information about array accesses in each qualifier. Those are needed when including
        // the 0 <= i < len(x) assumption in each refinement that accesses the array `x` in its right-hand side
        val (kappas, mus) = preprocessKappasAndMus(verificationUnitRenamed)

        // We convert each goal into its Z3 representation
        val generatedGoalsZ3: List<Z3Goal> = convertGoalsToZ3(goals, kappas, mus, newEnvironment)

        val kappasMap = kappas.map { it.name to it }.toMap()
        val musMap = mus.map { it.name to it }.toMap()

        // Finally, we find a solution by iterative weakening
        val solution: Solution = findSolution(generatedGoalsZ3, kappasMap, musMap, parsedArgs.trace)

        println("\nSolution found!".asGreen())

        // We look in the environment all the user-defined functions
        val userDefTypes = newEnvironment.programFunctions.filterKeys { !initialEnvironment.programFunctions.containsKey(it) }
        // Write the solution in the standard output or a file
        writeSolution(parsedArgs.outputFile, userDefTypes, solution, kappasMap, musMap)

    } catch (e: IOException) {
        println("Error:".asRed() + " " + e.message)
    } catch (e: TypeCheckerException) {
        println(("Type checker error: ".asRed()) + " " + e.message)
    } catch (e: CannotWeakenGoalException) {
        println("Solver error: ${e.message}")
    }
}


// Whenever the program executes a phase, it shows the name of
// the phase followed by 'ok' or 'error'. The constant WIDTH
// specifies the maximum number of characters of the phase name,
// so all the 'ok's become aligned
private const val WIDTH = 40

/**
 * It runs a given phase while measuring its time in milliseconds.
 * After its execution it shows 'ok' or 'error' depending on whether
 * the phase completes successfully or throws an exception.
 *
 * @param description Title describing the phase
 * @param action Function that will be called to execute the phase
 *
 * @return The value returned by `action`
 */
private fun <T> runPhase(description: String, action: () -> T): T {
    // Shows the description (possibly truncated to the maximum WIDTH
    val initCad = " - $description"
    print(initCad.substring(0, min(initCad.length, WIDTH)))
    System.out.flush()

    try {
        // Executes the action while measuring the time
        val start = System.currentTimeMillis()
        val result = action()
        val stop = System.currentTimeMillis()

        // Output blanks until the description fills the given WIDTH
        print(" ".repeat(WIDTH - initCad.length))
        // Shows running time
        println("ok".asGreen() + " " + "[${stop - start}ms]".asDarkGray())
        return result
    } catch (e: Exception) {
        print(" ".repeat(WIDTH - initCad.length))
        println("ERROR".asRed())
        throw e
    }
}

/**
 * It checks whether a goal is trivial (i.e. has true as a conclusion or
 * false in one of its assumptions)
 */
private fun Goal.isTrivial() =
        this.conclusion is True || this.assumptions.any { it is False }

/**
 * It removes the trivial assumptions from a goal
 */
private fun Goal.prune() =
        this.copy(assumptions = this.assumptions.filterNot { it is True })


/**
 * It parses the given file in order to obtain a verification unit
 */
private fun runParser(reader: FileReader): VerificationUnit =
        runPhase("Running parser") {
            val tokens = readTokens(reader)
            val sexps = getSExps(tokens)
            parseVerificationUnit(sexps)
        }

/**
 * It checks whether the verification unit is well-typed according to HM-type
 * rules, and without taking Liquid Types into account. It returns an environment
 * with the types of all the function definitions.
 */
private fun runTypeChecker(verificationUnit: VerificationUnit): GlobalEnvironment =
        runPhase("Running type checker") {
            checkVerificationUnit(verificationUnit, initialEnvironment)
        }

/**
 * It traverses all the function definitions in the verification unit in order to check
 * whether their input and output parameters have qualified types. In those cases when
 * there are no qualified types, a fresh template variable (kappa or mu) is generated and
 * assigned as a qualifier for that type.
 */
private fun generateTemplates(verificationUnit: VerificationUnit, environment: GlobalEnvironment): VerificationUnit {

    // We check the already declared kappa and mu variables, in order
    // to see later which ones are new
    val numberKappas = verificationUnit.kappaDeclarations.size
    val numberMus = verificationUnit.muDeclarations.size

    val verificationUnitDecorated = runPhase("Generating qualified types") {
        qualifyVerificationUnit(verificationUnit, environment)
    }

    val newKappas = verificationUnitDecorated.kappaDeclarations.size - numberKappas
    println("     Number of template variables (kappa): ${verificationUnitDecorated.kappaDeclarations.size} ($newKappas newly generated)")
    val newMus = verificationUnitDecorated.muDeclarations.size - numberMus
    println("     Number of template variables (mu): ${verificationUnitDecorated.muDeclarations.size} ($newMus newly generated)")

    return verificationUnitDecorated
}

/**
 * It traverses all the kappa and mu definitions. In those cases where they do not
 * have an specific Q-Set (namely, Q, QI, QE, QII, QEE, QLen) it generates one by
 * using the global Q*-Sets (respectively, Q*, QI*, QE*, QII*, QEE*, QLen*).
 */
private fun generateQStarSets(verificationUnit: VerificationUnit): VerificationUnit =
        runPhase("Generating qualifier instances") {
            instantiateVerificationUnit(verificationUnit)
        }

/**
 * It perforns a renaming of variables, so all the variables become unique, in order
 * to avoid name conflicts when generating goals later
 */
private fun renameIdentifiers(verificationUnit: VerificationUnit): VerificationUnit =
        runPhase("Renaming") {
            verificationUnit.applyRenaming()
        }

/**
 * It generates the verification conditions for all the functions in a given verification unit.
 * It also flushes all the verification conditions to a file, if specified by the user
 */
private fun generateGoals(verificationUnit: VerificationUnit, environment: GlobalEnvironment, inputFile: File, outputFileName: String?): List<Goal> {

    val generatedGoals: List<Goal> = runPhase("Generating goals") {
        val result = mutableListOf<Goal>()
        verificationUnit.definitions.map {
            generateForDefinition(it, listOf(), environment, result)
        }
        result
    }

    println("     Number of goals: ${generatedGoals.size}")

    if (outputFileName != null) {
        val outputFile = File(outputFileName)
        val outputWriter = FileWriter(outputFile)
        outputWriter.write("# Goals for `${inputFile.name}`\n\n")
        generatedGoals.forEach {
            outputWriter.write(it.prune().toString())
            outputWriter.write("\n")
            outputWriter.write("\n")
        }
        outputWriter.close()
        println("     Written to: ${outputFile.name}")
    }

    val prunedGoals = generatedGoals.filterNot { it.isTrivial() }.map { it.prune() }
    println("     Number of non-trivial goals: ${prunedGoals.size}")

    return prunedGoals
}


/**
 * It writes a verification unit (as an S-Expression) to a given file
 */
private fun writeVerificationUnit(fileName: String?, verificationUnit: VerificationUnit) {
    val output = FileWriter(fileName)
    verificationUnit.toSExpList().forEach {
        output.write(it.prettyPrint())
        output.write("\n")
    }
    output.close()
}


/**
 * It converts KappaDeclaration and MuDeclaration instances into Kappa and Mu instances, respectively.
 * The latter contain information about array accesses in each qualifier. Those are needed when including
 * the 0 <= i < len(x) assumption in each refinement that accesses the array `x` in its right-hand side
 */
private fun preprocessKappasAndMus(verificationUnitRenamed: VerificationUnit): Pair<List<Kappa>, List<Mu>> {
    return runPhase("Preparing kappas and mus") {
        Pair(
                verificationUnitRenamed.kappaDeclarations.map { fromKappaDeclaration(it) },
                verificationUnitRenamed.muDeclarations.map { fromMuDeclaration(it) }
        )
    }
}

/**
 * It converts the instaces of Goal class into a Z3 goal, including their corresponding kappas and mus.
 */
private fun convertGoalsToZ3(goals: List<Goal>, kappas: List<Kappa>, mus: List<Mu>, environment: GlobalEnvironment): List<Z3Goal> {
    return runPhase("Transforming goals into Z3") {
        val kappaIndices = kappas.map { it.name to getArrayParams(it.arguments) }.toMap()
        val muIndices = mus.map { it.name to getArrayParams(it.arguments) }.toMap()

        val goalsWithoutLen = goals.map { it.removeLen(kappaIndices, muIndices) }
        val kappasWithoutLen = kappas.map { it.removeLen(kappaIndices, muIndices) }
        val musWithoutLen = mus.map { it.removeLen(kappaIndices, muIndices) }

        val kappaLenIndices = kappasWithoutLen.map { (kappa, lenVars) ->
            kappa.name to lenVars.map { kappa.arguments.indexOf(it) }.toSet()
        }.toMap()

        val muLenIndices = musWithoutLen.map { (mu, lenVars) ->
            mu.name to lenVars.map { mu.arguments.indexOf(it) }.toSet()
        }.toMap()

        goalsWithoutLen.map {
            Z3Goal(
                    name = it.name,
                    description = it.description,
                    assumptions = it.assumptions,
                    conclusion = it.conclusion,
                    environment = it.environment,
                    kappas = kappasWithoutLen.map { (kappa, _) -> kappa.name to kappa }.toMap(),
                    mus = musWithoutLen.map { (mu, _) -> mu.name to mu }.toMap(),
                    kappaIndices = kappaLenIndices,
                    muIndices = muLenIndices,
                    declarationMap = environment.logicFunctions +
                            environment.logicPredicates.mapValues { (_, args) -> UninterpretedFunctionType(args, boolType) }
            )
        }
    }
}

/**
 * It runs the iterative weakening algorithm on a list of goals
 */
private fun findSolution(goals: List<Z3Goal>, kappas: Map<String, Kappa>, mus: Map<String, Mu>, traceFileName: String?): Solution {

    val buffer = if (traceFileName != null) StringBuffer() else null

    val result = runPhase("Running iterative weakening") {
        println("\n")
        val solution = buildStrongestSolution(kappas, mus)

        val goalsMap = goals.map { it.name to it }.toMap()
        val pending = goalsMap.keys.toMutableSet()
        var stepNumber = 1
        FormulaCounter.reset()

        while (!pending.isEmpty()) {
            buffer?.append("## Step $stepNumber\n\n")
            buffer?.append("Starting from solution:\n\n")
            buffer?.append(solution.toString(kappas, mus) + "\n\n")

            val goalId = pending.first()
            pending.remove(goalId)
            val goal = goalsMap[goalId]!!
            print("   - $goalId: ")
            val result = goal.check(solution, buffer)
            buffer?.append("\n\n")
            when (result) {
                is Correct -> {
                    println("valid".asGreen())
                }
                is CannotWeaken -> {
                    println("error".asRed())
                    throw CannotWeakenGoalException(goalId, solution.toString(kappas, mus))
                }
                is KappaWeakened -> {
                    val affected = goalsMap.filterValues { result.varName in it.mentionedKappas }.map { it.key }
                    pending.addAll(affected)
                    println("had to weaken ${result.varName}")
                }
                is MuWeakened -> {
                    val affected = goalsMap.filterValues { result.varName in it.mentionedMus }.map { it.key }
                    pending.addAll(affected)
                    println("had to weaken ${result.varName}")
                }
            }
            stepNumber += 1
        }

        print("\nFinished iterative weakening:\n $stepNumber steps, ${FormulaCounter.currentValue} formulas")
        solution
    }

    if (traceFileName != null) {
        val traceFile = File(traceFileName)
        val traceWriter = FileWriter(traceFile)
        traceWriter.write(buffer!!.toString())
        traceWriter.close()
        println("Trace written to: ${traceFile.name}")
    }

    return result
}

/**
 * It writes the given solution to a file, together with the types of the functions
 */
private fun writeSolution(outputFile: String?, environment: Map<String, FunctionalType>, solution: Solution, kappasMap: Map<String, Kappa>, musMap: Map<String, Mu>) {
    val outputWriter: PrintStream = if (outputFile != null) {
        PrintStream(FileOutputStream(outputFile))
    } else {
        System.out
    }
    outputWriter.println("## Function signatures\n")
    environment.forEach { name, ft ->
        outputWriter.println("### Function `$name`\n")
        outputWriter.println("Input parameters:\n")
        ft.input.forEach {
            outputWriter.println(" * `${it.varName}` of type `${it.type.toSExp()}`")
        }

        outputWriter.println("\nOutput parameters:\n")
        ft.input.forEach {
            outputWriter.println(" * `${it.varName}` of type `${it.type.toSExp()}`")
        }
        outputWriter.println("")
    }
    outputWriter.println("## Solution\n")
    outputWriter.println(solution.toString(kappasMap, musMap))
    if (outputFile != null) {
        println("Solution written to: ${File(outputFile).name}")
    }
}




class CannotWeakenGoalException(goalName: String, solution: String): RuntimeException("Cannot weaken goal $goalName under the following solution:\n$solution")