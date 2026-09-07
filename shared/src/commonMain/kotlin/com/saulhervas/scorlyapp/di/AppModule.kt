package com.saulhervas.scorlyapp.di

import com.saulhervas.scorlyapp.data.auth.FirebaseAuthService
import org.koin.dsl.module

/**
 * Módulo raíz de Koin para ScorlyApp.
 * Aquí agruparemos los submódulos de la app (dataModule, domainModule, etc.)
 */
val appModule = module {
    single { FirebaseAuthService() }
}
