package com.sdk.kmp.app.common.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.SnackbarHost
import androidx.compose.material.SnackbarHostState
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    state: MainState,
    onEvent: (GameEvent) -> Unit
) {
    val snack = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    var number by remember { mutableStateOf("") }
    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snack)
        },
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Guess Game")
                }
            )
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row {
                for(i in 0 until state.changeCount) {
                    Icon(
                        imageVector = Icons.Filled.Favorite,
                        contentDescription = "chance",
                        tint = Color.Red
                    )
                }
            }
            Spacer(Modifier.height(40.dp))
            Text(
                text = state.text,
                fontSize = 30.sp,
                color = Color.Black,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(30.dp))
            TextField(
                value = number,
                onValueChange = {
                    number = it
                },
                textStyle = TextStyle(
                    fontSize = 30.sp,
                    color = Color.Black
                ),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                ),
                placeholder = {
                    Text(text = "Your guess number")
                }
            )
            Spacer(Modifier.height(16.dp))
            ElevatedButton(
                onClick = {
                    if (!state.isGameStarted) {
                        onEvent(GameEvent.OnRandom)
                        number = ""
                        return@ElevatedButton
                    }
                    if (number.isBlank()) {
                        scope.launch {
                            snack.showSnackbar("Enter number")
                        }
                    } else {
                        onEvent(GameEvent.OnGuess(number.toInt()))
                    }
                }
            ) {
                Text(text = if (!state.isGameStarted) "Start the game" else "Check")
            }
        }
    }
}