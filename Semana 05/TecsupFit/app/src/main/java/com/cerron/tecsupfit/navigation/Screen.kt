package com.cerron.tecsupfit.navigation

sealed class Screen(val route: String) {

    object Inicio : Screen("inicio")

    object DetalleClase : Screen("detalle_clase/{claseId}") {
        fun crearRuta(claseId: Int): String {
            return "detalle_clase/$claseId"
        }
    }

    object Confirmacion : Screen("confirmacion/{claseId}") {
        fun crearRuta(claseId: Int): String {
            return "confirmacion/$claseId"
        }
    }

    object Reservas : Screen("reservas")

    object Rutinas : Screen("rutinas")

    object Perfil : Screen("perfil")
}
