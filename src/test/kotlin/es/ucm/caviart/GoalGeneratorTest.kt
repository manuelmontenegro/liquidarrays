package es.ucm.caviart

import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class GoalGeneratorTest {
    val prog = """
    (verification-unit dummy1
     :kappa (kappa1 ((nu int)))
     :kappa (kappa2 ((nu int) (x int)))
     :kappa (kappa3 ((nu (list int)) (x int) (y (list int))))
     :kappa (kappa4 ((nu (list int)) (x int)))
     :kappa (kappa5 ((nu int) (x int) (y int)))
     :kappa (kappa6 ((nu int) (x int) (y int) (z int)))
     :kappa (kappa7 ((nu int) (x int) (y int) (z int) (other int)))
     :external (sub ((x (qual nu int true)) (y (qual nu int (@ <= nu x)))) ((res (qual nu int (@ = nu (@ - x y))))))
     :external (cons ((x (qual nu int true)) (xs (qual nu (list int) true))) ((res (qual nu (list int) (@ = nu (@@ cons x y))))))
    )
    (define f0 () ((res (qual nu int (@ kappa1 nu))))
        (declare (assertion (precd true) (postcd (@ = res (the int 4)))))
        (the int 4)
    )

    (define f1 ((x (qual nu int (@ >= nu (the int 0))))) ((res (qual nu int (@ = nu (@ + x (the int 1))))))
        (declare (assertion (precd (@ >= x (the int 0))) (postcd (@ = (@ + x (the int 1)) res))))
        (@ + x (the int 1))
    )

    (define f2 ((x (qual nu int (@ kappa1 nu)))) ((res (qual nu int (@ kappa2 nu x))))
        (declare (assertion (precd (@ > x (the int 0))) (postcd (@ >= res (the int 0)))))
        x
    )

    (define f3 ((x (qual nu int (@ kappa1 nu)))) ((res (qual nu int (@ kappa2 nu x))))
        (declare (assertion (precd (@ > x (the int 3))) (postcd (@ >= res (the int 0)))))
        (@ sub x (the int 3))
    )

    (define f4 ((x (qual nu int (@ kappa1 nu))) (y (qual nu (list int) (@ kappa4 nu x)))) ((res (qual nu (list int) (@ kappa3 nu x y))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (@ = res (@@ cons x y)))))
        (@@ cons x y)
    )

    (define f5 ((x (qual nu int (@ kappa1 nu))) (y (qual nu int (@ kappa2 nu x))) (z (qual nu int (@ kappa5 nu x y)))) ((res1 (qual nu int (@ kappa6 nu x y z))) (res2 (qual nu int (@ kappa7 nu x y z res1))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (and (@ = res1 (@ + x y)) (@ = res2 (@ - x y))))))
        (let ((k int)) (@ + x y) (tuple z k))
    )

    (define f6 ((x (qual nu int (@ kappa1 nu))) (y (qual nu int (@ kappa2 nu x))) (z (qual nu int (@ kappa5 nu x y)))) ((res (qual nu int (@ kappa6 nu x y z))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (@ = res (@ + x y)))))
        (let ((k int) (m int)) (@ f5 x (the int 1) z) m)
    )"""

    lateinit var parsedProg: VerificationUnit
    lateinit var globalEnvironment: GlobalEnvironment

    @Before fun parseAndReset() {
        FreshNameGenerator.resetGenerator()
        parsedProg = parseVerificationUnit(getSExps(prog))
        globalEnvironment = initialEnvironment.copy(logicPredicates = initialEnvironment.logicPredicates + parsedProg.kappaDeclarations.map {
            it.name to (listOf(it.nuVar.HMType) + it.parameters.map { it.HMType })
        }.toMap(), logicFunctions = initialEnvironment.logicFunctions + ("cons" to UninterpretedFunctionType(listOf(VarType("'a"), ConstrType("list", listOf(VarType("'a")))), ConstrType("list", listOf(VarType("'a"))))),
                programFunctions = initialEnvironment.programFunctions.toMutableMap())
        checkVerificationUnit(parsedProg, globalEnvironment)

    }

    @Test fun simpleConst() {
        val f0 = parsedProg.definitions.find { it.name == "f0" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f0, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: The qualified type of the result of f0 must imply its postcondition
Assuming:
  (@ kappa1 res)
Prove:
  (@ = res (the int 4))

GOAL: The type of (the int 4) must match the type of the result #1 of f0
Assuming:
  (@ = _NU_1 (the int 4))
Prove:
  (@ kappa1 _NU_1)
        """.trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test fun simpleSum() {
        val f1 = parsedProg.definitions.find { it.name == "f1" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f1, null, globalEnvironment, mapOf(), goals)

        val expectedGoals = """
        GOAL: Precondition of f1 must imply the qualifier of its parameter x
Assuming:
  (@ >= x (the int 0))
Prove:
  (@ >= x (the int 0))

GOAL: The qualified type of the result of f1 must imply its postcondition
Assuming:
  (@ >= x (the int 0))
  (@ = res (@ + x (the int 1)))
Prove:
  (@ = (@ + x (the int 1)) res)

GOAL: Precondition of parameter x_0 in call to +
Assuming:
  (@ >= x (the int 0))
  (@ = _NU_1 x)
Prove:
  true

GOAL: Precondition of parameter x_1 in call to +
Assuming:
  (@ >= x (the int 0))
  (@ = _NU_2 (the int 1))
Prove:
  true

GOAL: The type of (@ + x (the int 1)) must match the type of the result #1 of f1
Assuming:
  (@ >= x (the int 0))
  (@ = _NU_3 (@ + x (the int 1)))
Prove:
  (@ = _NU_3 (@ + x (the int 1)))""".trim()

        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test fun identity() {
        val f2 = parsedProg.definitions.find { it.name == "f2" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f2, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: Precondition of f2 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 0))
Prove:
  (@ kappa1 x)

GOAL: The qualified type of the result of f2 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 res x)
Prove:
  (@ >= res (the int 0))

GOAL: The type of x must match the type of the result #1 of f2
Assuming:
  (@ kappa1 x)
  (@ = _NU_1 x)
Prove:
  (@ kappa2 _NU_1 x)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test fun functionCall() {
        val f3 = parsedProg.definitions.find { it.name == "f3" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f3, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: Precondition of f3 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 3))
Prove:
  (@ kappa1 x)

GOAL: The qualified type of the result of f3 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 res x)
Prove:
  (@ >= res (the int 0))

GOAL: Precondition of parameter x in call to sub
Assuming:
  (@ kappa1 x)
  (@ = _NU_1 x)
Prove:
  true

GOAL: Precondition of parameter y in call to sub
Assuming:
  (@ kappa1 x)
  (@ = _NU_2 (the int 3))
Prove:
  (@ <= _NU_2 x)

GOAL: The type of (@ sub x (the int 3)) must match the type of the result #1 of f3
Assuming:
  (@ kappa1 x)
  (@ = _NU_3 (@ - x (the int 3)))
Prove:
  (@ kappa2 _NU_3 x)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test fun constructorCall() {
        val f4 = parsedProg.definitions.find { it.name == "f4" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f4, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: Precondition of f4 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL: Precondition of f4 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa4 y x)

GOAL: The qualified type of the result of f4 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ kappa3 res x y)
Prove:
  (@ = res (@@ cons x y))

GOAL: Precondition of parameter x in call to cons
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ = _NU_1 x)
Prove:
  true

GOAL: Precondition of parameter xs in call to cons
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ = _NU_2 y)
Prove:
  true

GOAL: The type of (@@ cons x y) must match the type of the result #1 of f4
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ = _NU_3 (@@ cons x y))
Prove:
  (@ kappa3 _NU_3 x y)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test fun letAndTuple() {
        val f5 = parsedProg.definitions.find { it.name == "f5" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f5, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: Precondition of f5 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL: Precondition of f5 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa2 y x)

GOAL: Precondition of f5 must imply the qualifier of its parameter z
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ > x (the int 4))
Prove:
  (@ kappa5 z x y)

GOAL: The qualified type of the result of f5 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 res1 x y z)
  (@ kappa7 res2 x y z res1)
Prove:
  (and (@ = res1 (@ + x y)) (@ = res2 (@ - x y)))

GOAL: Precondition of parameter x_0 in call to +
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_1 x)
Prove:
  true

GOAL: Precondition of parameter x_1 in call to +
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_2 y)
Prove:
  true

GOAL: The type of (tuple z k) must match the type of the result #1 of f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = k (@ + x y))
  (@ = _NU_4 z)
Prove:
  (@ kappa6 _NU_4 x y z)

GOAL: The type of (tuple z k) must match the type of the result #2 of f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = k (@ + x y))
  (@ = _NU_4 z)
  (@ = _NU_5 k)
Prove:
  (@ kappa7 _NU_5 x y z _NU_4)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test fun letMultiple() {
        val f6 = parsedProg.definitions.find { it.name == "f6" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f6, null, globalEnvironment, mapOf(), goals)
        val expectedGoals = """
        GOAL: Precondition of f6 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL: Precondition of f6 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa2 y x)

GOAL: Precondition of f6 must imply the qualifier of its parameter z
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ > x (the int 4))
Prove:
  (@ kappa5 z x y)

GOAL: The qualified type of the result of f6 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 res x y z)
Prove:
  (@ = res (@ + x y))

GOAL: Precondition of parameter x in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_1 x)
Prove:
  (@ kappa1 _NU_1)

GOAL: Precondition of parameter y in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_2 (the int 1))
Prove:
  (@ kappa2 _NU_2 x)

GOAL: Precondition of parameter z in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_3 z)
Prove:
  (@ kappa5 _NU_3 x (the int 1))

GOAL: The type of m must match the type of the result #1 of f6
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 k x (the int 1) z)
  (@ kappa7 m x (the int 1) z k)
  (@ = _NU_6 m)
Prove:
  (@ kappa6 _NU_6 x y z)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))

    }
}