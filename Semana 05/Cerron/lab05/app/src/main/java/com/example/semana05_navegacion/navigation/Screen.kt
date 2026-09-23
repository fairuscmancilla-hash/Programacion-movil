package com.example.semana05_navegacion.navigation

// Clase sellada que actúa como contrato central de navegación.
// Recibe "route" como parámetro — es el identificador único de cada pantalla.
// Al ser sealed, el compilador conoce todas las rutas posibles en tiempo de compilación.
sealed class Screen(val route: String) {

    // RF01: pantalla de inicio de sesión — nuevo punto de entrada de la app
    object Login : Screen("login")

    // Pantalla de inicio (menú principal)
    object Home : Screen("home")

    // Pantalla que muestra la lista de alumnos
    object List : Screen("list")

    // Pantalla del perfil del usuario
    object Profile : Screen("profile")

    // ──────────────────────────────────────
    // RUTA CON ARGUMENTO
    // {itemId} es el placeholder que Navigation reemplaza
    // con el valor real al momento de navegar
    // ──────────────────────────────────────
    object Detail : Screen("detail/{itemId}") {
        // Ejemplo: createRoute(5) -> "detail/5"
        fun createRoute(itemId: Int): String = "detail/$itemId"
    }
}
