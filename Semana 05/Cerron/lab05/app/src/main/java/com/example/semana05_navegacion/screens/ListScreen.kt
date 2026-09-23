package com.example.semana05_navegacion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.AlumnoRepository
import com.example.semana05_navegacion.data.iniciales
import com.example.semana05_navegacion.navigation.Screen

// RF03 — Directorio de alumnos con búsqueda (versión con IA: tarjetas con avatar)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    var busqueda by remember { mutableStateOf("") }
    val alumnos = AlumnoRepository.filtrar(busqueda)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Directorio de Alumnos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = PortalColors.Lavanda)
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                placeholder = { Text("Buscar por nombre o carrera") },
                leadingIcon = { Icon(Icons.Filled.Search, contentDescription = null) },
                trailingIcon = {
                    if (busqueda.isNotEmpty()) {
                        IconButton(onClick = { busqueda = "" }) {
                            Icon(Icons.Filled.Clear, contentDescription = "Limpiar")
                        }
                    }
                },
                singleLine = true,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            Text(
                text = "${alumnos.size} alumno(s) encontrado(s)",
                style = MaterialTheme.typography.labelMedium,
                color = Color.Gray,
                modifier = Modifier.padding(horizontal = 20.dp)
            )

            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth(),
                contentPadding = PaddingValues(start = 16.dp, top = 16.dp, end = 16.dp, bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(alumnos, key = { it.id }) { alumno ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { navController.navigate(Screen.Detail.createRoute(alumno.id)) },
                        shape = RoundedCornerShape(16.dp),
                        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            FotoAlumno(alumno.foto, alumno.iniciales, size = 52.dp)
                            Spacer(modifier = Modifier.width(14.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(alumno.nombre, fontWeight = FontWeight.Bold)
                                Text(
                                    alumno.carrera,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = PortalColors.Primario
                                )
                            }
                            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = null, tint = Color.Gray)
                        }
                    }
                }
            }
        }
    }
}
