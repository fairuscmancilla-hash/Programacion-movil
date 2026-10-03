package com.tecsup.mibodega.ui.cliente.screens.confirmacion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun ConfirmacionScreen(
    total: Double,
    onVerPedidos: () -> Unit,
    onVolverInicio: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Pedido confirmado",
            tint = VerdeBodega,
            modifier = Modifier.fillMaxWidth(0.25f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "¡Pedido confirmado!",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Tu pedido fue registrado correctamente.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Total pagado",
            style = MaterialTheme.typography.bodyMedium
        )

        Text(
            text = "S/ %.2f".format(total),
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = VerdeBodega
        )

        Spacer(modifier = Modifier.height(32.dp))

        BotonPrimario(
            texto = "Ver mis pedidos",
            onClick = onVerPedidos
        )

        Spacer(modifier = Modifier.height(12.dp))

        androidx.compose.material3.TextButton(
            onClick = onVolverInicio
        ) {
            Text("Volver al inicio")
        }
    }
}