package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cerron.clinica.model.doctoresMock
import com.cerron.clinica.model.especialidades
import com.cerron.clinica.navigation.Screen

// Colores principales de Clínica Salud+
private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val MoradoIcono = Color(0xFFE9D7F2)
private val Dorado = Color(0xFFFFC107)
private val TextoSecundario = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {

    var especialidadSeleccionada by remember {
        mutableStateOf("Todos")
    }

    val medicosFiltrados =
        if (especialidadSeleccionada == "Todos") {
            doctoresMock
        } else {
            doctoresMock.filter {
                it.especialidad == especialidadSeleccionada
            }
        }

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "Clínica Salud+",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )

                        Text(
                            text = "Hola, Juan",
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MoradoPrincipal
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(modifier = Modifier.height(14.dp))

            // FILTROS DE ESPECIALIDAD
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(especialidades) { especialidad ->

                    FilterChip(
                        selected = especialidadSeleccionada == especialidad,
                        onClick = {
                            especialidadSeleccionada = especialidad
                        },
                        label = {
                            Text(
                                text = especialidad,
                                fontSize = 12.sp
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoPrincipal,
                            selectedLabelColor = Color.White,
                            containerColor = MoradoClaro,
                            labelColor = Color.DarkGray
                        ),
                        border = null
                    )
                }
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = "Médicos disponibles",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(10.dp))

            // LISTA DE MÉDICOS
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(bottom = 16.dp)
            ) {

                items(medicosFiltrados) { medico ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                navController.navigate(
                                    Screen.PerfilMedico.crearRuta(medico.id)
                                )
                            },
                        shape = RoundedCornerShape(10.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MoradoClaro
                        ),
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 0.dp
                        )
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            // Ícono circular +
                            Box(
                                modifier = Modifier
                                    .size(42.dp)
                                    .clip(CircleShape)
                                    .background(MoradoIcono),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "+",
                                    color = MoradoPrincipal,
                                    fontSize = 28.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }

                            Spacer(modifier = Modifier.width(12.dp))

                            // Datos del médico
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {

                                Text(
                                    text = medico.nombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 15.sp,
                                    color = Color.Black
                                )

                                Spacer(modifier = Modifier.height(2.dp))

                                Text(
                                    text = medico.especialidad,
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }

                            // Calificación
                            Row(
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Text(
                                    text = "★",
                                    color = Dorado,
                                    fontSize = 16.sp
                                )

                                Spacer(modifier = Modifier.width(3.dp))

                                Text(
                                    text = medico.calificacion.toString(),
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}