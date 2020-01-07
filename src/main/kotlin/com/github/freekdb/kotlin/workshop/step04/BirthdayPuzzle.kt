package com.github.freekdb.kotlin.workshop.step04

/**
 * A puzzle: Calvin and Hobbes are celebrating their birthdays.
 *
 * One is '44' in base a,
 * the other is '55' in base b,
 * the sum of their ages is '77' in base c and
 * the difference is '111' in base d.
 * d < a < b < c and all are natural numbers.
 * Hobbes is younger than Calvin.
 *
 * P.S. The names of my colleagues have been replaced by those of two famous
 * last century cartoon characters:
 * "Calvin and Hobbes follows the humorous antics of the title characters:
 * Calvin, a precocious, mischievous and adventurous six-year-old boy; and
 * Hobbes, his sardonic stuffed tiger."
 * https://en.wikipedia.org/wiki/Calvin_and_Hobbes
 */
fun main() {
    solveVersion1()

    println()

    solveVersion2()
}

private fun solveVersion1() {
    for (baseC in 8..16) {
        val sum = "77".toInt(baseC)

        for (baseB in 6 until baseC) {
            val ageCalvin = "55".toInt(baseB)

            for (baseA in 5 until baseB) {
                val ageHobbes = "44".toInt(baseA)

                if (sum == ageHobbes + ageCalvin) {
                    for (baseD in 2 until baseA) {
                        val difference = "111".toInt(baseD)

                        if (difference == ageCalvin - ageHobbes) {
                            println(
                                "Found age Hobbes: $ageHobbes and age Calvin: $ageCalvin, " +
                                        "with bases a: $baseA, b: $baseB, c: $baseC, and d: $baseD."
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun solveVersion2() {
    (8..16).forEach { baseC ->
        val sum = "77".toInt(baseC)

        (6 until baseC).forEach { baseB ->
            val ageCalvin = "55".toInt(baseB)

            (5 until baseB)
                .filter { sum == "44".toInt(it) + ageCalvin }
                .forEach { baseA ->
                    val ageHobbes = "44".toInt(baseA)

                    (2 until baseA)
                        .filter { "111".toInt(it) == ageCalvin - ageHobbes }
                        .forEach { baseD ->
                            println(
                                "Found age Hobbes: $ageHobbes and age Calvin: $ageCalvin, " +
                                        "with bases a: $baseA, b: $baseB, c: $baseC, and d: $baseD."
                            )
                        }
                }
        }
    }
}
