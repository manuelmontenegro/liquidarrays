package es.ucm.caviart

typealias Substitution = Map<String, String>

fun rebind(variables: List<String>, substitution: Substitution, prefix: String): Pair<List<String>, Substitution> {
    var currentSubst = substitution
    val newVariables = variables.map {
        if (it in substitution.keys) {
            currentSubst -= it
        }
        if (it in substitution.values) {
            val fresh = FreshNameGenerator.nextName(prefix)
            currentSubst += it to fresh
            fresh
        } else {
            it
        }
    }
    return Pair(newVariables, currentSubst)
}


fun Assertion.applySubstitution(substitution: Substitution) : Assertion = when(this) {
    is True -> this

    is False -> this

    is BooleanVariable -> BooleanVariable(substitution[this.name] ?: this.name)

    is BooleanEquality -> BooleanEquality(assertion1.applySubstitution(substitution), assertion2.applySubstitution(substitution))

    is PredicateApplication -> PredicateApplication(this.name, this.arguments.map { it.applySubstitution(substitution) })

    is Not -> Not(assertion.applySubstitution(substitution))

    is And -> And(conjuncts.map { it.applySubstitution(substitution) })

    is Or -> Or(disjuncts.map { it.applySubstitution(substitution) })

    is Implication -> Implication(operands.map { it.applySubstitution(substitution) })

    is Iff -> Iff(operands.map { it.applySubstitution(substitution) })

    is ForAll -> {
        val (newVars, newSubstitution) = rebind(boundVars.map { it.varName }, substitution, "_I")
        ForAll(newVars.zip(boundVars).map { (nv, tv) -> HMTypedVar(nv, tv.HMType) }, assertion.applySubstitution(newSubstitution))
    }

    is Exists -> {
        val (newVars, newSubstitution) = rebind(boundVars.map { it.varName }, substitution, "_I")
        Exists(newVars.zip(boundVars).map { (nv, tv) -> HMTypedVar(nv, tv.HMType) }, assertion.applySubstitution(newSubstitution))
    }

    else -> throw InvalidASTException(this)
}


fun Atomic.applySubstitution(substitution: Substitution): Atomic = when(this) {
    is Literal -> this

    is Variable -> Variable(substitution[name] ?: name)

    else -> throw InvalidASTException(this)
}

fun BindingExpression.applySubstitution(substitution: Substitution) : BindingExpression = when(this) {
    is Atomic -> this.applySubstitution(substitution)

    is FunctionApplication -> FunctionApplication(name, arguments.map { it.applySubstitution(substitution) })

    is ConstructorApplication -> ConstructorApplication(name, arguments.map { it.applySubstitution(substitution) })

    is Tuple -> Tuple(arguments.map { it.applySubstitution(substitution) })

    else -> throw InvalidASTException(this)
}


