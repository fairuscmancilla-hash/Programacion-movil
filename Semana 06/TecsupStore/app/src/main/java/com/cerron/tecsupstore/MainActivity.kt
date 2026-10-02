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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import com.cerron.tecsupstore.components.AppDrawer
import com.cerron.tecsupstore.screens.FavoritosScreen
import com.cerron.tecsupstore.screens.InicioScreen
import com.cerron.tecsupstore.screens.PedidosScreen
import com.cerron.tecsupstore.screens.PerfilScreen
import com.cerron.tecsupstore.ui.theme.TecsupstoreTheme
import kotlinx.coroutines.launch

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

                ModalNavigationDrawer(
                    drawerState = drawerState,

                    drawerContent = {

                        AppDrawer(

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
                            }
                        )
                    }
                ) {

                    Scaffold(

                        modifier = Modifier.fillMaxSize(),

                        topBar = {

                            TopAppBar(

                                title = {
                                    Text(pantallaActual)
                                },

                                navigationIcon = {

                                    IconButton(
                                        onClick = {

                                            scope.launch {
                                                drawerState.open()
                                            }
                                        }
                                    ) {

                                        Text("☰")
                                    }
                                }
                            )
                        }

                    ) { innerPadding ->

                        when (pantallaActual) {

                            "Inicio" -> {

                                InicioScreen(
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