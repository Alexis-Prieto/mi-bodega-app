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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.GrisClaro
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * Pantalla 6: Datos de Entrega.
 * Recopila dirección, teléfono y referencia del cliente para procesar la orden.
 */
@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmarPedido: () -> Unit
) {
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }

    // Control de intento de envío para validar campos vacíos
    var intentoConfirmar by remember { mutableStateOf(false) }

    val errorDireccion = intentoConfirmar && direccion.trim().isEmpty()
    val errorTelefono = intentoConfirmar && telefono.trim().isEmpty()
    val errorReferencia = intentoConfirmar && referencia.trim().isEmpty()

    fun validarYConfirmar() {
        intentoConfirmar = true
        if (direccion.trim().isNotEmpty() &&
            telefono.trim().isNotEmpty() &&
            referencia.trim().isNotEmpty()
        ) {
            onConfirmarPedido()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .padding(horizontal = 20.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Datos de entrega",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(16.dp))

        // Campo Dirección
        Text(
            text = "Dirección de envío",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = direccion,
            onValueChange = {
                direccion = it
            },
            isError = errorDireccion,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            placeholder = { Text("Ej. Av. Primavera 123") },
            leadingIcon = { Icon(Icons.Default.Home, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = GrisClaro,
                focusedContainerColor = GrisClaro,
                errorContainerColor = GrisClaro,
                unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedBorderColor = VerdeBodega,
                errorBorderColor = MaterialTheme.colorScheme.error
            )
        )
        if (errorDireccion) {
            Text(
                text = "La dirección es obligatoria",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Campo Teléfono
        Text(
            text = "Teléfono de contacto",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = telefono,
            onValueChange = {
                telefono = it
            },
            isError = errorTelefono,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            placeholder = { Text("Ej. 987654321") },
            leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = GrisClaro,
                focusedContainerColor = GrisClaro,
                errorContainerColor = GrisClaro,
                unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedBorderColor = VerdeBodega,
                errorBorderColor = MaterialTheme.colorScheme.error
            )
        )
        if (errorTelefono) {
            Text(
                text = "El teléfono es obligatorio",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.height(16.dp))

        // Campo Referencia
        Text(
            text = "Referencia",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold
        )

        OutlinedTextField(
            value = referencia,
            onValueChange = {
                referencia = it
            },
            isError = errorReferencia,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 6.dp),
            placeholder = { Text("Ej. Frente al parque / Casa de 2 pisos") },
            leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
            singleLine = true,
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedContainerColor = GrisClaro,
                focusedContainerColor = GrisClaro,
                errorContainerColor = GrisClaro,
                unfocusedBorderColor = androidx.compose.ui.graphics.Color.Transparent,
                focusedBorderColor = VerdeBodega,
                errorBorderColor = MaterialTheme.colorScheme.error
            )
        )
        if (errorReferencia) {
            Text(
                text = "La referencia es obligatoria",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp)
            )
        }

        Spacer(Modifier.weight(1f))

        BotonPrimario(
            texto = "Confirmar Pedido",
            onClick = { validarYConfirmar() },
            modifier = Modifier.padding(vertical = 24.dp)
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            onVolver = {},
            onConfirmarPedido = {}
        )
    }
}