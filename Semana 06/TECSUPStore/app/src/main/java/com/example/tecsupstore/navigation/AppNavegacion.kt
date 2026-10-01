package com.example.tecsupstore.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.tecsupstore.screens.InicioScreen

@Composable
fun AppNavegacion(
    pantallaActual: String
) {
    when (pantallaActual) {

        "Inicio" -> {
            InicioScreen()
        }

        "Mis pedidos" -> {
            PantallaTemporal("Mis pedidos")
        }

        "Favoritos" -> {
            PantallaTemporal("Favoritos")
        }

        "Perfil" -> {
            PantallaTemporal("Perfil")
        }
    }
}

@Composable
fun PantallaTemporal(
    titulo: String
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = titulo)
    }
}