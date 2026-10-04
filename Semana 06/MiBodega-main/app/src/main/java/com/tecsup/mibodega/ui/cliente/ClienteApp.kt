package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Pedido
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.MisPedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen

private object Rutas {

    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val INICIO = "inicio"

    const val DETALLE = "detalle/{productoId}"

    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"

    const val MIS_PEDIDOS = "mis_pedidos"
    const val FAVORITOS = "favoritos"
    const val PERFIL = "perfil"

    fun detalle(productoId: Int): String {
        return "detalle/$productoId"
    }
}


@Composable
fun ClienteApp(
    modoOscuro: Boolean,
    onModoOscuroChange: (Boolean) -> Unit
) {

    val navController = rememberNavController()

    // ---------------------------------------------
    // ESTADOS GENERALES
    // ---------------------------------------------

    var carrito by remember {
        mutableStateOf<List<ItemCarrito>>(emptyList())
    }

    var pedidos by remember {
        mutableStateOf<List<Pedido>>(emptyList())
    }

    var favoritos by remember {
        mutableStateOf<List<Producto>>(emptyList())
    }


    // ---------------------------------------------
    // NAVEGACIÓN
    // ---------------------------------------------

    NavHost(
        navController = navController,
        startDestination = Rutas.BIENVENIDA
    ) {

        // -----------------------------------------
        // BIENVENIDA
        // -----------------------------------------

        composable(Rutas.BIENVENIDA) {

            BienvenidaScreen(

                onRegistrarse = {
                    navController.navigate(Rutas.REGISTRO)
                },

                onIniciarSesion = {
                    navController.navigate(Rutas.LOGIN)
                },

                onTerminos = {
                    // TODO: abrir términos y condiciones
                }
            )
        }


        // -----------------------------------------
        // LOGIN
        // -----------------------------------------

        composable(Rutas.LOGIN) {

            LoginScreen(

                onLoginCorrecto = {

                    navController.navigate(Rutas.INICIO) {

                        popUpTo(Rutas.BIENVENIDA) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        // -----------------------------------------
        // REGISTRO
        // -----------------------------------------

        composable(Rutas.REGISTRO) {

            RegistroScreen(

                onVolver = {
                    navController.popBackStack()
                },

                onCrearCuenta = {
                        nombre,
                        telefono,
                        direccion,
                        referencia ->

                    navController.navigate(Rutas.INICIO) {

                        popUpTo(Rutas.BIENVENIDA) {
                            inclusive = true
                        }
                    }
                }
            )
        }


        // -----------------------------------------
        // INICIO
        // -----------------------------------------

        composable(Rutas.INICIO) {

            InicioScreen(

                cantidadCarrito = carrito.sumOf {
                    it.cantidad
                },

                onVerCarrito = {
                    navController.navigate(Rutas.CARRITO)
                },

                onProductoClick = { producto ->

                    navController.navigate(
                        Rutas.detalle(producto.id)
                    )
                },

                onAgregarProducto = { producto ->

                    carrito = agregarOSumarProducto(
                        carrito = carrito,
                        producto = producto,
                        cantidad = 1
                    )
                },

                onVerPedidos = {
                    navController.navigate(Rutas.MIS_PEDIDOS)
                },

                onVerFavoritos = {
                    navController.navigate(Rutas.FAVORITOS)
                },

                // NUEVO: ABRIR PERFIL
                onVerPerfil = {
                    navController.navigate(Rutas.PERFIL)
                },

                favoritos = favoritos,

                onFavoritoClick = { producto ->

                    val yaEsFavorito =
                        favoritos.any { favorito ->
                            favorito.id == producto.id
                        }

                    favoritos =
                        if (yaEsFavorito) {

                            favoritos.filterNot { favorito ->
                                favorito.id == producto.id
                            }

                        } else {

                            favoritos + producto
                        }
                }
            )
        }


        // -----------------------------------------
        // DETALLE DEL PRODUCTO
        // -----------------------------------------

        composable(route = Rutas.DETALLE,

            arguments = listOf(

                navArgument("productoId") {
                    type = NavType.IntType
                }
            )

        ) { backStackEntry ->

            val productoId =
                backStackEntry.arguments
                    ?.getInt("productoId")
                    ?: 0

            val producto =
                listaProductosFake.first {
                    it.id == productoId
                }

            DetalleProductoScreen(

                producto = producto,

                onVolver = {
                    navController.popBackStack()
                },

                onAgregarAlCarrito = {
                        productoSeleccionado,
                        cantidad ->

                    carrito = agregarOSumarProducto(
                        carrito = carrito,
                        producto = productoSeleccionado,
                        cantidad = cantidad
                    )

                    navController.popBackStack()
                }
            )
        }


        // -----------------------------------------
        // CARRITO
        // -----------------------------------------

        composable(Rutas.CARRITO) {

            CarritoScreen(

                carrito = carrito,

                onVolver = {
                    navController.popBackStack()
                },

                onIncrementar = { producto ->

                    carrito = carrito.map { item ->

                        if (item.producto.id == producto.id) {

                            item.copy(
                                cantidad = item.cantidad + 1
                            )

                        } else {

                            item
                        }
                    }
                },

                onDecrementar = { producto ->

                    carrito = carrito.mapNotNull { item ->

                        when {

                            item.producto.id != producto.id -> {
                                item
                            }

                            item.cantidad > 1 -> {

                                item.copy(
                                    cantidad = item.cantidad - 1
                                )
                            }

                            else -> {
                                null
                            }
                        }
                    }
                },

                onEliminar = { producto ->

                    carrito =
                        carrito.filterNot { item ->
                            item.producto.id == producto.id
                        }
                },

                onContinuarPedido = {
                    navController.navigate(Rutas.ENTREGA)
                }
            )
        }


        // -----------------------------------------
        // DATOS DE ENTREGA
        // -----------------------------------------

        composable(Rutas.ENTREGA) {

            DatosEntregaScreen(

                onVolver = {
                    navController.popBackStack()
                },

                onConfirmar = {
                        nombre,
                        telefono,
                        direccion,
                        referencia,
                        tipoEntrega ->

                    val subtotal =
                        carrito.sumOf { item ->

                            item.producto.precio *
                                    item.cantidad
                        }

                    val costoEnvio =
                        if (tipoEntrega == "Delivery") {
                            4.00
                        } else {
                            0.00
                        }

                    val total =
                        subtotal + costoEnvio

                    val nuevoPedido = Pedido(

                        id = pedidos.size + 1,

                        productos = carrito.toList(),

                        total = total,

                        nombreCliente = nombre,

                        direccion = direccion,

                        estado = "Confirmado"
                    )

                    pedidos =
                        pedidos + nuevoPedido

                    navController.navigate(
                        Rutas.CONFIRMACION
                    )
                }
            )
        }


        // -----------------------------------------
        // CONFIRMACIÓN
        // -----------------------------------------

        composable(Rutas.CONFIRMACION) {

            val ultimoPedido =
                pedidos.lastOrNull()

            val totalPedido =
                ultimoPedido?.total ?: 0.0

            ConfirmacionScreen(

                total = totalPedido,

                onVerPedidos = {

                    carrito = emptyList()

                    navController.navigate(
                        Rutas.MIS_PEDIDOS
                    ) {

                        popUpTo(Rutas.CONFIRMACION) {
                            inclusive = true
                        }
                    }
                },

                onVolverInicio = {

                    carrito = emptyList()

                    navController.navigate(
                        Rutas.INICIO
                    ) {

                        popUpTo(Rutas.INICIO) {
                            inclusive = false
                        }
                    }
                }
            )
        }


        // -----------------------------------------
        // MIS PEDIDOS
        // -----------------------------------------

        composable(Rutas.MIS_PEDIDOS) {

            MisPedidosScreen(

                pedidos = pedidos,

                onVolver = {

                    navController.navigate(
                        Rutas.INICIO
                    ) {

                        popUpTo(Rutas.INICIO) {
                            inclusive = false
                        }
                    }
                }
            )
        }


        // -----------------------------------------
        // FAVORITOS
        // -----------------------------------------

        composable(Rutas.FAVORITOS) {

            FavoritosScreen(

                favoritos = favoritos,

                onVolver = {
                    navController.popBackStack()
                },

                onProductoClick = { producto ->

                    navController.navigate(
                        Rutas.detalle(producto.id)
                    )
                },

                onAgregarProducto = { producto ->

                    carrito = agregarOSumarProducto(
                        carrito = carrito,
                        producto = producto,
                        cantidad = 1
                    )
                },

                onFavoritoClick = { producto ->

                    favoritos =
                        favoritos.filterNot { favorito ->
                            favorito.id == producto.id
                        }
                }
            )
        }


        // -----------------------------------------
        // PERFIL
        // -----------------------------------------

        composable(Rutas.PERFIL) {

            PerfilScreen(
                modoOscuro = modoOscuro,

                onModoOscuroChange = { nuevoValor ->
                    onModoOscuroChange(nuevoValor)
                },

                onVolver = {
                    navController.popBackStack()
                }
            )
        }
    }
}


/**
 * Si el producto ya está en el carrito,
 * suma la cantidad.
 *
 * Si no existe, agrega un ItemCarrito nuevo.
 */
private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {

    val itemExistente =
        carrito.find { item ->
            item.producto.id == producto.id
        }

    return if (itemExistente != null) {

        carrito.map { item ->

            if (item.producto.id == producto.id) {

                item.copy(
                    cantidad = item.cantidad + cantidad
                )

            } else {

                item
            }
        }

    } else {

        carrito + ItemCarrito(
            producto = producto,
            cantidad = cantidad
        )
    }
}