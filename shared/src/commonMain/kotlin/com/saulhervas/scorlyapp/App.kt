package com.saulhervas.scorlyapp

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.saulhervas.scorlyapp.di.appModule
import com.saulhervas.scorlyapp.presentation.navigation.NavigationRoot
import com.saulhervas.scorlyapp.presentation.theme.ScorlyTheme
import org.koin.compose.KoinApplication

@Composable
@Preview
fun App() {
    KoinApplication(application = {
        modules(appModule)
    }) {
        ScorlyTheme {
            NavigationRoot()
        }
    }
}