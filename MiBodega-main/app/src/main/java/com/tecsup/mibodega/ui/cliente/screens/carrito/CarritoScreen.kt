package com.tecsup.mibodega.ui.cliente.screens.carrito

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBasket
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.BotonPrimario
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

// Opciones de tipo de entrega
enum class TipoEnvio(val titulo: String, val costo: Double) {
    RECOJO("Recojo en tienda", 0.0),
    DELIVERY("Delivery a domicilio", 4.00)
}

/**
 * Pantalla 5: Mi carrito.
 */
@Composable
fun CarritoScreen(
    carrito: List<ItemCarrito>,
    onVolver: () -> Unit,
    onIncrementar: (Producto) -> Unit,
    onDecrementar: (Producto) -> Unit,
    onEliminar: (Producto) -> Unit,
    onVaciarCarrito: (() -> Unit)? = null,
    onContinuarPedido: () -> Unit
) {
    var tipoEnvioSeleccionado by remember { mutableStateOf(TipoEnvio.DELIVERY) }

    val subtotal = carrito.sumOf { it.producto.precio * it.cantidad }
    val costoEnvio = if (carrito.isNotEmpty()) tipoEnvioSeleccionado.costo else 0.0
    val total = if (carrito.isNotEmpty()) subtotal + costoEnvio else 0.0

    // Estado para controlar qué producto se solicitó eliminar
    var productoAEliminar by remember { mutableStateOf<Producto?>(null) }
    // Estado para confirmar vaciar todo el carrito
    var mostrarDialogoVaciar by remember { mutableStateOf(false) }

    // Diálogo de confirmación al eliminar un producto individual
    productoAEliminar?.let { producto ->
        AlertDialog(
            onDismissRequest = { productoAEliminar = null },
            title = {
                Text(
                    text = "Eliminar producto",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("¿Estás seguro de que deseas eliminar \"${producto.nombre}\" del carrito?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onEliminar(producto)
                        productoAEliminar = null
                    }
                ) {
                    Text(
                        text = "Eliminar",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { productoAEliminar = null }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de confirmación para vaciar todo el carrito
    if (mostrarDialogoVaciar) {
        AlertDialog(
            onDismissRequest = { mostrarDialogoVaciar = false },
            title = {
                Text(
                    text = "Vaciar carrito",
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                Text("¿Deseas eliminar todos los productos del carrito?")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        mostrarDialogoVaciar = false
                        if (onVaciarCarrito != null) {
                            onVaciarCarrito()
                        } else {
                            carrito.forEach { onEliminar(it.producto) }
                        }
                    }
                ) {
                    Text(
                        text = "Vaciar",
                        color = MaterialTheme.colorScheme.error,
                        fontWeight = FontWeight.Bold
                    )
                }
            },
            dismissButton = {
                TextButton(onClick = { mostrarDialogoVaciar = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
        ) {
            EncabezadoCarrito(
                onVolver = onVolver,
                mostrarTacho = carrito.isNotEmpty(),
                onVaciarClick = { mostrarDialogoVaciar = true }
            )

            if (carrito.isEmpty()) {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Tu carrito está vacío",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(vertical = 12.dp)
                ) {
                    items(carrito, key = { it.producto.id }) { item ->
                        FilaCarrito(
                            item = item,
                            onIncrementar = { onIncrementar(item.producto) },
                            onDecrementar = {
                                if (item.cantidad == 1) {
                                    productoAEliminar = item.producto
                                } else {
                                    onDecrementar(item.producto)
                                }
                            },
                            onEliminar = { productoAEliminar = item.producto }
                        )
                        HorizontalDivider(
                            modifier = Modifier.padding(vertical = 12.dp),
                            color = Color.LightGray.copy(alpha = 0.3f)
                        )
                    }
                }

                ResumenYBoton(
                    subtotal = subtotal,
                    tipoEnvioSeleccionado = tipoEnvioSeleccionado,
                    onSeleccionarEnvio = { tipoEnvioSeleccionado = it },
                    costoEnvio = costoEnvio,
                    total = total,
                    onContinuarPedido = onContinuarPedido
                )
            }
        }
    }
}

@Composable
private fun EncabezadoCarrito(
    onVolver: () -> Unit,
    mostrarTacho: Boolean,
    onVaciarClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onVolver) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
            }
            Text(
                text = "Mi carrito",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                fontWeight = FontWeight.Bold
            )
        }

        if (mostrarTacho) {
            IconButton(onClick = onVaciarClick) {
                Icon(
                    imageVector = Icons.Outlined.Delete,
                    contentDescription = "Vaciar carrito",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun FilaCarrito(
    item: ItemCarrito,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    onEliminar: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Card(
            modifier = Modifier.size(68.dp),
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = Color.Transparent)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                if (item.producto.imagenRes != 0) {
                    Image(
                        painter = painterResource(id = item.producto.imagenRes),
                        contentDescription = item.producto.nombre,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Fit
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.ShoppingBasket,
                        contentDescription = item.producto.nombre,
                        tint = VerdeBodega,
                        modifier = Modifier.size(32.dp)
                    )
                }
            }
        }

        Spacer(Modifier.width(16.dp))

        Column(modifier = Modifier.weight(1f)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                // Título del producto + Presentación (ej. "Arroz Costeño 1 kg")
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = item.producto.nombre,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    Text(
                        text = item.producto.presentacion,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Normal,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                IconButton(
                    onClick = onEliminar,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Delete,
                        contentDescription = "Eliminar ${item.producto.nombre}",
                        tint = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(Modifier.height(2.dp))

            Text(
                text = "S/ %.2f".format(item.producto.precio),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFE53935)
            )

            Spacer(Modifier.height(8.dp))

            // Componente de píldora neutra exclusivo para Mi Carrito
            SelectorCantidadCarrito(
                cantidad = item.cantidad,
                onIncrementar = onIncrementar,
                onDecrementar = onDecrementar,
                modifier = Modifier
                    .width(115.dp)
                    .height(36.dp)
            )
        }
    }
}

/**
 * Píldora de cantidad exclusiva para la pantalla "Mi Carrito".
 */
@Composable
private fun SelectorCantidadCarrito(
    cantidad: Int,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier,
        shape = CircleShape,
        color = Color(0xFFF2F4F7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            // Botón -
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE4E7EC))
                    .clickable { onDecrementar() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Remove,
                    contentDescription = "Disminuir cantidad",
                    tint = Color(0xFF1D2939),
                    modifier = Modifier.size(16.dp)
                )
            }

            // Valor
            Text(
                text = cantidad.toString(),
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF1D2939),
                modifier = Modifier.padding(horizontal = 8.dp)
            )

            // Botón +
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFFE4E7EC))
                    .clickable { onIncrementar() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Aumentar cantidad",
                    tint = Color(0xFF1D2939),
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
private fun ResumenYBoton(
    subtotal: Double,
    tipoEnvioSeleccionado: TipoEnvio,
    onSeleccionarEnvio: (TipoEnvio) -> Unit,
    costoEnvio: Double,
    total: Double,
    onContinuarPedido: () -> Unit
) {
    Column(modifier = Modifier.padding(horizontal = 20.dp, vertical = 8.dp)) {
        Text(
            text = "Tipo de entrega",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 6.dp)
        )

        TipoEnvio.entries.forEach { tipo ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSeleccionarEnvio(tipo) }
                    .padding(vertical = 2.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = tipoEnvioSeleccionado == tipo,
                    onClick = { onSeleccionarEnvio(tipo) },
                    colors = RadioButtonDefaults.colors(selectedColor = VerdeBodega)
                )
                Text(
                    text = tipo.titulo,
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 15.sp
                )
                Text(
                    text = if (tipo.costo == 0.0) "Gratis" else "S/ %.2f".format(tipo.costo),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    fontSize = 15.sp
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(vertical = 10.dp),
            color = Color.LightGray.copy(alpha = 0.3f)
        )

        FilaResumen(etiqueta = "Subtotal", valor = subtotal)
        FilaResumen(
            etiqueta = if (tipoEnvioSeleccionado == TipoEnvio.RECOJO) "Costo de entrega" else "Costo de delivery",
            valor = costoEnvio
        )

        Spacer(Modifier.height(4.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Total",
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "S/ %.2f".format(total),
                style = MaterialTheme.typography.titleLarge.copy(fontSize = 22.sp),
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        Spacer(Modifier.height(16.dp))

        BotonPrimario(
            texto = "Continuar pedido",
            onClick = onContinuarPedido
        )
    }
}

@Composable
private fun FilaResumen(etiqueta: String, valor: Double) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 3.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = etiqueta,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            fontSize = 15.sp
        )
        Text(
            text = "S/ %.2f".format(valor),
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            fontSize = 15.sp
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun CarritoPreview() {
    val carritoEjemplo = listOf(
        ItemCarrito(listaProductosFake[4], 1),
        ItemCarrito(listaProductosFake[0], 2),
        ItemCarrito(listaProductosFake[2], 1)
    )
    BodegaTheme {
        CarritoScreen(
            carrito = carritoEjemplo,
            onVolver = {},
            onIncrementar = {},
            onDecrementar = {},
            onEliminar = {},
            onContinuarPedido = {}
        )
    }
}