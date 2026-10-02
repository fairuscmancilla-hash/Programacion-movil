package com.cerron.tecsupstore.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    pantallaActual: String,
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
                Text("Favoritos")
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
