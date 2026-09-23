package com.cerron.clinica.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.cerron.clinica.model.Medico
import com.cerron.clinica.model.fechasDisponibles
import com.cerron.clinica.model.horasDisponibles
import com.cerron.clinica.navigation.Screen

private val MoradoPrincipal = Color(0xFF6A1B9A)
private val MoradoClaro = Color(0xFFF4EEF8)
private val GrisTexto = Color(0xFF666666)
private val VerdeClaro = Color(0xFFE8F5E9)
private val VerdeTexto = Color(0xFF2E7D32)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AgendarCitaScreen(
    navController: NavController,
    medico: Medico
) {

    var fechaSeleccionada by remember {
        mutableStateOf("")
    }

    var horaSeleccionada by remember {
        mutableStateOf("")
    }

    // El botón solo estará habilitado cuando
    // exista una fecha y una hora seleccionadas.
    val formularioCompleto =
        fechaSeleccionada.isNotEmpty() &&
                horaSeleccionada.isNotEmpty()

    Scaffold(
        containerColor = Color.White,

        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Agendar cita",
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
                        Screen.Confirmacion.crearRuta(
                            medico.id,
                            fechaSeleccionada,
                            horaSeleccionada
                        )
                    )
                },

                enabled = formularioCompleto,

                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(52.dp),

                colors = ButtonDefaults.buttonColors(
                    containerColor = MoradoPrincipal,
                    disabledContainerColor = Color(0xFFD1C4D9),
                    disabledContentColor = Color.White
                )
            ) {

                Text(
                    text = if (formularioCompleto) {
                        "Confirmar cita"
                    } else {
                        "Selecciona fecha y hora"
                    },
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
                .padding(horizontal = 20.dp)
        ) {

            Spacer(
                modifier = Modifier.height(15.dp)
            )

            // MÉDICO
            Text(
                text = medico.nombre,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = medico.especialidad,
                fontSize = 13.sp,
                color = GrisTexto
            )

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // FECHA
            Text(
                text = "Selecciona fecha",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                fechasDisponibles.forEach { fecha ->

                    OpcionCita(
                        texto = fecha,
                        seleccionado =
                            fechaSeleccionada == fecha,
                        modifier = Modifier.weight(1f),

                        onClick = {
                            fechaSeleccionada = fecha
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(30.dp)
            )

            // HORA
            Text(
                text = "Selecciona hora",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(
                modifier = Modifier.height(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement =
                    Arrangement.spacedBy(10.dp)
            ) {

                horasDisponibles.forEach { hora ->

                    OpcionCita(
                        texto = hora,
                        seleccionado =
                            horaSeleccionada == hora,
                        modifier = Modifier.weight(1f),

                        onClick = {
                            horaSeleccionada = hora
                        }
                    )
                }
            }

            Spacer(
                modifier = Modifier.height(28.dp)
            )

            // RESUMEN AGREGADO EN LA FASE CON IA
            if (
                fechaSeleccionada.isNotEmpty() ||
                horaSeleccionada.isNotEmpty()
            ) {

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),

                    colors = CardDefaults.cardColors(
                        containerColor =
                            if (formularioCompleto) {
                                VerdeClaro
                            } else {
                                MoradoClaro
                            }
                    )
                ) {

                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {

                        Text(
                            text = "Resumen de tu cita",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color =
                                if (formularioCompleto) {
                                    VerdeTexto
                                } else {
                                    MoradoPrincipal
                                }
                        )

                        Spacer(
                            modifier = Modifier.height(10.dp)
                        )

                        Text(
                            text = "Médico: ${medico.nombre}",
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Fecha: ${
                                if (fechaSeleccionada.isNotEmpty()) {
                                    fechaSeleccionada
                                } else {
                                    "Pendiente"
                                }
                            }",
                            fontSize = 13.sp
                        )

                        Spacer(
                            modifier = Modifier.height(5.dp)
                        )

                        Text(
                            text = "Hora: ${
                                if (horaSeleccionada.isNotEmpty()) {
                                    horaSeleccionada
                                } else {
                                    "Pendiente"
                                }
                            }",
                            fontSize = 13.sp
                        )

                        if (formularioCompleto) {

                            Spacer(
                                modifier = Modifier.height(10.dp)
                            )

                            Text(
                                text = "✓ Cita lista para confirmar",
                                fontSize = 12.sp,
                                color = VerdeTexto,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OpcionCita(
    texto: String,
    seleccionado: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {

    val fondo =
        if (seleccionado) {
            MoradoPrincipal
        } else {
            MoradoClaro
        }

    val textoColor =
        if (seleccionado) {
            Color.White
        } else {
            Color.Black
        }

    Box(
        modifier = modifier
            .height(55.dp)
            .background(
                color = fondo,
                shape = RoundedCornerShape(10.dp)
            )
            .clickable {
                onClick()
            },

        contentAlignment = Alignment.Center
    ) {

        Text(
            text = texto,
            color = textoColor,

            fontWeight =
                if (seleccionado) {
                    FontWeight.Bold
                } else {
                    FontWeight.Normal
                },

            fontSize = 13.sp
        )
    }
}