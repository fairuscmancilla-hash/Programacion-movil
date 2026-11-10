package com.tecsup.catalogo.ui.favoritos
import androidx.compose.runtime.*
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tecsup.catalogo.ui.UiState
import com.tecsup.catalogo.ui.lista.Catalogo
import com.tecsup.catalogo.ui.components.*
@Composable fun FavoritosScreen(vm: FavoritosViewModel, abrir: (Int)->Unit) {
 val estado by vm.estado.collectAsStateWithLifecycle()
 when(val s=estado) { UiState.Cargando -> Cargando(); is UiState.Error -> ErrorView(s.mensaje,{}); is UiState.Exito -> Catalogo(s.datos,abrir,vm::alternarFavorito,mensajeVacio="Aún no tienes favoritos.\nToca el corazón ♡ de un producto para guardarlo aquí.") }
}
