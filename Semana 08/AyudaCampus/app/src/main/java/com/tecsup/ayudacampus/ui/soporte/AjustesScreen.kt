package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.EstiloBoton
import com.tecsup.ayudacampus.ui.components.NotaDataStore
import com.tecsup.ayudacampus.ui.components.OpcionSwitch
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.TarjetaCambiarPerfil
import com.tecsup.ayudacampus.ui.components.TarjetaPreferencias
import com.tecsup.ayudacampus.ui.components.TarjetaUsuario
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun AjustesScreen(
    sesionVm: SesionViewModel,
    usuario: Usuario,
    onAtras: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val oscuro by sesionVm.modoOscuro.collectAsState()
    val notificaciones by sesionVm.notificaciones.collectAsState()
    val recibir by sesionVm.recibirTickets.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AppTopBar("Ajustes", onAtras = onAtras)
        Column(
            Modifier
                .fillMaxSize()
                .scrollConBarra()
                .padding(horizontal = 16.dp)
        ) {
            TarjetaUsuario(usuario, "Perfil activo")
            Spacer(Modifier.height(12.dp))
            TarjetaCambiarPerfil("Modo práctica: ir a vista Estudiante", onCambiarPerfil)

            Seccion("Preferencias")
            TarjetaPreferencias(
                listOf(
                    OpcionSwitch("Modo oscuro", "Cambia el tema de la app", oscuro, sesionVm::cambiarModoOscuro),
                    OpcionSwitch("Notificaciones", "Avisar tickets nuevos", notificaciones, sesionVm::cambiarNotificaciones),
                    OpcionSwitch("Recibir tickets", "Abrir o cerrar la mesa de ayuda", recibir, sesionVm::cambiarRecibirTickets)
                )
            )
            Spacer(Modifier.height(12.dp))
            NotaDataStore()
            Spacer(Modifier.height(16.dp))
            AyudaButton("Cerrar sesión", onClick = onCerrarSesion, estilo = EstiloBoton.PELIGRO)
            Spacer(Modifier.height(24.dp))
        }
    }
}
