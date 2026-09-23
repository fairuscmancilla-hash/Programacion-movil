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

    // ESTADO DEL FILTRO DE ESPECIALIDAD
    var especialidadSeleccionada by remember {
        mutableStateOf("Todos")
    }

    // MEJORA IA #3:
    // Estado que almacena lo escrito en el buscador.
    var textoBusqueda by remember {
        mutableStateOf("")
    }

    val drawerState = rememberDrawerState(
        initialValue = DrawerValue.Closed
    )

    val scope = rememberCoroutineScope()

    // FILTRADO POR ESPECIALIDAD + BÚSQUEDA
    val medicosFiltrados = doctoresMock.filter { medico ->

        val coincideEspecialidad =
            especialidadSeleccionada == "Todos" ||
                    medico.especialidad == especialidadSeleccionada

        val coincideBusqueda =
            textoBusqueda.isBlank() ||
                    medico.nombre.contains(
                        textoBusqueda,
                        ignoreCase = true
                    ) ||
                    medico.especialidad.contains(
                        textoBusqueda,
                        ignoreCase = true
                    )

        coincideEspecialidad && coincideBusqueda
    }

    ModalNavigationDrawer(
        drawerState = drawerState,

        drawerContent = {

            ModalDrawerSheet(
                drawerContainerColor = Color.White
            ) {

                Spacer(
                    modifier = Modifier.height(25.dp)
                )

                // PERFIL DEL USUARIO
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 15.dp
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(MoradoIcono),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = "JP",
                            color = MoradoPrincipal,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp
                        )
                    }

                    Spacer(
                        modifier = Modifier.width(12.dp)
                    )

                    Column {

                        Text(
                            text = "Juan Pérez",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )

                        Text(
                            text = "Paciente",
                            fontSize = 12.sp,
                            color = TextoSecundario
                        )
                    }
                }

                HorizontalDivider(
                    color = Color(0xFFE0E0E0)
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
                )

                // INICIO
                NavigationDrawerItem(
                    icon = {
                        Text(
                            text = "○",
                            fontSize = 25.sp,
                            color = Color.DarkGray
                        )
                    },

                    label = {
                        Text(
                            text = "Inicio",
                            fontSize = 14.sp
                        )
                    },

                    selected = true,

                    onClick = {
                        scope.launch {
                            drawerState.close()
                        }
                    },

                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MoradoClaro,
                        selectedTextColor = MoradoPrincipal
                    ),

                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 3.dp
                    )
                )

                // MIS CITAS
                NavigationDrawerItem(
                    icon = {
                        Text(
                            text = "○",
                            fontSize = 25.sp,
                            color = MoradoPrincipal
                        )
                    },

                    label = {
                        Text(
                            text = "Mis citas",
                            fontSize = 14.sp
                        )
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

                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MoradoClaro,
                        selectedTextColor = MoradoPrincipal
                    ),

                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 3.dp
                    )
                )

                // HISTORIAL MÉDICO
                NavigationDrawerItem(
                    icon = {
                        Text(
                            text = "○",
                            fontSize = 25.sp,
                            color = Color.DarkGray
                        )
                    },

                    label = {
                        Text(
                            text = "Historial médico",
                            fontSize = 14.sp
                        )
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

                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MoradoClaro,
                        selectedTextColor = MoradoPrincipal
                    ),

                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 3.dp
                    )
                )

                // PERFIL
                NavigationDrawerItem(
                    icon = {
                        Text(
                            text = "○",
                            fontSize = 25.sp,
                            color = Color.DarkGray
                        )
                    },

                    label = {
                        Text(
                            text = "Perfil",
                            fontSize = 14.sp
                        )
                    },

                    selected = false,

                    onClick = {

                        scope.launch {
                            drawerState.close()
                        }

                        navController.navigate(
                            Screen.Perfil.route
                        )
                    },

                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MoradoClaro,
                        selectedTextColor = MoradoPrincipal
                    ),

                    modifier = Modifier.padding(
                        horizontal = 12.dp,
                        vertical = 3.dp
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
                    modifier = Modifier.height(16.dp)
                )

                // =====================================
                // MEJORA IA #3 - BUSCADOR DE MÉDICOS
                // =====================================
                OutlinedTextField(
                    value = textoBusqueda,

                    onValueChange = {
                        textoBusqueda = it
                    },

                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),

                    placeholder = {
                        Text(
                            text = "Buscar médico o especialidad",
                            fontSize = 13.sp
                        )
                    },

                    leadingIcon = {
                        Text(
                            text = "⌕",
                            fontSize = 22.sp,
                            color = MoradoPrincipal
                        )
                    },

                    trailingIcon = {

                        if (textoBusqueda.isNotEmpty()) {

                            TextButton(
                                onClick = {
                                    textoBusqueda = ""
                                }
                            ) {

                                Text(
                                    text = "×",
                                    color = MoradoPrincipal,
                                    fontSize = 22.sp
                                )
                            }
                        }
                    },

                    singleLine = true,

                    shape = RoundedCornerShape(12.dp),

                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MoradoPrincipal,
                        unfocusedBorderColor = Color(0xFFDDDDDD),
                        cursorColor = MoradoPrincipal
                    )
                )

                Spacer(
                    modifier = Modifier.height(12.dp)
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

                    // SI NO HAY RESULTADOS
                    if (medicosFiltrados.isEmpty()) {

                        item {

                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 35.dp),

                                horizontalAlignment =
                                    Alignment.CenterHorizontally
                            ) {

                                Text(
                                    text = "No encontramos médicos",
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.Bold
                                )

                                Spacer(
                                    modifier = Modifier.height(5.dp)
                                )

                                Text(
                                    text = "Prueba con otro nombre o especialidad.",
                                    fontSize = 12.sp,
                                    color = TextoSecundario
                                )
                            }
                        }
                    }

                    // RESULTADOS
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