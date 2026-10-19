package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DesktopWindows
import androidx.compose.material.icons.outlined.Print
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.Wifi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.rolEnum
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.viewmodel.SesionViewModel
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(vm: SesionViewModel, onIrLogin: () -> Unit, onIrPerfil: (Rol) -> Unit) {
    val listo by vm.inicioListo.collectAsState()
    val usuario by vm.usuarioActual.collectAsState()
    val colores = MaterialTheme.colorScheme

    // Si ya hay perfil guardado, salta el login y va directo a su pantalla principal
    LaunchedEffect(listo, usuario) {
        val u = usuario
        if (listo && u != null) {
            delay(600)
            onIrPerfil(u.rolEnum())
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(colores.background)
            .padding(horizontal = 20.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.weight(1f))
        Box(
            Modifier.size(88.dp).background(colores.primary, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(Icons.Outlined.Sms, null, tint = colores.onPrimary, modifier = Modifier.size(48.dp))
        }
        Spacer(Modifier.height(28.dp))
        Text("AyudaCampus", style = MaterialTheme.typography.headlineSmall)
        Text("Reporta y sigue tus incidencias", style = MaterialTheme.typography.bodySmall, color = colores.onSurfaceVariant)
        Spacer(Modifier.height(56.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            IconoSplash(Icons.Outlined.DesktopWindows)
            IconoSplash(Icons.Outlined.Wifi)
            IconoSplash(Icons.Outlined.Print)
        }
        Spacer(Modifier.weight(1.4f))

        // Sin perfil guardado: el usuario entra al login desde aquí
        if (listo && usuario == null) {
            AyudaButton("Comenzar", onClick = onIrLogin)
            TextButton(onClick = onIrLogin, modifier = Modifier.fillMaxWidth()) {
                Text("Ya tengo una cuenta", style = MaterialTheme.typography.labelSmall, color = colores.primary)
            }
        } else {
            Spacer(Modifier.height(100.dp))
        }
    }
}

@Composable
private fun IconoSplash(icono: ImageVector) {
    Box(
        Modifier.size(64.dp).background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(16.dp)),
        contentAlignment = Alignment.Center
    ) {
        Icon(icono, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(30.dp))
    }
}
