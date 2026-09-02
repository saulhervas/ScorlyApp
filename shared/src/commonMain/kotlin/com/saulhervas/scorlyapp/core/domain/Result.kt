package com.saulhervas.scorlyapp.core.domain

/**
 * Representa el resultado de una operación que puede tener éxito (D) o fallar con un error (E).
 *
 * @param D Tipo de dato en caso de éxito.
 * @param E Tipo de error (debe heredar de AppError).
 */
sealed interface Result<out D, out E : AppError> {
    data class Success<out D>(val data: D) : Result<D, Nothing>
    data class Error<out E : AppError>(val error: E) : Result<Nothing, E>
}

// Tipo alias para operaciones que no retornan datos (ej: un Logout o Guardado)
typealias EmptyResult<E> = Result<Unit, E>

/**
 * Ejecuta una acción si el resultado fue exitoso.
 */
inline fun <T, E : AppError> Result<T, E>.onSuccess(action: (T) -> Unit): Result<T, E> {
    if (this is Result.Success) action(data)
    return this
}

/**
 * Ejecuta una acción si el resultado fue un error.
 */
inline fun <T, E : AppError> Result<T, E>.onError(action: (E) -> Unit): Result<T, E> {
    if (this is Result.Error) action(error)
    return this
}

/**
 * Transforma los datos de un Result.Success manteniendo el mismo error si falló.
 */
inline fun <T, E : AppError, R> Result<T, E>.map(transform: (T) -> R): Result<R, E> {
    return when (this) {
        is Result.Success -> Result.Success(transform(data))
        is Result.Error -> Result.Error(error)
    }
}
