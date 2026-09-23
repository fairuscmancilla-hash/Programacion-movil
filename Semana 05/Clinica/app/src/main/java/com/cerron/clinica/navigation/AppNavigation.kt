package com.cerron.clinica.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.cerron.clinica.model.Cita
import com.cerron.clinica.model.doctoresMock
import com.cerron.clinica.screens.AgendarCitaScreen
import com.cerron.clinica.screens.ConfirmacionScreen
import com.cerron.clinica.screens.HistorialScreen
import com.cerron.clinica.screens.HomeScreen
import com.cerron.clinica.screens.MisCitasScreen
import com.cerron.clinica.screens.PerfilMedicoScreen

@Composable
fun AppNavigation() {

    val navController = rememberNavController()

    val citas = remember {
        mutableStateListOf<Cita>()
    }

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

        // AGENDAR CITA
        composable(
            route = Screen.AgendarCita.route,
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
                AgendarCitaScreen(
                    navController = navController,
                    medico = medico
                )
            }
        }

        // CONFIRMACIÓN
        composable(
            route = Screen.Confirmacion.route,
            arguments = listOf(
                navArgument("medicoId") {
                    type = NavType.IntType
                },
                navArgument("fecha") {
                    type = NavType.StringType
                },
                navArgument("hora") {
                    type = NavType.StringType
                }
            )
        ) { backStackEntry ->

            val medicoId =
                backStackEntry.arguments?.getInt("medicoId")

            val fecha =
                backStackEntry.arguments?.getString("fecha") ?: ""

            val hora =
                backStackEntry.arguments?.getString("hora") ?: ""

            val medico = doctoresMock.find {
                it.id == medicoId
            }

            if (medico != null) {
                ConfirmacionScreen(
                    navController = navController,
                    medico = medico,
                    fecha = fecha,
                    hora = hora,
                    onGuardarCita = {
                        citas.add(
                            Cita(
                                medico = medico.nombre,
                                especialidad = medico.especialidad,
                                fecha = fecha,
                                hora = hora,
                                estado = "Confirmada"
                            )
                        )
                    }
                )
            }
        }

        // MIS CITAS
        composable(Screen.MisCitas.route) {
            MisCitasScreen(
                navController = navController,
                citas = citas
            )
        }

        // HISTORIAL MÉDICO
        composable(Screen.Historial.route) {
            HistorialScreen(
                navController = navController
            )
        }
    }
}