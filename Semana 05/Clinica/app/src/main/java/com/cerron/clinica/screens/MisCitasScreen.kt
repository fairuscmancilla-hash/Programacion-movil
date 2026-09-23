package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cerron.clinica.model.Cita

private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val VerdeEstado = Color(0xFF2E7D32)


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
                    TextButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "←",
                            color = Color.White,
                            fontSize = 25.sp
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MoradoPrincipal
                )
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp),

            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(citas) { cita ->

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MoradoClaro
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = cita.medico,
                            fontSize = 17.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(
                            modifier = Modifier.height(4.dp)
                        )

                        Text(
                            text = cita.especialidad,
                            fontSize = 13.sp,
                            color = Color.Gray
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "${cita.fecha} • ${cita.hora}",
                            fontSize = 14.sp
                        )

                        Spacer(
                            modifier = Modifier.height(8.dp)
                        )

                        Text(
                            text = cita.estado,
                            color = if (cita.estado == "Confirmada") {
                                VerdeEstado
                            } else {
                                MoradoPrincipal
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp
                        )
                    }
                }
            }
        }
    }
}