package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.tecsup.ayudacampus.AyudaApp
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

@Composable
fun NavAyuda() {
    val nav = rememberNavController()
    val app = LocalContext.current.applicationContext as AyudaApp

    NavHost(
        navController = nav,
        startDestination = Rutas.SPLASH,
        modifier = Modifier.fillMaxSize().systemBarsPadding()
    ) {
        composable(Rutas.SPLASH) {
            SplashScreen(onComenzar = {
                nav.navigate(Rutas.CATEGORIAS) { popUpTo(Rutas.SPLASH) { inclusive = true } }
            })
        }
        // TODO (Paso 6): login, grafo del estudiante y grafo de Soporte
        composable(Rutas.CATEGORIAS) {
            val vm: CategoriasViewModel = viewModel(factory = AyudaViewModelFactory(app.repository))
            CategoriasScreen(vm, onAtras = { nav.popBackStack() }, onEditar = { /* TODO (Paso 11): formulario */ })
        }
    }
}
