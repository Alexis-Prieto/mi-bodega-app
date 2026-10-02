package com.tecsup.mibodega.ui.cliente.screens.inicio

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.Assignment
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tecsup.mibodega.R
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaCategorias
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.componentes.ProductoCard
import com.tecsup.mibodega.ui.theme.BodegaTheme
import com.tecsup.mibodega.ui.theme.VerdeBodega

// Opciones para el ordenamiento de productos por precio
enum class OrdenPrecio(val titulo: String) {
    NINGUNO("Todos"),
    MENOR_A_MAYOR("Menor a Mayor"),
    MAYOR_A_MENOR("Mayor a Menor")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InicioScreen(
    productos: List<Producto> = listaProductosFake,
    favoritosIds: Set<Int> = emptySet(),
    cantidadCarrito: Int,
    onVerCarrito: () -> Unit,
    onProductoClick: (Producto) -> Unit,
    onAgregarProducto: (Producto) -> Unit,
    onToggleFavorito: (Producto) -> Unit = {},
    onNavegarPedidos: () -> Unit = {},
    onNavegarFavoritos: () -> Unit = {},
    onNavegarPerfil: () -> Unit = {}
) {
    var categoriaSeleccionada by remember { mutableStateOf(listaCategorias.first()) }
    var ordenSeleccionado by remember { mutableStateOf(OrdenPrecio.NINGUNO) }

    var textoBusqueda by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current

    // Filtrado por categoría y búsqueda + Ordenamiento por precio
    val productosFiltrados = productos
        .filter { producto ->
            val coincideCategoria = categoriaSeleccionada == "Todos" || producto.categoria == categoriaSeleccionada
            val coincideBusqueda = producto.nombre.contains(textoBusqueda.trim(), ignoreCase = true)
            coincideCategoria && coincideBusqueda
        }
        .let { lista ->
            when (ordenSeleccionado) {
                OrdenPrecio.MENOR_A_MAYOR -> lista.sortedBy { it.precio }
                OrdenPrecio.MAYOR_A_MENOR -> lista.sortedByDescending { it.precio }
                OrdenPrecio.NINGUNO -> lista
            }
        }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = buildAnnotatedString {
                    append("Mi ")
                    withStyle(SpanStyle(color = VerdeBodega)) { append("Bodega") }
                },
                    style = MaterialTheme.typography.titleLarge.copy(fontSize = 25.sp,
                        fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.onBackground) },
                actions = {
                    IconButton(onClick = onNavegarFavoritos) {
                        Icon(
                            imageVector = Icons.Outlined.FavoriteBorder,
                            contentDescription = "Mis Favoritos"
                        )
                    }
                    IconButton(onClick = onVerCarrito) {
                        BadgedBox(
                            badge = {
                                if (cantidadCarrito > 0) {
                                    Badge { Text("$cantidadCarrito") }
                                }
                            }
                        ) {
                            Icon(Icons.Default.ShoppingCart, contentDescription = "Carrito")
                        }
                    }
                }
            )
        },
        bottomBar = {
            BarraInferior(
                onNavegarPedidos = onNavegarPedidos,
                onNavegarPerfil = onNavegarPerfil
            )
        }
    ) { paddingInterno ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingInterno)
                .padding(horizontal = 16.dp)
        ) {
            // 1. Buscador
            OutlinedTextField(
                value = textoBusqueda,
                onValueChange = { textoBusqueda = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                placeholder = {
                    Text(
                        "Buscar productos...",
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                leadingIcon = {
                    Icon(
                        Icons.Default.Search,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                },
                trailingIcon = {
                    if (textoBusqueda.isNotEmpty()) {
                        IconButton(onClick = { textoBusqueda = "" }) {
                            Icon(
                                Icons.Default.Clear,
                                contentDescription = "Limpiar búsqueda",
                                tint = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { keyboardController?.hide() }
                ),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                    focusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                    unfocusedBorderColor = Color.Transparent,
                    focusedBorderColor = VerdeBodega
                )
            )

            // 2. Categorías (Chips con íconos)
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(start = 16.dp, top = 12.dp, end = 16.dp, bottom = 4.dp)
            ) {
                items(listaCategorias) { categoria ->
                    ChipCategoria(
                        texto = categoria,
                        seleccionado = categoria == categoriaSeleccionada,
                        onClick = { categoriaSeleccionada = categoria }
                    )
                }
            }

            // 3. Opciones de orden por precio desplazables
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 4.dp)
            ) {
                items(OrdenPrecio.entries.toTypedArray()) { orden ->
                    FilterChip(
                        selected = ordenSeleccionado == orden,
                        onClick = { ordenSeleccionado = orden },
                        label = { Text(orden.titulo) },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = VerdeBodega.copy(alpha = 0.2f),
                            selectedLabelColor = VerdeBodega,
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            labelColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    )
                }
            }

            // 4. Título de productos destacados (Ubicado justo abajo del filtro de precios)
            Text(
                text = "Productos destacados",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(top = 12.dp, bottom = 4.dp)
            )

            // 5. Lista de Productos
            if (productosFiltrados.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(32.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (textoBusqueda.trim().isNotEmpty()) "No se encontraron productos para \"${textoBusqueda.trim()}\"" else "No se encontraron productos en esta categoría",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontWeight = FontWeight.Medium
                    )
                }
            } else {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 12.dp),
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

@Composable
private fun ChipCategoria(
    texto: String,
    seleccionado: Boolean,
    onClick: () -> Unit
) {
    val fondo = if (seleccionado) VerdeBodega else Color(0xFFF3F4F6)
    val contenido = if (seleccionado) Color.White else Color(0xFF333333)
    val imagenRes = obtenerIconoCategoria(texto)

    Box(
        modifier = Modifier
            .width(72.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(fondo)
            .clickable(onClick = onClick)
            .padding(vertical = 10.dp, horizontal = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if (imagenRes != 0) {
                Image(
                    painter = painterResource(id = imagenRes),
                    contentDescription = texto,
                    modifier = Modifier.size(28.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
            }

            Text(
                text = texto,
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp),
                fontWeight = if (seleccionado) FontWeight.Bold else FontWeight.Medium,
                color = contenido,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

private fun obtenerIconoCategoria(categoria: String): Int {
    return when (categoria.lowercase()) {
        "todos" -> R.drawable.logo_carrito
        "bebidas" -> R.drawable.bebidas_logo
        "abarrotes" -> R.drawable.abarrotes_logo
        "snacks" -> R.drawable.snacks_logo
        else -> R.drawable.logo_carrito
    }
}

@Composable
private fun BarraInferior(
    onNavegarPedidos: () -> Unit = {},
    onNavegarPerfil: () -> Unit = {}
) {
    var seleccionado by remember { mutableStateOf(0) }
    val items = listOf(
        Triple("Inicio", Icons.Default.Home, 0),
        Triple("Categorías", Icons.Default.GridView, 1),
        Triple("Pedidos", Icons.Outlined.Assignment, 2),
        Triple("Perfil", Icons.Outlined.Person, 3)
    )
    NavigationBar (containerColor = MaterialTheme.colorScheme.surface,tonalElevation = 0.dp) {
        items.forEach { (etiqueta, icono, indice) ->
            NavigationBarItem(
                selected = seleccionado == indice,
                onClick = {
                    seleccionado = indice
                    when (indice) {
                        2 -> onNavegarPedidos()
                        3 -> onNavegarPerfil()
                    }
                },
                icon = { Icon(icono, contentDescription = etiqueta) },
                label = { Text(etiqueta) },
                colors = androidx.compose.material3.NavigationBarItemDefaults.colors(
                    selectedIconColor = VerdeBodega,
                    selectedTextColor = VerdeBodega
                )
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun InicioPreview() {
    BodegaTheme {
        InicioScreen(
            cantidadCarrito = 3,
            favoritosIds = setOf(1, 2),
            onVerCarrito = {},
            onProductoClick = {},
            onAgregarProducto = {},
            onToggleFavorito = {},
            onNavegarPedidos = {},
            onNavegarFavoritos = {},
            onNavegarPerfil = {}
        )
    }
}