package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.componentes.CampoTexto
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmar: (
        nombre: String,
        telefono: String,
        direccion: String,
        referencia: String,
        tipoEntrega: String
    ) -> Unit
) {

    var nombre by remember {
        mutableStateOf("")
    }

    var telefono by remember {
        mutableStateOf("")
    }

    var direccion by remember {
        mutableStateOf("")
    }

    var referencia by remember {
        mutableStateOf("")
    }

    var tipoEntrega by remember {
        mutableStateOf("Delivery")
    }

    var intentoContinuar by remember {
        mutableStateOf(false)
    }

    val esDelivery = tipoEntrega == "Delivery"

    val errorNombre =
        intentoContinuar && nombre.isBlank()

    val errorTelefono =
        intentoContinuar && telefono.isBlank()

    val errorDireccion =
        intentoContinuar &&
                esDelivery &&
                direccion.isBlank()

    val errorReferencia =
        intentoContinuar &&
                esDelivery &&
                referencia.isBlank()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = onVolver
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver"
                )
            }

            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        Text(
            text = "Completa los datos para realizar tu pedido",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )

        // --------------------------------------------
        // TIPO DE ENTREGA
        // --------------------------------------------

        Text(
            text = "Tipo de entrega",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = tipoEntrega == "Delivery",
                onClick = {
                    tipoEntrega = "Delivery"
                }
            )

            Text(
                text = "Delivery (+ S/ 4.00)"
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            RadioButton(
                selected = tipoEntrega == "Recojo en tienda",
                onClick = {
                    tipoEntrega = "Recojo en tienda"
                }
            )

            Text(
                text = "Recojo en tienda (Gratis)"
            )
        }

        Text(
            text = if (esDelivery) {
                "Costo de envío: S/ 4.00"
            } else {
                "Costo de envío: S/ 0.00"
            },
            color = VerdeBodega,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(
                top = 4.dp,
                bottom = 20.dp
            )
        )

        // --------------------------------------------
        // DATOS PERSONALES
        // --------------------------------------------

        CampoTexto(
            etiqueta = "Nombre completo",
            valor = nombre,
            onValorCambia = {
                nombre = it
            },
            placeholder = "Juan Pérez",
            esError = errorNombre,
            mensajeError = "El nombre es obligatorio"
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        CampoTexto(
            etiqueta = "Teléfono",
            valor = telefono,
            onValorCambia = {
                telefono = it
            },
            placeholder = "987 654 321",
            teclado = KeyboardType.Phone,
            esError = errorTelefono,
            mensajeError = "El teléfono es obligatorio"
        )

        // --------------------------------------------
        // SOLO PARA DELIVERY
        // --------------------------------------------

        if (esDelivery) {

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            CampoTexto(
                etiqueta = "Dirección",
                valor = direccion,
                onValorCambia = {
                    direccion = it
                },
                placeholder = "Av. Los Olivos 123",
                esError = errorDireccion,
                mensajeError = "La dirección es obligatoria"
            )

            Spacer(
                modifier = Modifier.height(16.dp)
            )

            CampoTexto(
                etiqueta = "Referencia",
                valor = referencia,
                onValorCambia = {
                    referencia = it
                },
                placeholder = "Frente al parque",
                esError = errorReferencia,
                mensajeError = "La referencia es obligatoria"
            )
        }

        Spacer(
            modifier = Modifier.height(28.dp)
        )

        BotonPrimario(
            texto = "Confirmar datos",
            onClick = {

                intentoContinuar = true

                val datosPersonalesValidos =
                    nombre.isNotBlank() &&
                            telefono.isNotBlank()

                val datosEntregaValidos =
                    if (esDelivery) {

                        direccion.isNotBlank() &&
                                referencia.isNotBlank()

                    } else {

                        true
                    }

                if (
                    datosPersonalesValidos &&
                    datosEntregaValidos
                ) {

                    onConfirmar(
                        nombre.trim(),
                        telefono.trim(),
                        if (esDelivery) {
                            direccion.trim()
                        } else {
                            "Recojo en tienda"
                        },
                        if (esDelivery) {
                            referencia.trim()
                        } else {
                            ""
                        },
                        tipoEntrega
                    )
                }
            }
        )

        Spacer(
            modifier = Modifier.height(24.dp)
        )
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun DatosEntregaPreview() {

    BodegaTheme {

        DatosEntregaScreen(
            onVolver = {},
            onConfirmar = { _, _, _, _, _ -> }
        )
    }
}