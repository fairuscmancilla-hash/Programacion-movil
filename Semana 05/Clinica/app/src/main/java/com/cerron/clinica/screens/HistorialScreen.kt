package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

private val MoradoHistorial = Color(0xFF6A1B9A)
private val MoradoClaroHistorial = Color(0xFFF4EEF8)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HistorialScreen(
    navController: NavController
) {

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Historial médico",
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
                    containerColor = MoradoHistorial
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            Text(
                text = "Atenciones anteriores",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MoradoClaroHistorial
                )
            ) {

                Column(
                    modifier = Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "Dr. Luis Vega",
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )

                    Text(
                        text = "Pediatría",
                        color = Color.Gray,
                        fontSize = 13.sp
                    )

                    Spacer(
                        modifier = Modifier.height(8.dp)
                    )

                    Text(
                        text = "Consulta general",
                        fontSize = 14.sp
                    )

                    Text(
                        text = "15 de septiembre",
                        color = Color.Gray,
                        fontSize = 12.sp
                    )
                }
            }
        }
    }
}