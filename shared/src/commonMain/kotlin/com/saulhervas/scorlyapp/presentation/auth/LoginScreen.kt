package com.saulhervas.scorlyapp.presentation.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.saulhervas.scorlyapp.presentation.theme.ScorlyTheme
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel
import scorly.shared.generated.resources.Res
import scorly.shared.generated.resources.ic_app

/**
 * Stateful Composable: Inyección con Koin, corrutinas y eventos de navegación.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToRegister: () -> Unit = {},
    viewModel: LoginViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel.events) {
        viewModel.events.collect { event ->
            when (event) {
                is LoginViewModel.LoginEvent.LoginSuccess -> onLoginSuccess()
            }
        }
    }

    LoginContent(
        state = state,
        onAction = viewModel::onAction,
        onNavigateToRegister = onNavigateToRegister
    )
}

/**
 * Stateless Composable: Vista pura adaptada al sistema de diseño Voltage Arena (Stitch).
 */
@Composable
fun LoginContent(
    state: LoginState,
    onAction: (LoginAction) -> Unit,
    onNavigateToRegister: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .widthIn(max = 420.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Icono de la App
            Image(
                painter = painterResource(Res.drawable.ic_app),
                contentDescription = "Logo Scorly",
                modifier = Modifier
                    .size(80.dp)
                    .clip(RoundedCornerShape(20.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Header Branding (Stitch Voltage Arena)
            Text(
                text = "SCORLY",
                style = MaterialTheme.typography.displaySmall.copy(
                    fontWeight = FontWeight.ExtraBold,
                    letterSpacing = (-1).sp
                ),
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Card adaptativa (claro y oscuro)
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .border(
                        width = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant,
                        shape = RoundedCornerShape(20.dp)
                    ),
                color = MaterialTheme.colorScheme.surfaceContainerHigh,
                tonalElevation = 2.dp
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "Iniciar sesión",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    // Input Email
                    OutlinedTextField(
                        value = state.email,
                        onValueChange = { onAction(LoginAction.OnEmailChange(it)) },
                        label = { Text("Correo electrónico") },
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Input Contraseña
                    OutlinedTextField(
                        value = state.pass,
                        onValueChange = { onAction(LoginAction.OnPasswordChange(it)) },
                        label = { Text("Contraseña") },
                        singleLine = true,
                        visualTransformation = PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    )

                    // Mensaje de Error reactivo
                    state.errorMessage?.let { error ->
                        Text(
                            text = error,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    // Botón Principal (ENTRAR ➔)
                    Button(
                        onClick = { onAction(LoginAction.OnSubmit) },
                        enabled = !state.isLoading,
                        shape = RoundedCornerShape(14.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer,
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(24.dp),
                                color = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        } else {
                            Text(
                                text = "ENTRAR ➔",
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 1.sp
                            )
                        }
                    }

                    // Separador visual "O"
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                        Text(
                            text = "O",
                            modifier = Modifier.padding(horizontal = 12.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        HorizontalDivider(
                            modifier = Modifier.weight(1f),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }

                    // Botón Continuar con Google
                    OutlinedButton(
                        onClick = { onAction(LoginAction.OnGoogleSignInClick) },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = BorderStroke(
                            1.dp,
                            MaterialTheme.colorScheme.outlineVariant
                        )
                    ) {
                        Text(
                            text = "Continuar con Google",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Enlace hacia Pantalla de Registro
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "¿No tienes una cuenta? ",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Crear cuenta",
                            color = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            modifier = Modifier.clickable { onNavigateToRegister() }
                        )
                    }
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Previews para Android Studio
// -------------------------------------------------------------

@Preview
@Composable
private fun LoginScreenDarkPreview() {
    ScorlyTheme(darkTheme = true) {
        LoginContent(
            state = LoginState(email = "alex@scorly.com"),
            onAction = {}
        )
    }
}

@Preview
@Composable
private fun LoginScreenLightPreview() {
    ScorlyTheme(darkTheme = false) {
        LoginContent(
            state = LoginState(email = "alex@scorly.com"),
            onAction = {}
        )
    }
}
