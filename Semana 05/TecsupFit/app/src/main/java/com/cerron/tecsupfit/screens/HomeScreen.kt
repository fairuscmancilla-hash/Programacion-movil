package com.cerron.tecsupfit.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.cerron.tecsupfit.model.ClaseFit
import com.cerron.tecsupfit.model.clasesMock
import com.cerron.tecsupfit.model.filtrosClases
import com.cerron.tecsupfit.navigation.Screen

private val VerdePrincipal = Color(0xFF078568)
private val VerdeClaro = Color(0xFFDDF3EC)
private val GrisTarjeta = Color(0xFFF1F1F1)
private val GrisTexto = Color(0xFF666666)

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
                            text = "Hola, Diego",
                            color = Color.White,
                            fontSize = 11.sp
                        )
                    }
                },

                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = VerdePrincipal
                )
            )
        },

        bottomBar = {

            NavigationBar(
                containerColor = Color.White
            ) {

                NavigationBarItem(
                    selected = true,
                    onClick = { },
                    icon = {
                        BottomIcon(
                            simbolo = "○",
                            seleccionado = true
                        )
                    },
                    label = {
                        Text(
                            text = "Inicio",
                            fontSize = 10.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VerdePrincipal,
                        selectedTextColor = VerdePrincipal,
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(
                            Screen.Reservas.route
                        )
                    },
                    icon = {
                        BottomIcon(
                            simbolo = "○",
                            seleccionado = false
                        )
                    },
                    label = {
                        Text(
                            text = "Reservas",
                            fontSize = 10.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VerdePrincipal,
                        selectedTextColor = VerdePrincipal,
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(
                            Screen.Rutinas.route
                        )
                    },
                    icon = {
                        BottomIcon(
                            simbolo = "○",
                            seleccionado = false
                        )
                    },
                    label = {
                        Text(
                            text = "Rutinas",
                            fontSize = 10.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VerdePrincipal,
                        selectedTextColor = VerdePrincipal,
                        indicatorColor = Color.Transparent
                    )
                )

                NavigationBarItem(
                    selected = false,
                    onClick = {
                        navController.navigate(
                            Screen.Perfil.route
                        )
                    },
                    icon = {
                        BottomIcon(
                            simbolo = "○",
                            seleccionado = false
                        )
                    },
                    label = {
                        Text(
                            text = "Perfil",
                            fontSize = 10.sp
                        )
                    },
                    colors = NavigationBarItemDefaults.colors(
                        selectedIconColor = VerdePrincipal,
                        selectedTextColor = VerdePrincipal,
                        indicatorColor = Color.Transparent
                    )
                )
            }
        }

    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            // FILTROS
            LazyRow(
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
                                fontSize = 11.sp
                            )
                        },

                        colors =
                            FilterChipDefaults.filterChipColors(
                                selectedContainerColor =
                                    VerdePrincipal,

                                selectedLabelColor =
                                    Color.White,

                                containerColor =
                                    GrisTarjeta
                            ),

                        border =
                            FilterChipDefaults.filterChipBorder(
                                enabled = true,
                                selected =
                                    filtroSeleccionado == filtro,
                                borderColor =
                                    Color.Transparent,
                                selectedBorderColor =
                                    Color.Transparent
                            )
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(14.dp)
            )

            Text(
                text = "Clases disponibles",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(
                modifier = Modifier.height(10.dp)
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize(),

                verticalArrangement =
                    Arrangement.spacedBy(10.dp),

                contentPadding =
                    PaddingValues(bottom = 16.dp)
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

        modifier = Modifier
            .fillMaxWidth(),

        shape = RoundedCornerShape(10.dp),

        colors = CardDefaults.cardColors(
            containerColor = GrisTarjeta
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(VerdeClaro),

                contentAlignment =
                    Alignment.Center
            ) {

                // Representación simple de la pesa del PDF
                Text(
                    text = "▰",
                    color = VerdePrincipal,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column {

                Text(
                    text = clase.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = "${clase.horario} · ${clase.sala}",
                    fontSize = 11.sp,
                    color = GrisTexto
                )
            }
        }
    }
}

@Composable
fun BottomIcon(
    simbolo: String,
    seleccionado: Boolean
) {

    Text(
        text = simbolo,
        fontSize = 24.sp,

        color =
            if (seleccionado) {
                VerdePrincipal
            } else {
                Color.DarkGray
            },

        fontWeight =
            if (seleccionado) {
                FontWeight.Bold
            } else {
                FontWeight.Normal
            }
    )
}


