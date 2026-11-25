package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.ui.theme.AntiqueGold
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    // El ViewModel emite el evento; la pantalla (que sí tiene Context) muestra la notificación.
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // TODO Paso 8: si no se mostró, avisar al ViewModel
        }
    }

    // TODO Paso 2: lanzador del permiso y consulta inicial con Notificador.tienePermiso

    // TODO Paso 6: DisposableEffect(escuchando) para registrar y quitar el receptor del modo avión

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("AvisosCampus", fontWeight = FontWeight.SemiBold) },
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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // ---------- Tarjeta: Permiso de notificaciones ----------
            TarjetaSeccion(titulo = "Permiso de notificaciones") {
                Pendiente("TODO Paso 2: estado del permiso y botón Permitir notificaciones")
            }

            // ---------- Tarjeta: Avisos (Paso 3) ----------
            TarjetaSeccion(titulo = "Avisos") {
                Pendiente("TODO Paso 3: botón Mostrar aviso")
            }

            // ---------- Tarjeta: Recordatorio (Paso 5) ----------
            TarjetaSeccion(titulo = "Recordatorio") {
                Pendiente("TODO Paso 5: Recordar en 10 s, Cancelar y Faltan Ns")
            }

            // ---------- Tarjeta: Receptor de anuncios (Paso 6) ----------
            TarjetaSeccion(titulo = "Receptor de anuncios") {
                Pendiente("TODO Paso 6: switch Escuchar el modo avión")
            }

            // ---------- Tarjeta: Historial (Paso 7) ----------
            TarjetaSeccion(titulo = "Historial") {
                Pendiente("TODO Paso 7: lista de avisos (HistorialAvisos)")
            }

            Spacer(Modifier.height(8.dp))
        }
    }
}

// Una Card por función, con su título en azul y un filete dorado.
@Composable
fun TarjetaSeccion(titulo: String, contenido: @Composable ColumnScope.() -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    titulo,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Box(
                    Modifier
                        .width(32.dp)
                        .height(3.dp)
                        .background(AntiqueGold, RoundedCornerShape(2.dp))
                )
            }
            contenido()
        }
    }
}

// Texto temporal para las funciones que aún no se implementan.
@Composable
private fun Pendiente(texto: String) {
    Text(
        texto,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}
