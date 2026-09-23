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

private val MoradoPerfil = Color(0xFF6A1B9A)
private val MoradoClaroPerfil = Color(0xFFF4EEF8)
private val GrisPerfil = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PerfilScreen(
    navController: NavController
) {

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Mi perfil",
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
                            fontSize = 25.sp
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MoradoPerfil
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(
                modifier = Modifier.height(25.dp)
            )

            // FOTO / INICIALES
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .clip(CircleShape)
                    .background(MoradoClaroPerfil),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "JP",
                    color = MoradoPerfil,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            Text(
                text = "Juan Pérez",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Paciente",
                fontSize = 14.sp,
                color = GrisPerfil
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MoradoClaroPerfil
                )
            ) {

                Column(
                    modifier = Modifier.padding(20.dp)
                ) {

                    Text(
                        text = "Información personal",
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(
                        modifier = Modifier.height(20.dp)
                    )

                    CampoPerfil(
                        titulo = "Nombre",
                        valor = "Juan Pérez"
                    )

                    HorizontalDivider()

                    CampoPerfil(
                        titulo = "Tipo de usuario",
                        valor = "Paciente"
                    )

                    HorizontalDivider()

                    CampoPerfil(
                        titulo = "Documento",
                        valor = "DNI"
                    )
                }
            }
        }
    }
}

@Composable
fun CampoPerfil(
    titulo: String,
    valor: String
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp)
    ) {

        Text(
            text = titulo,
            fontSize = 12.sp,
            color = GrisPerfil
        )

        Spacer(
            modifier = Modifier.height(3.dp)
        )

        Text(
            text = valor,
            fontSize = 15.sp,
            fontWeight = FontWeight.Medium
        )
    }
}
