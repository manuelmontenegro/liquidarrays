package es.ucm.caviart.typecheck

import es.ucm.caviart.ast.*


private val logicFunctions = mapOf(
        "+" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "-" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "*" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "/" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "get-array" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(ConstrType("int"))),
                ConstrType("int")), ConstrType("int")),
        "set-array" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(ConstrType("int"))),
                ConstrType("int"), ConstrType("int")), ConstrType("array", listOf(ConstrType("int")))),
        "len" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(ConstrType("int")))), ConstrType("int"))
)

private val logicPredicates = mapOf(
        "<" to listOf(ConstrType("int"), ConstrType("int")),
        ">" to listOf(ConstrType("int"), ConstrType("int")),
        "<=" to listOf(ConstrType("int"), ConstrType("int")),
        ">=" to listOf(ConstrType("int"), ConstrType("int")),
        "=" to listOf(ConstrType("int"), ConstrType("int")),
        "=[]" to listOf(ConstrType("array", listOf(intType)), ConstrType("array", listOf(intType)))
)

val initialEnvironment = GlobalEnvironment(
        logicFunctions = logicFunctions,

        logicPredicates = logicPredicates,

        programFunctions = (
                logicFunctions.map { (name, type) ->
                    val argNames = type.argumentTypes.mapIndexed { index, _ -> "x_$index" }
                    name to FunctionalType(
                            argNames.zip(type.argumentTypes).map { (name, hmType) -> TypedVar(name, QualType("nu", hmType, True())) },
                            listOf(TypedVar("res", QualType("nu", type.resultType, PredicateApplication("=", listOf(Variable("nu"),
                                    FunctionApplication(name, argNames.map { Variable(it) }))))))
                    )
                } + logicPredicates.map { (name, types) ->
                    val argNames = types.mapIndexed { index, _ -> "x_$index" }
                    name to FunctionalType(
                            argNames.zip(types).map { (name, hmType) -> TypedVar(name, QualType("nu", hmType, True())) },
                            listOf(TypedVar("res", QualType("nu", ConstrType("bool"),
                                    Iff(BooleanVariable("nu"), PredicateApplication(name, argNames.map { Variable(it) })))))
                    )
                }).toMap().toMutableMap()

)