package com.github.freekdb.kotlinintro.mastermind

// Code-breaking game: https://en.wikipedia.org/wiki/Mastermind_(board_game)
// Implementation inspired by: http://www.rosettacode.org/wiki/Mastermind#Python

private const val MINIMUM_LETTER_COUNT = 2
private const val MAXIMUM_LETTER_COUNT = 20
private const val MINIMUM_CODE_LENGTH = 4
private const val MAXIMUM_CODE_LENGTH = 10

fun main() {
    val (letters, code) = initializeGame()
    runGame(code, letters)
}

private fun initializeGame(): Pair<String, String> {
    println("Welcome to Mastermind. You are challenged to guess a random code!")
    println("In response to each guess, you will receive a hint.")
    println("In this hint, an X means you guessed that letter correctly.")
    println("An O means that letter is in the code, but in a different position.")
    println()

    val letterCount = readNumber("Select a number of possible letters for the code",
                                 MINIMUM_LETTER_COUNT, MAXIMUM_LETTER_COUNT)

    val codeLength = readNumber("Select a length for the code",
                                MINIMUM_CODE_LENGTH, MAXIMUM_CODE_LENGTH)

    val letters = "ABCDEFGHIJKLMNOPQRST".take(letterCount)
    val code = (1..codeLength).map { letters.random() }.joinToString("")

    return Pair(letters, code)
}

private fun readNumber(prompt: String, minimum: Int, maximum: Int): Int {
    var number: Int? = null
    val promptWithRange = "$prompt ($minimum-$maximum): "

    while (number == null || number !in minimum..maximum) {
        print(promptWithRange)
        number = readLine()?.toIntOrNull()
    }

    return number
}

private fun runGame(code: String, letters: String) {
    val guesses = mutableListOf<String>()

    var finished = false
    while (!finished) {
        println()
        print("Enter a guess of length ${code.length} ($letters): ")
        val guess = readLine()?.toUpperCase()?.trim() ?: ""
        println()

        if (guess == code) {
            println("Your guess $guess was correct!")
            finished = true
        } else if (guess.length != code.length || guess.any { it !in letters }) {
            println("Your guess $guess was invalid. Please try again...")
        } else {
            guesses += "${guesses.size + 1}: ${guess.toCharArray().joinToString(" ")} =>" +
                    " ${encode(code, guess)}"

            guesses.forEach {
                println("------------------------------------")
                println(it)
                println("------------------------------------")
            }
        }
    }
}

private fun encode(code: String, guess: String): String =
    code
        .zip(guess)
        .joinToString(" ") { if (it.first == it.second) "X" else if (it.second in code) "O" else "-" }
