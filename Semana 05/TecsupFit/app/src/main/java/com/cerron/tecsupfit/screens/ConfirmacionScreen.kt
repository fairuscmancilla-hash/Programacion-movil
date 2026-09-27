package com.cerron.tecsupfit.screens

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
import com.cerron.tecsupfit.model.ClaseFit
import com.cerron.tecsupfit.navigation.Screen

private val VerdeConfirmacion = Color(0xFF078568)
private val VerdeClaroConfirmacion = Color(0xFFDDF3EC)
private val GrisConfirmacion = Color(0xFF666666)
private val GrisBoton = Color(0xFFF1F1F1)

@Composable
fun ConfirmacionScreen(
    navController: NavController,
    clase: ClaseFit
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 30.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        // CÍRCULO CON CHECK
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(VerdeClaroConfirmacion),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "✓",
                color = VerdeConfirmacion,
                fontSize = 42.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        Text(
            text = "¡Cupo reservado!",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = clase.nombre,
            fontSize = 14.sp,
            color = GrisConfirmacion
        )

        Spacer(
            modifier = Modifier.height(5.dp)
        )

        Text(
            text = "Hoy, ${clase.horario} · ${clase.sala}",
            fontSize = 13.sp,
            color = GrisConfirmacion
        )

        Spacer(
            modifier = Modifier.height(35.dp)
        )

        Button(
            onClick = {
                navController.navigate(Screen.Reservas.route)
            },

            modifier = Modifier
                .width(190.dp)
                .height(48.dp),

            shape = RoundedCornerShape(8.dp),

            colors = ButtonDefaults.buttonColors(
                containerColor = GrisBoton,
                contentColor = Color.Black
            )
        ) {
            Text(
                text = "Ver mis reservas",
                fontSize = 13.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

