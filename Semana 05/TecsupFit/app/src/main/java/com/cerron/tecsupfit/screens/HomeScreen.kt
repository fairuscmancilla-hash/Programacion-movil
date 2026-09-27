package com.cerron.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import com.cerron.tecsupfit.model.ClaseFit
import com.cerron.tecsupfit.model.clasesMock
import com.cerron.tecsupfit.model.filtrosClases
import com.cerron.tecsupfit.navigation.Screen

private val AzulPrincipal = Color(0xFF1565C0)
private val AzulClaro = Color(0xFFEAF3FC)
private val TextoSecundario = Color(0xFF666666)
private val VerdeCupos = Color(0xFF2E7D32)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    var filtroSeleccionado by remember {
        mutableStateOf("Hoy")
    }

    val clasesFiltradas = clasesMock.filter {
        it.categoria == filtroSeleccionado
    }

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = "TECSUP Fit",
                            color = Color.White,
                            fontSize = 19.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Encuentra tu próxima clase",
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AzulPrincipal
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

            Spacer(
                modifier = Modifier.height(18.dp)
            )

            Text(
                text = "Clases disponibles",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(4.dp)
            )

            Text(
                text = "Elige una clase y reserva tu cupo",
                modifier = Modifier.padding(horizontal = 16.dp),
                fontSize = 13.sp,
                color = TextoSecundario
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            // FILTROS: HOY / ESTA SEMANA
            LazyRow(
                modifier = Modifier.fillMaxWidth(),

                contentPadding = PaddingValues(
                    horizontal = 16.dp
                ),

                horizontalArrangement =
                    Arrangement.spacedBy(8.dp)
            ) {

                items(filtrosClases) { filtro ->

                    FilterChip(
                        selected =
                            filtroSeleccionado == filtro,

                        onClick = {
                            filtroSeleccionado = filtro
                        },

                        label = {
                            Text(
                                text = filtro,
                                fontSize = 13.sp
                            )
                        },

                        colors =
                            FilterChipDefaults.filterChipColors(
                                selectedContainerColor =
                                    AzulPrincipal,
                                selectedLabelColor =
                                    Color.White,
                                containerColor =
                                    AzulClaro
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // LISTA DE CLASES
            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                contentPadding = PaddingValues(
                    start = 16.dp,
                    end = 16.dp,
                    bottom = 20.dp
                ),

                verticalArrangement =
                    Arrangement.spacedBy(12.dp)
            ) {

                items(clasesFiltradas) { clase ->

                    ClaseCard(
                        clase = clase,

                        onClick = {
                            navController.navigate(
                                Screen.DetalleClase
                                    .crearRuta(clase.id)
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun ClaseCard(
    clase: ClaseFit,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,

        modifier = Modifier.fillMaxWidth(),

        shape = RoundedCornerShape(14.dp),

        colors = CardDefaults.cardColors(
            containerColor = AzulClaro
        )
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween,

                verticalAlignment =
                    Alignment.CenterVertically
            ) {

                Text(
                    text = clase.nombre,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = clase.horario,
                    fontSize = 14.sp,
                    color = AzulPrincipal,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.height(8.dp)
            )

            Text(
                text = "Entrenador: ${clase.entrenador}",
                fontSize = 13.sp,
                color = TextoSecundario
            )

            Spacer(
                modifier = Modifier.height(5.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),

                horizontalArrangement =
                    Arrangement.SpaceBetween
            ) {

                Text(
                    text = "Duración: ${clase.duracion}",
                    fontSize = 12.sp,
                    color = TextoSecundario
                )

                Text(
                    text = "${clase.cupos} cupos",
                    fontSize = 12.sp,
                    color = VerdeCupos,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}


