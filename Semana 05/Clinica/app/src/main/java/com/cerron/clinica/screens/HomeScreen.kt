package com.cerron.clinica.screens

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
import com.cerron.clinica.model.Medico
import com.cerron.clinica.model.doctoresMock
import com.cerron.clinica.model.especialidades
import com.cerron.clinica.navigation.Screen
import kotlinx.coroutines.launch

private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val MoradoIcono = Color(0xFFE9D7F2)
private val Dorado = Color(0xFFFFC107)
private val TextoSecundario = Color(0xFF666666)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {

    var especialidadSeleccionada by remember {
        mutableStateOf("Todos")
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    val medicosFiltrados =
        if (especialidadSeleccionada == "Todos") {
            doctoresMock
        } else {
            doctoresMock.filter {
                it.especialidad == especialidadSeleccionada
            }
        }

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet {

                Spacer(
                    modifier = Modifier.height(30.dp)
                )

                Text(
                    text = "Clínica Salud+",
                    modifier = Modifier.padding(
                        horizontal = 20.dp,
                        vertical = 12.dp
                    ),
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = MoradoPrincipal
                )

                HorizontalDivider()

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // INICIO
                NavigationDrawerItem(
                    label = {
                        Text("Inicio")
                    },
                    selected = true,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }
                    },
                    modifier = Modifier.padding(
                        horizontal = 12.dp
                    )
                )

                // MIS CITAS
                NavigationDrawerItem(
                    label = {
                        Text("Mis citas")
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        navController.navigate(
                            Screen.MisCitas.route
                        )
                    },
                    modifier = Modifier.padding(
                        horizontal = 12.dp
                    )
                )

                // HISTORIAL
                NavigationDrawerItem(
                    label = {
                        Text("Historial médico")
                    },
                    selected = false,
                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        navController.navigate(
                            Screen.Historial.route
                        )
                    },
                    modifier = Modifier.padding(
                        horizontal = 12.dp
                    )
                )
            }
        }
    ) {

        Scaffold(
            containerColor = Color.White,

            topBar = {

                TopAppBar(

                    navigationIcon = {

                        IconButton(
                            onClick = {

                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {

                            Text(
                                text = "☰",
                                color = Color.White,
                                fontSize = 26.sp
                            )
                        }
                    },

                    title = {

                        Column {

                            Text(
                                text = "Clínica Salud+",
                                color = Color.White,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Text(
                                text = "Hola, Juan",
                                color = Color.White,
                                fontSize = 10.sp
                            )
                        }
                    },

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MoradoPrincipal
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

                // FILTROS DE ESPECIALIDAD
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp
                    ),
                    horizontalArrangement =
                        Arrangement.spacedBy(8.dp)
                ) {

                    items(especialidades) { especialidad ->

                        FilterChip(
                            selected =
                                especialidadSeleccionada ==
                                        especialidad,

                            onClick = {
                                especialidadSeleccionada =
                                    especialidad
                            },

                            label = {
                                Text(
                                    text = especialidad,
                                    fontSize = 11.sp
                                )
                            },

                            colors =
                                FilterChipDefaults.filterChipColors(
                                    selectedContainerColor =
                                        MoradoPrincipal,
                                    selectedLabelColor =
                                        Color.White,
                                    containerColor =
                                        MoradoClaro
                                ),

                            border =
                                FilterChipDefaults.filterChipBorder(
                                    enabled = true,
                                    selected =
                                        especialidadSeleccionada ==
                                                especialidad,
                                    borderColor =
                                        Color.Transparent,
                                    selectedBorderColor =
                                        Color.Transparent
                                )
                        )
                    }
                }

                Spacer(
                    modifier = Modifier.height(15.dp)
                )

                Text(
                    text = "Médicos disponibles",
                    modifier = Modifier.padding(
                        horizontal = 16.dp
                    ),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(10.dp)
                )

                // LISTA DE MÉDICOS
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        horizontal = 16.dp,
                        vertical = 4.dp
                    ),
                    verticalArrangement =
                        Arrangement.spacedBy(10.dp)
                ) {

                    items(medicosFiltrados) { medico ->

                        MedicoCard(
                            medico = medico,
                            onClick = {

                                navController.navigate(
                                    Screen.PerfilMedico
                                        .crearRuta(medico.id)
                                )
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MedicoCard(
    medico: Medico,
    onClick: () -> Unit
) {

    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),

        colors = CardDefaults.cardColors(
            containerColor = MoradoClaro
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),

            verticalAlignment =
                Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(45.dp)
                    .clip(CircleShape)
                    .background(MoradoIcono),

                contentAlignment =
                    Alignment.Center
            ) {

                Text(
                    text = "+",
                    color = MoradoPrincipal,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(
                modifier = Modifier.width(12.dp)
            )

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = medico.nombre,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )

                Spacer(
                    modifier = Modifier.height(3.dp)
                )

                Text(
                    text = medico.especialidad,
                    fontSize = 11.sp,
                    color = TextoSecundario
                )
            }

            Text(
                text = "★",
                color = Dorado,
                fontSize = 15.sp
            )

            Spacer(
                modifier = Modifier.width(4.dp)
            )

            Text(
                text = medico.calificacion.toString(),
                fontSize = 11.sp,
                color = TextoSecundario
            )
        }
    }
}