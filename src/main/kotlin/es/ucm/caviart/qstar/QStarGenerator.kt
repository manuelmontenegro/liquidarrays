/*
    Copyright (c) 2018 Manuel Montenegro

    Permission is hereby granted, free of charge, to any person obtaining a copy
    of this software and associated documentation files (the "Software"), to deal
    in the Software without restriction, including without limitation the rights
    to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
    copies of the Software, and to permit persons to whom the Software is
    furnished to do so, subject to the following conditions:

    The above copyright notice and this permission notice shall be included in all
    copies or substantial portions of the Software.

    THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
    IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
    FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
    AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
    LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
    OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
    SOFTWARE.
*/
package es.ucm.caviart.qstar

import es.ucm.caviart.ast.*
import es.ucm.caviart.utils.FreshNameGenerator

/**
 * It generates all the possible well-typed instantiations of a generic
 * qualifier (that is, a qualifier with placeholders). Placeholders are
 * be replaced by parameters and the `nu` of the generic qualifier are replaced
 * by the `nu` passed as first parameter.
 *
 * @param nuVar Name and type of the nu variable
 * @param parameters List of the parameters of the corresponding kappa/mu
 * @param genericQualifier Generic qualifier to instantiate
 *
 * @return List of specific instances of the generic qualifier
 */
fun instantiateGenericQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericQualifier: GenericQualifier)
        : Set<Assertion> {

    // We generate all the possible substitutions from markers to parameters
    return matchParameters(genericQualifier.markers, parameters).map {
        // For each one, we apply the substitution and replace the generic nu by the specific one
        genericQualifier.assertion.applySubstitution(it + (genericQualifier.nu.varName to Variable(nuVar.varName)))
    }.toSet()
}

/**
 * It generates all the possible well-typed instantiations of a generic singly-quantified
 * qualifier (that is, a qualifier with placeholders). Placeholders are
 * be replaced by parameters and the `nu` of the generic qualifier are replaced
 * by the `nu` passed as first parameter.
 *
 * @param nuVar Name and type of the nu variable
 * @param parameters List of the parameters of the corresponding kappa/mu
 * @param genericSingleQualifier Singly-quantified generic qualifier to instantiate
 *
 * @return List of specific instances of the generic qualifier
 */
fun instantiateGenericSingleQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericSingleQualifier: GenericSingleQualifier)
        : Set<SingleQualifier> {

    return matchParameters(genericSingleQualifier.markers, parameters).map {
        // We do the same as in instantiateGenericQualifier, but we must check whether the
        // index variable is in the range of the substitution. In this case, we have to rename
        // it to avoid capture.
        val boundVarBinding = if (genericSingleQualifier.boundVar in it.values.filter { it is Variable }.map { (it as Variable).name }) {
            mapOf(genericSingleQualifier.boundVar to Variable(FreshNameGenerator.nextName("_J")))
        } else emptyMap()


        SingleQualifier(
                // If we have renamed, put the new name; otherwise keep the old one
                boundVarBinding[genericSingleQualifier.boundVar]?.name ?: genericSingleQualifier.boundVar,
                genericSingleQualifier.assertion.applySubstitution(it + (genericSingleQualifier.nu.varName to Variable(nuVar.varName)) + boundVarBinding)
        )
    }.toSet()
}

fun instantiateGenericDoubleQualifier(nuVar: HMTypedVar, parameters: List<HMTypedVar>, genericDoubleQualifier: GenericDoubleQualifier)
        : Set<DoubleQualifier> {
    return matchParameters(genericDoubleQualifier.markers, parameters).map {
        // We do the same as in instantiateGenericQualifier, but we must check whether the
        // index variables are in the range of the substitution. In this case, we have to rename
        // them to avoid capture.
        val boundVar1Binding = if (genericDoubleQualifier.boundVar1 in it.values.filter { it is Variable }.map { (it as Variable).name }) {
            mapOf(genericDoubleQualifier.boundVar1 to Variable(FreshNameGenerator.nextName("_J")))
        } else emptyMap()
        val boundVar2Binding = if (genericDoubleQualifier.boundVar2 in it.values.filter { it is Variable }.map { (it as Variable).name }) {
            mapOf(genericDoubleQualifier.boundVar2 to Variable(FreshNameGenerator.nextName("_J")))
        } else emptyMap()
        DoubleQualifier(
                // If we have renamed, put the new name; otherwise keep the old one
                boundVar1Binding[genericDoubleQualifier.boundVar1]?.name ?: genericDoubleQualifier.boundVar1,
                // If we have renamed, put the new name; otherwise keep the old one
                boundVar2Binding[genericDoubleQualifier.boundVar2]?.name ?: genericDoubleQualifier.boundVar2,
                genericDoubleQualifier.assertion.applySubstitution(it + (genericDoubleQualifier.nu.varName to Variable(nuVar.varName)) + boundVar1Binding + boundVar2Binding)
        )
    }.toSet()
}

/**
 * Given a list of markers and a list of parameters, it returns all the possible
 * mappings from markers to parameters in which the Hindley Milner types match.
 *
 * For example, given
 *
 * ```
 * markers: [(* :: int) (** :: bool)]
 * variables: [(a :: bool) (b :: int)]
 * ```
 *
 * It returns the substitution `[* -> b, ** -> a]`.
 *
 * Another example:

 * ```
 * markers: [(* :: int) (** :: bool) (* :: int)]
 * variables: [(a :: bool) (b :: int) (c :: int)]
 * ```
 *
 * It returns the substitutions `[* -> b, ** -> a, *** -> b]`,
 *      `[* -> b, ** -> a, *** -> c]`,
 *      `[* -> c, ** -> a, *** -> c]` and
 *      `[* -> c, ** -> a, *** -> c]`.
 *
 */
fun matchParameters(markers: List<HMTypedVar>, parameters: List<HMTypedVar>): Set<Substitution> =
        matchParameters(emptyMap(), markers, parameters, 0)


/**
 * Generalized version of match parameters, which receives the current substitution and
 * an integer (fromIndex) that specifies in which position of `markers` do we start.
 *
 * Basically `matchParameters(sub, marks, params, idx) == sub + matchParameters(marks.sublist(idx, end), params)`.
 */
private fun matchParameters(currentSubst: Substitution, markers: List<HMTypedVar>, parameters: List<HMTypedVar>, fromIndex: Int): Set<Substitution> =
        if (fromIndex >= markers.size) {
            setOf(currentSubst)
        } else {
            val head = markers[fromIndex]

            parameters.flatMap {
                if (head.HMType == it.HMType) {
                    matchParameters(currentSubst + (head.varName to Variable(it.varName)), markers, parameters, fromIndex + 1)
                } else {
                    emptySet()
                }
            }.toSet()
        }


/**
 * It decorates a kappa declaration by instantiating the given generic Qsets
 * into specific assertions.
 */
fun instantiateKappa(kappaDeclaration: KappaDeclaration, qset: Set<GenericQualifier>): KappaDeclaration {

    // If the kappa already has a specific qSet, we leave it as is. Otherwise we find
    // instantiations from the generic set and apply them
    return kappaDeclaration.copy(qSet = kappaDeclaration.qSet ?: qset.flatMap {
        instantiateGenericQualifier(kappaDeclaration.nuVar, kappaDeclaration.parameters, it)
    }.toSet())
}

/**
 * It decorates a mu declaration by instantiating the given generic Qsets
 * into specific assertions.
 */
fun instantiateMu(muDeclaration: MuDeclaration, qI: Set<GenericSingleQualifier>, qE: Set<GenericSingleQualifier>,
                  qII: Set<GenericDoubleQualifier>, qEE: Set<GenericDoubleQualifier>, qLen: Set<GenericQualifier>): MuDeclaration {
    val boundVar1 = "i" //FreshNameGenerator.nextName("_I")
    val boundVar2 = "j" //FreshNameGenerator.nextName("_J")

    val qIStar: Set<SingleQualifier> = muDeclaration.qISet ?: qI.map {
        it.copy(boundVar = boundVar1, assertion = it.assertion.applySubstitution(mapOf(it.boundVar to Variable(boundVar1)))).addPropertiesFrom(it)
    }.flatMap {
                instantiateGenericSingleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
            }.toSet()

    val qEStar: Set<SingleQualifier> = if (muDeclaration.qESet != null) {
        muDeclaration.qESet
                .map {
                    it.copy(boundVar = boundVar1, assertion = it.assertion.applySubstitution(mapOf(it.boundVar to Variable(boundVar1)))).addPropertiesFrom(it)
                }.toSet()
    } else {
        qE.map {
            val newAssertion = it.assertion.applySubstitution(mapOf(it.boundVar to Variable(boundVar1)))
            it.copy(boundVar = boundVar1, assertion = newAssertion)
        }.flatMap {
                    instantiateGenericSingleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
                }.toSet()
    }

    val qIIStar: Set<DoubleQualifier> = muDeclaration.qIISet ?: qII.map {
        it.copy(boundVar1 = boundVar1, boundVar2 = boundVar2,
                assertion = it.assertion.applySubstitution(mapOf(it.boundVar1 to Variable(boundVar1), it.boundVar2 to Variable(boundVar2)))).addPropertiesFrom(it)
    }.flatMap {
                instantiateGenericDoubleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
            }.toSet()

    val qEEStar: Set<DoubleQualifier> = muDeclaration.qEESet ?: qEE.map {
        it.copy(boundVar1 = boundVar1, boundVar2 = boundVar2,
                assertion = it.assertion.applySubstitution(mapOf(it.boundVar1 to Variable(boundVar1), it.boundVar2 to Variable(boundVar2)))).addPropertiesFrom(it)
    }.flatMap {
                instantiateGenericDoubleQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
            }.toSet()

    val qLenStar: Set<Assertion> = muDeclaration.qLenSet ?: qLen.flatMap {
        instantiateGenericQualifier(muDeclaration.nuVar, muDeclaration.parameters, it)
    }.toSet()

    return muDeclaration.copy(
            qISet = qIStar,
            qESet = qEStar,
            qIISet = qIIStar,
            qEESet = qEEStar,
            qLenSet = qLenStar
    ).addPropertiesFrom(muDeclaration)
}

/**
 * Given a verification unit, it fills the Qset fields of each kappa and mu declaration,
 * with well-typed instantiations of the generic sets given in the verification unit.
 */
fun instantiateVerificationUnit(verificationUnit: VerificationUnit): VerificationUnit {
    val newKappas = verificationUnit.kappaDeclarations.map {
        instantiateKappa(it, verificationUnit.qSet)
    }

    val newMus = verificationUnit.muDeclarations.map {
        instantiateMu(it, verificationUnit.qISet, verificationUnit.qESet, verificationUnit.qIISet, verificationUnit.qEESet, verificationUnit.qLenSet)
    }

    return verificationUnit.copy(
            kappaDeclarations = newKappas.toSet(),
            muDeclarations = newMus.toSet()
    ).addPropertiesFrom(verificationUnit)
}
