package com.github.freekdb.kotlin.workshop.step02

import kotlin.time.ExperimentalTime
import kotlin.time.minutes

@ExperimentalTime
fun main() {
    // Immutable variable with type inference (Duration).
    val workshopDuration = 150.minutes
    println("Workshop: ${workshopDuration.inHours} hours.")

    // Mutable variable with type inference (Int).
    var kotlinReasons = 3
    kotlinReasons += 6
    println("Kotlin reasons: $kotlinReasons (and counting).")


    println()


    // Mutable variable with type nullable String.
    var favoriteMovie: String? = null
    println("Favorite movie: $favoriteMovie.")

    favoriteMovie = "The Shawshank Redemption"
    println("New favorite movie: $favoriteMovie.")
}
