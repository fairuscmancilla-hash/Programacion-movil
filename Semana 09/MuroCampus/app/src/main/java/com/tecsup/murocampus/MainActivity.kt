package com.tecsup.murocampus

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.murocampus.ui.PantallaCrear
import com.tecsup.murocampus.ui.PantallaDetalle
import com.tecsup.murocampus.ui.PantallaEditar
import com.tecsup.murocampus.ui.PantallaLista
import com.tecsup.murocampus.ui.theme.MuroCampusTheme
import com.tecsup.murocampus.viewmodel.PostsViewModel
import com.tecsup.murocampus.viewmodel.PostsViewModelFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        // Iconos claros en la barra de estado (la barra superior es azul oscuro)
        enableEdgeToEdge(statusBarStyle = SystemBarStyle.dark(Color.TRANSPARENT))
        super.onCreate(savedInstanceState)

        setContent {
            MuroCampusTheme {
                // Un solo ViewModel compartido por las tres pantallas (sobrevive a la rotacion)
                val vm: PostsViewModel = viewModel(factory = PostsViewModelFactory())

                // Navegacion simple: null = lista; un id = detalle; creando = formulario
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }
                var creando by rememberSaveable { mutableStateOf(false) }
                var editando by rememberSaveable { mutableStateOf(false) }

                val id = idSeleccionado
                when {
                    creando -> {
                        val cerrar = { creando = false; vm.reiniciarCreacion() }
                        BackHandler(onBack = cerrar)
                        PantallaCrear(vm = vm, onVolver = cerrar)
                    }
                    id != null && editando -> {
                        // Mejora 2: Editar vuelve al detalle, que ya muestra el titulo nuevo
                        val cerrar = { editando = false; vm.reiniciarEdicion() }
                        BackHandler(onBack = cerrar)
                        PantallaEditar(vm = vm, onVolver = cerrar)
                    }
                    id != null -> {
                        val cerrar = { idSeleccionado = null; vm.reiniciarEliminacion() }
                        BackHandler(onBack = cerrar)
                        PantallaDetalle(
                            id = id,
                            vm = vm,
                            onVolver = cerrar,
                            onEditar = { editando = true },
                            onEliminado = cerrar
                        )
                    }
                    else -> PantallaLista(
                        vm = vm,
                        onAbrir = { postId ->
                            idSeleccionado = postId
                            vm.abrir(postId)   // se pide aqui para no recargar al rotar
                        },
                        onCrear = { creando = true }
                    )
                }
            }
        }
    }
}
