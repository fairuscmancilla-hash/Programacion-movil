package com.cerron.Registronotas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme {
                RegistroNotasScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroNotasScreen() {

    var fundamentos by remember { mutableFloatStateOf(0f) }
    var poo by remember { mutableFloatStateOf(0f) }
    var moviles by remember { mutableFloatStateOf(0f) }
    var baseDatos by remember { mutableFloatStateOf(0f) }

    var redondear by remember { mutableStateOf(false) }
    var confirmar by remember { mutableStateOf(false) }
    var calculado by remember { mutableStateOf(false) }

    var promedioPonderado by remember { mutableDoubleStateOf(0.0) }
    var promedioFinal by remember { mutableDoubleStateOf(0.0) }
    var observacion by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Registro de Notas",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF6546B4)
                )
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color(0xFFF1E9FF),
                            Color.White
                        )
                    )
                )
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {

            Text(
                "Notas del ciclo",
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Desliza para asignar las notas de 0 a 20",
                fontSize = 12.sp,
                color = Color.Gray
            )

            Spacer(modifier = Modifier.height(16.dp))

            CursoSlider(
                "Fundamentos de Programación",
                "20%",
                fundamentos
            ) {
                fundamentos = it
                calculado = false
            }

            CursoSlider(
                "Programación Orientada a Objetos",
                "25%",
                poo
            ) {
                poo = it
                calculado = false
            }

            CursoSlider(
                "Programación en Móviles",
                "30%",
                moviles
            ) {
                moviles = it
                calculado = false
            }

            CursoSlider(
                "Base de Datos",
                "25%",
                baseDatos
            ) {
                baseDatos = it
                calculado = false
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    "Redondear promedio final",
                    modifier = Modifier.weight(1f)
                )

                Switch(
                    checked = redondear,
                    onCheckedChange = {
                        redondear = it
                        calculado = false
                    }
                )
            }

            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {

                Checkbox(
                    checked = confirmar,
                    onCheckedChange = {
                        confirmar = it
                    }
                )

                Text("Confirmo que las notas son correctas")
            }

            Spacer(modifier = Modifier.height(15.dp))

            Button(
                onClick = {

                    promedioPonderado =
                        fundamentos * 0.20 +
                                poo * 0.25 +
                                moviles * 0.30 +
                                baseDatos * 0.25

                    promedioFinal =
                        if (redondear) {
                            promedioPonderado.roundToInt().toDouble()
                        } else {
                            promedioPonderado
                        }

                    observacion = when {
                        promedioFinal >= 17 -> "EXCELENTE"
                        promedioFinal >= 13 -> "APROBADO"
                        promedioFinal >= 10 -> "EN RECUPERACIÓN"
                        else -> "DESAPROBADO"
                    }

                    calculado = true
                },
                enabled = confirmar,
                modifier = Modifier.fillMaxWidth()
            ) {

                Text("CALCULAR PROMEDIO")
            }

            Spacer(modifier = Modifier.height(16.dp))

            if (!calculado) {

                Text(
                    "Asigna las notas y confirma para calcular",
                    color = Color.Gray
                )

            } else {

                ResultadoCard(
                    promedioPonderado,
                    promedioFinal,
                    redondear,
                    observacion
                )

                Spacer(modifier = Modifier.height(10.dp))

                Text(
                    "✓ Promedio calculado correctamente",
                    color = Color(0xFF388E3C)
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            Text(
                "Desarrollado por: Yajaira Cerron Mancilla",
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }
    }
}

@Composable
fun CursoSlider(
    nombre: String,
    peso: String,
    nota: Float,
    onNotaChange: (Float) -> Unit
) {

    Column(
        modifier = Modifier.padding(vertical = 8.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                "$nombre ($peso)",
                modifier = Modifier.weight(1f),
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold
            )

            Surface(
                color = Color(0xFFE8DFFF),
                shape = RoundedCornerShape(7.dp)
            ) {
                Text(
                    nota.toInt().toString(),
                    modifier = Modifier.padding(
                        horizontal = 10.dp,
                        vertical = 4.dp
                    ),
                    color = Color(0xFF6546B4),
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Slider(
            value = nota,
            onValueChange = onNotaChange,
            valueRange = 0f..20f,
            steps = 19
        )
    }
}

@Composable
fun ResultadoCard(
    promedioPonderado: Double,
    promedioFinal: Double,
    redondeado: Boolean,
    observacion: String
) {

    val colorChip = when (observacion) {
        "EXCELENTE" -> Color(0xFF1B5E20)
        "APROBADO" -> Color(0xFF4CAF50)
        "EN RECUPERACIÓN" -> Color(0xFFFFB300)
        else -> Color(0xFFE53935)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {

            Text(
                "Promedio ponderado: %.2f".format(promedioPonderado)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                if (redondeado) {
                    "Promedio final: ${promedioFinal.toInt()}"
                } else {
                    "Promedio final: %.2f".format(promedioFinal)
                },
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )

            if (redondeado) {
                Text(
                    "(redondeado)",
                    color = Color.Gray
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                color = colorChip.copy(alpha = 0.18f),
                shape = RoundedCornerShape(20.dp)
            ) {

                Text(
                    observacion,
                    color = colorChip,
                    modifier = Modifier.padding(
                        horizontal = 15.dp,
                        vertical = 7.dp
                    ),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}