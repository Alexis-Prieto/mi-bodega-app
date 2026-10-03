package com.tecsup.mibodega.ui.cliente.screens.categoria

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.VerdeBodega

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriasScreen(
    productos: List<Producto> = listaProductosFake,
    favoritosIds: Set<Int> = emptySet(),
    onProductoClick: (Producto) -> Unit = {},
    onAgregarProducto: (Producto) -> Unit = {},
    onToggleFavorito: (Producto) -> Unit = {},
    onNavegarInicio: () -> Unit = {},
    onNavegarPedidos: () -> Unit = {},
    onNavegarPerfil: () -> Unit = {}
) {
    // Si es null muestra las tarjetas de categorías. Si tiene texto, muestra los productos de esa categoría.
    var categoriaSeleccionada by remember { mutableStateOf<String?>(null) }
    val categorias = listaCategorias.filterNot { it.equals("Todos", ignoreCase = true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = categoriaSeleccionada ?: "Categorías",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    // Flecha para regresar a la lista de categorías cuando se está viendo una categoría
                    if (categoriaSeleccionada != null) {
                        IconButton(onClick = { categoriaSeleccionada = null }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Volver a categorías"
                            )
                        }
                    }
                }
            )
        },
        bottomBar = {
            BarraInferiorCategorias(
                onNavegarInicio = onNavegarInicio,
                onNavegarPedidos = onNavegarPedidos,
                onNavegarPerfil = onNavegarPerfil
            )
        }
    ) { paddingInterno ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
        ) {
            if (categoriaSeleccionada == null) {
                // VISTA 1: Tarjetas de Categorías
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(categorias) { categoriaNombre ->
                        val cantidadProductos = productos.count {
                            it.categoria.equals(categoriaNombre, ignoreCase = true)
                        }
                        val iconoRes = obtenerIconoCategoria(categoriaNombre)

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(130.dp)
                                .clickable { categoriaSeleccionada = categoriaNombre },
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Image(
                                    painter = painterResource(id = iconoRes),
                                    contentDescription = categoriaNombre,
                                    modifier = Modifier.size(44.dp)
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = categoriaNombre,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 14.sp
                                )
                                Text(
                                    text = "$cantidadProductos productos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            } else {
                // VISTA 2: Productos filtrados por la categoría seleccionada
                val productosFiltrados = productos.filter {
                    it.categoria.equals(categoriaSeleccionada, ignoreCase = true)
                }

                if (productosFiltrados.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay productos en esta categoría",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(16.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(productosFiltrados) { producto ->
                            ProductoCard(
                                producto = producto,
                                esFavorito = favoritosIds.contains(producto.id),
                                onClick = { onProductoClick(producto) },
                                onAgregar = { onAgregarProducto(producto) },
                                onToggleFavorito = { onToggleFavorito(producto) }
                            )
                        }
                    }
                }
            }
        }
    }
}

private fun obtenerIconoCategoria(categoria: String): Int {
    return when (categoria.lowercase()) {
        "bebidas" -> R.drawable.bebidas_logo
        "abarrotes" -> R.drawable.abarrotes_logo
        "snacks" -> R.drawable.snacks_logo
        else -> R.drawable.logo_carrito
    }
}

@Composable
private fun BarraInferiorCategorias(
    onNavegarInicio: () -> Unit,
    onNavegarPedidos: () -> Unit,
    onNavegarPerfil: () -> Unit
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface, tonalElevation = 0.dp) {
        NavigationBarItem(
            selected = false,
            onClick = onNavegarInicio,
            icon = { Icon(Icons.Default.Home, contentDescription = "Inicio") },
            label = { Text("Inicio") }
        )
        NavigationBarItem(
            selected = true,
            onClick = { },
            icon = { Icon(Icons.Default.GridView, contentDescription = "Categorías") },
            label = { Text("Categorías") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = VerdeBodega,
                selectedTextColor = VerdeBodega
            )
        )
        NavigationBarItem(
            selected = false,
            onClick = onNavegarPedidos,
            icon = { Icon(Icons.Outlined.Assignment, contentDescription = "Pedidos") },
            label = { Text("Pedidos") }
        )
        NavigationBarItem(
            selected = false,
            onClick = onNavegarPerfil,
            icon = { Icon(Icons.Outlined.Person, contentDescription = "Perfil") },
            label = { Text("Perfil") }
        )
    }
}