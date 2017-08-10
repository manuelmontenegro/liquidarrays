package es.ucm.caviart

private var kappaGenerated = mutableSetOf<KappaDeclaration>()
private var muGenerated = mutableSetOf<MuDeclaration>()


private fun addToScope(scope: MutableList<HMTypedVar>, variable: String, type: HMType) {
    scope.removeIf { (v, type) -> v == variable }
    scope.add(HMTypedVar(variable, type))
}

private fun emptyKappa(name: String, nu: HMTypedVar, arguments: List<HMTypedVar>) =
        KappaDeclaration(name, nu, arguments, null)

private fun emptyMu(name: String, nu: HMTypedVar, arguments: List<HMTypedVar>) =
        MuDeclaration(name, nu, arguments, null, null, null, null, null)

private fun toQualType(typedVar: TypedVar, defName: String, scope: MutableList<HMTypedVar>): Type {
    val result = if (typedVar.type is HMType) {
        if (typedVar.type is ConstrType && typedVar.type.typeConstructor == "array") {
            val name = "_mu_${defName}_${typedVar.varName}"
            val alreadyExisting = muGenerated.find { it.name == name }
            if (alreadyExisting != null) {
                QualType(alreadyExisting.nuVar.varName, alreadyExisting.nuVar.HMType, PredicateApplication(alreadyExisting.name, listOf(Variable(alreadyExisting.nuVar.varName)) + alreadyExisting.parameters.map { Variable(it.varName) }))
            } else {
                val parameters = scope.toList()
                val mu = emptyMu(name, HMTypedVar("nu", typedVar.type), parameters)
                muGenerated.add(mu)
                QualType("nu", typedVar.type, PredicateApplication(name, listOf(Variable("nu")) + parameters.map { Variable(it.varName) }))
            }

        } else {
            val name = "_kappa_${defName}_${typedVar.varName}"
            val alreadyExisting = kappaGenerated.find { it.name == name }
            if (alreadyExisting != null) {
                QualType(alreadyExisting.nuVar.varName, alreadyExisting.nuVar.HMType, PredicateApplication(alreadyExisting.name, listOf(Variable(alreadyExisting.nuVar.varName)) + alreadyExisting.parameters.map { Variable(it.varName) }))
            } else {
                val parameters = scope.toList()
                val kappa = emptyKappa(name, HMTypedVar("nu", typedVar.type), parameters)
                kappaGenerated.add(kappa)
                QualType("nu", typedVar.type, PredicateApplication(name, listOf(Variable("nu")) + parameters.map { Variable(it.varName) }))
            }
        }
    } else {
        typedVar.type
    }
    return result
}


private fun qualifyFunctionDefinition(definition: FunctionDefinition, scope: MutableList<HMTypedVar>, externals: Map<String, FunctionalType>): FunctionDefinition {
    val parameters = definition.inputParams.map {
        val result = toQualType(it, definition.name, scope)
        addToScope(scope, it.varName, it.type.hmType)
        TypedVar(it.varName, result)
    }

    val expression = qualifyExpression(definition.body, scope.toMutableList(), externals)

    val results = definition.outputParams.map {
        val result = toQualType(it, definition.name, scope)
        addToScope(scope, it.varName, it.type.hmType)
        TypedVar(it.varName, result)
    }

    return definition.copy(inputParams = parameters, outputParams = results, body = expression)
}


private fun qualifyExpression(expression: Term, scope: MutableList<HMTypedVar>, externals: Map<String, FunctionalType>): Term = when (expression) {
    is BindingExpression -> expression

    is Let -> {
        expression.bindings.forEach { addToScope(scope, it.varName, it.HMType) }
        expression.copy(mainExpression = qualifyExpression(expression.mainExpression, scope, externals))
    }

    is LetFun -> {
        expression.copy(
                defs = expression.defs.map {
                    val newScope = scope.toMutableList()
                    qualifyFunctionDefinition(it, newScope, externals)
                },
                mainExpression = qualifyExpression(expression.mainExpression, scope, externals)
        )
    }

    is Case -> {
        expression.copy(
                branches = expression.branches.map { (pat, exp) ->
                    val newScope = scope.toMutableList()
                    if (pat is ConstructorPattern) {
                        val constrType = externals[pat.constructorName]
                        if (constrType == null) throw UndefinedConstructorException(pat.line, pat.column, pat.constructorName)
                        pat.constructorArgs.zip(constrType.input).forEach { (varName, typedVar) ->
                            addToScope(newScope, varName, typedVar.type.hmType)
                        }
                    }
                    CaseBranch(pat, qualifyExpression(exp, newScope, externals))
                },

                defaultBranch = if (expression.defaultBranch != null) qualifyExpression(expression.defaultBranch, scope.toMutableList(), externals) else null
        )
    }


    else -> throw InvalidASTException(expression)
}


fun qualifyVeriticationUnit(verificationUnit: VerificationUnit): VerificationUnit {
    kappaGenerated = verificationUnit.kappaDeclarations.toMutableSet()
    muGenerated = verificationUnit.muDeclarations.toMutableSet()


    val newDefs = verificationUnit.definitions.map { qualifyFunctionDefinition(it, mutableListOf(), verificationUnit.external) }

    return verificationUnit.copy(
            kappaDeclarations = kappaGenerated,
            muDeclarations = muGenerated,
            definitions = newDefs
    )
}

