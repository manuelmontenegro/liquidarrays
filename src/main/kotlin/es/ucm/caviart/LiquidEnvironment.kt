package es.ucm.caviart


abstract class EnvironmentEntry(val next: EnvironmentEntry?)

class AssertionEntry(val assertion: Assertion, next: EnvironmentEntry?): EnvironmentEntry(next)

class VariableEntry(val variable: String, val type: QualType, next: EnvironmentEntry?): EnvironmentEntry(next)

