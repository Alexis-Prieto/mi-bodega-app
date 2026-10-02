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
import com.tecsup.mibodega.ui.theme.BodegaTheme

private object Rutas {
    const val BIENVENIDA = "bienvenida"
    const val REGISTRO = "registro"
    const val LOGIN = "login"
    const val INICIO = "inicio"
    const val DETALLE = "detalle/{productoId}"
    const val CARRITO = "carrito"
    const val ENTREGA = "entrega"
    const val CONFIRMACION = "confirmacion"
    const val PEDIDOS = "pedidos"
    const val FAVORITOS = "favoritos"
    const val PERFIL = "perfil"
    // const val CATEGORIAS = "categorias" // Descomentar cuando tengas esta pantalla

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

    // Función auxiliar para navegar desde la barra inferior sin acumular pantallas
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
                    onTerminos = { /* TODO: abrir términos y condiciones */ }
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
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) },
                    onNavegarFavoritos = { navController.navigate(Rutas.FAVORITOS) }, // Si favoritos no está en la barra inferior, usa navegación normal
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
                    onConfirmarPedido = {
                        val totalPedido = carrito.sumOf { it.producto.precio * it.cantidad } + 4.0
                        val nuevoPedido = PedidoHistorial(
                            id = "PED-00${historialPedidos.size + 1}",
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
                    onVolverInicio = {
                        carrito = emptyList()
                        navController.navigate(Rutas.INICIO) {
                            popUpTo(Rutas.INICIO) { inclusive = true }
                        }
                    }
                )
            }

            // AQUI ESTABA EL ERROR: Agregamos las funciones de navegación
            composable(Rutas.PEDIDOS) {
                MisPedidosScreen(
                    pedidos = historialPedidos,
                    onVolver = { navController.popBackStack() },
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarCategorias = { /* navegarMenuInferior(Rutas.CATEGORIAS) */ }, // Configura cuando tengas la pantalla
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

            // AQUI ESTABA EL ERROR: Agregamos las funciones de navegación
            composable(Rutas.PERFIL) {
                PerfilScreen(
                    esModoOscuro = esModoOscuro,
                    onModoOscuroChanged = { esModoOscuro = it },
                    onVolver = { navController.popBackStack() },
                    onNavegarInicio = { navegarMenuInferior(Rutas.INICIO) },
                    onNavegarCategorias = { /* navegarMenuInferior(Rutas.CATEGORIAS) */ }, // Configura cuando tengas la pantalla
                    onNavegarPedidos = { navegarMenuInferior(Rutas.PEDIDOS) }
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