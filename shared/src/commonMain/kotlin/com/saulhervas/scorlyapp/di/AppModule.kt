package com.saulhervas.scorlyapp.di

import org.koin.dsl.module

/**
 * Módulo raíz de Koin para ScorlyApp.
 * Aquí agruparemos los submódulos de la app (dataModule, domainModule, etc.)
 */
val appModule = module {
    // Por ahora vacío. Se irán agregando las dependencias de cada feature.
}
