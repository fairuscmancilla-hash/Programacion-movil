package com.tecsup.ayudacampus.ui.soporte

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.tecsup.ayudacampus.data.local.entity.Categoria
import com.tecsup.ayudacampus.ui.components.AppTopBar
import com.tecsup.ayudacampus.ui.components.IconoCategoria
import com.tecsup.ayudacampus.ui.components.Tarjeta
import com.tecsup.ayudacampus.ui.components.barraScroll
import com.tecsup.ayudacampus.viewmodel.CategoriasViewModel

/** Ejemplo resuelto: la View solo observa y avisa. onEditar(0) = nueva categoría. */
@Composable
fun CategoriasScreen(vm: CategoriasViewModel, onAtras: () -> Unit, onEditar: (Int) -> Unit) {
    val categorias by vm.categorias.collectAsState()
    val estadoLista = rememberLazyListState()

    Box(Modifier.fillMaxSize()) {
        Column(Modifier.fillMaxSize()) {
            AppTopBar("Categorías", onAtras = onAtras)
            LazyColumn(
                state = estadoLista,
                modifier = Modifier.weight(1f).barraScroll(estadoLista),
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 96.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                items(categorias, key = { it.id }) { c ->
                    CategoriaItem(c, onToggle = { vm.alternar(c) }, onEditar = { onEditar(c.id) })
                }
            }
        }
        FloatingActionButton(
            onClick = { onEditar(0) },
            modifier = Modifier.align(Alignment.BottomEnd).padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary
        ) { Icon(Icons.Filled.Add, contentDescription = "Nueva categoría") }
    }
}

@Composable
private fun CategoriaItem(c: Categoria, onToggle: () -> Unit, onEditar: () -> Unit) {
    Tarjeta {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconoCategoria(c.icono)
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                Text(c.nombre, style = MaterialTheme.typography.titleSmall)
                Text(
                    if (c.activa) "Activa" else "Inactiva",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Switch(checked = c.activa, onCheckedChange = { onToggle() })
            IconButton(onClick = onEditar) {
                Icon(Icons.Outlined.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
