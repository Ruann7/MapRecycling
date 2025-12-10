package com.example.maprecycling.ui.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.maprecycling.data.repository.RecyclingRepository
import com.example.maprecycling.ui.ui.RecyclingPoint
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class MapUiState(
    val loading: Boolean = false,
    val points: List<RecyclingPoint> = emptyList(),
    val error: String? = null
)

sealed class MapEvent {
    data class ShowError(val message: String) : MapEvent()
}

class MapViewModel(
    private val repository: RecyclingRepository = RecyclingRepository()
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapUiState())
    val uiState: StateFlow<MapUiState> = _uiState.asStateFlow()

    private val _events = Channel<MapEvent>()
    val events = _events.receiveAsFlow()

    init {
        loadPoints()
    }

    fun loadPoints() {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true) }
            runCatching {
                repository.getPoints()
            }.onSuccess { list ->
                _uiState.update {
                    it.copy(
                        loading = false,
                        points = list.map { p ->
                            RecyclingPoint(p.lat, p.lng, p.title, p.description, p.imageUrl)
                        }
                    )
                }
            }.onFailure {
                _uiState.update { it.copy(loading = false) }
                _events.send(MapEvent.ShowError("Erro ao carregar pontos"))
            }
        }
    }
}