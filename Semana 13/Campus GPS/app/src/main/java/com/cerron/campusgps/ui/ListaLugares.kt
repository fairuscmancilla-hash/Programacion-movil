package com.cerron.campusgps.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cerron.campusgps.model.LugarConDistancia
import com.cerron.campusgps.ui.theme.GlacierSeleccion
import com.cerron.campusgps.ui.theme.GlacierSuave
import com.cerron.campusgps.util.formatearDistancia
@Composable
fun ListaLugares(lugares: List<LugarConDistancia>, seleccionado: Int?, onClick: (Int) -> Unit, modifier: Modifier = Modifier) {
 val estadoLista = rememberLazyListState()
 val orden = lugares.joinToString(",") { it.lugar.id.toString() }
 var ordenAnterior by rememberSaveable { mutableStateOf(orden) }
 // Al reordenar, LazyColumn sigue al primer ítem visible: se vuelve arriba para mostrar el más cercano.
 LaunchedEffect(orden) {
  if (orden != ordenAnterior) {
   ordenAnterior = orden
   estadoLista.scrollToItem(0)
  }
 }
 LazyColumn(modifier = modifier, state = estadoLista, verticalArrangement = Arrangement.spacedBy(6.dp), contentPadding = PaddingValues(vertical = 4.dp)) {
  items(lugares, key = { it.lugar.id }) { item ->
   val elegido = seleccionado == item.lugar.id
   Card(onClick = { onClick(item.lugar.id) }, modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(10.dp),
    colors = CardDefaults.cardColors(containerColor = if (elegido) GlacierSeleccion else GlacierSuave),
    border = if (elegido) BorderStroke(2.dp, MaterialTheme.colorScheme.primary) else null) {
    Row(Modifier.padding(horizontal = 12.dp, vertical = 6.dp), verticalAlignment = Alignment.CenterVertically) {
     Box(Modifier.size(10.dp).background(MaterialTheme.colorScheme.primary, CircleShape))
     Spacer(Modifier.width(12.dp))
     Column(Modifier.weight(1f)) {
      Text(item.lugar.nombre, color = Color.Black, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
      Text(item.lugar.categoria, color = Color(0xFF6B7280), style = MaterialTheme.typography.bodySmall)
     }
     Text(item.metros?.let(::formatearDistancia) ?: "Sin calcular", color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold, style = MaterialTheme.typography.bodyMedium)
    }
   }
  }
 }
}
