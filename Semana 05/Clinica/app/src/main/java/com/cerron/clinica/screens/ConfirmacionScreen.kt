package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
private val VerdeConfirmacion = Color(0xFF4CAF50)
private val GrisTexto = Color(0xFF666666)

@Composable
fun ConfirmacionScreen(
    navController: NavController,
    medico: Medico,
    fecha: String,
    hora: String,
    onGuardarCita: () -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(60.dp))

        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(VerdeConfirmacion),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                color = Color.White,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "¡Cita confirmada!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu cita ha sido agendada correctamente",
            fontSize = 14.sp,
            color = GrisTexto
        )

        Spacer(modifier = Modifier.height(35.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(
                containerColor = MoradoClaro
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp)
            ) {

                Text(
                    text = "Resumen de la cita",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Médico",
                    fontSize = 12.sp,
                    color = GrisTexto
                )

                Text(
                    text = medico.nombre,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = medico.especialidad,
                    fontSize = 13.sp,
                    color = GrisTexto
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Fecha",
                    fontSize = 12.sp,
                    color = GrisTexto
                )

                Text(
                    text = fecha,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(18.dp))

                Text(
                    text = "Hora",
                    fontSize = 12.sp,
                    color = GrisTexto
                )

                Text(
                    text = hora,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                onGuardarCita()

                navController.navigate(Screen.Home.route) {
                    popUpTo(Screen.Home.route) {
                        inclusive = true
                    }
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MoradoPrincipal
            )
        ) {
            Text(
                text = "Volver al inicio",
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(20.dp))
    }
}

