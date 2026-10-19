package com.tecsup.ayudacampus

import android.Manifest
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.tecsup.ayudacampus.ui.components.puedeNotificar
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.tecsup.ayudacampus.data.local.entity.rolEnum
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.navigation.NavAyuda
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.AyudaViewModelFactory
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val app = application as AyudaApp
        setContent {
            val sesionVm: SesionViewModel = viewModel(
                factory = AyudaViewModelFactory(app.repository, app.preferencias)
            )
            val oscuro by sesionVm.modoOscuro.collectAsState()
            val usuario by sesionVm.usuarioActual.collectAsState()

            // Íconos de la barra de estado según el modo guardado en DataStore
            LaunchedEffect(oscuro) {
                val estilo = if (oscuro) SystemBarStyle.dark(Color.TRANSPARENT)
                else SystemBarStyle.light(Color.TRANSPARENT, Color.TRANSPARENT)
                enableEdgeToEdge(statusBarStyle = estilo, navigationBarStyle = estilo)
            }

            // Android 13+: pide permiso para las notificaciones si el switch está encendido
            val notificaciones by sesionVm.notificaciones.collectAsState()
            val pedirPermiso = rememberLauncherForActivityResult(ActivityResultContracts.RequestPermission()) {}
            LaunchedEffect(notificaciones) {
                if (notificaciones && Build.VERSION.SDK_INT >= 33 && !puedeNotificar(this@MainActivity)) {
                    pedirPermiso.launch(Manifest.permission.POST_NOTIFICATIONS)
                }
            }

            // El tema de TODA la app depende del perfil y del modo oscuro
            AyudaTheme(rol = usuario?.rolEnum() ?: Rol.CLIENTE, oscuro = oscuro) {
                NavAyuda(sesionVm)
            }
        }
    }
}
