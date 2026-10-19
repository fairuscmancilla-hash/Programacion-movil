package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.model.Rol
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import com.tecsup.ayudacampus.viewmodel.SesionViewModel

/** Crear una cuenta de estudiante (los usuarios de Soporte TI no se registran desde la app). */
@Composable
fun RegistroScreen(vm: SesionViewModel, onAtras: () -> Unit, onRegistrado: (Rol) -> Unit) {
    val errores by vm.erroresRegistro.collectAsState()
    var nombre by rememberSaveable { mutableStateOf("") }
    var correo by rememberSaveable { mutableStateOf("") }
    var clave by rememberSaveable { mutableStateOf("") }
    var confirmar by rememberSaveable { mutableStateOf("") }

    DisposableEffect(Unit) { onDispose { vm.limpiarErroresRegistro() } }

    Column(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.background)) {
        AppTopBar("Crear cuenta", onAtras = onAtras)
        Column(
            Modifier
                .fillMaxSize()
                .scrollConBarra()
                .imePadding()
                .padding(horizontal = 20.dp)
        ) {
            Text("Regístrate como estudiante", style = MaterialTheme.typography.titleLarge)
            Text(
                "Con tu cuenta podrás reportar incidencias y seguir su atención.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(20.dp))
            AyudaTextField(nombre, { nombre = it; vm.limpiarErroresRegistro() }, etiqueta = "Nombre completo", icono = Icons.Outlined.Person, error = errores.nombre)
            Spacer(Modifier.height(10.dp))
            AyudaTextField(
                correo, { correo = it; vm.limpiarErroresRegistro() }, etiqueta = "Correo", icono = Icons.Outlined.Email,
                teclado = KeyboardType.Email, error = errores.correo
            )
            Spacer(Modifier.height(10.dp))
            AyudaTextField(clave, { clave = it; vm.limpiarErroresRegistro() }, etiqueta = "Contraseña", icono = Icons.Outlined.Lock, esClave = true, error = errores.clave)
            Spacer(Modifier.height(10.dp))
            AyudaTextField(
                confirmar, { confirmar = it; vm.limpiarErroresRegistro() }, etiqueta = "Confirmar contraseña", icono = Icons.Outlined.Lock,
                esClave = true, error = errores.confirmar
            )
            Spacer(Modifier.height(20.dp))
            AyudaButton("Crear cuenta", onClick = { vm.registrar(nombre, correo, clave, confirmar, onRegistrado) })
            TextButton(onClick = onAtras, modifier = Modifier.fillMaxWidth()) {
                Text("¿Ya tienes cuenta?  Inicia sesión", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
