package com.tecsup.mibodega.ui.cliente.modelo

import androidx.annotation.DrawableRes

/**
 * Modelo de datos para los productos de la bodega.
 */
data class Producto(
    val id: Int,
    val nombre: String,
    val descripcion: String,
    val precio: Double,
    val categoria: String,
    val presentacion: String = "",
    @DrawableRes val imagenRes: Int
)