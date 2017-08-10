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


