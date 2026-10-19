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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.AyudaButton

// TODO (Paso 6): recibir SesionViewModel y saltar el login si ya hay perfil guardado
@Composable
fun SplashScreen(onComenzar: () -> Unit) {
    val colores = MaterialTheme.colorScheme

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
        AyudaButton("Comenzar", onClick = onComenzar)
        TextButton(onClick = onComenzar, modifier = Modifier.fillMaxWidth()) {
            Text("Ya tengo una cuenta", style = MaterialTheme.typography.labelSmall, color = colores.primary)
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
