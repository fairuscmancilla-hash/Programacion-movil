package com.example.tecsupstore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import com.example.tecsupstore.navigation.AppDrawer
import com.example.tecsupstore.navigation.AppNavegacion
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {

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
                            pantallaActual = pantallaActual,

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
                                        Icon(
                                            imageVector = Icons.Default.Menu,
                                            contentDescription = "Abrir menú"
                                        )
                                    }
                                }
                            )
                        }
                    ) { innerPadding ->

                        Box(
                            modifier = Modifier.padding(innerPadding)
                        ) {
                            AppNavegacion(
                                pantallaActual = pantallaActual
                            )
                        }
                    }
                }
            }

        }
    }
}
