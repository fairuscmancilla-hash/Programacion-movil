package com.example.semana05_navegacion.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import com.example.semana05_navegacion.data.Sesion
import com.example.semana05_navegacion.navigation.Screen

// RF05 — Configuración de perfil y cierre de sesión (versión con IA)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {
    // Si el correo coincide con un alumno registrado, mostramos sus datos académicos
    val alumno = AlumnoRepository.alumnos.find { it.correo.equals(Sesion.correo, ignoreCase = true) }
    val iniciales = Sesion.nombre.split(" ").take(2).joinToString("") { it.take(1) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración de Perfil", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Cabecera con degradado
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(PortalColors.cabeceraPerfil)
                    .padding(vertical = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (alumno != null) {
                    FotoAlumno(alumno.foto, alumno.iniciales, size = 90.dp, conBorde = true)
                } else {
                    AvatarIniciales(iniciales.ifBlank { "?" }, size = 90.dp, conBorde = true)
                }
                Spacer(modifier = Modifier.height(10.dp))
                Text(Sesion.nombre, color = Color.White, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.titleLarge)
            }

            Column(modifier = Modifier.padding(20.dp)) {
                Seccion("INFORMACIÓN PERSONAL")
                FilaInfo(Icons.Filled.Person, "Nombre", Sesion.nombre)
                FilaInfo(Icons.Filled.Email, "Correo", Sesion.correo)

                Spacer(modifier = Modifier.height(12.dp))
                Seccion("ACADÉMICO")
                FilaInfo(Icons.Filled.School, "Carrera", alumno?.carrera ?: "Diseño y Desarrollo de Software")
                FilaInfo(Icons.Filled.CalendarMonth, "Ciclo actual", alumno?.ciclo ?: "—")

                Spacer(modifier = Modifier.height(24.dp))
                OutlinedButton(
                    onClick = {
                        navController.navigate(Screen.Home.route) {
                            // Limpia el back stack — evita apilar Homes
                            popUpTo(Screen.Home.route) { inclusive = true }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Filled.Home, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Ir al inicio")
                }
                Spacer(modifier = Modifier.height(12.dp))
                Button(
                    onClick = {
                        Sesion.cerrar()
                        navController.navigate(Screen.Login.route) {
                            popUpTo(navController.graph.id) { inclusive = true }
                        }
                    },
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFFBE4E1),
                        contentColor = PortalColors.Peligro
                    ),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.AutoMirrored.Filled.Logout, contentDescription = null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cerrar Sesión", fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun Seccion(titulo: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.labelMedium,
        fontWeight = FontWeight.Bold,
        color = PortalColors.Primario,
        modifier = Modifier.padding(vertical = 6.dp)
    )
}
