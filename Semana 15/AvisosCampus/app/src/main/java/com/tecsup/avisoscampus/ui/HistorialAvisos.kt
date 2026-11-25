package com.tecsup.avisoscampus.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.avisoscampus.model.Aviso

// Paso 7: dibuja la tarjeta Historial. No conoce el ViewModel: solo recibe la lista.
@Composable
fun HistorialAvisos(avisos: List<Aviso>) {
    TarjetaSeccion(titulo = "Historial") {
        if (avisos.isEmpty()) {
            Text(
                "Sin avisos todavía",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            // Column + forEach (no LazyColumn): la pantalla ya se desplaza
            val recientes = avisos.take(20)
            recientes.forEachIndexed { i, aviso ->
                FilaAviso(aviso)
                if (i < recientes.lastIndex) {
                    HorizontalDivider(
                        modifier = Modifier.padding(vertical = 8.dp),
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}

@Composable
private fun FilaAviso(aviso: Aviso) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.Top
    ) {
        Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Text(
                aviso.origen.texto,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .background(
                        MaterialTheme.colorScheme.secondaryContainer,
                        RoundedCornerShape(50)
                    )
                    .padding(horizontal = 10.dp, vertical = 3.dp)
            )
            Text(
                aviso.titulo,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                aviso.texto,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(Modifier.width(12.dp))
        Text(
            aviso.hora,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
