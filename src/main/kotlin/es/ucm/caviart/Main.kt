package es.ucm.caviart

import es.ucm.caviart.ast.getSExps
import es.ucm.caviart.ast.parseVerificationUnit
import es.ucm.caviart.ast.readTokens
import es.ucm.caviart.ast.toSExpList
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
        runPhase("Running type checker") {
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

        val sexpAgain = verificationUnitDecorated.toSExpList()
        sexpAgain.forEach {
            println(it.prettyPrint(100))
        }


    } catch (e: IOException) {
        println("Error:".asRed() + " " + e.message)
    } catch (e: TypeCheckerException) {
        println(("Type checker error: ".asRed()) + " " + e.message)
    }


}