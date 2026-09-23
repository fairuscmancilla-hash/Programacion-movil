package com.example.semana05_navegacion.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.AlumnoRepository

// RF04 — Detalle del alumno recibido como argumento tipado Int (detail/{itemId})
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) {
    val alumno = AlumnoRepository.buscarPorId(itemId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del alumno") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
        ) {
            if (alumno == null) {
                // Validación: el id recibido no existe
                Text(
                    text = "No existe un alumno con ID $itemId",
                    color = MaterialTheme.colorScheme.error
                )
            } else {
                Text(
                    text = alumno.nombre,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(12.dp))
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("ID recibido: $itemId", style = MaterialTheme.typography.bodyLarge)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Carrera: ${alumno.carrera}")
                        Text("Correo: ${alumno.correo}")
                        Text("Ciclo: ${alumno.ciclo}")
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Este valor llegó como argumento tipado Int desde el NavHost.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
