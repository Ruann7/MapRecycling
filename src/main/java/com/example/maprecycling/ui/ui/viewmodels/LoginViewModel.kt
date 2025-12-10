package com.example.maprecycling.ui.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class LoginUiState(
    val loading: Boolean = false,
    val error: String? = null
)

sealed class LoginEvent {
    object NavigateHome : LoginEvent()
}

class LoginViewModel : ViewModel() {

    private val auth = FirebaseAuth.getInstance()

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    private val _events = Channel<LoginEvent>()
    val events = _events.receiveAsFlow()

    fun login(email: String, password: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(loading = true, error = null) }

            auth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener {
                    viewModelScope.launch {
                        _events.send(LoginEvent.NavigateHome)
                    }
                }
                .addOnFailureListener { exception ->
                    _uiState.update { state ->
                        state.copy(
                            loading = false,
                            error = exception.message ?: "Erro ao fazer login"
                        )
                    }
                }
                .addOnCompleteListener {
                    _uiState.update { it.copy(loading = false) }
                }
        }
    }
}
