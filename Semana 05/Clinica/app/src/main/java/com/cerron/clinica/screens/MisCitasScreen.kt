package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cerron.clinica.model.Cita

private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val VerdeClaro = Color(0xFFE1F5EA)
private val VerdeEstado = Color(0xFF2E7D32)
private val GrisEstado = Color(0xFFE5E5E5)
private val GrisTexto = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisCitasScreen(
    navController: NavController,
    citas: List<Cita>
) {

    // ESTADO CREADO EN LA FASE CON IA
    var filtroSeleccionado by remember {
        mutableStateOf("Todas")
    }

    val filtros = listOf(
        "Todas",
        "Confirmadas",
        "Completadas"
    )

    // FILTRADO SEGÚN LA OPCIÓN SELECCIONADA
    val citasFiltradas = when (filtroSeleccionado) {

        "Confirmadas" -> {
            citas.filter {
                it.estado == "Confirmada"
            }
        }

        "Completadas" -> {
            citas.filter {
                it.estado == "Completada"
            }
        }

        else -> citas
    }

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mis citas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },

                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "←",
                            color = Color.White,
                            fontSize = 26.sp
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
        ) {

            // FILTROS
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 16.dp,
                        vertical = 14.dp
                    ),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(filtros.size) { index ->

                    val filtro = filtros[index]

                    FilterChip(
                        selected =
                            filtroSeleccionado == filtro,

                        onClick = {
                            filtroSeleccionado = filtro
                        },

                        label = {
                            Text(
                                text = filtro
                            )
                        },

                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = MoradoPrincipal,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = GrisTexto
                        )
                    )
                }
            }

            // NO HAY CITAS PARA EL FILTRO
            if (citasFiltradas.isEmpty()) {

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),

                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        horizontalAlignment =
                            Alignment.CenterHorizontally
                    ) {

                        Text(
                            text = if (citas.isEmpty()) {
                                "No tienes citas registradas"
                            } else {
                                "No hay citas en esta categoría"
                            },
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = if (citas.isEmpty()) {
                                "Agenda una cita con uno de nuestros médicos."
                            } else {
                                "Selecciona otro filtro para ver tus citas."
                            },
                            fontSize = 13.sp,
                            color = GrisTexto
                        )
                    }
                }

            } else {

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),

                    contentPadding = PaddingValues(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 18.dp
                    ),

                    verticalArrangement =
                        Arrangement.spacedBy(12.dp)
                ) {

                    items(citasFiltradas) { cita ->

                        CitaCard(
                            cita = cita
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CitaCard(
    cita: Cita
) {

    val esConfirmada =
        cita.estado == "Confirmada"

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),

        colors = CardDefaults.cardColors(
            containerColor = MoradoClaro
        )
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {

            // LÍNEA LATERAL MORADA
            Box(
                modifier = Modifier
                    .width(5.dp)
                    .height(120.dp)
                    .background(MoradoPrincipal)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {

                Text(
                    text = cita.medico,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = cita.especialidad,
                    fontSize = 12.sp,
                    color = GrisTexto
                )

                Spacer(
                    modifier = Modifier.height(7.dp)
                )

                Text(
                    text = "${cita.fecha}, ${cita.hora}",
                    fontSize = 13.sp,
                    color = GrisTexto
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                Surface(
                    color =
                        if (esConfirmada) {
                            VerdeClaro
                        } else {
                            GrisEstado
                        },

                    shape = RoundedCornerShape(20.dp)
                ) {

                    Text(
                        text = cita.estado,

                        color =
                            if (esConfirmada) {
                                VerdeEstado
                            } else {
                                GrisTexto
                            },

                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,

                        modifier = Modifier.padding(
                            horizontal = 12.dp,
                            vertical = 5.dp
                        )
                    )
                }
            }
        }
    }
}