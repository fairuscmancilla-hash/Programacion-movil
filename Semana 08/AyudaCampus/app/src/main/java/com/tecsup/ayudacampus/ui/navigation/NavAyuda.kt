package com.tecsup.ayudacampus.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.isImeVisible
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.BarChart
import androidx.compose.material.icons.outlined.LocalOffer
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.ayudacampus.AyudaApp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.cliente.LoginScreen
import com.tecsup.ayudacampus.ui.cliente.MisTicketsScreen
import com.tecsup.ayudacampus.ui.cliente.RegistroScreen
import com.tecsup.ayudacampus.ui.cliente.SplashScreen
import com.tecsup.ayudacampus.ui.soporte.CategoriasScreen
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

private data class ItemNav(
    val ruta: String,
    val etiqueta: String,
    val icono: ImageVector,
    val activoEn: Set<String>
)

private val itemsCliente = listOf(
    ItemNav(Rutas.MIS_TICKETS, "Tickets", Icons.AutoMirrored.Filled.List, setOf(Rutas.MIS_TICKETS, Rutas.DETALLE)),
    ItemNav(Rutas.NUEVO_TICKET, "Nuevo", Icons.Filled.Add, setOf(Rutas.NUEVO_TICKET)),
    ItemNav(Rutas.PERFIL, "Perfil", Icons.Outlined.Person, setOf(Rutas.PERFIL))
)

private val itemsSoporte = listOf(
    ItemNav(Rutas.PANEL, "Panel", Icons.Outlined.BarChart, setOf(Rutas.PANEL)),
    ItemNav(Rutas.bandeja(), "Tickets", Icons.AutoMirrored.Filled.List, setOf(Rutas.BANDEJA, Rutas.ATENDER)),
    ItemNav(Rutas.CATEGORIAS, "Categorías", Icons.Outlined.LocalOffer, setOf(Rutas.CATEGORIAS)),
    ItemNav(Rutas.AJUSTES, "Ajustes", Icons.Outlined.Settings, setOf(Rutas.AJUSTES))
)

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NavAyuda(sesionVm: SesionViewModel) {
    val nav = rememberNavController()
    val app = LocalContext.current.applicationContext as AyudaApp
    val usuario by sesionVm.usuarioActual.collectAsState()
    val entrada by nav.currentBackStackEntryAsState()
    val ruta = entrada?.destination?.route

    fun fabrica(id: Int = 0, filtro: String = "") =
        AyudaViewModelFactory(app.repository, app.preferencias, usuario, id, filtro)

    // Entra al grafo del perfil y limpia la pila (no se puede volver al login con "atrás")
    fun irAGrafo(rol: Rol) {
        nav.navigate(if (rol == Rol.ADMIN) Rutas.GRAFO_SOPORTE else Rutas.GRAFO_CLIENTE) {
            popUpTo(nav.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    fun irALogin() {
        nav.navigate(Rutas.LOGIN) {
            popUpTo(nav.graph.id) { inclusive = true }
            launchSingleTop = true
        }
    }

    val cambiarPerfil = { sesionVm.cambiarPerfil { irAGrafo(it) } }
    val cerrarSesion = { sesionVm.cerrarSesion { irALogin() } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = { if (!WindowInsets.isImeVisible) BarraInferior(ruta, nav) }
    ) { padding ->
        NavHost(
            navController = nav,
            startDestination = Rutas.SPLASH,
            modifier = Modifier.padding(padding).consumeWindowInsets(padding)
        ) {
            composable(Rutas.SPLASH) {
                SplashScreen(sesionVm, onIrLogin = { irALogin() }, onIrPerfil = { irAGrafo(it) })
            }
            composable(Rutas.LOGIN) {
                LoginScreen(sesionVm, onIngreso = { irAGrafo(it) }, onRegistro = { nav.navigate(Rutas.REGISTRO) })
            }
            composable(Rutas.REGISTRO) {
                RegistroScreen(sesionVm, onAtras = { nav.popBackStack() }, onRegistrado = { irAGrafo(it) })
            }

            // ---------- Estudiante ----------
            navigation(startDestination = Rutas.MIS_TICKETS, route = Rutas.GRAFO_CLIENTE) {
                composable(Rutas.MIS_TICKETS) {
                    ConUsuario(usuario) { u ->
                        val vm: MisTicketsViewModel = viewModel(key = "mis_${u.id}", factory = fabrica())
                        MisTicketsScreen(
                            vm, u,
                            onAbrir = { nav.navigate(Rutas.detalle(it)) },
                            onNuevo = { nav.navigate(Rutas.NUEVO_TICKET) { launchSingleTop = true } },
                        )
                    }
                }
                composable(Rutas.NUEVO_TICKET) { Pendiente("Nuevo ticket", "Paso 8") }
                composable(Rutas.DETALLE) { Pendiente("Detalle del ticket", "Paso 9") }
                composable(Rutas.PERFIL) { Pendiente("Perfil", "Paso 12") }
            }

            // ---------- Soporte TI ----------
            navigation(startDestination = Rutas.PANEL, route = Rutas.GRAFO_SOPORTE) {
                composable(Rutas.PANEL) { Pendiente("Panel de soporte", "Paso 10") }
                composable(Rutas.BANDEJA) { Pendiente("Bandeja de tickets", "Paso 10") }
                composable(Rutas.ATENDER) { Pendiente("Atender ticket", "Paso 11") }
                composable(Rutas.CATEGORIAS) {
                    val vm: CategoriasViewModel = viewModel(factory = fabrica())
                    CategoriasScreen(
                        vm,
                        onAtras = { nav.popBackStack() },
                        onEditar = { nav.navigate(Rutas.formCategoria(it)) }
                    )
                }
                composable(Rutas.FORM_CATEGORIA) { Pendiente("Formulario de categoría", "Paso 11") }
                composable(Rutas.AJUSTES) { Pendiente("Ajustes", "Paso 12") }
            }
        }
    }
}

/** Muestra el contenido solo cuando hay un usuario con sesión. */
@Composable
private fun ConUsuario(usuario: Usuario?, contenido: @Composable (Usuario) -> Unit) {
    if (usuario == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { CircularProgressIndicator() }
    } else {
        contenido(usuario)
    }
}

/** 3 ítems para el estudiante y 4 para Soporte; se oculta en splash, login y formularios. */
@Composable
private fun BarraInferior(ruta: String?, nav: NavHostController) {
    val (items, inicio) = when {
        itemsCliente.any { ruta in it.activoEn } -> itemsCliente to Rutas.MIS_TICKETS
        itemsSoporte.any { ruta in it.activoEn } -> itemsSoporte to Rutas.PANEL
        else -> return
    }
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        items.forEach { item ->
            NavigationBarItem(
                selected = ruta in item.activoEn,
                onClick = {
                    nav.navigate(item.ruta) {
                        popUpTo(inicio)
                        launchSingleTop = true
                    }
                },
                icon = { Icon(item.icono, contentDescription = item.etiqueta) },
                label = { Text(item.etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    }
}

/** Pantalla que todavía no se construye en este paso del laboratorio. */
@Composable
private fun Pendiente(titulo: String, paso: String) {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("$titulo: se construye en el $paso", color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}
