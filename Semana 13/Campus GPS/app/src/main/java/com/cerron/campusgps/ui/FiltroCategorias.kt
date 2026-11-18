package com.cerron.campusgps.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable
fun FiltroCategorias(categorias: List<String>, seleccionada: String?, onSeleccionar: (String?) -> Unit) {
 Row(Modifier.fillMaxWidth().horizontalScroll(rememberScrollState()), horizontalArrangement = Arrangement.spacedBy(6.dp)) {
  ChipCampus("Todas", seleccionada == null) { onSeleccionar(null) }
  categorias.forEach { categoria -> ChipCampus(categoria, seleccionada == categoria) { onSeleccionar(categoria) } }
 }
}
@Composable
fun ChipCampus(texto: String, seleccionado: Boolean, onClick: () -> Unit) {
 FilterChip(selected = seleccionado, onClick = onClick, label = { Text(texto) },
  colors = FilterChipDefaults.filterChipColors(selectedContainerColor = MaterialTheme.colorScheme.tertiary, selectedLabelColor = MaterialTheme.colorScheme.onTertiary))
}
