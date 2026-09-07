package com.saulhervas.scorlyapp.data.auth

import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.FirebaseAuth
import dev.gitlive.firebase.auth.FirebaseUser
import dev.gitlive.firebase.auth.auth
import kotlinx.coroutines.flow.Flow

class FirebaseAuthService(
    private val auth: FirebaseAuth = Firebase.auth
) {

    /**
     * Registra un nuevo usuario en Firebase Authentication.
     */
    suspend fun registerWithEmail(email: String, pass: String): FirebaseUser? {
        val authResult = auth.createUserWithEmailAndPassword(email, pass)
        return authResult.user
    }

    /**
     * Inicia sesión con un usuario existente en Firebase Authentication.
     */
    suspend fun loginWithEmail(email: String, pass: String): FirebaseUser? {
        val authResult = auth.signInWithEmailAndPassword(email, pass)
        return authResult.user
    }

    /**
     * Cierra la sesión activa en Firebase.
     */
    suspend fun logOut() {
        auth.signOut()
    }

    /**
     * Obtiene el usuario actual logueado en Firebase (o null si no hay sesión activa).
     */
    fun getCurrentUser(): FirebaseUser? {
        return auth.currentUser
    }

    /**
     * Flujo reactivo que emite cada vez que cambia el estado de autenticación (login / logout).
     */
    val authState: Flow<FirebaseUser?> = auth.authStateChanged
}
