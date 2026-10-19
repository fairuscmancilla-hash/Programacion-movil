package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Prioridad
import com.tecsup.ayudacampus.ui.theme.color

@Composable
fun PrioridadChip(prioridad: Prioridad, modifier: Modifier = Modifier) =
    Etiqueta(prioridad.etiqueta, prioridad.color(), modifier)

/** Selector Baja / Media / Alta; la opción elegida toma el color de su prioridad. */
@Composable
fun SelectorPrioridad(
    seleccion: Prioridad,
    onSeleccion: (Prioridad) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Row(modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Prioridad.entries.forEach { p ->
            val elegido = p == seleccion
            OutlinedButton(
                onClick = { onSeleccion(p) },
                enabled = enabled,
                modifier = Modifier.weight(1f).height(42.dp),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, if (elegido) p.color() else MaterialTheme.colorScheme.outlineVariant),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = if (elegido) p.color() else MaterialTheme.colorScheme.surface,
                    contentColor = if (elegido) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                )
            ) { Text(p.etiqueta, style = MaterialTheme.typography.titleSmall) }
        }
    }
}
