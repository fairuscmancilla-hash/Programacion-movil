package com.cerron.campusgps.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
@Composable
fun SelectorRadio(radios: List<Int>, seleccionado: Int, onSeleccionar: (Int) -> Unit) {
 Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(6.dp)) {
  Text("Radio:", fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodySmall)
  radios.forEach { radio -> ChipCampus("$radio m", radio == seleccionado) { onSeleccionar(radio) } }
 }
}
