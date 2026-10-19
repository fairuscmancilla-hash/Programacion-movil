package com.tecsup.ayudacampus.ui.cliente

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import com.tecsup.ayudacampus.ui.components.scrollConBarra
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Place
import androidx.compose.material.icons.outlined.Schedule
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.AyudaButton
import com.tecsup.ayudacampus.ui.components.AyudaTextField
import com.tecsup.ayudacampus.ui.components.Seccion
import com.tecsup.ayudacampus.ui.components.SelectorPrioridad
import com.tecsup.ayudacampus.ui.components.iconoCategoria
import com.tecsup.ayudacampus.viewmodel.NuevoTicketViewModel

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NuevoTicketScreen(vm: NuevoTicketViewModel, onAtras: () -> Unit, onCreado: () -> Unit) {
    val categorias by vm.categorias.collectAsState()
    val form by vm.form.collectAsState()
    val mesaAbierta by vm.mesaAbierta.collectAsState()
    val colores = MaterialTheme.colorScheme

    Column(Modifier.fillMaxSize()) {
        AppTopBar("Nuevo ticket", onAtras = onAtras)
        Column(
            Modifier
                .fillMaxSize()
                .scrollConBarra()
                .imePadding()
                .padding(horizontal = 16.dp)
        ) {
            if (!mesaAbierta) {
                Surface(shape = RoundedCornerShape(14.dp), color = colores.error.copy(alpha = 0.10f), modifier = Modifier.fillMaxWidth()) {
                    Row(Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Outlined.Block, null, tint = colores.error)
                        Spacer(Modifier.width(10.dp))
                        Column {
                            Text("Mesa de ayuda cerrada", style = MaterialTheme.typography.titleSmall, color = colores.error)
                            Text("Soporte TI no está recibiendo tickets por ahora.", style = MaterialTheme.typography.bodySmall)
                        }
                    }
                }
            }

            Seccion("Categoría")
            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                categorias.forEach { c ->
                    FilterChip(
                        selected = form.categoriaId == c.id,
                        onClick = { vm.elegirCategoria(c.id) },
                        label = { Text(c.nombre) },
                        leadingIcon = { Icon(iconoCategoria(c.icono), null, Modifier.size(18.dp)) },
                        shape = RoundedCornerShape(50),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = colores.primary,
                            selectedLabelColor = colores.onPrimary,
                            selectedLeadingIconColor = colores.onPrimary,
                            containerColor = colores.surface
                        )
                    )
                }
            }
            form.errorCategoria?.let {
                Text(it, color = colores.error, style = MaterialTheme.typography.bodySmall, modifier = Modifier.padding(start = 4.dp))
            }
            Spacer(Modifier.height(10.dp))
            AyudaTextField(form.titulo, vm::cambiarTitulo, etiqueta = "Título", error = form.errorTitulo)
            Spacer(Modifier.height(10.dp))
            AyudaTextField(form.ubicacion, vm::cambiarUbicacion, etiqueta = "Ubicación", icono = Icons.Outlined.Place)

            Seccion("Prioridad")
            SelectorPrioridad(form.prioridad, vm::cambiarPrioridad)
            Spacer(Modifier.height(14.dp))
            AyudaTextField(
                form.descripcion, vm::cambiarDescripcion,
                etiqueta = "Descripción", error = form.errorDescripcion, minLineas = 4
            )
            Spacer(Modifier.height(10.dp))
            Surface(shape = RoundedCornerShape(12.dp), color = colores.primary.copy(alpha = 0.08f), modifier = Modifier.fillMaxWidth()) {
                Row(Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Schedule, null, tint = colores.primary, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(
                        "Tiempo de respuesta estimado: ${form.prioridad.horasRespuesta} horas",
                        style = MaterialTheme.typography.bodySmall, color = colores.onSurfaceVariant
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            AyudaButton("Enviar ticket", onClick = { vm.enviar(onCreado) }, enabled = mesaAbierta && !form.enviando)
            Spacer(Modifier.height(24.dp))
        }
    }
}
