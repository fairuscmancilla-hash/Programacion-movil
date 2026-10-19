package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.EstiloBoton
import com.tecsup.ayudacampus.ui.components.ICONOS_CATEGORIA
import com.tecsup.ayudacampus.ui.components.IconoCategoria
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.Tarjeta
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

/** Crear (id = null) o editar (id de la categoría). */
@Composable
fun FormularioCategoriaScreen(vm: CategoriasViewModel, id: Int?, onListo: () -> Unit) {
    val categorias by vm.categorias.collectAsState()
    val conteos by vm.ticketsPorCategoria.collectAsState()
    val existente = categorias.firstOrNull { it.id == id }
    val mensaje by vm.mensaje.collectAsState()

    var nombre by rememberSaveable { mutableStateOf("") }
    var icono by rememberSaveable { mutableStateOf("tag") }
    var activa by rememberSaveable { mutableStateOf(true) }
    var cargado by rememberSaveable { mutableStateOf(id == null) }
    var errorNombre by rememberSaveable { mutableStateOf<String?>(null) }

    // Al editar, llena el formulario una sola vez con la categoría de Room
    LaunchedEffect(existente) {
        if (!cargado && existente != null) {
            nombre = existente.nombre; icono = existente.icono; activa = existente.activa
            cargado = true
        }
    }

    Column(Modifier.fillMaxSize()) {
        AppTopBar(if (id == null) "Nueva categoría" else "Editar categoría", onAtras = onListo)
        Column(
            Modifier
                .fillMaxSize()
                .scrollConBarra()
                .padding(horizontal = 16.dp)
        ) {
            AyudaTextField(
                nombre, { nombre = it; errorNombre = null },
                etiqueta = "Nombre de la categoría", error = errorNombre
            )
            Seccion("Ícono")
            ICONOS_CATEGORIA.chunked(3).forEach { fila ->
                Row(horizontalArrangement = Arrangement.spacedBy(10.dp), modifier = Modifier.padding(bottom = 10.dp)) {
                    fila.forEach { ic ->
                        val elegido = ic == icono
                        Surface(
                            onClick = { icono = ic },
                            shape = RoundedCornerShape(14.dp),
                            color = if (elegido) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface,
                            border = BorderStroke(
                                if (elegido) 2.dp else 1.dp,
                                if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant
                            ),
                            modifier = Modifier.weight(1f).height(64.dp)
                        ) {
                            Box(contentAlignment = Alignment.Center) {
                                Icon(iconoCategoria(ic), contentDescription = ic, tint = if (elegido) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface)
                            }
                        }
                    }
                }
            }
            Tarjeta {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Column(Modifier.weight(1f)) {
                        Text("Categoría activa", style = MaterialTheme.typography.titleSmall)
                        Text("Los estudiantes podrán elegirla", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    Switch(checked = activa, onCheckedChange = { activa = it })
                }
            }
            Seccion("Vista previa")
            Tarjeta {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconoCategoria(icono)
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text(nombre.ifBlank { "Nombre de la categoría" }, style = MaterialTheme.typography.titleSmall)
                        Text("${conteos[id] ?: 0} tickets", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                }
            }
            Spacer(Modifier.height(18.dp))
            AyudaButton("Guardar categoría", onClick = {
                if (nombre.isBlank()) {
                    errorNombre = "El nombre es obligatorio"
                } else if (vm.guardar(Categoria(id = id ?: 0, nombre = nombre, icono = icono, activa = activa))) {
                    onListo()
                }
            })
            Spacer(Modifier.height(10.dp))
            AyudaButton("Cancelar", onClick = onListo, estilo = EstiloBoton.SECUNDARIO)
            if (existente != null) {
                Spacer(Modifier.height(10.dp))
                AyudaButton("Eliminar categoría", onClick = {
                    if (vm.eliminar(existente)) onListo()
                }, estilo = EstiloBoton.PELIGRO)
            }
            mensaje?.let {
                Text(it, color = MaterialTheme.colorScheme.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(top = 10.dp))
            }
            Spacer(Modifier.height(24.dp))
        }
    }
}
