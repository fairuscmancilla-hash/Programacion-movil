package com.cerron.clinica.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cerron.clinica.model.doctoresMock
import com.cerron.clinica.screens.HomeScreen
import com.cerron.clinica.screens.PerfilMedicoScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {

        // INICIO
        composable(Screen.Home.route) {
            HomeScreen(
                navController = navController
            )
        }

        // PERFIL DEL MÉDICO
        composable(
            route = Screen.PerfilMedico.route,
            arguments = listOf(
                navArgument("medicoId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->

            val medicoId =
                backStackEntry.arguments?.getInt("medicoId")

            val medico = doctoresMock.find {
                it.id == medicoId
            }

            if (medico != null) {
                PerfilMedicoScreen(
                    navController = navController,
                    medico = medico
                )
            }
        }
    }
}