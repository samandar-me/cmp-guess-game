package com.sdk.kmp.app.common.main

sealed interface GameEvent  {
    object OnRandom: GameEvent
    data class OnGuess(val guessNumber: Int): GameEvent
}