package com.sdk.kmp.app.common.main

data class MainState(
    val text: String = "Enter button to start the new game",
    val changeCount: Int = 8,
    val isGameStarted: Boolean = false,
    val userGuess: String = ""
)