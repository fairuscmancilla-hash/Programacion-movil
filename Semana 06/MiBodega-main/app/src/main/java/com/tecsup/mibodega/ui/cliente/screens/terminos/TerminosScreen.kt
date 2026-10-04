package com.tecsup.mibodega.ui.cliente.screens.terminos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun TerminosScreen(
    onVolver: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
    ) {

        IconButton(
            onClick = onVolver,
            modifier = Modifier.padding(4.dp)
        ) {
            Icon(
                imageVector = Icons.Default.ArrowBack,
                contentDescription = "Volver"
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {

            Text(
                text = "Términos y Condiciones",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(8.dp))

            Text(
                text = "Mi Bodega",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(Modifier.height(16.dp))

            HorizontalDivider()

            Spacer(Modifier.height(20.dp))

            SeccionTerminos(
                titulo = "1. Uso de la aplicación",
                contenido = "Mi Bodega permite consultar productos, agregarlos al carrito y realizar pedidos mediante la aplicación."
            )

            SeccionTerminos(
                titulo = "2. Información del usuario",
                contenido = "El usuario es responsable de proporcionar información correcta al registrarse y al ingresar sus datos para la entrega."
            )

            SeccionTerminos(
                titulo = "3. Productos y precios",
                contenido = "Los productos, precios y descripciones mostrados en la aplicación son informativos y pueden actualizarse cuando sea necesario."
            )

            SeccionTerminos(
                titulo = "4. Pedidos",
                contenido = "Antes de confirmar un pedido, el usuario debe revisar los productos seleccionados, cantidades, modalidad de entrega y monto total."
            )

            SeccionTerminos(
                titulo = "5. Entrega y recojo",
                contenido = "El usuario puede seleccionar delivery o recojo en tienda según las opciones disponibles al momento de realizar el pedido."
            )

            SeccionTerminos(
                titulo = "6. Aceptación",
                contenido = "Al continuar utilizando Mi Bodega, el usuario declara haber leído y aceptado estos términos y condiciones."
            )

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionTerminos(
    titulo: String,
    contenido: String
) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )

    Spacer(Modifier.height(6.dp))

    Text(
        text = contenido,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )

    Spacer(Modifier.height(20.dp))
}