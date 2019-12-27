package com.github.freekdb.kotlinintro.step03

fun main() {
    val numbers = listOf(1, 2, 4, 8, 16, 32)
    val numbersSquared = numbers.map { it * it }

    println("Numbers squared: $numbersSquared")
}
