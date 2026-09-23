package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

        if (citas.isEmpty()) {

            // MENSAJE CUANDO NO EXISTEN CITAS
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "No tienes citas registradas",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(5.dp)
                    )

                    Text(
                        text = "Agenda una cita con uno de nuestros médicos.",
                        fontSize = 13.sp,
                        color = GrisTexto
                    )
                }
            }

        } else {

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues),

                contentPadding = PaddingValues(
                    horizontal = 16.dp,
                    vertical = 18.dp
                ),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(citas) { cita ->

                    CitaCard(
                        cita = cita
                    )
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

            // LÍNEA MORADA COMO EN LA GUÍA
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

                // ESTADO
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