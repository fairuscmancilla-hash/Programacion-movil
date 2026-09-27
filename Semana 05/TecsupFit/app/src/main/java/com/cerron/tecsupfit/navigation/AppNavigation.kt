package com.cerron.tecsupfit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cerron.tecsupfit.model.clasesMock
import com.cerron.tecsupfit.screens.ConfirmacionScreen
import com.cerron.tecsupfit.screens.DetalleClaseScreen
import com.cerron.tecsupfit.screens.HomeScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Inicio.route
    ) {

        // INICIO
        composable(
            route = Screen.Inicio.route
        ) {
            HomeScreen(
                navController = navController
            )
        }

        // DETALLE DE CLASE
        composable(
            route = Screen.DetalleClase.route,
            arguments = listOf(
                navArgument("claseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val claseId =
                backStackEntry.arguments
                    ?.getInt("claseId")
                    ?: 0

            val clase =
                clasesMock.find {
                    it.id == claseId
                }

            if (clase != null) {
                DetalleClaseScreen(
                    navController = navController,
                    clase = clase
                )
            }
        }

        // CONFIRMACION
        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(
                navArgument("claseId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val claseId =
                backStackEntry.arguments
                    ?.getInt("claseId")
                    ?: 0

            val clase =
                clasesMock.find {
                    it.id == claseId
                }

            if (clase != null) {
                ConfirmacionScreen(
                    navController = navController,
                    clase = clase
                )
            }
        }
    }
}

