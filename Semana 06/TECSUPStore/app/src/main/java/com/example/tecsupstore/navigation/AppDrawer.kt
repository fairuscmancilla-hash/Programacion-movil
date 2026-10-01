package com.example.tecsupstore.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Icon(
                imageVector = Icons.Default.AccountCircle,
                contentDescription = "Usuario"
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Yajaira Cerron",
                style = MaterialTheme.typography.titleMedium
            )

            Text(
                text = "Estudiante TECSUP",
                style = MaterialTheme.typography.bodyMedium
            )

            Spacer(modifier = Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(modifier = Modifier.height(16.dp))

            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Home,
                        contentDescription = "Inicio"
                    )
                },
                label = {
                    Text("Inicio")
                },
                selected = pantallaActual == "Inicio",
                onClick = onInicioClick
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Mis pedidos"
                    )
                },
                label = {
                    Text("Mis pedidos")
                },
                selected = pantallaActual == "Mis pedidos",
                onClick = onPedidosClick
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Favoritos"
                    )
                },
                label = {
                    Text("Favoritos")
                },
                selected = pantallaActual == "Favoritos",
                onClick = onFavoritosClick
            )

            NavigationDrawerItem(
                icon = {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Perfil"
                    )
                },
                label = {
                    Text("Perfil")
                },
                selected = pantallaActual == "Perfil",
                onClick = onPerfilClick
            )
        }
    }
}