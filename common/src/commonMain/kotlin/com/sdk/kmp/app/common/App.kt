package com.sdk.kmp.app.common

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.sdk.kmp.app.common.main.MainScreen
import com.sdk.kmp.app.common.main.MainViewModel
import com.sdk.kmp.app.common.ui.theme.MyKmpAppTheme
import dev.icerock.moko.mvvm.compose.getViewModel
import dev.icerock.moko.mvvm.compose.viewModelFactory

@Composable
fun App() {
    MyKmpAppTheme(
        darkTheme = false
    ) {
        val vm = getViewModel(
            "main-view-model",
            viewModelFactory {
                MainViewModel()
            }
        )
        val state by vm.state.collectAsState()
        MainScreen(
            state = state,
            onEvent = vm::onEvent
        )
    }
}
