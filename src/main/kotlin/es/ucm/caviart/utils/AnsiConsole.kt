package es.ucm.caviart.utils

fun String.asRed(): String {
    return "\u001B[31m${this}\u001B[0m"
}

fun String.asGreen(): String {
    return "\u001B[32m${this}\u001B[0m"
}

fun String.asDarkGray(): String {
    return "\u001B[90m${this}\u001B[0m"
}