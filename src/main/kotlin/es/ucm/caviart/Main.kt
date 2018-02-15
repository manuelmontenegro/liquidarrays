package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.goal.Goal
import es.ucm.caviart.goal.generateForDefinition
import es.ucm.caviart.iterativeweakening.fromKappaDeclaration
import es.ucm.caviart.iterativeweakening.fromMuDeclaration
import es.ucm.caviart.qstar.instantiateVerificationUnit
import es.ucm.caviart.typecheck.TypeCheckerException
import es.ucm.caviart.typecheck.checkVerificationUnit
import es.ucm.caviart.typecheck.initialEnvironment
import es.ucm.caviart.typecheck.qualifyVeriticationUnit
import es.ucm.caviart.utils.asDarkGray
import es.ucm.caviart.utils.asGreen
import es.ucm.caviart.utils.asRed
import java.io.FileReader
import java.io.IOException
import kotlin.math.min

private const val WIDTH = 40

fun<T> runPhase(description: String, action: () -> T): T {
    val initCad = " - $description"
    print(initCad.substring(0, min(initCad.length, WIDTH)))
    System.out.flush()

    try {
        val start = System.currentTimeMillis()
        val result = action()
        val stop = System.currentTimeMillis()
        print(" ".repeat(WIDTH - initCad.length))
        println("ok".asGreen() + " " + "[${stop - start}ms]".asDarkGray())
        return result
    } catch (e: Exception) {
        print(" ".repeat(WIDTH - initCad.length))
        println("ERROR".asRed())
        throw e
    }
}

fun Goal.isTrivial() =
    this.conclusion is True || this.assumptions.any { it is False }

fun Goal.prune() =
        this.copy(assumptions = this.assumptions.filterNot { it is True })


fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Error:".asRed() + " no input file given")
        System.exit(1)
    }

    val filename = args[0]

    try {
        val reader = FileReader(filename)

        val tokens = runPhase("Running lexer") {
            readTokens(reader)
        }
        val sexps = runPhase("Running S-expression parser") {
            getSExps(tokens)
        }
        val verificationUnit = runPhase("Running parser") {
            parseVerificationUnit(sexps)
        }
        val newEnvironment = runPhase("Running type checker") {
            checkVerificationUnit(verificationUnit, initialEnvironment)
        }

        val numberKappas = verificationUnit.kappaDeclarations.size
        val numberMus = verificationUnit.muDeclarations.size

        val verificationUnitDecorated = runPhase("Generating qualified types") {
            qualifyVeriticationUnit(verificationUnit)
        }

        val newKappas = verificationUnitDecorated.kappaDeclarations.size - numberKappas
        println("     Number of template variables (kappa): ${verificationUnitDecorated.kappaDeclarations.size} ($newKappas newly generated)")
        val newMus = verificationUnitDecorated.muDeclarations.size - numberMus
        println("     Number of template variables (mu): ${verificationUnitDecorated.muDeclarations.size} ($newMus newly generated)")

        val verificationUnitDecorated2 = runPhase("Generating qualifier instances") {
            instantiateVerificationUnit(verificationUnitDecorated)
        }


        val generatedGoals: List<Goal> = runPhase("Generating goals") {
            val result = mutableListOf<Goal>()
            verificationUnitDecorated2.definitions.map {
                generateForDefinition(it, listOf(), newEnvironment, result)
            }
            result
        }

        println("     Number of goals: ${generatedGoals.size}")
        val prunedGoals = generatedGoals.filterNot { it.isTrivial() }.map { it.prune() }
        println("     Non-trivial: ${prunedGoals.size}")


        val kappas = verificationUnitDecorated2.kappaDeclarations.map { fromKappaDeclaration(it) }
        val mus = verificationUnitDecorated2.muDeclarations.map { fromMuDeclaration(it) }

        println(kappas)
        println(mus)

        val sexpAgain = verificationUnitDecorated2.toSExpList()
        sexpAgain.forEach {
            println(it.prettyPrint(100))
        }


    } catch (e: IOException) {
        println("Error:".asRed() + " " + e.message)
    } catch (e: TypeCheckerException) {
        println(("Type checker error: ".asRed()) + " " + e.message)
    }


}