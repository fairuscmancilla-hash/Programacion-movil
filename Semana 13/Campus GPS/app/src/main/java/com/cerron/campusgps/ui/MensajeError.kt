package com.cerron.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cerron.campusgps.model.*
@Composable
fun MensajeError(error: UbicacionUi.Error, abrirAjustes: () -> Unit, reintentar: () -> Unit) {
 Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
  Surface(Modifier.fillMaxWidth(), color = MaterialTheme.colorScheme.errorContainer, shape = RoundedCornerShape(12.dp)) {
   Text(error.mensaje, Modifier.padding(horizontal = 16.dp, vertical = 14.dp), color = MaterialTheme.colorScheme.onErrorContainer, textAlign = TextAlign.Center)
  }
  when (error.tipo) {
   TipoError.SIN_PERMISO -> BotonAccion("Abrir ajustes", abrirAjustes)
   TipoError.GPS_APAGADO, TipoError.OTRO -> BotonAccion("Reintentar", reintentar)
   TipoError.SIN_PLAY_SERVICES -> Unit
  }
 }
}
@Composable
private fun BotonAccion(texto: String, onClick: () -> Unit) {
 OutlinedButton(onClick = onClick, modifier = Modifier.padding(top = 20.dp), colors = ButtonDefaults.outlinedButtonColors(containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onTertiary), border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)) {
  Text(texto, fontWeight = FontWeight.Bold)
 }
}
