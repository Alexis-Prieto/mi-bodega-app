package com.tecsup.mibodega.ui.cliente.screens.terminos

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold
                        )
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .verticalScroll(rememberScrollState())
        ) {
            SeccionTermino(
                titulo = "1. Aceptación de los Términos",
                contenido = "Al acceder y utilizar la aplicación Mi Bodega, aceptas estar sujeto a los presentes términos y condiciones de uso. Si no estás de acuerdo con alguno de ellos, te pedimos abstenerte de usar la aplicación."
            )

            SeccionTermino(
                titulo = "2. Registro y Cuenta de Usuario",
                contenido = "El usuario se compromete a proporcionar información real, exacta y actualizada durante el proceso de registro y entrega de sus pedidos. Eres responsable de mantener la confidencialidad de tus datos de acceso."
            )

            SeccionTermino(
                titulo = "3. Pedidos y Precios",
                contenido = "Todos los precios de los productos están expresados en Soles (S/) e incluyen los impuestos aplicables. Los precios y la disponibilidad pueden cambiar sin previo aviso. El costo del servicio de entrega se especificará antes de confirmar el pedido."
            )

            SeccionTermino(
                titulo = "4. Entrega y Cobertura",
                contenido = "Las entregas se realizan dentro de las zonas de cobertura establecidas por la bodega. Los tiempos de entrega son estimados y pueden variar debido al tráfico o la disponibilidad de repartidores."
            )

            SeccionTermino(
                titulo = "5. Cancelaciones y Devoluciones",
                contenido = "Puedes solicitar la cancelación de un pedido antes de que este entre en estado 'En camino'. En caso de recibir un producto defectuoso o incorrecto, contacta inmediatamente con la bodega."
            )

            SeccionTermino(
                titulo = "6. Protección de Datos",
                contenido = "Tus datos personales ingresados en la aplicación serán utilizados únicamente para la atención, despacho y gestión de tus pedidos dentro del servicio."
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SeccionTermino(
    titulo: String,
    contenido: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Text(
            text = titulo,
            style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.SemiBold
            ),
            color = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
            text = contenido,
            style = MaterialTheme.typography.bodyMedium.copy(
                lineHeight = 20.sp
            ),
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}