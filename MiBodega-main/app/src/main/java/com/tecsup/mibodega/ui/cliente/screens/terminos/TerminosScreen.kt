package com.tecsup.mibodega.ui.cliente.screens.terminos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TerminosScreen(
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Términos y Condiciones",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp)
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(modifier = Modifier.height(12.dp))

            SeccionTermino(
                titulo = "1. Aceptación de los Términos",
                contenido = "Al acceder y utilizar la aplicación Mi Bodega, aceptas cumplir con los presentes términos y condiciones de servicio. Si no estás de acuerdo con alguno de los puntos, te recomendamos no utilizar la aplicación."
            )

            SeccionTermino(
                titulo = "2. Uso de la Aplicación",
                contenido = "Mi Bodega es una plataforma destinada al pedido y entrega de productos de abarrotes, bebidas y snacks a domicilio. Queda prohibido el uso de la aplicación para fines ilícitos o no autorizados."
            )

            SeccionTermino(
                titulo = "3. Registro y Cuentas",
                contenido = "Para realizar compras, es necesario registrarse con un número de teléfono válido. Eres responsable de mantener la confidencialidad de tu cuenta y de la actividad que ocurra en ella."
            )

            SeccionTermino(
                titulo = "4. Precios y Disponibilidad",
                contenido = "Todos los precios expuestos en la aplicación incluyen tributos aplicables. La disponibilidad de los productos está sujeta al inventario local al momento de procesar el pedido."
            )

            SeccionTermino(
                titulo = "5. Entregas y Pedidos",
                contenido = "Los tiempos de entrega son estimados y pueden variar según la demanda. El usuario podrá consultar el estado de sus compras en la sección 'Pedidos'."
            )

            SeccionTermino(
                titulo = "6. Privacidad de Datos",
                contenido = "Tus datos personales recopilados serán procesados respetando tu privacidad y utilizándose únicamente para la gestión de envíos y la mejora de tu experiencia de compra."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionTermino(titulo: String, contenido: String) {
    Text(
        text = titulo,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.primary
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
        text = contenido,
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onBackground
    )
    Spacer(modifier = Modifier.height(16.dp))
}