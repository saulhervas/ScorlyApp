package com.saulhervas.scorlyapp.presentation.auth

/**
 * Estado inmutable de la pantalla de inicio de sesión.
 */
data class LoginState(
    val email: String = "",
    val pass: String = "",
    val isLoading: Boolean = false,
    val errorMessage: String? = null
)

/**
 * Acciones que el usuario puede disparar desde la pantalla de login.
 */
sealed interface LoginAction {
    data class OnEmailChange(val email: String) : LoginAction
    data class OnPasswordChange(val pass: String) : LoginAction
    data object OnSubmit : LoginAction
    data object OnGoogleSignInClick : LoginAction
}
