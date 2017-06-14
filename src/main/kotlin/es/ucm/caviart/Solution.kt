package es.ucm.caviart

/**
 * Created by manuel on 14/06/17.
 */

data class Kappa(val name: String, val arguments: List<TypedVar>, val availableQualifiers: List<Assertion>)

data class Solution(val kappas: MutableMap<String, MutableSet<Int>>)