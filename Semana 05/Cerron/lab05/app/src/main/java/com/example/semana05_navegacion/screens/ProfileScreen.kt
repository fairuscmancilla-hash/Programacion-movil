package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.Sesion
import com.example.semana05_navegacion.navigation.Screen

// RF05 — Perfil del usuario y cierre de sesión (limpia el back stack)
@Composable
fun ProfileScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Mi Perfil",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = Sesion.nombre,
            style = MaterialTheme.typography.bodyLarge
        )
        Text(
            text = Sesion.correo,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(
            onClick = {
                navController.navigate(Screen.Home.route) {
                    // Limpia el back stack — evita apilar Homes
                    popUpTo(Screen.Home.route) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Ir al inicio")
        }
        Spacer(modifier = Modifier.height(12.dp))
        OutlinedButton(
            onClick = {
                Sesion.cerrar()
                navController.navigate(Screen.Login.route) {
                    // Borra TODO el historial: no se puede volver atrás sin iniciar sesión
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Cerrar sesión")
        }
    }
}
