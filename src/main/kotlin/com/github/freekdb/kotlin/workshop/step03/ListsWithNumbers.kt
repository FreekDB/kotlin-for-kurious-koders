package com.github.freekdb.kotlin.workshop.step03

fun main() {
    val numbers = listOf(1, 2, 4, 8, 16, 32)
    val numbersSquared = numbers.map { it * it }

    println("Numbers squared: $numbersSquared")

    val perfectNumber = numbers[1] + numbers[2]
    println("Perfect number: $perfectNumber.")
}
