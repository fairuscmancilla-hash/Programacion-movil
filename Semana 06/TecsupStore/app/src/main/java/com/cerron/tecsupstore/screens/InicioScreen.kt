package com.cerron.tecsupstore.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cerron.tecsupstore.data.categorias
import com.cerron.tecsupstore.data.productos
import com.cerron.tecsupstore.model.Producto

@Composable
fun InicioScreen(
    favoritos: List<Producto>,
    onAgregarFavorito: (Producto) -> Unit,
    modifier: Modifier = Modifier
) {

    var categoriaSeleccionada by remember {
        mutableStateOf("Todos")
    }

    val productosFiltrados =
        if (categoriaSeleccionada == "Todos") {
            productos
        } else {
            productos.filter { producto ->
                producto.categoria == categoriaSeleccionada
            }
        }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 16.dp)
    ) {

        Text(
            text = "TECSUP Store",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Text(
            text = "Encuentra lo que necesitas",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(
                start = 16.dp,
                end = 16.dp,
                top = 4.dp,
                bottom = 12.dp
            )
        )

        LazyRow(
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            items(categorias) { categoria ->

                FilterChip(
                    selected = categoriaSeleccionada == categoria,
                    onClick = {
                        categoriaSeleccionada = categoria
                    },
                    label = {
                        Text(categoria)
                    }
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            items(productosFiltrados) { producto ->

                ProductoCard(
                    producto = producto,
                    esFavorito = favoritos.any {
                        it.id == producto.id
                    },
                    onAgregarFavorito = {
                        onAgregarFavorito(producto)
                    }
                )
            }
        }
    }
}


@Composable
fun ProductoCard(
    producto: Producto,
    esFavorito: Boolean,
    onAgregarFavorito: () -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = producto.descripcion,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )

                Text(
                    text = producto.categoria,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )

                Text(
                    text = "S/ %.2f".format(producto.precio),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Box {

                IconButton(
                    onClick = {
                        expanded = true
                    }
                ) {

                    Text(
                        text = "⋮",
                        style = MaterialTheme.typography.headlineSmall
                    )
                }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = {
                        expanded = false
                    }
                ) {

                    DropdownMenuItem(
                        text = {
                            Text(
                                if (esFavorito) {
                                    "Agregado a favoritos"
                                } else {
                                    "Favoritos"
                                }
                            )
                        },
                        leadingIcon = {
                            Text(
                                text = if (esFavorito) "♥" else "♡",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        onClick = {

                            if (!esFavorito) {
                                onAgregarFavorito()
                            }

                            expanded = false
                        }
                    )

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Text("Compartir")
                        },
                        leadingIcon = {
                            Text(
                                text = "➤",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        onClick = {
                            expanded = false
                        }
                    )

                    HorizontalDivider()

                    DropdownMenuItem(
                        text = {
                            Text("Reportar")
                        },
                        leadingIcon = {
                            Text(
                                text = "⚠",
                                style = MaterialTheme.typography.titleMedium
                            )
                        },
                        onClick = {
                            expanded = false
                        }
                    )
                }
            }
        }
    }
}
