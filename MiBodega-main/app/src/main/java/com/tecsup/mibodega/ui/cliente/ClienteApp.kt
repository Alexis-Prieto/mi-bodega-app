package com.tecsup.mibodega.ui.cliente

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.tecsup.mibodega.ui.cliente.modelo.ItemCarrito
import com.tecsup.mibodega.ui.cliente.modelo.Producto
import com.tecsup.mibodega.ui.cliente.modelo.listaProductosFake
import com.tecsup.mibodega.ui.cliente.screens.bienvenida.BienvenidaScreen
import com.tecsup.mibodega.ui.cliente.screens.carrito.CarritoScreen
import com.tecsup.mibodega.ui.cliente.screens.categoria.CategoriasScreen
import com.tecsup.mibodega.ui.cliente.screens.confirmacion.ConfirmacionScreen
import com.tecsup.mibodega.ui.cliente.screens.detalle.DetalleProductoScreen
import com.tecsup.mibodega.ui.cliente.screens.entrega.DatosEntregaScreen
import com.tecsup.mibodega.ui.cliente.screens.favoritos.FavoritosScreen
import com.tecsup.mibodega.ui.cliente.screens.inicio.InicioScreen
import com.tecsup.mibodega.ui.cliente.screens.login.LoginScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.MisPedidosScreen
import com.tecsup.mibodega.ui.cliente.screens.pedidos.PedidoHistorial
import com.tecsup.mibodega.ui.cliente.screens.pedidos.listaPedidosFake
import com.tecsup.mibodega.ui.cliente.screens.perfil.PerfilScreen
import com.tecsup.mibodega.ui.cliente.screens.registro.RegistroScreen
import com.tecsup.mibodega.ui.cliente.screens.terminos.TerminosScreen
import com.tecsup.mibodega.ui.theme.BodegaTheme

private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val INICIO = "inicio"

    const val TERMINOS = "terminos"
    const val CATEGORIAS = "categorias"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"
    const val PEDIDOS = "pedidos"
    const val FAVORITOS = "favoritos"
    const val PERFIL = "perfil"

    fun detalle(productoId: Int) = "detalle/$productoId"
}

@Composable
fun ClienteApp() {
    val navController = rememberNavController()

    // Estado global
    var esModoOscuro by remember { mutableStateOf(false) }
    var carrito by remember { mutableStateOf<List<ItemCarrito>>(emptyList()) }
    var historialPedidos by remember { mutableStateOf(listaPedidosFake) }
    var favoritosIds by remember { mutableStateOf(setOf<Int>()) }
    var ultimoNumeroPedido by remember { mutableStateOf("") }
    var ultimoTotalPedido by remember { mutableStateOf("") }
    var ultimaDireccion by remember { mutableStateOf("") }
    var ultimaReferencia by remember { mutableStateOf("") }

    val navegarMenuInferior = { ruta: String ->
        navController.navigate(ruta) {
            popUpTo(navController.graph.startDestinationId) { saveState = true }
            launchSingleTop = true
            restoreState = true
        }
    }

    BodegaTheme(darkTheme = esModoOscuro) {
        NavHost(
            navController = navController,
            startDestination = Rutas.BIENVENIDA
        ) {
            composable(Rutas.BIENVENIDA) {
                BienvenidaScreen(
                    onRegistrarse = { navController.navigate(Rutas.REGISTRO) },
                    onIniciarSesion = { navController.navigate(Rutas.LOGIN) },
                    onTerminos = { navController.navigate(Rutas.TERMINOS)}
                )
            }

            composable(Rutas.LOGIN) {
                LoginScreen(
                    onVolver = { navController.popBackStack() },
                    onLoginExitoso = {
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.REGISTRO) {
                RegistroScreen(
                    onVolver = { navController.popBackStack() },
                    onCrearCuenta = { nombre, telefono, direccion, referencia ->
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.BIENVENIDA) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.INICIO) {
                InicioScreen(
                    cantidadCarrito = carrito.sumOf { it.cantidad },
                    favoritosIds = favoritosIds,
                    onVerCarrito = { navController.navigate(Rutas.CARRITO) },
                    onProductoClick = { producto ->
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarOSumarProducto(carrito, producto, 1)
                    },
                    onToggleFavorito = { producto ->
                        favoritosIds = if (favoritosIds.contains(producto.id)) {
                            favoritosIds - producto.id
                        } else {
                            favoritosIds + producto.id
                        }
                    },
                    onNavegarCategorias = { navegarMenuInferior(Rutas.CATEGORIAS) }, // <-- 2. Enlazado aquí
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) },
                    onNavegarFavoritos = { navController.navigate(Rutas.FAVORITOS) },
                    onNavegarPerfil = { navegarMenuInferior(Rutas.PERFIL) }
                )
            }

            composable(Rutas.TERMINOS) {
                TerminosScreen(
                    onVolver = { navController.popBackStack() }
                )
            }

            composable(Rutas.CATEGORIAS) {
                CategoriasScreen(
                    productos = listaProductosFake,
                    favoritosIds = favoritosIds,
                    onProductoClick = { producto ->
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarOSumarProducto(carrito, producto, 1)
                    },
                    onToggleFavorito = { producto ->
                        favoritosIds = if (favoritosIds.contains(producto.id)) {
                            favoritosIds - producto.id
                        } else {
                            favoritosIds + producto.id
                        }
                    },
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) },
                    onNavegarPerfil = { navegarMenuInferior(Rutas.PERFIL) }
                )
            }
            composable(
                route = Rutas.DETALLE,
                arguments = listOf(navArgument("productoId") { type = NavType.IntType })
            ) { backStackEntry ->
                val productoId = backStackEntry.arguments?.getInt("productoId") ?: 0
                val producto = listaProductosFake.first { it.id == productoId }

                DetalleProductoScreen(
                    producto = producto,
                    onVolver = { navController.popBackStack() },
                    onToggleFavorito = {
                        favoritosIds = if (favoritosIds.contains(producto.id)) {
                            favoritosIds - producto.id
                        } else {
                            favoritosIds + producto.id
                        }
                    },
                    onAgregarAlCarrito = { productoSeleccionado, cantidad ->
                        carrito = agregarOSumarProducto(carrito, productoSeleccionado, cantidad)
                        navController.popBackStack()
                    }
                )
            }

            composable(Rutas.CARRITO) {
                CarritoScreen(
                    carrito = carrito,
                    onVolver = { navController.popBackStack() },
                    onIncrementar = { producto ->
                        carrito = carrito.map {
                            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + 1) else it
                        }
                    },
                    onDecrementar = { producto ->
                        carrito = carrito.mapNotNull {
                            when {
                                it.producto.id != producto.id -> it
                                it.cantidad > 1 -> it.copy(cantidad = it.cantidad - 1)
                                else -> null
                            }
                        }
                    },
                    onEliminar = { producto ->
                        carrito = carrito.filterNot { it.producto.id == producto.id }
                    },
                    onContinuarPedido = { navController.navigate(Rutas.ENTREGA) }
                )
            }

            composable(Rutas.ENTREGA) {
                DatosEntregaScreen(
                    onVolver = { navController.popBackStack() },
                    onConfirmarPedido = { nombre, telefono, direccion, referencia ->
                        val totalPedido = carrito.sumOf { it.producto.precio * it.cantidad } + 4.0
                        val correlativo = historialPedidos.size + 1
                        val numPedido = "%02d".format(correlativo)
                        val totalFormateado = "S/ %.2f".format(totalPedido)

                        ultimoNumeroPedido = numPedido
                        ultimoTotalPedido = totalFormateado
                        ultimaDireccion = direccion
                        ultimaReferencia = referencia

                        val nuevoPedido = PedidoHistorial(
                            id = "PED-$numPedido",
                            fecha = "Hoy",
                            estado = "En camino",
                            cantidadProductos = carrito.sumOf { it.cantidad },
                            total = totalPedido
                        )
                        historialPedidos = listOf(nuevoPedido) + historialPedidos

                        navController.navigate(Rutas.CONFIRMACION)
                    }
                )
            }

            composable(Rutas.CONFIRMACION) {
                ConfirmacionScreen(
                    numeroPedido = ultimoNumeroPedido,
                    total = ultimoTotalPedido,
                    direccion = ultimaDireccion,
                    referencia = ultimaReferencia,
                    onVerEstadoPedido = {
                        carrito = emptyList()
                        navController.navigate(Rutas.PEDIDOS) {
                            popUpTo(Rutas.INICIO) { inclusive = false }
                        }
                    },
                    onVolverInicio = {
                        carrito = emptyList()
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                    }
                )
            }

            composable(Rutas.PEDIDOS) {
                MisPedidosScreen(
                    pedidos = historialPedidos,
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarCategorias = { navegarMenuInferior(Rutas.CATEGORIAS) }, // <-- 4. Corregido
                    onNavegarPerfil = { navegarMenuInferior(Rutas.PERFIL) }
                )
            }

            composable(Rutas.FAVORITOS) {
                val productosFavoritos = listaProductosFake.filter { favoritosIds.contains(it.id) }
                FavoritosScreen(
                    productosFavoritos = productosFavoritos,
                    onVolver = { navController.popBackStack() },
                    onProductoClick = { producto ->
                        navController.navigate(Rutas.detalle(producto.id))
                    },
                    onAgregarProducto = { producto ->
                        carrito = agregarOSumarProducto(carrito, producto, 1)
                    },
                    onToggleFavorito = { producto ->
                        favoritosIds = favoritosIds - producto.id
                    }
                )
            }

            composable(Rutas.PERFIL) {
                PerfilScreen(
                    esModoOscuro = esModoOscuro,
                    onModoOscuroChanged = { esModoOscuro = it },
                    onVolver = { navController.popBackStack() },
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarCategorias = { navegarMenuInferior(Rutas.CATEGORIAS) },
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) }
                )
            }
            composable(Rutas.PERFIL) {
                PerfilScreen(
                    esModoOscuro = esModoOscuro,
                    onModoOscuroChanged = { esModoOscuro = it },
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarCategorias = { navegarMenuInferior(Rutas.CATEGORIAS) },
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) },
                    onCerrarSesion = {
                        navController.navigate(Rutas.BIENVENIDA) {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}

private fun agregarOSumarProducto(
    carrito: List<ItemCarrito>,
    producto: Producto,
    cantidad: Int
): List<ItemCarrito> {
    val itemExistente = carrito.find { it.producto.id == producto.id }
    return if (itemExistente != null) {
        carrito.map {
            if (it.producto.id == producto.id) it.copy(cantidad = it.cantidad + cantidad) else it
        }
    } else {
        carrito + ItemCarrito(producto = producto, cantidad = cantidad)
    }
}