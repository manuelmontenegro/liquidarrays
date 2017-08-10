package es.ucm.caviart

object FreshNameGenerator {
    private val generatedSubscripts: MutableMap<String, Int> = mutableMapOf()

    fun nextName(prefix: String): String {
        val lastGenerated = generatedSubscripts[prefix] ?: 0
        generatedSubscripts[prefix] = lastGenerated + 1;
        return "${prefix}_${lastGenerated + 1}"
    }
}