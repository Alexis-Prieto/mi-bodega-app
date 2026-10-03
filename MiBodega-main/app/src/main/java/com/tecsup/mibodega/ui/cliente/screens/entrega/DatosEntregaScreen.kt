package com.tecsup.mibodega.ui.cliente.screens.entrega

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

enum class MetodoPago {
    EFECTIVO,
    YAPE,
    PLIN
}

@Composable
fun DatosEntregaScreen(
    onVolver: () -> Unit,
    onConfirmarPedido: (nombre: String, telefono: String, direccion: String, referencia: String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    var nombre by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var referencia by remember { mutableStateOf("") }
    var metodoPagoSeleccionado by remember { mutableStateOf(MetodoPago.EFECTIVO) }

    var intentoConfirmar by remember { mutableStateOf(false) }

    val errorNombre = intentoConfirmar && nombre.trim().isEmpty()
    val errorTelefono = intentoConfirmar && telefono.trim().isEmpty()
    val errorDireccion = intentoConfirmar && direccion.trim().isEmpty()
    val errorReferencia = intentoConfirmar && referencia.trim().isEmpty()

    fun validarYConfirmar() {
        focusManager.clearFocus()
        intentoConfirmar = true
        if (nombre.trim().isNotEmpty() &&
            telefono.trim().isNotEmpty() &&
            direccion.trim().isNotEmpty() &&
            referencia.trim().isNotEmpty()
        ) {
            onConfirmarPedido(nombre, telefono, direccion, referencia)
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            // 1. Encabezado superior
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp, horizontal = 4.dp)
            ) {
                IconButton(
                    onClick = onVolver,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "Datos de entrega",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            // 2. Formulario scrolleable limpio
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 20.dp, vertical = 12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Campo Nombre
                Column {
                    Text(
                        text = "Nombre",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedTextField(
                        value = nombre,
                        onValueChange = { nombre = it },
                        isError = errorNombre,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        placeholder = {
                            Text(
                                "Juan Pérez",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        supportingText = if (errorNombre) {
                            {
                                Text(
                                    text = "El nombre es obligatorio",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp
                                )
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            errorContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.8f),
                            focusedBorderColor = VerdeBodega,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        )
                    )
                }

                // Campo Teléfono
                Column {
                    Text(
                        text = "Teléfono",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        isError = errorTelefono,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        placeholder = {
                            Text(
                                "987 654 321",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        supportingText = if (errorTelefono) {
                            {
                                Text(
                                    text = "El teléfono es obligatorio",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp
                                )
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            errorContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.8f),
                            focusedBorderColor = VerdeBodega,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        )
                    )
                }

                // Campo Dirección
                Column {
                    Text(
                        text = "Dirección",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedTextField(
                        value = direccion,
                        onValueChange = { direccion = it },
                        isError = errorDireccion,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        placeholder = {
                            Text(
                                "Av. Los Olivos 123",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        supportingText = if (errorDireccion) {
                            {
                                Text(
                                    text = "La dirección es obligatoria",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp
                                )
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            errorContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.8f),
                            focusedBorderColor = VerdeBodega,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        )
                    )
                }

                // Campo Referencia
                Column {
                    Text(
                        text = "Referencia",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    OutlinedTextField(
                        value = referencia,
                        onValueChange = { referencia = it },
                        isError = errorReferencia,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp),
                        placeholder = {
                            Text(
                                "Frente al parque",
                                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f),
                                fontSize = 14.sp
                            )
                        },
                        supportingText = if (errorReferencia) {
                            {
                                Text(
                                    text = "La referencia es obligatoria",
                                    color = MaterialTheme.colorScheme.error,
                                    fontSize = 11.sp
                                )
                            }
                        } else null,
                        singleLine = true,
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            errorContainerColor = MaterialTheme.colorScheme.surface,
                            focusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                            unfocusedBorderColor = Color.LightGray.copy(alpha = 0.8f),
                            focusedBorderColor = VerdeBodega,
                            errorBorderColor = MaterialTheme.colorScheme.error
                        )
                    )
                }

                // Sección Método de pago (cierra teclado al seleccionar)
                Column {
                    Text(
                        text = "Método de pago",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Column(
                        modifier = Modifier.padding(top = 4.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OpcionMetodoPago(
                            titulo = "Efectivo al entregar",
                            iconoRes = R.drawable.dinero_icono,
                            seleccionado = metodoPagoSeleccionado == MetodoPago.EFECTIVO,
                            onSelect = {
                                focusManager.clearFocus()
                                metodoPagoSeleccionado = MetodoPago.EFECTIVO
                            }
                        )

                        OpcionMetodoPago(
                            titulo = "Yape",
                            iconoRes = R.drawable.logo_yape,
                            seleccionado = metodoPagoSeleccionado == MetodoPago.YAPE,
                            onSelect = {
                                focusManager.clearFocus()
                                metodoPagoSeleccionado = MetodoPago.YAPE
                            }
                        )

                        OpcionMetodoPago(
                            titulo = "Plin",
                            iconoRes = R.drawable.logo_plin,
                            seleccionado = metodoPagoSeleccionado == MetodoPago.PLIN,
                            onSelect = {
                                focusManager.clearFocus()
                                metodoPagoSeleccionado = MetodoPago.PLIN
                            }
                        )
                    }
                }
            }

            // 3. Botón inferior fijo
            BotonPrimario(
                texto = "Confirmar pedido",
                onClick = { validarYConfirmar() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp, vertical = 8.dp)
            )
        }
    }
}

@Composable
private fun OpcionMetodoPago(
    titulo: String,
    iconoRes: Int,
    seleccionado: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelect() }
            .padding(vertical = 4.dp, horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        RadioButton(
            selected = seleccionado,
            onClick = onSelect,
            colors = RadioButtonDefaults.colors(
                selectedColor = VerdeBodega,
                unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant
            )
        )

        Card(
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier.size(30.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Image(
                painter = painterResource(id = iconoRes),
                contentDescription = titulo,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Fit
            )
        }

        Text(
            text = titulo,
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun DatosEntregaPreview() {
    BodegaTheme {
        DatosEntregaScreen(
            onVolver = {},
            onConfirmarPedido = { _, _, _, _ -> }
        )
    }
}