package es.ucm.caviart

fun instantiateGenericQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericQualifier: GenericQualifier)
        : Set<Assertion> {
    val instantiation = findInstanceOf(genericQualifier.nu.HMType, nuVar.HMType) ?: return emptySet()

    return matchParameters(instantiation, emptyMap(), genericQualifier.markers, parameters).map {
        genericQualifier.assertion.applySubstitution(it + (genericQualifier.nu.varName to nuVar.varName))
    }.toSet()
}

fun instantiateGenericSingleQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericSingleQualifier: GenericSingleQualifier): Set<SingleQualifier> {
    val instantiation = findInstanceOf(genericSingleQualifier.nu.HMType, nuVar.HMType) ?: return emptySet()

    return matchParameters(instantiation, emptyMap(), genericSingleQualifier.markers, parameters).map {
        val boundVarBinding = if (genericSingleQualifier.boundVar in it.values) {
            mapOf(genericSingleQualifier.boundVar to FreshNameGenerator.nextName("_J"))
        } else emptyMap()
        SingleQualifier(boundVarBinding[genericSingleQualifier.boundVar] ?: genericSingleQualifier.boundVar,
                genericSingleQualifier.assertion.applySubstitution(it + (genericSingleQualifier.nu.varName to nuVar.varName) + boundVarBinding)
        )
    }.toSet()
}

fun instantiateGenericDoubleQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericDoubleQualifier: GenericDoubleQualifier): Set<DoubleQualifier> {
    val instantiation = findInstanceOf(genericDoubleQualifier.nu.HMType, nuVar.HMType) ?: return emptySet()

    return matchParameters(instantiation, emptyMap(), genericDoubleQualifier.markers, parameters).map {
        val boundVar1Binding = if (genericDoubleQualifier.boundVar1 in it.values) {
            mapOf(genericDoubleQualifier.boundVar1 to FreshNameGenerator.nextName("_J"))
        } else emptyMap()
        val boundVar2Binding = if (genericDoubleQualifier.boundVar2 in it.values) {
            mapOf(genericDoubleQualifier.boundVar2 to FreshNameGenerator.nextName("_J"))
        } else emptyMap()
        DoubleQualifier(boundVar1Binding[genericDoubleQualifier.boundVar1] ?: genericDoubleQualifier.boundVar1,
                boundVar2Binding[genericDoubleQualifier.boundVar2] ?: genericDoubleQualifier.boundVar2,
                genericDoubleQualifier.assertion.applySubstitution(it + (genericDoubleQualifier.nu.varName to nuVar.varName) + boundVar1Binding + boundVar2Binding)
        )
    }.toSet()
}

fun matchParameters(instantiation: Map<String, HMType>, currentSubst: Substitution, markers: List<HMTypedVar>, parameters: List<HMTypedVar>): Set<Substitution> =
        if (markers.isEmpty()) {
            setOf(currentSubst)
        } else {
            val head = markers[0]
            val tail = markers.subList(1, markers.size)

            parameters.flatMap {
                val newInstantation = findInstanceOf(head.HMType, it.HMType)
                if (newInstantation == null || !compatibleInstantiations(instantiation, newInstantation))
                    emptySet()
                else
                    matchParameters(instantiation + newInstantation, currentSubst + (head.varName to it.varName), tail, parameters)
            }.toSet()
        }


fun compatibleInstantiations(instantiation1: Map<String, HMType>, instantiation2: Map<String, HMType>): Boolean =
        instantiation1.all {
            val type2 = instantiation2[it.key]
            type2 == null || type2 == it.value
        }


fun generateKappa(kappaDeclaration: KappaDeclaration, qset: Set<GenericQualifier>): Kappa {
    return Kappa(
            kappaDeclaration.name,
            listOf(kappaDeclaration.nuVar) + kappaDeclaration.parameters,
            if (kappaDeclaration.qSet != null) {
                kappaDeclaration.qSet.toList()
            } else {
                qset.flatMap {
                    instantiateGenericQualifier(kappaDeclaration.nuVar, kappaDeclaration.parameters, it)
                }
            }
    )
}

fun generateMu(muDeclaration: MuDeclaration, qI: Set<GenericSingleQualifier>, qE: Set<GenericSingleQualifier>,
               qII: Set<GenericDoubleQualifier>, qEE: Set<GenericDoubleQualifier>, qLen: Set<GenericQualifier>): Mu {
    val boundVar1 = FreshNameGenerator.nextName("_J");
    val boundVar2 = FreshNameGenerator.nextName("_J")

    val qIStar = if (muDeclaration.qISet != null) {
        muDeclaration.qISet.map { it.assertion.applySubstitution(mapOf(it.boundVar to boundVar1)) }
    } else {
        qI.map {
            it.copy(boundVar = boundVar1, assertion = it.assertion.applySubstitution(mapOf(it.boundVar to boundVar1)))
        }.flatMap {
            instantiateGenericSingleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
        }.map { it.assertion }
    }

    val qEStar = if (muDeclaration.qESet != null) {
        muDeclaration.qESet
                .map { it.assertion.applySubstitution(mapOf(it.boundVar to boundVar1)) }
    } else {
        qE.map {
            val newAssertion = it.assertion.applySubstitution(mapOf(it.boundVar to boundVar1))
            it.copy(boundVar = boundVar1, assertion = newAssertion)
        }.flatMap {
            instantiateGenericSingleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
        }.map { it.assertion }
    }.map {
        val environment = (muDeclaration.parameters + muDeclaration.nuVar).map { it.varName to it.HMType }.toMap()
        QEStarElement(it, it.findArrayAccesses(boundVar1, environment).toList())
    }

    val qIIStar = if (muDeclaration.qIISet != null) {
        muDeclaration.qIISet.map { it.assertion.applySubstitution(mapOf(it.boundVar1 to boundVar1)) }
    } else {
        qII.map {
            it.copy(boundVar1 = boundVar1, boundVar2 = boundVar2,
                    assertion = it.assertion.applySubstitution(mapOf(it.boundVar1 to boundVar1, it.boundVar2 to boundVar2)))
        }.flatMap {
            instantiateGenericDoubleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
        }.map { it.assertion }
    }

    val qEEStar = if (muDeclaration.qEESet != null) {
        muDeclaration.qEESet.map { it.assertion.applySubstitution(mapOf(it.boundVar1 to boundVar1, it.boundVar2 to boundVar2)) }
    } else {
        qEE.map {
            it.copy(boundVar1 = boundVar1, boundVar2 = boundVar2,
                    assertion = it.assertion.applySubstitution(mapOf(it.boundVar1 to boundVar1, it.boundVar2 to boundVar2)))
        }.flatMap {
            instantiateGenericDoubleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
        }.map { it.assertion }
    }.map {
        val environment = (muDeclaration.parameters + muDeclaration.nuVar).map { it.varName to it.HMType }.toMap()
        QEEStarElement(it, it.findArrayAccesses(boundVar1, environment).toList(), it.findArrayAccesses(boundVar2, environment).toList())
    }

    val qLenStar = if (muDeclaration.qLenSet != null) {
        muDeclaration.qLenSet.toList()
    } else {
        qLen.flatMap {
            instantiateGenericQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
        }
    }

    return Mu(
            muDeclaration.name,
            listOf(muDeclaration.nuVar) + muDeclaration.parameters,
            boundVar1,
            boundVar2,
            qIStar, qEStar, qIIStar, qEEStar, qLenStar
    )
}

fun BindingExpression.findArrayAccesses(indexVar: String, environment: Map<String, HMType>): Set<Pair<String, HMType>> = when(this) {
    is Literal -> emptySet()

    is Variable -> emptySet()

    is FunctionApplication -> {
        if (this.name == "get-array" || this.name == "set-array") {
            val arrayName = (this.arguments[0] as Variable).name
            val indexName = (this.arguments[1] as Variable).name
            if (indexVar == indexName) {
                val arrayType = environment[arrayName]
                if (arrayType == null || arrayType !is ConstrType || arrayType.typeConstructor != "array") {
                    throw RuntimeException("get-array applied to $arrayName$ of non-array type, but it has not been caught before (?)")
                } else {
                    setOf(arrayName to arrayType.arguments[0])
                }
            } else {
                emptySet()
            }
        } else {
            emptySet()
        }
    }

    is ConstructorApplication -> emptySet()

    is Tuple -> emptySet()

    else -> throw InvalidASTException(this)
}


fun Assertion.findArrayAccesses(indexVar: String, environment: Map<String, HMType>): Set<Pair<String, HMType>> = when(this) {
    is True -> emptySet()

    is False -> emptySet()

    is BooleanVariable -> emptySet()

    is BooleanEquality -> assertion1.findArrayAccesses(indexVar, environment) + assertion2.findArrayAccesses(indexVar, environment)

    is PredicateApplication -> arguments.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Not -> assertion.findArrayAccesses(indexVar, environment)

    is And -> conjuncts.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Or -> disjuncts.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Implication -> operands.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is Iff -> operands.flatMap { it.findArrayAccesses(indexVar, environment) }.toSet()

    is ForAll -> assertion.findArrayAccesses(indexVar, environment + boundVars.map { it.varName to it.HMType })

    is Exists -> assertion.findArrayAccesses(indexVar, environment + boundVars.map { it.varName to it.HMType })

    else -> throw InvalidASTException(this)

}