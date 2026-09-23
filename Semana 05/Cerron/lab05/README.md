# Semana 05 — Navegación en Jetpack Compose (rama **sin-ia**)

Versión desarrollada siguiendo la guía del laboratorio (Home, List, Detail, Profile) y ampliada con Login y búsqueda, sin asistencia de IA en el diseño. Interfaz Material 3 básica.

## Requerimientos funcionales

| Código | Requerimiento | Cómo se cumple en la navegación |
|---|---|---|
| **RF01** | **Inicio de sesión.** El sistema valida que el correo termine en `@tecsup.edu.pe`, que ningún campo esté vacío y que la contraseña tenga al menos 6 caracteres; muestra el error correspondiente. | `startDestination = login`. Al ingresar: `navigate("home") { popUpTo("login") { inclusive = true } }` → el botón *atrás* ya no regresa al login. |
| **RF02** | **Menú principal.** Saluda al usuario por su nombre (obtenido del correo) y ofrece ir al listado de alumnos y al perfil. | `navigate(Screen.List.route)` y `navigate(Screen.Profile.route)` — rutas simples. |
| **RF03** | **Listado de alumnos con búsqueda.** Muestra 8 alumnos en un `LazyColumn` y los filtra en tiempo real por nombre o carrera; si no hay coincidencias muestra un mensaje. | Cada ítem navega con `Screen.Detail.createRoute(alumno.id)`; la flecha usa `popBackStack()`. |
| **RF04** | **Detalle del alumno.** Recibe el ID como argumento tipado `Int` y muestra nombre, carrera, correo y ciclo; si el ID no existe muestra un mensaje de error. | Ruta con argumento `detail/{itemId}` + `navArgument("itemId") { type = NavType.IntType }`. |
| **RF05** | **Perfil y cierre de sesión.** Muestra los datos del usuario, permite volver al inicio sin duplicar pantallas y cerrar sesión. | *Ir al inicio*: `popUpTo("home") { inclusive = true }`. *Cerrar sesión*: `popUpTo(navController.graph.id) { inclusive = true }` → limpia todo el back stack y vuelve al login. |

**Credenciales de prueba:** cualquier correo `nombre.apellido@tecsup.edu.pe` y una contraseña de 6 o más caracteres (ej. `juan.leon@tecsup.edu.pe` / `123456`).

## Estructura

```
com.example.semana05_navegacion
├── data/Alumno.kt            ← modelo, repositorio, sesión y validador (RF01, RF03)
├── navigation/
│   ├── Screen.kt             ← sealed class con las 5 rutas
│   └── AppNavigation.kt      ← NavHost
├── screens/
│   ├── LoginScreen.kt        ← RF01
│   ├── HomeScreen.kt         ← RF02
│   ├── ListScreen.kt         ← RF03
│   ├── DetailScreen.kt       ← RF04
│   └── ProfileScreen.kt      ← RF05
└── MainActivity.kt
```

## Cómo abrir

Android Studio → File → Open → seleccionar esta carpeta (no la subcarpeta `app`) → Gradle Sync → Run.

## Dependencias (ya incluidas en app/build.gradle.kts)

```kotlin
implementation("androidx.navigation:navigation-compose:2.7.7")
```
