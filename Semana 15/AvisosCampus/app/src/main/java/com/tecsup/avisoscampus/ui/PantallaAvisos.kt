package com.tecsup.avisoscampus.ui

import android.Manifest
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import com.tecsup.avisoscampus.model.PermisoUi
import com.tecsup.avisoscampus.notificacion.Notificador
import com.tecsup.avisoscampus.receiver.ModoAvionReceiver
import com.tecsup.avisoscampus.ui.theme.AntiqueGold
import com.tecsup.avisoscampus.ui.theme.GrisFondo
import com.tecsup.avisoscampus.ui.theme.GrisTexto
import com.tecsup.avisoscampus.ui.theme.RojoFondo
import com.tecsup.avisoscampus.ui.theme.RojoTexto
import com.tecsup.avisoscampus.ui.theme.VerdeFondo
import com.tecsup.avisoscampus.ui.theme.VerdeTexto
import com.tecsup.avisoscampus.viewmodel.AvisosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAvisos(vm: AvisosViewModel) {
    val contexto = LocalContext.current

    val permiso by vm.permiso.collectAsState()
    val mensaje by vm.mensaje.collectAsState()
    val faltan by vm.faltan.collectAsState()
    val escuchando by vm.escuchandoAvion.collectAsState()
    val avisos by vm.avisos.collectAsState()

    // El ViewModel emite el evento; la pantalla (que sí tiene Context) muestra la notificación.
    LaunchedEffect(Unit) {
        vm.eventos.collect { aviso ->
            val mostrado = Notificador.mostrar(contexto, aviso)
            // Paso 8: si no se mostró, avisar al ViewModel
            if (!mostrado) vm.noSePudoMostrar()
        }
    }

    // ---------- Paso 2: permiso ----------
    val lanzador = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { concedido ->
        vm.permisoRespondido(concedido)
    }

    LaunchedEffect(Unit) {
        if (Notificador.tienePermiso(contexto)) vm.permisoRespondido(true)
    }

    // ---------- Paso 6: receptor del modo avión ----------
    DisposableEffect(escuchando) {
        if (escuchando) {
            val receptor = ModoAvionReceiver { activo -> vm.alCambiarModoAvion(activo) }
            ContextCompat.registerReceiver(
                contexto,
                receptor,
                IntentFilter(Intent.ACTION_AIRPLANE_MODE_CHANGED),
                ContextCompat.RECEIVER_NOT_EXPORTED
            )
            onDispose { contexto.unregisterReceiver(receptor) }
        } else {
            onDispose { }
        }
    }

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
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        "Estado:",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(8.dp))
                    EtiquetaPermiso(permiso)
                }
                Button(
                    onClick = {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                            lanzador.launch(Manifest.permission.POST_NOTIFICATIONS)
                        } else {
                            vm.permisoRespondido(true)
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) { Text("Permitir notificaciones") }

                // Paso 8: mensaje de error y acceso a Ajustes
                mensaje?.let { texto ->
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(
                                MaterialTheme.colorScheme.errorContainer,
                                RoundedCornerShape(12.dp)
                            )
                            .padding(12.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            texto,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onErrorContainer
                        )
                        OutlinedButton(
                            onClick = {
                                val intent = Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS)
                                    .putExtra(Settings.EXTRA_APP_PACKAGE, contexto.packageName)
                                contexto.startActivity(intent)
                            },
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = MaterialTheme.colorScheme.error
                            )
                        ) { Text("Abrir ajustes") }
                    }
                }
            }

            // ---------- Tarjeta: Avisos (Paso 3) ----------
            TarjetaSeccion(titulo = "Avisos") {
                Text(
                    "Muestra la notificación «Tarea lista» al instante.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Button(
                    onClick = { vm.avisarAhora() },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    )
                ) { Text("Mostrar aviso") }
            }

            // ---------- Tarjeta: Recordatorio (Paso 5) ----------
            TarjetaSeccion(titulo = "Recordatorio") {
                val corriendo = faltan != null
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                    Button(
                        onClick = { vm.recordarEn(10) },
                        enabled = !corriendo,
                        modifier = Modifier.weight(1f)
                    ) { Text("Recordar en 10 s") }
                    OutlinedButton(
                        onClick = { vm.cancelarRecordatorio() },
                        enabled = corriendo,
                        modifier = Modifier.weight(1f)
                    ) { Text("Cancelar") }
                }
                Text(
                    text = faltan?.let { "Faltan ${it}s" } ?: "Sin recordatorio activo",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (corriendo) AntiqueGold else MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // ---------- Tarjeta: Receptor de anuncios (Paso 6) ----------
            TarjetaSeccion(titulo = "Receptor de anuncios") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Escuchar el modo avión",
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.weight(1f)
                    )
                    Switch(
                        checked = escuchando,
                        onCheckedChange = { vm.cambiarEscucha(it) },
                        colors = SwitchDefaults.colors(
                            checkedTrackColor = MaterialTheme.colorScheme.secondary
                        )
                    )
                }
            }

            // ---------- Tarjeta: Historial (Paso 7) ----------
            HistorialAvisos(avisos)

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

@Composable
private fun EtiquetaPermiso(permiso: PermisoUi) {
    val (texto, fondo, color) = when (permiso) {
        PermisoUi.SinPedir -> Triple("Sin pedir", GrisFondo, GrisTexto)
        PermisoUi.Concedido -> Triple("Permitido", VerdeFondo, VerdeTexto)
        PermisoUi.Denegado -> Triple("Denegado", RojoFondo, RojoTexto)
    }
    Text(
        texto,
        style = MaterialTheme.typography.labelLarge,
        fontWeight = FontWeight.SemiBold,
        color = color,
        modifier = Modifier
            .background(fondo, RoundedCornerShape(50))
            .padding(horizontal = 12.dp, vertical = 4.dp)
    )
}
