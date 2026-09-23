package com.example.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.Sesion
import com.example.semana05_navegacion.navigation.Screen

// RF02 — Menú principal (versión con IA: fondo degradado y tarjetas de opción)
@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(PortalColors.fondoHome)
            .padding(horizontal = 24.dp, vertical = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(48.dp))
        Text(
            text = "Bienvenido,\n${Sesion.nombre}",
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "¿Qué deseas gestionar hoy?",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.White.copy(alpha = 0.85f)
        )
        Spacer(modifier = Modifier.height(24.dp))

        OpcionMenu(
            icono = Icons.Filled.Groups,
            titulo = "Directorio de Alumnos",
            subtitulo = "Ver y buscar estudiantes",
            onClick = { navController.navigate(Screen.List.route) }
        )
        Spacer(modifier = Modifier.height(14.dp))
        OpcionMenu(
            icono = Icons.Filled.Person,
            titulo = "Mi Perfil Académico",
            subtitulo = "Datos personales y cuenta",
            onClick = { navController.navigate(Screen.Profile.route) }
        )

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = {
                Sesion.cerrar()
                navController.navigate(Screen.Login.route) {
                    popUpTo(navController.graph.id) { inclusive = true }
                }
            }
        ) {
            Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null, tint = PortalColors.Peligro)
            Spacer(modifier = Modifier.width(8.dp))
            Text("Cerrar Sesión Segura", color = PortalColors.Peligro, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
private fun OpcionMenu(icono: ImageVector, titulo: String, subtitulo: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(18.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(PortalColors.Lavanda),
                contentAlignment = Alignment.Center
            ) {
                Icon(icono, contentDescription = null, tint = PortalColors.Primario)
            }
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(titulo, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleMedium)
                Text(subtitulo, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
            }
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
        }
    }
}
