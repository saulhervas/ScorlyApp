package com.saulhervas.scorlyapp.presentation.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.saulhervas.scorlyapp.presentation.auth.LoginScreen

@Composable
fun NavigationRoot(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = Route.Login
    ) {
        // 1. Pantalla de Login con Firebase
        composable<Route.Login> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Login) { inclusive = true }
                    }
                }
            )
        }

        // 2. Pantalla de Home (provisional)
        composable<Route.Home> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Button(onClick = { navController.popBackStack() }) {
                    Text("Estás en Home ➔ Volver atrás")
                }
            }
        }
    }
}
