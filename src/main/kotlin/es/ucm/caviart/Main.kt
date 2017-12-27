package es.ucm.caviart

import es.ucm.caviart.utils.asDarkGray
import es.ucm.caviart.utils.asGreen
import es.ucm.caviart.utils.asRed
import java.io.FileReader
import java.io.IOException
import kotlin.math.min
import kotlin.system.measureTimeMillis

private const val WIDTH = 40

fun<T> runPhase(description: String, action: () -> T): T {
    val initCad = " - $description"
    print(initCad.substring(0, min(initCad.length, WIDTH)))
    System.out.flush()
    var result: T? = null
    val time = measureTimeMillis {
        result = action()
    }
    print(" ".repeat(WIDTH - initCad.length))
    println("ok".asGreen() + " " + "[${time}ms]".asDarkGray())
    return result!!
}

fun main(args: Array<String>) {
    if (args.size != 1) {
        println("Error:".asRed() + " no input file given")
        System.exit(1);
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
        println(sexps)

    } catch (e: IOException) {
        println("Error:".asRed() + " " + e.message)
    }


}