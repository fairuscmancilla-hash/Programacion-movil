package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cerron.clinica.model.Medico
import com.cerron.clinica.navigation.Screen

private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val Dorado = Color(0xFFFFC107)
private val GrisTexto = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilMedicoScreen(
    navController: NavController,
    medico: Medico
) {

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Perfil del médico",
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
                            color = MoradoPrincipal,
                            fontSize = 25.sp
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },

        bottomBar = {
            Button(
                onClick = {
                    navController.navigate(
                        Screen.AgendarCita.crearRuta(medico.id)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrincipal
                )
            ) {
                Text(
                    text = "Agendar cita",
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(25.dp))

            // Icono del médico
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(MoradoClaro),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "+",
                    color = MoradoPrincipal,
                    fontSize = 55.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(18.dp))

            Text(
                text = medico.nombre,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = medico.especialidad,
                fontSize = 14.sp,
                color = GrisTexto
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = "★",
                    color = Dorado,
                    fontSize = 18.sp
                )

                Spacer(modifier = Modifier.width(5.dp))

                Text(
                    text = medico.calificacion.toString(),
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            HorizontalDivider(
                color = MoradoClaro
            )

            Spacer(modifier = Modifier.height(25.dp))

            Text(
                text = "Acerca del médico",
                modifier = Modifier.fillMaxWidth(),
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = medico.descripcion,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 14.sp,
                color = GrisTexto,
                lineHeight = 21.sp
            )
        }
    }
}