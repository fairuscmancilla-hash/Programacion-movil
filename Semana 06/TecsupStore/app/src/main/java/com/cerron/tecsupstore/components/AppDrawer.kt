package com.cerron.tecsupstore.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cerron.tecsupstore.ui.theme.MoradoClaro
import com.cerron.tecsupstore.ui.theme.MoradoPrincipal
import com.cerron.tecsupstore.ui.theme.MoradoSeleccionado

@Composable
fun AppDrawer(
    pantallaActual: String,
    cantidadFavoritos: Int,
    onInicioClick: () -> Unit,
    onPedidosClick: () -> Unit,
    onFavoritosClick: () -> Unit,
    onPerfilClick: () -> Unit,
    onCerrarSesionClick: () -> Unit
) {

    ModalDrawerSheet {

        // =========================
        // ENCABEZADO
        // =========================
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = 28.dp,
                    bottom = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically
        ) {

            // Avatar
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .background(
                        color = MoradoClaro,
                        shape = CircleShape
                    ),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "YC",
                    color = MoradoPrincipal,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            Spacer(
                modifier = Modifier.size(14.dp)
            )

            Column {

                Text(
                    text = "Yajaira Cerron",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = "yajaira@tecsup.edu.pe",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(
            modifier = Modifier.height(14.dp)
        )

        // =========================
        // INICIO
        // =========================
        NavigationDrawerItem(
            icon = {
                DrawerIcon(
                    symbol = "○",
                    seleccionado = pantallaActual == "Inicio"
                )
            },
            label = {
                Text("Inicio")
            },
            selected = pantallaActual == "Inicio",
            onClick = onInicioClick,
            colors = drawerItemColors(),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // =========================
        // MIS PEDIDOS
        // =========================
        NavigationDrawerItem(
            icon = {
                DrawerIcon(
                    symbol = "○",
                    seleccionado = pantallaActual == "Mis pedidos"
                )
            },
            label = {
                Text("Mis pedidos")
            },
            selected = pantallaActual == "Mis pedidos",
            onClick = onPedidosClick,
            colors = drawerItemColors(),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // =========================
        // FAVORITOS
        // =========================
        NavigationDrawerItem(
            icon = {
                DrawerIcon(
                    symbol = "♡",
                    seleccionado = pantallaActual == "Favoritos"
                )
            },

            label = {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text("Favoritos")

                    if (cantidadFavoritos > 0) {

                        Badge(
                            containerColor = MoradoPrincipal,
                            contentColor = Color.White
                        ) {

                            Text(
                                text = cantidadFavoritos.toString()
                            )
                        }
                    }
                }
            },

            selected = pantallaActual == "Favoritos",
            onClick = onFavoritosClick,
            colors = drawerItemColors(),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // =========================
        // PERFIL
        // =========================
        NavigationDrawerItem(
            icon = {
                DrawerIcon(
                    symbol = "○",
                    seleccionado = pantallaActual == "Perfil"
                )
            },
            label = {
                Text("Perfil")
            },
            selected = pantallaActual == "Perfil",
            onClick = onPerfilClick,
            colors = drawerItemColors(),
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        // Separador antes de cerrar sesión
        Spacer(
            modifier = Modifier.height(8.dp)
        )

        HorizontalDivider(
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        // =========================
        // CERRAR SESIÓN
        // =========================
        NavigationDrawerItem(
            icon = {
                Text(
                    text = "↪",
                    color = Color.DarkGray,
                    style = MaterialTheme.typography.titleLarge
                )
            },
            label = {
                Text("Cerrar sesión")
            },
            selected = false,
            onClick = onCerrarSesionClick,
            colors = drawerItemColors(),
            modifier = Modifier.padding(horizontal = 12.dp)
        )
    }
}


@Composable
private fun DrawerIcon(
    symbol: String,
    seleccionado: Boolean
) {

    Text(
        text = if (seleccionado) "●" else symbol,
        color = if (seleccionado) {
            MoradoPrincipal
        } else {
            Color.DarkGray
        },
        style = MaterialTheme.typography.titleLarge
    )
}


@Composable
private fun drawerItemColors() =
    NavigationDrawerItemDefaults.colors(
        selectedContainerColor = MoradoSeleccionado,
        selectedTextColor = MoradoPrincipal,
        selectedIconColor = MoradoPrincipal,

        unselectedContainerColor = Color.Transparent,
        unselectedTextColor = Color.DarkGray,
        unselectedIconColor = Color.DarkGray
    )