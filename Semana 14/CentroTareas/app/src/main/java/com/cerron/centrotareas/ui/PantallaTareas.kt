package com.cerron.centrotareas.ui

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Assignment
import androidx.compose.material.icons.rounded.Bolt
import androidx.compose.material.icons.rounded.Calculate
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Dashboard
import androidx.compose.material.icons.rounded.ErrorOutline
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Layers
import androidx.compose.material.icons.rounded.LinearScale
import androidx.compose.material.icons.rounded.PlayArrow
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Schedule
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material.icons.rounded.Storage
import androidx.compose.material.icons.rounded.TouchApp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cerron.centrotareas.model.CargaUi
import com.cerron.centrotareas.model.PrimosUi
import com.cerron.centrotareas.ui.theme.AzulHielo
import com.cerron.centrotareas.ui.theme.Blanco
import com.cerron.centrotareas.ui.theme.BordeSuave
import com.cerron.centrotareas.ui.theme.Crema
import com.cerron.centrotareas.ui.theme.Marino
import com.cerron.centrotareas.ui.theme.MarinoMedio
import com.cerron.centrotareas.ui.theme.MarinoProfundo
import com.cerron.centrotareas.ui.theme.NaranjaBoton
import com.cerron.centrotareas.ui.theme.NaranjaClaro
import com.cerron.centrotareas.ui.theme.NaranjaVivo
import com.cerron.centrotareas.ui.theme.TextoSuave
import com.cerron.centrotareas.ui.theme.VerdeFondo
import com.cerron.centrotareas.ui.theme.VerdeTexto
import com.cerron.centrotareas.viewmodel.TareasViewModel

// Ajustado al emulador Pixel_5: con 500000 tardaba menos de 1 s
private const val N_POR_DEFECTO = "3000000"

// Evita que los números cambien de ancho mientras se actualizan
private val EstiloNumeros = TextStyle(fontSize = 14.sp, fontFeatureSettings = "tnum")

private enum class Seccion(val titulo: String, val icono: ImageVector) {
    Inicio("Inicio", Icons.Rounded.Home),
    Panel("Panel", Icons.Rounded.Dashboard)
}

@Composable
fun PantallaTareas(vm: TareasViewModel) {
    var seccion by rememberSaveable { mutableStateOf(Seccion.Inicio) }
    val estadoSecciones = rememberSaveableStateHolder()

    BackHandler(enabled = seccion == Seccion.Panel) { seccion = Seccion.Inicio }

    Scaffold(
        containerColor = Crema,
        topBar = { Encabezado() },
        bottomBar = { BarraInferior(seccion) { seccion = it } }
    ) { relleno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .consumeWindowInsets(relleno)
        ) {
            FondoDecorado()
            estadoSecciones.SaveableStateProvider(seccion.name) {
                when (seccion) {
                    Seccion.Inicio -> SeccionInicio(onComenzar = { seccion = Seccion.Panel })
                    Seccion.Panel -> SeccionPanel(vm)
                }
            }
        }
    }
}

@Composable
private fun Encabezado() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(Brush.verticalGradient(listOf(MarinoProfundo, Marino, MarinoMedio)))
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = NaranjaVivo,
                radius = 64.dp.toPx(),
                center = Offset(size.width + 12.dp.toPx(), size.height + 18.dp.toPx())
            )
        }
        Row(
            modifier = Modifier
                .statusBarsPadding()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.Layers,
                contentDescription = null,
                tint = Blanco,
                modifier = Modifier.size(36.dp)
            )
            Column {
                Text(
                    text = "CentroTareas",
                    color = Blanco,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Hilos y corrutinas en Android",
                    color = AzulHielo,
                    fontSize = 13.sp
                )
            }
        }
    }
}

@Composable
private fun BarraInferior(actual: Seccion, onCambiar: (Seccion) -> Unit) {
    NavigationBar(containerColor = MarinoProfundo) {
        Seccion.entries.forEach { opcion ->
            NavigationBarItem(
                selected = opcion == actual,
                onClick = { onCambiar(opcion) },
                icon = { Icon(opcion.icono, contentDescription = null) },
                label = { Text(opcion.titulo) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Blanco,
                    selectedTextColor = Blanco,
                    indicatorColor = NaranjaBoton,
                    unselectedIconColor = AzulHielo,
                    unselectedTextColor = AzulHielo
                )
            )
        }
    }
}

@Composable
private fun FondoDecorado() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        drawCircle(
            color = NaranjaVivo,
            radius = 90.dp.toPx(),
            center = Offset(size.width + 20.dp.toPx(), size.height + 10.dp.toPx())
        )
        drawCircle(
            color = Marino,
            radius = 70.dp.toPx(),
            center = Offset(-20.dp.toPx(), size.height + 10.dp.toPx())
        )
    }
}

// ----- Bienvenida

@Composable
private fun SeccionInicio(onComenzar: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Card(
            shape = RoundedCornerShape(24.dp),
            colors = CardDefaults.cardColors(containerColor = Blanco),
            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = "¡Bienvenido!",
                            color = Marino,
                            fontSize = 26.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "Explora las tareas del laboratorio y aprende a ejecutar " +
                                "procesos en segundo plano sin congelar la interfaz.",
                            color = TextoSuave,
                            fontSize = 14.sp
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Ilustracion()
                }
                BotonLleno(
                    texto = "Comenzar",
                    icono = Icons.Rounded.PlayArrow,
                    onClick = onComenzar,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )
            }
        }
        Text(
            text = "¿Qué encontrarás?",
            modifier = Modifier.padding(top = 8.dp),
            color = Marino,
            fontSize = 19.sp,
            fontWeight = FontWeight.Bold
        )
        FilaFuncion(
            icono = Icons.Rounded.Schedule,
            titulo = "Tarea con progreso",
            detalle = "Ejecuta una tarea larga con barra de progreso y cancelación.",
            onClick = onComenzar
        )
        FilaFuncion(
            icono = Icons.Rounded.Calculate,
            titulo = "Contar números primos",
            detalle = "Realiza un cálculo pesado en segundo plano.",
            onClick = onComenzar
        )
        FilaFuncion(
            icono = Icons.Rounded.Storage,
            titulo = "Dos cargas",
            detalle = "Compara la carga secuencial y paralela de datos simulados.",
            onClick = onComenzar
        )
        Spacer(Modifier.height(48.dp))
    }
}

@Composable
private fun Ilustracion() {
    Box(modifier = Modifier.size(96.dp)) {
        Box(
            modifier = Modifier
                .size(84.dp)
                .clip(CircleShape)
                .background(AzulHielo),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.AutoMirrored.Rounded.Assignment,
                contentDescription = null,
                tint = Marino,
                modifier = Modifier.size(52.dp)
            )
        }
        Icon(
            imageVector = Icons.Rounded.Settings,
            contentDescription = null,
            tint = NaranjaVivo,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(40.dp)
        )
    }
}

@Composable
private fun FilaFuncion(
    icono: ImageVector,
    titulo: String,
    detalle: String,
    onClick: () -> Unit
) {
    Card(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            IconoTarjeta(icono)
            Column {
                Text(titulo, color = Marino, fontWeight = FontWeight.Bold)
                Text(detalle, color = TextoSuave, fontSize = 13.sp)
            }
        }
    }
}

// ----- Panel de actividades

@Composable
private fun SeccionPanel(vm: TareasViewModel) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .imePadding()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Column {
            Text(
                text = "Panel de actividades",
                color = Marino,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Controla y monitorea las tareas de tu aplicación.",
                color = TextoSuave,
                fontSize = 14.sp
            )
        }
        TarjetaProgreso(vm)
        TarjetaPrimos(vm)
        TarjetaCargas(vm)
        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun TarjetaProgreso(vm: TareasViewModel) {
    val mensaje by vm.mensaje.collectAsState()
    val progreso by vm.progreso.collectAsState()
    val corriendo by vm.corriendo.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Tarjeta(
        titulo = "Tarea con progreso",
        detalle = "Ejecución sin bloquear la interfaz",
        icono = Icons.Rounded.Schedule
    ) {
        Button(
            onClick = { toques++ },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = Marino,
                contentColor = Blanco
            )
        ) {
            Icon(
                imageVector = Icons.Rounded.TouchApp,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(Modifier.width(8.dp))
            Text("Tócame: $toques", fontWeight = FontWeight.Medium, style = EstiloNumeros)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonLleno(
                texto = "Iniciar",
                icono = Icons.Rounded.PlayArrow,
                onClick = { vm.tareaLarga() },
                enabled = !corriendo,
                modifier = Modifier.weight(1f)
            )
            BotonBorde(
                texto = "Cancelar",
                icono = Icons.Rounded.Close,
                onClick = { vm.cancelar() },
                enabled = corriendo,
                modifier = Modifier.weight(1f)
            )
        }
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp),
            color = NaranjaVivo,
            trackColor = AzulHielo,
            strokeCap = StrokeCap.Round,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(mensaje, color = TextoSuave, fontSize = 14.sp)
            Text(
                text = "${(progreso * 100).toInt()} %",
                color = Marino,
                fontWeight = FontWeight.Bold,
                style = EstiloNumeros
            )
        }
    }
}

@Composable
private fun TarjetaPrimos(vm: TareasViewModel) {
    val primos by vm.primos.collectAsState()
    var texto by rememberSaveable { mutableStateOf(N_POR_DEFECTO) }
    var contado by rememberSaveable { mutableIntStateOf(0) }
    val calculando = primos is PrimosUi.Calculando

    Tarjeta(
        titulo = "Contar números primos",
        detalle = "Cálculo pesado en segundo plano",
        icono = Icons.Rounded.Calculate
    ) {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Hasta el número") },
            singleLine = true,
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(unfocusedBorderColor = TextoSuave),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonLleno(
                texto = "Contar",
                icono = Icons.Rounded.Calculate,
                onClick = {
                    val numero = texto.toIntOrNull()
                    if (numero != null && numero >= 2) {
                        contado = numero
                        vm.contar(numero)
                    }
                },
                enabled = !calculando,
                modifier = Modifier.weight(1f)
            )
            BotonBorde(
                texto = "Cancelar",
                icono = Icons.Rounded.Close,
                onClick = { vm.cancelarPrimos() },
                enabled = calculando,
                modifier = Modifier.weight(1f)
            )
        }
        when (val estado = primos) {
            PrimosUi.Inicial -> Unit
            PrimosUi.Calculando -> Text("Calculando...", color = TextoSuave)
            PrimosUi.Cancelado -> Text("Cancelado", color = TextoSuave)
            is PrimosUi.Listo -> RecuadroExito {
                Text(
                    text = "Primos hasta $contado: ${estado.cantidad}",
                    color = VerdeTexto,
                    fontWeight = FontWeight.Bold,
                    style = EstiloNumeros
                )
                Text(
                    text = "Tiempo: ${estado.milisegundos} ms",
                    color = VerdeTexto,
                    style = EstiloNumeros
                )
            }
        }
    }
}

@Composable
private fun TarjetaCargas(vm: TareasViewModel) {
    val carga by vm.carga.collectAsState()
    val simularError by vm.simularError.collectAsState()
    val cargando = carga is CargaUi.Cargando

    Tarjeta(
        titulo = "Dos cargas",
        detalle = "Comparación de tiempos",
        icono = Icons.Rounded.Storage
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Simular error", color = Marino, fontWeight = FontWeight.Medium)
            Switch(
                checked = simularError,
                onCheckedChange = { vm.cambiarSimularError(it) },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Blanco,
                    checkedTrackColor = NaranjaBoton,
                    uncheckedThumbColor = TextoSuave,
                    uncheckedTrackColor = AzulHielo,
                    uncheckedBorderColor = TextoSuave
                )
            )
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            BotonBorde(
                texto = "Una por una",
                icono = Icons.Rounded.LinearScale,
                onClick = { vm.cargarUnaPorUna() },
                enabled = !cargando,
                modifier = Modifier.weight(1f)
            )
            BotonLleno(
                texto = "A la vez",
                icono = Icons.Rounded.Bolt,
                onClick = { vm.cargarALaVez() },
                enabled = !cargando,
                modifier = Modifier.weight(1f)
            )
        }
        when (val estado = carga) {
            CargaUi.Inicial -> Unit
            CargaUi.Cargando -> CircularProgressIndicator(color = NaranjaVivo)
            is CargaUi.Exito -> RecuadroExito {
                Text(
                    text = "Modo: ${estado.modo}",
                    color = VerdeTexto,
                    fontWeight = FontWeight.Bold,
                    style = EstiloNumeros
                )
                Text("Perfil: ${estado.perfil}", color = VerdeTexto, style = EstiloNumeros)
                Text("Pedidos: ${estado.pedidos}", color = VerdeTexto, style = EstiloNumeros)
                Text(
                    text = "Tiempo: ${estado.milisegundos} ms",
                    color = VerdeTexto,
                    style = EstiloNumeros
                )
            }
            is CargaUi.Error -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(14.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.ErrorOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text = estado.mensaje,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Start
                        )
                    }
                }
                BotonBorde(
                    texto = "Reintentar",
                    icono = Icons.Rounded.Refresh,
                    onClick = { vm.cargarALaVez() }
                )
            }
        }
    }
}

// ----- Piezas reutilizables

@Composable
private fun Tarjeta(
    titulo: String,
    detalle: String,
    icono: ImageVector,
    contenido: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = Blanco),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                IconoTarjeta(icono)
                Column {
                    Text(titulo, color = Marino, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    Text(detalle, color = TextoSuave, fontSize = 13.sp)
                }
            }
            contenido()
        }
    }
}

@Composable
private fun IconoTarjeta(icono: ImageVector) {
    Box(
        modifier = Modifier
            .size(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Marino),
        contentAlignment = Alignment.Center
    ) {
        Icon(icono, contentDescription = null, tint = Blanco)
    }
}

@Composable
private fun BotonLleno(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        ContenidoBoton(texto, icono)
    }
}

@Composable
private fun BotonBorde(
    texto: String,
    icono: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    OutlinedButton(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(14.dp),
        border = BorderStroke(1.5.dp, if (enabled) NaranjaBoton else BordeSuave),
        contentPadding = PaddingValues(horizontal = 10.dp, vertical = 10.dp)
    ) {
        ContenidoBoton(texto, icono)
    }
}

@Composable
private fun ContenidoBoton(texto: String, icono: ImageVector) {
    Icon(icono, contentDescription = null, modifier = Modifier.size(18.dp))
    Spacer(Modifier.width(6.dp))
    Text(texto, maxLines = 1, overflow = TextOverflow.Ellipsis)
}

@Composable
private fun RecuadroExito(contenido: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = VerdeFondo,
        shape = RoundedCornerShape(14.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = Icons.Rounded.CheckCircle,
                contentDescription = null,
                tint = VerdeTexto
            )
            Column(content = contenido)
        }
    }
}
