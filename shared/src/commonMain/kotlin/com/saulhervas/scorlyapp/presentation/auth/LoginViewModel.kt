package com.saulhervas.scorlyapp.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.saulhervas.scorlyapp.data.auth.FirebaseAuthService
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val authService: FirebaseAuthService
) : ViewModel() {

    private val _state = MutableStateFlow(LoginState())
    val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _eventChannel = Channel<LoginEvent>()
    val events = _eventChannel.receiveAsFlow()

    sealed interface LoginEvent {
        data object LoginSuccess : LoginEvent
    }

    fun onAction(action: LoginAction) {
        when (action) {
            is LoginAction.OnEmailChange -> _state.update { it.copy(email = action.email, errorMessage = null) }
            is LoginAction.OnPasswordChange -> _state.update { it.copy(pass = action.pass, errorMessage = null) }
            is LoginAction.OnSubmit -> submit()
            is LoginAction.OnGoogleSignInClick -> {
                // Preparado para conectar Google Sign-In
            }
        }
    }

    private fun submit() {
        val currentState = _state.value
        if (currentState.email.isBlank() || currentState.pass.isBlank()) {
            _state.update { it.copy(errorMessage = "Por favor, completa todos los campos.") }
            return
        }

        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, errorMessage = null) }

            try {
                val user = authService.loginWithEmail(currentState.email.trim(), currentState.pass)

                if (user != null) {
                    _eventChannel.send(LoginEvent.LoginSuccess)
                } else {
                    _state.update { it.copy(errorMessage = "Credenciales incorrectas.") }
                }
            } catch (e: Exception) {
                _state.update { it.copy(errorMessage = e.message ?: "Ocurrió un error al iniciar sesión.") }
            } finally {
                _state.update { it.copy(isLoading = false) }
            }
        }
    }
}
