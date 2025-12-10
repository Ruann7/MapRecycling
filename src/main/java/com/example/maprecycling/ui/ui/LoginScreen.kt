package com.example.maprecycling.ui.ui

import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.maprecycling.ui.ui.viewmodels.LoginEvent
import com.example.maprecycling.ui.ui.viewmodels.LoginViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LoginScreen(navController: NavController, vm: LoginViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collectLatest {
            if (it is LoginEvent.NavigateHome) {
                navController.navigate("home") {
                    popUpTo("login") { inclusive = true }
                }
            }
        }
    }

    LoginContent(
        loading = state.loading,
        error = state.error,
        onLogin = { email, password ->
            vm.login(email, password)
        },
        onRegister = {
            navController.navigate("register")
        }
    )
}



