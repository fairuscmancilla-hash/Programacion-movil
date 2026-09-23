package com.example.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.AlumnoRepository
import com.example.semana05_navegacion.data.iniciales

// RF04 — Expediente académico recibido como argumento Int (versión con IA)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(navController: NavController, itemId: Int) {
    val alumno = AlumnoRepository.buscarPorId(itemId)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Expediente Académico", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        if (alumno == null) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("No existe un alumno con ID $itemId", color = MaterialTheme.colorScheme.error)
            }
        } else {
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Cabecera con degradado y avatar superpuesto
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(170.dp),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(110.dp)
                            .align(Alignment.TopCenter)
                            .background(PortalColors.cabecera, RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
                    )
                    FotoAlumno(alumno.foto, alumno.iniciales, size = 110.dp, conBorde = true)
                }
                Spacer(modifier = Modifier.height(12.dp))
                Text(alumno.nombre, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                Text(alumno.carrera, color = PortalColors.Primario, style = MaterialTheme.typography.bodyMedium)
                Spacer(modifier = Modifier.height(20.dp))

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFF6F3FC))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        FilaInfo(Icons.Filled.Badge, "ID Estudiante (argumento recibido)", "2026-%04d".format(itemId))
                        FilaInfo(Icons.Filled.Email, "Correo electrónico", alumno.correo)
                        FilaInfo(Icons.Filled.School, "Carrera", alumno.carrera)
                        FilaInfo(Icons.Filled.CalendarMonth, "Ciclo actual", alumno.ciclo)
                        HorizontalDivider(modifier = Modifier.padding(vertical = 10.dp))
                        Text("Biografía", fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(alumno.bio, style = MaterialTheme.typography.bodyMedium, color = Color.DarkGray)
                    }
                }
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}
