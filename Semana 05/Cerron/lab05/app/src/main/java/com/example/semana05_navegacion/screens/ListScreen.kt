package com.example.semana05_navegacion.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.semana05_navegacion.data.AlumnoRepository
import com.example.semana05_navegacion.navigation.Screen

// RF03 — Lista de alumnos con búsqueda en tiempo real por nombre o carrera
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(navController: NavController) {
    var busqueda by remember { mutableStateOf("") }
    val alumnos = AlumnoRepository.filtrar(busqueda)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Lista de alumnos") },
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
        Column(modifier = Modifier.padding(padding)) {
            OutlinedTextField(
                value = busqueda,
                onValueChange = { busqueda = it },
                label = { Text("Buscar por nombre o carrera") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            )
            if (alumnos.isEmpty()) {
                Text(
                    text = "No se encontraron alumnos",
                    modifier = Modifier.padding(16.dp)
                )
            }
            LazyColumn {
                items(alumnos) { alumno ->
                    ListItem(
                        headlineContent = { Text(alumno.nombre) },
                        supportingContent = { Text(alumno.carrera) },
                        modifier = Modifier.clickable {
                            navController.navigate(Screen.Detail.createRoute(alumno.id))
                        }
                    )
                    HorizontalDivider()
                }
            }
        }
    }
}
