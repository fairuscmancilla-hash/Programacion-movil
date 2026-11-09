package com.tecsup.catalogo.ui.components
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@Composable fun ErrorView(mensaje: String, reintentar: () -> Unit) { Column(Modifier.fillMaxWidth().padding(24.dp),horizontalAlignment=Alignment.CenterHorizontally, verticalArrangement=Arrangement.spacedBy(16.dp)) { Text(mensaje); Button(onClick=reintentar) { Text("Reintentar") } } }
