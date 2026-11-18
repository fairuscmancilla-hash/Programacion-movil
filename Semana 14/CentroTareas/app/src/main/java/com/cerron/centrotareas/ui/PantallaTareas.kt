package com.cerron.centrotareas.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cerron.centrotareas.model.CargaUi
import com.cerron.centrotareas.model.PrimosUi
import com.cerron.centrotareas.ui.theme.VerdeFondo
import com.cerron.centrotareas.ui.theme.VerdeTexto
import com.cerron.centrotareas.viewmodel.TareasViewModel

// Ajustado al emulador Pixel_5: con 500000 tardaba menos de 1 s
private const val N_POR_DEFECTO = "3000000"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaTareas(vm: TareasViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("CentroTareas", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { relleno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(relleno)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TarjetaProgreso(vm)
            TarjetaPrimos(vm)
            TarjetaCargas(vm)
        }
    }
}

@Composable
private fun TarjetaProgreso(vm: TareasViewModel) {
    val mensaje by vm.mensaje.collectAsState()
    val progreso by vm.progreso.collectAsState()
    val corriendo by vm.corriendo.collectAsState()
    var toques by rememberSaveable { mutableIntStateOf(0) }

    Tarjeta("Tarea con progreso") {
        Button(onClick = { toques++ }) {
            Text("Tócame: $toques")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { vm.tareaLarga() }, enabled = !corriendo) {
                Text("Iniciar")
            }
            OutlinedButton(onClick = { vm.cancelar() }, enabled = corriendo) {
                Text("Cancelar")
            }
        }
        LinearProgressIndicator(
            progress = { progreso },
            modifier = Modifier.fillMaxWidth()
        )
        Text("${(progreso * 100).toInt()} %")
        Text(mensaje)
    }
}

@Composable
private fun TarjetaPrimos(vm: TareasViewModel) {
    val primos by vm.primos.collectAsState()
    var texto by rememberSaveable { mutableStateOf(N_POR_DEFECTO) }
    var contado by rememberSaveable { mutableIntStateOf(0) }
    val calculando = primos is PrimosUi.Calculando

    Tarjeta("Contar números primos") {
        OutlinedTextField(
            value = texto,
            onValueChange = { texto = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Hasta el número") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val numero = texto.toIntOrNull()
                    if (numero != null && numero >= 2) {
                        contado = numero
                        vm.contar(numero)
                    }
                },
                enabled = !calculando
            ) {
                Text("Contar")
            }
            OutlinedButton(onClick = { vm.cancelarPrimos() }, enabled = calculando) {
                Text("Cancelar")
            }
        }
        when (val estado = primos) {
            PrimosUi.Inicial -> Unit
            PrimosUi.Calculando -> Text("Calculando...")
            PrimosUi.Cancelado -> Text("Cancelado")
            is PrimosUi.Listo -> RecuadroExito {
                Text(
                    text = "Primos hasta $contado: ${estado.cantidad}",
                    color = VerdeTexto,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
                Text("Tiempo: ${estado.milisegundos} ms", color = VerdeTexto)
            }
        }
    }
}

@Composable
private fun RecuadroExito(contenido: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = VerdeFondo,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = contenido
        )
    }
}

@Composable
private fun TarjetaCargas(vm: TareasViewModel) {
    val carga by vm.carga.collectAsState()
    val simularError by vm.simularError.collectAsState()
    val cargando = carga is CargaUi.Cargando

    Tarjeta("Dos cargas") {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Switch(checked = simularError, onCheckedChange = { vm.cambiarSimularError(it) })
            Text("Simular error")
        }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            OutlinedButton(onClick = { vm.cargarUnaPorUna() }, enabled = !cargando) {
                Text("Una por una")
            }
            Button(onClick = { vm.cargarALaVez() }, enabled = !cargando) {
                Text("A la vez")
            }
        }
        when (val estado = carga) {
            CargaUi.Inicial -> Unit
            CargaUi.Cargando -> CircularProgressIndicator()
            is CargaUi.Exito -> RecuadroExito {
                Text("Modo: ${estado.modo}", color = VerdeTexto, fontWeight = FontWeight.Bold)
                Text("Perfil: ${estado.perfil}", color = VerdeTexto)
                Text("Pedidos: ${estado.pedidos}", color = VerdeTexto)
                Text("Tiempo: ${estado.milisegundos} ms", color = VerdeTexto)
            }
            is CargaUi.Error -> {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = MaterialTheme.colorScheme.errorContainer,
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Text(
                        text = estado.mensaje,
                        modifier = Modifier.padding(12.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer,
                        textAlign = TextAlign.Center
                    )
                }
                OutlinedButton(onClick = { vm.cargarALaVez() }) {
                    Text("Reintentar")
                }
            }
        }
    }
}

@Composable
private fun Tarjeta(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = titulo,
                modifier = Modifier.fillMaxWidth(),
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )
            contenido()
        }
    }
}
