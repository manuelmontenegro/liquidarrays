package es.ucm.caviart


private val logicFunctions = mapOf(
        "+" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "-" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "*" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "/" to UninterpretedFunctionType(listOf(ConstrType("int"), ConstrType("int")), ConstrType("int")),
        "get-array" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(VarType("'a"))),
                ConstrType("int")), VarType("'a")),
        "set-array" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(VarType("'a"))),
                ConstrType("int"), VarType("'a")), ConstrType("array", listOf(VarType("'a")))),
        "len" to UninterpretedFunctionType(listOf(ConstrType("array", listOf(VarType("'a")))), ConstrType("int"))
)

private val logicPredicates = mapOf(
        "<" to listOf(ConstrType("int"), ConstrType("int")),
        ">" to listOf(ConstrType("int"), ConstrType("int")),
        "<=" to listOf(ConstrType("int"), ConstrType("int")),
        ">=" to listOf(ConstrType("int"), ConstrType("int")),
        "=" to listOf(ConstrType("int"), ConstrType("int"))
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
                                            BooleanEquality(BooleanVariable("nu"), PredicateApplication(name, argNames.map { Variable(it) })))))
                            )
                        }).toMap().toMutableMap()
)