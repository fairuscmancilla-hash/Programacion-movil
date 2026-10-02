package com.cerron.tecsupstore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Badge
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    pantallaActual: String,
    cantidadFavoritos: Int,
    onInicioClick: () -> Unit,
    onPedidosClick: () -> Unit,
    onFavoritosClick: () -> Unit,
    onPerfilClick: () -> Unit
) {

    ModalDrawerSheet {

        // Encabezado del usuario
        Column(
            modifier = Modifier.padding(
                start = 24.dp,
                end = 24.dp,
                top = 32.dp,
                bottom = 20.dp
            )
        ) {

            Text(
                text = "TECSUP Store",
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            Text(
                text = "Yajaira Cerron",
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "yajaira@tecsup.edu.pe"
            )
        }

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        NavigationDrawerItem(
            label = {
                Text("Inicio")
            },
            selected = pantallaActual == "Inicio",
            onClick = onInicioClick
        )

        NavigationDrawerItem(
            label = {
                Text("Mis pedidos")
            },
            selected = pantallaActual == "Mis pedidos",
            onClick = onPedidosClick
        )

        NavigationDrawerItem(
            label = {

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Text("Favoritos")

                    if (cantidadFavoritos > 0) {

                        Badge(
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text(
                                text = cantidadFavoritos.toString()
                            )
                        }
                    }
                }
            },

            selected = pantallaActual == "Favoritos",
            onClick = onFavoritosClick
        )

        NavigationDrawerItem(
            label = {
                Text("Perfil")
            },
            selected = pantallaActual == "Perfil",
            onClick = onPerfilClick
        )
    }
}
