package com.saulhervas.scorlyapp.core.domain

/**
 * Interfaz marcadora para todos los errores de la aplicación.
 */
sealed interface AppError

/**
 * Errores comunes de la capa de datos (red, disco, etc.)
 */
sealed interface DataError : AppError {
    enum class Network : DataError {
        REQUEST_TIMEOUT,
        UNAUTHORIZED,
        NO_INTERNET,
        SERVER_ERROR,
        UNKNOWN
    }

    enum class Local : DataError {
        DISK_FULL,
        NOT_FOUND,
        UNKNOWN
    }
}
