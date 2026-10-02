package com.cerron.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cerron.tecsupstore.components.AppDrawer
import com.cerron.tecsupstore.model.Producto
import com.cerron.tecsupstore.screens.FavoritosScreen
import com.cerron.tecsupstore.screens.InicioScreen
import com.cerron.tecsupstore.screens.PedidosScreen
import com.cerron.tecsupstore.screens.PerfilScreen
import com.cerron.tecsupstore.ui.theme.TecsupstoreTheme
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import com.cerron.tecsupstore.ui.theme.MoradoPrincipal

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {

            TecsupstoreTheme {

                val drawerState = rememberDrawerState(
                    initialValue = DrawerValue.Closed
                )

                val scope = rememberCoroutineScope()

                var pantallaActual by remember {
                    mutableStateOf("Inicio")
                }

                // Lista observable de productos favoritos
                val favoritos = remember {
                    mutableStateListOf<Producto>()
                }

                ModalNavigationDrawer(
                    drawerState = drawerState,

                    drawerContent = {

                        AppDrawer(
                            pantallaActual = pantallaActual,
                            cantidadFavoritos = favoritos.size,

                            onInicioClick = {
                                pantallaActual = "Inicio"

                                scope.launch {
                                    drawerState.close()
                                }
                            },

                            onPedidosClick = {
                                pantallaActual = "Mis pedidos"

                                scope.launch {
                                    drawerState.close()
                                }
                            },

                            onFavoritosClick = {
                                pantallaActual = "Favoritos"

                                scope.launch {
                                    drawerState.close()
                                }
                            },

                            onPerfilClick = {
                                pantallaActual = "Perfil"

                                scope.launch {
                                    drawerState.close()
                                }
                            },

                            onCerrarSesionClick = {
                                pantallaActual = "Inicio"

                                scope.launch {
                                    drawerState.close()
                                }
                            }
                        )
                    }
                ) {

                    Scaffold(
                        modifier = Modifier.fillMaxSize(),

                        topBar = {

                            TopAppBar(
                                title = {
                                    Column {
                                        Text(
                                            text = if (pantallaActual == "Inicio") {
                                                "TECSUP Store"
                                            } else {
                                                pantallaActual
                                            },
                                            fontWeight = FontWeight.Bold,
                                            color = Color.White
                                        )

                                        if (pantallaActual == "Inicio") {
                                            Text(
                                                text = "Más vendidos",
                                                style = MaterialTheme.typography.labelSmall,
                                                color = Color.White.copy(alpha = 0.8f)
                                            )
                                        }
                                    }
                                },

                                navigationIcon = {
                                    IconButton(
                                        onClick = {
                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    ) {
                                        Text(
                                            text = "☰",
                                            color = Color.White
                                        )
                                    }
                                },

                                colors = TopAppBarDefaults.topAppBarColors(
                                    containerColor = MoradoPrincipal,
                                    titleContentColor = Color.White,
                                    navigationIconContentColor = Color.White
                                )
                            )
                        }
                    ) { innerPadding ->

                        when (pantallaActual) {

                            "Inicio" -> {

                                InicioScreen(
                                    favoritos = favoritos,

                                    onAgregarFavorito = { producto ->

                                        val yaExiste = favoritos.any {
                                            it.id == producto.id
                                        }

                                        if (!yaExiste) {
                                            favoritos.add(producto)
                                        }
                                    },

                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                )
                            }

                            "Mis pedidos" -> {

                                PedidosScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                )
                            }

                            "Favoritos" -> {

                                FavoritosScreen(
                                    favoritos = favoritos,

                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                )
                            }

                            "Perfil" -> {

                                PerfilScreen(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(innerPadding)
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
