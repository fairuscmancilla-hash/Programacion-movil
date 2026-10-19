package com.tecsup.ayudacampus.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

enum class EstiloBoton { PRIMARIO, SECUNDARIO, PELIGRO }

@Composable
fun AyudaButton(
    texto: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    estilo: EstiloBoton = EstiloBoton.PRIMARIO
) {
    val forma = RoundedCornerShape(14.dp)
    val mod = modifier.fillMaxWidth().height(52.dp)
    val colores = MaterialTheme.colorScheme
    when (estilo) {
        EstiloBoton.PRIMARIO -> Button(onClick = onClick, modifier = mod, enabled = enabled, shape = forma) {
            Text(texto, fontWeight = FontWeight.SemiBold)
        }
        EstiloBoton.SECUNDARIO -> OutlinedButton(
            onClick = onClick, modifier = mod, enabled = enabled, shape = forma,
            border = BorderStroke(1.5.dp, colores.primary),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colores.primary)
        ) { Text(texto, fontWeight = FontWeight.SemiBold) }
        EstiloBoton.PELIGRO -> OutlinedButton(
            onClick = onClick, modifier = mod, enabled = enabled, shape = forma,
            border = BorderStroke(1.5.dp, colores.error),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colores.error)
        ) { Text(texto, fontWeight = FontWeight.SemiBold) }
    }
}
