package com.example.tecsupstore.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AppDrawer(
    onInicioClick: () -> Unit = {},
    onPedidosClick: () -> Unit = {},
    onFavoritosClick: () -> Unit = {},
    onPerfilClick: () -> Unit = {}
) {
    ModalDrawerSheet {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text("TECSUP Store")

            Spacer(modifier = Modifier.height(16.dp))

            NavigationDrawerItem(
                label = { Text("Inicio") },
                selected = true,
                onClick = onInicioClick
            )

            NavigationDrawerItem(
                label = { Text("Mis pedidos") },
                selected = false,
                onClick = onPedidosClick
            )

            NavigationDrawerItem(
                label = { Text("Favoritos") },
                selected = false,
                onClick = onFavoritosClick
            )

            NavigationDrawerItem(
                label = { Text("Perfil") },
                selected = false,
                onClick = onPerfilClick
            )
        }
    }
}

