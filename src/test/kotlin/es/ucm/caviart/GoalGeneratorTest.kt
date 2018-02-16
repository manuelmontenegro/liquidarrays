package es.ucm.caviart

import es.ucm.caviart.ast.*
import es.ucm.caviart.goal.Goal
import es.ucm.caviart.goal.generateForDefinition
import es.ucm.caviart.typecheck.GlobalEnvironment
import es.ucm.caviart.typecheck.checkVerificationUnit
import es.ucm.caviart.typecheck.initialEnvironment
import es.ucm.caviart.utils.FreshNameGenerator
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

    private lateinit var parsedProg: VerificationUnit
    private lateinit var globalEnvironment: GlobalEnvironment

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
## Goal `G_1`

The qualified type of the result of f0 must imply its postcondition

**For all**:

  * `res` of type `int`

**such that**:

  * `(@ kappa1 res)`

**Prove:** `(@ = res (the int 4))`

## Goal `G_2`

The type of (the int 4) must match the type of the result #1 of f0

**For all**:

  * `_X_1` of type `int`

**such that**:

  * `(@ = _X_1 (the int 4))`

**Prove:** `(@ kappa1 _X_1)`
        """.trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun simpleSum() {
        val f1 = parsedProg.definitions.find { it.name == "f1" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f1, listOf(), globalEnvironment, goals)

        val expectedGoals = """
        ## Goal `G_1`

Precondition of f1 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`

**such that**:

  * `true`
  * `(@ >= x (the int 0))`

**Prove:** `(@ >= x (the int 0))`

## Goal `G_2`

The qualified type of the result of f1 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `res` of type `int`

**such that**:

  * `true`
  * `(@ >= x (the int 0))`
  * `(@ = res (@ + x (the int 1)))`

**Prove:** `(@ = (@ + x (the int 1)) res)`

## Goal `G_3`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `(@ >= x (the int 0))`
  * `(@ = _NU_1 x)`

**Prove:** `true`

## Goal `G_4`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `true`
  * `(@ >= x (the int 0))`
  * `(@ = _NU_2 (the int 1))`

**Prove:** `true`

## Goal `G_5`

The type of (@ + x (the int 1)) must match the type of the result #1 of f1

**For all**:

  * `x` of type `int`
  * `_X_1` of type `int`

**such that**:

  * `true`
  * `(@ >= x (the int 0))`
  * `(@ = _X_1 (@ + x (the int 1)))`

**Prove:** `(@ = _X_1 (@ + x (the int 1)))`""".trim()

        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun identity() {
        val f2 = parsedProg.definitions.find { it.name == "f2" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f2, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        ## Goal `G_1`

Precondition of f2 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`

**such that**:

  * `true`
  * `(@ > x (the int 0))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

The qualified type of the result of f2 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `res` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 res x)`

**Prove:** `(@ >= res (the int 0))`

## Goal `G_3`

The type of x must match the type of the result #1 of f2

**For all**:

  * `x` of type `int`
  * `_X_1` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _X_1 x)`

**Prove:** `(@ kappa2 _X_1 x)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test
    fun functionCall() {
        val f3 = parsedProg.definitions.find { it.name == "f3" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f3, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        ## Goal `G_1`

Precondition of f3 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`

**such that**:

  * `true`
  * `(@ > x (the int 3))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

The qualified type of the result of f3 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `res` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 res x)`

**Prove:** `(@ >= res (the int 0))`

## Goal `G_3`

Precondition of parameter x in call to sub

**For all**:

  * `x` of type `int`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _NU_1 x)`

**Prove:** `(@ >= _NU_1 (the int 0))`

## Goal `G_4`

Precondition of parameter y in call to sub

**For all**:

  * `x` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _NU_2 (the int 3))`

**Prove:** `(@ <= _NU_2 x)`

## Goal `G_5`

The type of (@ sub x (the int 3)) must match the type of the result #1 of f3

**For all**:

  * `x` of type `int`
  * `_X_1` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _X_1 (@ - x (the int 3)))`

**Prove:** `(@ kappa2 _X_1 x)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun constructorCall() {
        val f4 = parsedProg.definitions.find { it.name == "f4" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f4, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        ## Goal `G_1`

Precondition of f4 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`

**such that**:

  * `true`
  * `true`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

Precondition of f4 must imply the qualifier of its parameter y

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`

**such that**:

  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa4 y x)`

## Goal `G_3`

The qualified type of the result of f4 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`
  * `res` of type `(list int)`

**such that**:

  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa4 y x)`
  * `(@ kappa3 res x y)`

**Prove:** `(@ =L res (@@ cons x y))`

## Goal `G_4`

Precondition of parameter x in call to cons

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa4 y x)`
  * `(@ = _NU_1 x)`

**Prove:** `true`

## Goal `G_5`

Precondition of parameter xs in call to cons

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`
  * `_NU_2` of type `(list int)`

**such that**:

  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa4 y x)`
  * `(@ = _NU_2 y)`

**Prove:** `true`

## Goal `G_6`

The type of (@@ cons x y) must match the type of the result #1 of f4

**For all**:

  * `x` of type `int`
  * `y` of type `(list int)`
  * `_X_1` of type `(list int)`

**such that**:

  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa4 y x)`
  * `(@ =L _X_1 (@@ cons x y))`

**Prove:** `(@ kappa3 _X_1 x y)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }


    @Test
    fun letAndTuple() {
        val f5 = parsedProg.definitions.find { it.name == "f5" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f5, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        ## Goal `G_1`

Precondition of f5 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

Precondition of f5 must imply the qualifier of its parameter y

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa2 y x)`

## Goal `G_3`

Precondition of f5 must imply the qualifier of its parameter z

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa5 z x y)`

## Goal `G_4`

The qualified type of the result of f5 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `res1` of type `int`
  * `res2` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ kappa6 res1 x y z)`
  * `(@ kappa7 res2 x y z res1)`

**Prove:** `(and (@ = res1 (@ + x y)) (@ = res2 (@ - x y)))`

## Goal `G_5`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = _NU_1 x)`

**Prove:** `true`

## Goal `G_6`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = _NU_2 y)`

**Prove:** `true`

## Goal `G_7`

The type of (tuple z k) must match the type of the result #1 of f5

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `k` of type `int`
  * `_X_2` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = k (@ + x y))`
  * `(@ = _X_2 z)`

**Prove:** `(@ kappa6 _X_2 x y z)`

## Goal `G_8`

The type of (tuple z k) must match the type of the result #2 of f5

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `k` of type `int`
  * `_X_2` of type `int`
  * `_X_3` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = k (@ + x y))`
  * `(@ = _X_2 z)`
  * `(@ = _X_3 k)`

**Prove:** `(@ kappa7 _X_3 x y z _X_2)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))
    }

    @Test
    fun letMultiple() {
        val f6 = parsedProg.definitions.find { it.name == "f6" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f6, listOf(), globalEnvironment, goals)
        val expectedGoals = """
        ## Goal `G_1`

Precondition of f6 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

Precondition of f6 must imply the qualifier of its parameter y

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa2 y x)`

## Goal `G_3`

Precondition of f6 must imply the qualifier of its parameter z

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa5 z x y)`

## Goal `G_4`

The qualified type of the result of f6 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `res` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ kappa6 res x y z)`

**Prove:** `(@ = res (@ + x y))`

## Goal `G_5`

Precondition of parameter x in call to f5

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = _NU_1 x)`

**Prove:** `(@ kappa1 _NU_1)`

## Goal `G_6`

Precondition of parameter y in call to f5

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = _NU_2 (the int 1))`

**Prove:** `(@ kappa2 _NU_2 x)`

## Goal `G_7`

Precondition of parameter z in call to f5

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `_NU_3` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ = _NU_3 z)`

**Prove:** `(@ kappa5 _NU_3 x (the int 1))`

## Goal `G_8`

The type of m must match the type of the result #1 of f6

**For all**:

  * `x` of type `int`
  * `y` of type `int`
  * `z` of type `int`
  * `k` of type `int`
  * `m` of type `int`
  * `_X_3` of type `int`

**such that**:

  * `true`
  * `true`
  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 y x)`
  * `(@ kappa5 z x y)`
  * `(@ kappa6 k x (the int 1) z)`
  * `(@ kappa7 m x (the int 1) z k)`
  * `(@ = _X_3 m)`

**Prove:** `(@ kappa6 _X_3 x y z)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))

    }


    @Test
    fun letCase() {
        val f7 = parsedProg.definitions.find { it.name == "f7" }!!
        val goals = mutableListOf<Goal>()
        generateForDefinition(f7, listOf(), globalEnvironment, goals)
        val expectedGoals = """
## Goal `G_1`

Precondition of f7 must imply the qualifier of its parameter x

**For all**:

  * `x` of type `int`

**such that**:

  * `true`
  * `(@ > x (the int 4))`

**Prove:** `(@ kappa1 x)`

## Goal `G_2`

The qualified type of the result of f7 must imply its postcondition

**For all**:

  * `x` of type `int`
  * `res` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ kappa2 res x)`

**Prove:** `(@ = res x)`

## Goal `G_3`

Precondition of parameter x_0 in call to >

**For all**:

  * `x` of type `int`
  * `_NU_1` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _NU_1 x)`

**Prove:** `true`

## Goal `G_4`

Precondition of parameter x_1 in call to >

**For all**:

  * `x` of type `int`
  * `_NU_2` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(@ = _NU_2 (the int 0))`

**Prove:** `true`

## Goal `G_5`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_NU_4` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `b`
  * `(@ = _NU_4 (the int 1))`

**Prove:** `true`

## Goal `G_6`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_NU_5` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `b`
  * `(@ = _NU_5 x)`

**Prove:** `true`

## Goal `G_7`

The type of (@ + (the int 1) x) must match the type of the result #1 of f7

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_X_2` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `b`
  * `(@ = _X_2 (@ + (the int 1) x))`

**Prove:** `(@ kappa2 _X_2 x)`

## Goal `G_8`

Precondition of parameter x_0 in call to +

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_NU_6` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `(not b)`
  * `(@ = _NU_6 (the int 2))`

**Prove:** `true`

## Goal `G_9`

Precondition of parameter x_1 in call to +

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_NU_7` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `(not b)`
  * `(@ = _NU_7 x)`

**Prove:** `true`

## Goal `G_10`

The type of (@ + (the int 2) x) must match the type of the result #1 of f7

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_X_3` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `(not b)`
  * `(@ = _X_3 (@ + (the int 2) x))`

**Prove:** `(@ kappa2 _X_3 x)`

## Goal `G_11`

The type of (the int -4) must match the type of the result #1 of f7

**For all**:

  * `x` of type `int`
  * `b` of type `bool`
  * `_X_4` of type `int`

**such that**:

  * `true`
  * `(@ kappa1 x)`
  * `(<-> b (@ > x (the int 0)))`
  * `(@ = _X_4 (the int -4))`

**Prove:** `(@ kappa2 _X_4 x)`""".trim()
        assertEquals(expectedGoals, goals.joinToString("\n\n"))

    }


}