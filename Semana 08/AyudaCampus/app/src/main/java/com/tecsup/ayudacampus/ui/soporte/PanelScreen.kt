package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.Contador
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.Tarjeta
import com.tecsup.ayudacampus.ui.components.graficos.GraficoBarras
import com.tecsup.ayudacampus.ui.components.graficos.GraficoDona
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoEnProceso
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.ui.theme.PrioridadAlta
import com.tecsup.ayudacampus.viewmodel.PanelViewModel

@Composable
fun PanelScreen(vm: PanelViewModel, onVerAlertas: () -> Unit, campana: @Composable () -> Unit = {}) {
    val abiertos by vm.abiertos.collectAsState()
    val enProceso by vm.enProceso.collectAsState()
    val resueltos by vm.resueltos.collectAsState()
    val porEstado by vm.porEstado.collectAsState()
    val porCategoria by vm.porCategoria.collectAsState()
    val altas by vm.altasSinAtender.collectAsState()

    Column(
        Modifier
            .fillMaxSize()
            .scrollConBarra()
            .padding(horizontal = 16.dp)
    ) {
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column(Modifier.weight(1f)) {
                Text("Panel de soporte", style = MaterialTheme.typography.headlineSmall)
                Text("Mesa de ayuda TI · Hoy", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            campana()
        }
        Spacer(Modifier.height(14.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Contador(abiertos, "Abiertos", EstadoAbierto, Modifier.weight(1f))
            Contador(enProceso, "En proceso", EstadoEnProceso, Modifier.weight(1f))
            Contador(resueltos, "Resueltos", EstadoResuelto, Modifier.weight(1f))
        }

        Seccion("Tickets por estado")
        Tarjeta { GraficoDona(porEstado) }

        Seccion("Tickets por categoría")
        Tarjeta { GraficoBarras(porCategoria) }

        if (altas > 0) {
            Spacer(Modifier.height(14.dp))
            Surface(shape = RoundedCornerShape(16.dp), color = PrioridadAlta.copy(alpha = 0.12f), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.Warning, null, tint = PrioridadAlta)
                    Spacer(Modifier.width(10.dp))
                    Column(Modifier.weight(1f)) {
                        Text("$altas ticket(s) de prioridad Alta", style = MaterialTheme.typography.titleSmall, color = PrioridadAlta)
                        Text("sin atender hace más de 1 hora", style = MaterialTheme.typography.bodySmall)
                    }
                    Button(
                        onClick = onVerAlertas,
                        colors = ButtonDefaults.buttonColors(containerColor = PrioridadAlta, contentColor = Color.White)
                    ) { Text("Ver") }
                }
            }
        }
        Spacer(Modifier.height(24.dp))
    }
}
