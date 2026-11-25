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
import com.tecsup.murocampus.ui.PantallaDetalle
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

                // Navegacion simple: null = lista; un id = detalle
                var idSeleccionado by rememberSaveable { mutableStateOf<Int?>(null) }

                val id = idSeleccionado
                when {
                    id != null -> {
                        BackHandler { idSeleccionado = null }
                        PantallaDetalle(id = id, vm = vm, onVolver = { idSeleccionado = null })
                    }
                    else -> PantallaLista(
                        vm = vm,
                        onAbrir = { postId ->
                            idSeleccionado = postId
                            vm.abrir(postId)   // se pide aqui para no recargar al rotar
                        }
                    )
                }
            }
        }
    }
}
