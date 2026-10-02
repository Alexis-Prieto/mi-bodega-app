package com.tecsup.mibodega.ui.cliente.screens.pedidos

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ReceiptLong
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

data class PedidoHistorial(
    val id: String,
    val fecha: String,
    val estado: String,
    val cantidadProductos: Int,
    val total: Double
)

val listaPedidosFake = listOf(
    PedidoHistorial("PED-001", "24 Oct 2024", "Entregado", 3, 35.50),
    PedidoHistorial("PED-002", "26 Oct 2024", "En camino", 2, 18.00)
)

/**
 * Pantalla de Historial de Pedidos confirmados.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MisPedidosScreen(
    pedidos: List<PedidoHistorial> = listaPedidosFake,
    onNavegarInicio: () -> Unit = {},
    onNavegarCategorias: () -> Unit = {},
    onNavegarPerfil: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis Pedidos",
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            )
        },
        bottomBar = {
            BarraInferior(
                onNavegarInicio = onNavegarInicio,
                onNavegarCategorias = onNavegarCategorias,
                onNavegarPerfil = onNavegarPerfil
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
        ) {
            if (pedidos.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.ReceiptLong,
                            contentDescription = null,
                            modifier = Modifier.size(64.dp),
                            tint = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Spacer(Modifier.height(16.dp))
                        Text(
                            text = "Aún no tienes pedidos realizados",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(pedidos, key = { it.id }) { pedido ->
                        PedidoCard(pedido = pedido)
                    }
                }
            }
        }
    }
}

@Composable
private fun PedidoCard(pedido: PedidoHistorial) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pedido.id,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = pedido.estado,
                    style = MaterialTheme.typography.labelMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = if (pedido.estado == "Entregado") VerdeBodega else MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .background(
                            color = (if (pedido.estado == "Entregado") VerdeBodega else MaterialTheme.colorScheme.primary).copy(alpha = 0.15f),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Fecha: ${pedido.fecha}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text = "${pedido.cantidadProductos} producto(s)",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )

            HorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp),
                color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Total",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = "S/ %.2f".format(pedido.total),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = VerdeBodega
                )
            }
        }
    }
}

@Composable
private fun BarraInferior(
    onNavegarInicio: () -> Unit = {},
    onNavegarCategorias: () -> Unit = {},
    onNavegarPerfil: () -> Unit = {}
) {
    var seleccionado by remember { mutableStateOf(2) } // Pestaña Pedidos (índice 2)
    val items = listOf(
        Triple("Inicio", Icons.Default.Home, 0),
        Triple("Categorías", Icons.Default.GridView, 1),
        Triple("Pedidos", Icons.Outlined.Assignment, 2),
        Triple("Perfil", Icons.Outlined.Person, 3)
    )
    NavigationBar {
        items.forEach { (etiqueta, icono, indice) ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = {
                    seleccionado = indice
                    when (indice) {
                        0 -> onNavegarInicio()
                        1 -> onNavegarCategorias()
                        3 -> onNavegarPerfil()
                    }
                },
                icon = { Icon(icono, contentDescription = etiqueta) },
                label = { Text(etiqueta) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeBodega,
                    selectedTextColor = VerdeBodega,
                    indicatorColor = VerdeBodega.copy(alpha = 0.15f)
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun MisPedidosPreview() {
    BodegaTheme {
        MisPedidosScreen()
    }
}