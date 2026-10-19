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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.theme.AyudaTheme
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

/** Un solo Composable para ambos roles: el color sigue a la pestaña elegida. */
@Composable
fun LoginScreen(vm: SesionViewModel, onIngreso: (Rol) -> Unit, onRegistro: () -> Unit) {
    val error by vm.error.collectAsState()
    val oscuro by vm.modoOscuro.collectAsState()
    var rol by rememberSaveable { mutableStateOf(Rol.CLIENTE) }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }

    AyudaTheme(rol = rol, oscuro = oscuro) {
        Column(
            Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .scrollConBarra()
                .imePadding()
                .padding(horizontal = 20.dp, vertical = 28.dp)
        ) {
            Text("Bienvenido", style = MaterialTheme.typography.headlineSmall)
            Text("Ingresa para continuar", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(24.dp))

            SelectorRol(rol) { rol = it; vm.limpiarError() }
            Spacer(Modifier.height(20.dp))

            AyudaTextField(
                correo, { correo = it; vm.limpiarError() },
                etiqueta = "Correo", icono = Icons.Outlined.Email, teclado = KeyboardType.Email
            )
            Spacer(Modifier.height(12.dp))
            AyudaTextField(
                clave, { clave = it; vm.limpiarError() },
                etiqueta = "Contraseña", icono = Icons.Outlined.Lock, esClave = true
            )
            Text(
                "¿Olvidaste tu contraseña?",
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.fillMaxWidth().padding(top = 10.dp),
                textAlign = TextAlign.End
            )
            error?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 12.dp))
            }
            Spacer(Modifier.height(20.dp))
            AyudaButton("Ingresar", onClick = { vm.login(correo, clave, rol, onIngreso) })
            Spacer(Modifier.height(18.dp))

            if (rol == Rol.CLIENTE) {
                TextButton(onClick = { vm.limpiarError(); onRegistro() }, modifier = Modifier.fillMaxWidth()) {
                    Text(
                        "¿No tienes cuenta?  Regístrate",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            } else {
                Surface(
                    shape = RoundedCornerShape(14.dp),
                    color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(Modifier.padding(14.dp)) {
                        Text("Modo práctica", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                        Text(
                            "Puedes cambiar de perfil desde Ajustes para probar ambos roles.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun SelectorRol(rol: Rol, onCambio: (Rol) -> Unit) {
    Surface(
        shape = RoundedCornerShape(14.dp),
        color = MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(Modifier.padding(4.dp), horizontalArrangement = Arrangement.spacedBy(4.dp)) {
            Rol.entries.forEach { r ->
                val elegido = r == rol
                Surface(
                    onClick = { onCambio(r) },
                    shape = RoundedCornerShape(11.dp),
                    color = if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (elegido) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.weight(1f).height(40.dp)
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(r.etiqueta, style = MaterialTheme.typography.titleSmall)
                    }
                }
            }
        }
    }
}
