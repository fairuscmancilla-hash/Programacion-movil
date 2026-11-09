package com.tecsup.catalogo.ui.admin
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.components.*
@Composable fun AdminScreen(vm: AdminViewModel) {
 val estado by vm.estado.collectAsStateWithLifecycle(); var confirmar by rememberSaveable { mutableStateOf(false) }
 Column(Modifier.fillMaxSize().padding(24.dp),verticalArrangement=Arrangement.spacedBy(20.dp)) {
  Text("Panel de administración",fontSize=26.sp,fontWeight=FontWeight.Bold)
  when(val s=estado) { UiState.Cargando -> Cargando(); is UiState.Error -> ErrorView(s.mensaje,vm::sincronizar); is UiState.Exito -> {
   Text("${s.datos.guardados} productos guardados",fontSize=22.sp);Text("${s.datos.favoritos} favoritos",fontSize=22.sp)
   Text(s.datos.mensaje)
   Button(onClick=vm::sincronizar,modifier=Modifier.fillMaxWidth()) { Text("Sincronizar") }
   OutlinedButton(onClick={confirmar=true},modifier=Modifier.fillMaxWidth()) { Text("Limpiar caché") }
  } }
 }
 if(confirmar) AlertDialog(onDismissRequest={confirmar=false},title={Text("¿Limpiar caché?")},text={Text("Se eliminarán los productos y favoritos guardados en este dispositivo.")},confirmButton={TextButton(onClick={confirmar=false;vm.limpiarCache()}) {Text("Eliminar")}},dismissButton={TextButton(onClick={confirmar=false}) {Text("Cancelar")}})
}
