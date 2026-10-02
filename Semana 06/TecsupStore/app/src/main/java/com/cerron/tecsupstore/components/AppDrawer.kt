package com.cerron.tecsupstore.components

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
fun AppDrawer() {

    ModalDrawerSheet {

        Text(
            text = "TECSUP Store",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(24.dp)
        )

        HorizontalDivider()

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        NavigationDrawerItem(
            label = {
                Text("Inicio")
            },
            selected = true,
            onClick = {
                // La navegación se implementará en el siguiente hito
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Mis pedidos")
            },
            selected = false,
            onClick = {
                // La navegación se implementará en el siguiente hito
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Favoritos")
            },
            selected = false,
            onClick = {
                // La navegación se implementará en el siguiente hito
            }
        )

        NavigationDrawerItem(
            label = {
                Text("Perfil")
            },
            selected = false,
            onClick = {
                // La navegación se implementará en el siguiente hito
            }
        )
    }
}


