package com.example.maprecycling.ui.ui

import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.maprecycling.ui.ui.viewmodels.MapEvent
import com.example.maprecycling.ui.ui.viewmodels.MapViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun OSMMapScreen(navController: NavHostController, vm: MapViewModel = viewModel()) {

    val state by vm.uiState.collectAsState()

    LaunchedEffect(Unit) {
        vm.events.collectLatest { event ->
            if (event is MapEvent.ShowError) {
                println(event.message)
            }
        }
    }

    if (state.loading) {
        CircularProgressIndicator()
        return
    }

    MapContent(
        navController = navController,
        recyclingPoints = state.points
    )
}