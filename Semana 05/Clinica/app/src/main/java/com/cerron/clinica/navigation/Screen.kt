package com.cerron.clinica.navigation

sealed class Screen(val route: String) {

    // Pantalla principal
    object Home : Screen("home")

    // Perfil del médico seleccionado
    object PerfilMedico : Screen("perfil_medico/{medicoId}") {
        fun crearRuta(medicoId: Int): String {
            return "perfil_medico/$medicoId"
        }
    }

    // Pantalla para seleccionar fecha y hora
    object AgendarCita : Screen("agendar_cita/{medicoId}") {
        fun crearRuta(medicoId: Int): String {
            return "agendar_cita/$medicoId"
        }
    }

    // Pantalla final con el resumen de la cita
    object Confirmacion :
        Screen("confirmacion/{medicoId}/{fecha}/{hora}") {

        fun crearRuta(
            medicoId: Int,
            fecha: String,
            hora: String
        ): String {
            return "confirmacion/$medicoId/$fecha/$hora"
        }
    }

    // Mis citas
    object MisCitas : Screen("mis_citas")

    // Historial médico
    object Historial : Screen("historial")

    // Perfil del paciente
    object Perfil : Screen("perfil")
}