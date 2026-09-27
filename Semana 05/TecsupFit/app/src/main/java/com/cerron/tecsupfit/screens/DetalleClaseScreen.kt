package com.cerron.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
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
import com.cerron.tecsupfit.model.ClaseFit
import com.cerron.tecsupfit.navigation.Screen
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size


private val VerdeDetalle = Color(0xFF078568)
private val VerdeClaroDetalle = Color(0xFFDDF3EC)
private val GrisDetalle = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetalleClaseScreen(
    navController: NavController,
    clase: ClaseFit
) {

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        }
                    ) {
                        Text(
                            text = "←",
                            fontSize = 24.sp,
                            color = Color.Black
                        )
                    }
                },

                title = {
                    Text(
                        text = "Detalle de clase",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
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
                        Screen.Confirmacion.crearRuta(
                            clase.id
                        )
                    )
                },

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),

                shape = RoundedCornerShape(8.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = VerdeDetalle
                )
            ) {
                Text(
                    text = "Reservar cupo",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 18.dp)
        ) {

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            // ZONA DE IMAGEN / MANCUERNA
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
                    .background(
                        color = VerdeClaroDetalle,
                        shape = RoundedCornerShape(14.dp)
                    ),

                contentAlignment = Alignment.Center
            ) {

                IconoMancuerna(
                    modifier = Modifier
                        .width(85.dp)
                        .height(45.dp),
                    color = VerdeDetalle
                )
            }

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = clase.nombre,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Text(
                text = "${clase.horario} · ${clase.sala} · ${clase.duracion}",
                fontSize = 13.sp,
                color = GrisDetalle
            )

            Spacer(
                modifier = Modifier.height(20.dp)
            )

            Text(
                text = clase.descripcion,
                fontSize = 14.sp,
                color = GrisDetalle,
                lineHeight = 20.sp
            )

            Spacer(
                modifier = Modifier.height(22.dp)
            )

            Text(
                text = "${clase.cuposDisponibles} de ${clase.cuposTotales} cupos disponibles",
                fontSize = 13.sp,
                color = GrisDetalle
            )
        }
    }
}
@Composable
fun IconoMancuerna(
    modifier: Modifier = Modifier,
    color: Color
) {
    Canvas(modifier = modifier) {

        val centroY = size.height / 2f

        // Barra central
        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.22f,
                y = centroY - size.height * 0.08f
            ),
            size = Size(
                width = size.width * 0.56f,
                height = size.height * 0.16f
            )
        )

        // Disco izquierdo interior
        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.15f,
                y = centroY - size.height * 0.23f
            ),
            size = Size(
                width = size.width * 0.10f,
                height = size.height * 0.46f
            )
        )

        // Disco izquierdo exterior
        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.07f,
                y = centroY - size.height * 0.32f
            ),
            size = Size(
                width = size.width * 0.10f,
                height = size.height * 0.64f
            )
        )

        // Disco derecho interior
        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.75f,
                y = centroY - size.height * 0.23f
            ),
            size = Size(
                width = size.width * 0.10f,
                height = size.height * 0.46f
            )
        )

        // Disco derecho exterior
        drawRoundRect(
            color = color,
            topLeft = Offset(
                x = size.width * 0.83f,
                y = centroY - size.height * 0.32f
            ),
            size = Size(
                width = size.width * 0.10f,
                height = size.height * 0.64f
            )
        )
    }
}


