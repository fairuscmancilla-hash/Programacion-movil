package com.tecsup.murocampus

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
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

                // Paso 6: por ahora solo la lista (el detalle llega en el Paso 7)
                PantallaLista(vm = vm, onAbrir = { })
            }
        }
    }
}
