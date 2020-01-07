package com.github.freekdb.kotlin.workshop.step05

// Code-breaking game: https://en.wikipedia.org/wiki/Mastermind_(board_game)
// Additional information: http://mathworld.wolfram.com/Mastermind.html
// Implementation inspired by: http://www.rosettacode.org/wiki/Mastermind#Python

// https://www.coursera.org/learn/kotlin-for-java-developers/programming/vmwVT/mastermind-game


private const val MINIMUM_LETTER_COUNT = 2
private const val MAXIMUM_LETTER_COUNT = 20
private const val MINIMUM_CODE_LENGTH = 4
private const val MAXIMUM_CODE_LENGTH = 10

fun main() {
    val (letters, code) = initializeGame()
    runGame(letters, code)
}

private fun initializeGame(): Pair<String, String> {
    println("""
        |Welcome to Mastermind. You are challenged to guess a random code!
        |In response to each guess, you will receive a hint.
        |In this hint, an X means you guessed that letter correctly.
        |An O means that letter is in the code, but in a different position.
        
    """.trimMargin())

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

private fun runGame(letters: String, code: String) {
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

// A black key peg ("X") is placed for each code peg from the guess which is correct in both color and position ("cc-cp").
// A white key peg ("O") indicates the existence of a correct color code peg placed in the wrong position ("cc-wp").
private fun encode(code: String, guess: String): String =
    code
        .zip(guess)
        .map { if (it.first == it.second) "cc-cp" else if (it.second in code) "cc-wp" else "wc-wp" }
        .sorted()
        .map { when(it) {
            "cc-cp" -> "X"
            "cc-wp" -> "O"
            else -> "-"
        } }
        .joinToString(" ")
