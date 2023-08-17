package com.sdk.kmp.app.common.main

import androidx.compose.runtime.*
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _state = MutableStateFlow(MainState())
    val state get() = _state.asStateFlow()
    private var randomNumber = 0
    private var chance = 8

    fun onEvent(event: GameEvent) {
        when(event){
            is GameEvent.OnRandom -> {
                viewModelScope.launch {
                    _state.update {
                        it.copy(text = "Randomizing...")
                    }
                    randomNumber = (0..100).random()
                    delay(2000L)
                    _state.update {
                        it.copy(text = "Start guessing", isGameStarted = true)
                    }
                }
            }
            is GameEvent.OnGuess -> {
                guess(event.guessNumber)
            }
        }
    }
    private fun guess(guess: Int) {
        chance -= 1
        _state.update {
            it.copy(
                changeCount = chance
            )
        }
        if (_state.value.changeCount == 0) {
            chance = 8
            _state.update {
                it.copy(
                    text = "You lost :( Random number was $randomNumber",
                    isGameStarted = false,
                    changeCount = 8
                )
            }
            return
        }
        if (guess == randomNumber) {
            chance = 8
            _state.update {
                it.copy(
                    text = "You won :)",
                    changeCount = 8,
                    isGameStarted = false,
                )
            }
        } else {
            _state.update {
                it.copy(
                    text = if (randomNumber > guess) "Less" else "Greater"
                )
            }
        }
    }
}