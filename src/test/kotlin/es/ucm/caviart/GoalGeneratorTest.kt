package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.goal.Goal
import es.ucm.caviart.goal.generateForDefinition
import es.ucm.caviart.typecheck.GlobalEnvironment
import es.ucm.caviart.typecheck.checkVerificationUnit
import es.ucm.caviart.typecheck.initialEnvironment
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class GoalGeneratorTest {
    private val prog = """
    (verification-unit dummy1
     :kappa (kappa1 ((nu int)))
     :kappa (kappa2 ((nu int) (x int)))
     :kappa (kappa3 ((nu (list int)) (x int) (y (list int))))
     :kappa (kappa4 ((nu (list int)) (x int)))
     :kappa (kappa5 ((nu int) (x int) (y int)))
     :kappa (kappa6 ((nu int) (x int) (y int) (z int)))
     :kappa (kappa7 ((nu int) (x int) (y int) (z int) (other int)))
     :external (sub ((x (qual nu int (@ >= nu (the int 0)))) (y (qual nu int (@ <= nu x)))) ((res (qual nu int (@ = nu (@ - x y))))))
     :external (cons ((x (qual nu int true)) (xs (qual nu (list int) true))) ((res (qual nu (list int) (@ =L nu (@@ cons x xs))))))
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
        (declare (assertion (precd (@ > x (the int 4))) (postcd (@ =L res (@@ cons x y)))))
        (@@ cons x y)
    )

    (define f5 ((x (qual nu int (@ kappa1 nu))) (y (qual nu int (@ kappa2 nu x))) (z (qual nu int (@ kappa5 nu x y)))) ((res1 (qual nu int (@ kappa6 nu x y z))) (res2 (qual nu int (@ kappa7 nu x y z res1))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (and (@ = res1 (@ + x y)) (@ = res2 (@ - x y))))))
        (let ((k int)) (@ + x y) (tuple z k))
    )

    (define f6 ((x (qual nu int (@ kappa1 nu))) (y (qual nu int (@ kappa2 nu x))) (z (qual nu int (@ kappa5 nu x y)))) ((res (qual nu int (@ kappa6 nu x y z))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (@ = res (@ + x y)))))
        (let ((k int) (m int)) (@ f5 x (the int 1) z) m)
    )

    (define f7 ((x (qual nu int (@ kappa1 nu)))) ((res (qual nu int (@ kappa2 nu x))))
        (declare (assertion (precd (@ > x (the int 4))) (postcd (@ = res x))))
        (let ((b bool)) (@ > x (the int 0)) (case b ((true (@ + (the int 1) x)) (false (@ + (the int 2) x))) (the int -4)))
    )

"""

    lateinit var parsedProg: VerificationUnit
    lateinit var globalEnvironment: GlobalEnvironment

    @Before
    fun parseAndReset() {
        FreshNameGenerator.resetGenerator()
        val listInt = ConstrType("list", listOf(intType))
        parsedProg = parseVerificationUnit(getSExps(prog))
        globalEnvironment = initialEnvironment.copy(
                logicPredicates = initialEnvironment.logicPredicates + parsedProg.kappaDeclarations.map {
                    it.name to (listOf(it.nuVar.HMType) + it.parameters.map { it.HMType })
                }.toMap() + mapOf("=L" to listOf(listInt, listInt)),
                logicFunctions = initialEnvironment.logicFunctions + ("cons" to UninterpretedFunctionType(listOf(intType, listInt), listInt)),
                programFunctions = initialEnvironment.programFunctions)
        globalEnvironment = checkVerificationUnit(parsedProg, globalEnvironment)

    }

    @Test
    fun simpleConst() {
        val f0 = parsedProg.definitions.find { it.name == "f0" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f0, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: The qualified type of the result of f0 must imply its postcondition
Assuming:
  (@ kappa1 res)
Prove:
  (@ = res (the int 4))

GOAL G_2: The type of (the int 4) must match the type of the result #1 of f0
Assuming:
  (@ = _X_1 (the int 4))
Prove:
  (@ kappa1 _X_1)
        """.trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun simpleSum() {
        val f1 = parsedProg.definitions.find { it.name == "f1" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f1, listOf(), globalEnvironment, goals)

        val expectedGoals = """
        GOAL G_1: Precondition of f1 must imply the qualifier of its parameter x
Assuming:
  (@ >= x (the int 0))
Prove:
  (@ >= x (the int 0))

GOAL G_2: The qualified type of the result of f1 must imply its postcondition
Assuming:
  (@ >= x (the int 0))
  (@ = res (@ + x (the int 1)))
Prove:
  (@ = (@ + x (the int 1)) res)

GOAL G_3: Precondition of parameter x_0 in call to +
Assuming:
  (@ >= x (the int 0))
  (@ = _NU_1 x)
Prove:
  true

GOAL G_4: Precondition of parameter x_1 in call to +
Assuming:
  (@ >= x (the int 0))
  (@ = _NU_2 (the int 1))
Prove:
  true

GOAL G_5: The type of (@ + x (the int 1)) must match the type of the result #1 of f1
Assuming:
  (@ >= x (the int 0))
  (@ = _X_1 (@ + x (the int 1)))
Prove:
  (@ = _X_1 (@ + x (the int 1)))""".trim()

        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun identity() {
        val f2 = parsedProg.definitions.find { it.name == "f2" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f2, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: Precondition of f2 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 0))
Prove:
  (@ kappa1 x)

GOAL G_2: The qualified type of the result of f2 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 res x)
Prove:
  (@ >= res (the int 0))

GOAL G_3: The type of x must match the type of the result #1 of f2
Assuming:
  (@ kappa1 x)
  (@ = _X_1 x)
Prove:
  (@ kappa2 _X_1 x)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test
    fun functionCall() {
        val f3 = parsedProg.definitions.find { it.name == "f3" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f3, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: Precondition of f3 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 3))
Prove:
  (@ kappa1 x)

GOAL G_2: The qualified type of the result of f3 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 res x)
Prove:
  (@ >= res (the int 0))

GOAL G_3: Precondition of parameter x in call to sub
Assuming:
  (@ kappa1 x)
  (@ = _NU_1 x)
Prove:
  (@ >= _NU_1 (the int 0))

GOAL G_4: Precondition of parameter y in call to sub
Assuming:
  (@ kappa1 x)
  (@ = _NU_2 (the int 3))
Prove:
  (@ <= _NU_2 x)

GOAL G_5: The type of (@ sub x (the int 3)) must match the type of the result #1 of f3
Assuming:
  (@ kappa1 x)
  (@ = _X_1 (@ - x (the int 3)))
Prove:
  (@ kappa2 _X_1 x)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun constructorCall() {
        val f4 = parsedProg.definitions.find { it.name == "f4" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f4, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: Precondition of f4 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL G_2: Precondition of f4 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa4 y x)

GOAL G_3: The qualified type of the result of f4 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ kappa3 res x y)
Prove:
  (@ =L res (@@ cons x y))

GOAL G_4: Precondition of parameter x in call to cons
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ = _NU_1 x)
Prove:
  true

GOAL G_5: Precondition of parameter xs in call to cons
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ = _NU_2 y)
Prove:
  true

GOAL G_6: The type of (@@ cons x y) must match the type of the result #1 of f4
Assuming:
  (@ kappa1 x)
  (@ kappa4 y x)
  (@ =L _X_1 (@@ cons x y))
Prove:
  (@ kappa3 _X_1 x y)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test
    fun letAndTuple() {
        val f5 = parsedProg.definitions.find { it.name == "f5" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f5, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: Precondition of f5 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL G_2: Precondition of f5 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa2 y x)

GOAL G_3: Precondition of f5 must imply the qualifier of its parameter z
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ > x (the int 4))
Prove:
  (@ kappa5 z x y)

GOAL G_4: The qualified type of the result of f5 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 res1 x y z)
  (@ kappa7 res2 x y z res1)
Prove:
  (and (@ = res1 (@ + x y)) (@ = res2 (@ - x y)))

GOAL G_5: Precondition of parameter x_0 in call to +
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_1 x)
Prove:
  true

GOAL G_6: Precondition of parameter x_1 in call to +
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_2 y)
Prove:
  true

GOAL G_7: The type of (tuple z k) must match the type of the result #1 of f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = k (@ + x y))
  (@ = _X_2 z)
Prove:
  (@ kappa6 _X_2 x y z)

GOAL G_8: The type of (tuple z k) must match the type of the result #2 of f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = k (@ + x y))
  (@ = _X_2 z)
  (@ = _X_3 k)
Prove:
  (@ kappa7 _X_3 x y z _X_2)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun letMultiple() {
        val f6 = parsedProg.definitions.find { it.name == "f6" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f6, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        GOAL G_1: Precondition of f6 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL G_2: Precondition of f6 must imply the qualifier of its parameter y
Assuming:
  (@ kappa1 x)
  (@ > x (the int 4))
Prove:
  (@ kappa2 y x)

GOAL G_3: Precondition of f6 must imply the qualifier of its parameter z
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ > x (the int 4))
Prove:
  (@ kappa5 z x y)

GOAL G_4: The qualified type of the result of f6 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 res x y z)
Prove:
  (@ = res (@ + x y))

GOAL G_5: Precondition of parameter x in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_1 x)
Prove:
  (@ kappa1 _NU_1)

GOAL G_6: Precondition of parameter y in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_2 (the int 1))
Prove:
  (@ kappa2 _NU_2 x)

GOAL G_7: Precondition of parameter z in call to f5
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ = _NU_3 z)
Prove:
  (@ kappa5 _NU_3 x (the int 1))

GOAL G_8: The type of m must match the type of the result #1 of f6
Assuming:
  (@ kappa1 x)
  (@ kappa2 y x)
  (@ kappa5 z x y)
  (@ kappa6 k x (the int 1) z)
  (@ kappa7 m x (the int 1) z k)
  (@ = _X_3 m)
Prove:
  (@ kappa6 _X_3 x y z)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))

    }


    @Test
    fun letCase() {
        val f7 = parsedProg.definitions.find { it.name == "f7" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f7, listOf(), globalEnvironment, goals)
        val expectedGoals = """
GOAL G_1: Precondition of f7 must imply the qualifier of its parameter x
Assuming:
  (@ > x (the int 4))
Prove:
  (@ kappa1 x)

GOAL G_2: The qualified type of the result of f7 must imply its postcondition
Assuming:
  (@ kappa1 x)
  (@ kappa2 res x)
Prove:
  (@ = res x)

GOAL G_3: Precondition of parameter x_0 in call to >
Assuming:
  (@ kappa1 x)
  (@ = _NU_1 x)
Prove:
  true

GOAL G_4: Precondition of parameter x_1 in call to >
Assuming:
  (@ kappa1 x)
  (@ = _NU_2 (the int 0))
Prove:
  true

GOAL G_5: Precondition of parameter x_0 in call to +
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  b
  (@ = _NU_4 (the int 1))
Prove:
  true

GOAL G_6: Precondition of parameter x_1 in call to +
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  b
  (@ = _NU_5 x)
Prove:
  true

GOAL G_7: The type of (@ + (the int 1) x) must match the type of the result #1 of f7
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  b
  (@ = _X_2 (@ + (the int 1) x))
Prove:
  (@ kappa2 _X_2 x)

GOAL G_8: Precondition of parameter x_0 in call to +
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  (not b)
  (@ = _NU_6 (the int 2))
Prove:
  true

GOAL G_9: Precondition of parameter x_1 in call to +
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  (not b)
  (@ = _NU_7 x)
Prove:
  true

GOAL G_10: The type of (@ + (the int 2) x) must match the type of the result #1 of f7
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  (not b)
  (@ = _X_3 (@ + (the int 2) x))
Prove:
  (@ kappa2 _X_3 x)

GOAL G_11: The type of (the int -4) must match the type of the result #1 of f7
Assuming:
  (@ kappa1 x)
  (<-> b (@ > x (the int 0)))
  (@ = _X_4 (the int -4))
Prove:
  (@ kappa2 _X_4 x)""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))

    }


    val fill = """
    (verification-unit fill)
    (define fill ((xs (array 'a)) (elem 'a)) ((res (array 'a)))
    (declare
        (assertion
            (precd true)
            (postcd (forall ((i int)) (-> (@ <= (the int 0) i) (-> (@ < i (@ len res)) (@ = (@ get-array res i) elem)))))))
        (letfun (
            (filln ((n int) (elem 'a) (xs (array 'a))) ((res (array 'a)))
                (let ((l int)) (@ len xs)
                  (let ((b bool)) (@ >= n l)
                    (case b (
                        (true xs)
                        (false (let ((xsp (array 'a))) (@ set-array xs n elem)
                                    (let ((n1 int)) (@ + n (the int 1)) (@ filln n1 elem xsp))))))))))
            (@ filln (the int 0) elem xs)))"""

}