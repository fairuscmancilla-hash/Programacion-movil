package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Usuario
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.Contador
import com.tecsup.ayudacampus.ui.components.EstiloBoton
import com.tecsup.ayudacampus.ui.components.NotaDataStore
import com.tecsup.ayudacampus.ui.components.OpcionSwitch
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.TarjetaCambiarPerfil
import com.tecsup.ayudacampus.ui.components.TarjetaPreferencias
import com.tecsup.ayudacampus.ui.components.TarjetaUsuario
import com.tecsup.ayudacampus.ui.theme.EstadoAbierto
import com.tecsup.ayudacampus.ui.theme.EstadoResuelto
import com.tecsup.ayudacampus.viewmodel.MisTicketsViewModel
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

@Composable
fun PerfilScreen(
    sesionVm: SesionViewModel,
    ticketsVm: MisTicketsViewModel,
    usuario: Usuario,
    onAtras: () -> Unit,
    onCambiarPerfil: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val resumen by ticketsVm.resumen.collectAsState()
    val oscuro by sesionVm.modoOscuro.collectAsState()
    val notificaciones by sesionVm.notificaciones.collectAsState()

    Column(Modifier.fillMaxSize()) {
        AppTopBar("Perfil", onAtras = onAtras)
        Column(
            Modifier
                .fillMaxSize()
                .scrollConBarra()
                .padding(horizontal = 16.dp)
        ) {
            TarjetaUsuario(usuario, Rol.CLIENTE.etiqueta)
            Spacer(Modifier.height(12.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
                Contador(resumen.abiertos, "Abiertos", EstadoAbierto, Modifier.weight(1f))
                Contador(resumen.resueltos, "Resueltos", EstadoResuelto, Modifier.weight(1f))
                Contador(resumen.total, "Total", MaterialTheme.colorScheme.primary, Modifier.weight(1f))
            }
            Spacer(Modifier.height(12.dp))
            TarjetaCambiarPerfil("Modo práctica: ir a vista Soporte TI", onCambiarPerfil)

            Seccion("Preferencias")
            TarjetaPreferencias(
                listOf(
                    OpcionSwitch("Modo oscuro", "Cambia el tema de la app", oscuro, sesionVm::cambiarModoOscuro),
                    OpcionSwitch("Notificaciones", "Avisar cambios de estado", notificaciones, sesionVm::cambiarNotificaciones)
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
