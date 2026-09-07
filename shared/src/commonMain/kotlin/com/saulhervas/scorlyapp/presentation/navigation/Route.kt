package com.saulhervas.scorlyapp.presentation.navigation

import kotlinx.serialization.Serializable

/**
 * Contrato de rutas de navegación fuertemente tipadas para ScorlyApp.
 */
sealed interface Route {

    @Serializable
    data object Login : Route

    @Serializable
    data object Home : Route
}
