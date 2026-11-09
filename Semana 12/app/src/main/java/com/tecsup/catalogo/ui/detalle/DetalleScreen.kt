package com.tecsup.catalogo.ui.detalle
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.components.*
import com.tecsup.catalogo.ui.lista.precio
@Composable fun DetalleScreen(vm: DetalleViewModel, volver: ()->Unit) {
 val estado by vm.estado.collectAsStateWithLifecycle()
 Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(20.dp)) {
  TextButton(onClick=volver) { Text("← Volver al catálogo") }
  when(val s=estado) {
   UiState.Cargando -> Cargando()
   is UiState.Error -> ErrorView(s.mensaje,vm::cargar)
   is UiState.Exito -> { val p=s.datos
    AsyncImage(p.imagenUrl,contentDescription=p.titulo,modifier=Modifier.fillMaxWidth().height(300.dp))
    Text(p.marca,fontSize=14.sp);Text(p.titulo,fontSize=28.sp,fontWeight=FontWeight.Bold)
    Text(precio(p.precio),fontSize=24.sp,modifier=Modifier.padding(vertical=16.dp))
    Text(com.tecsup.catalogo.domain.model.Moneda.aviso, style=MaterialTheme.typography.bodySmall)
    Text("★ ${p.rating}  ·  Unidades disponibles: ${p.stock}", modifier=Modifier.padding(top=12.dp))
    Text(p.descripcion,modifier=Modifier.padding(vertical=20.dp))
    Button(onClick=vm::alternarFavorito,modifier=Modifier.fillMaxWidth()) { Text(if(p.esFavorito) "Quitar de favoritos" else "Guardar en favoritos") }
   }
  }
 }
}
