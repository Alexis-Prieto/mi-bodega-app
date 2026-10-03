package com.tecsup.mibodega.ui.componentes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tecsup.mibodega.ui.theme.GrisBorde
import com.tecsup.mibodega.ui.theme.VerdeBodega

/**
 * El "− cantidad +" reutilizable. Se usa en: Detalle del producto
 * y en cada fila del Carrito.
 */
@Composable
fun SelectorCantidad(
    cantidad: Int,
    onIncrementar: () -> Unit,
    onDecrementar: () -> Unit,
    modifier: Modifier = Modifier,
    minimo: Int = 1
) {
    Surface(
        modifier = modifier, // Recibe las dimensiones desde la pantalla donde se use
        shape = CircleShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.5.dp, Color(0xFFE0E0E0))
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            BotonCirculo(
                icono = Icons.Default.Remove,
                habilitado = cantidad > minimo,
                relleno = false,
                onClick = onDecrementar
            )

            Box(
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$cantidad",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            BotonCirculo(
                icono = Icons.Default.Add,
                habilitado = true,
                relleno = true,
                onClick = onIncrementar
            )
        }
    }
}

@Composable
private fun BotonCirculo(
    icono: ImageVector,
    habilitado: Boolean,
    relleno: Boolean,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        enabled = habilitado,
        modifier = Modifier
            .size(32.dp)
            .background(
                color = if (relleno) VerdeBodega else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                shape = CircleShape
            )
    ) {
        val colorIcono = when {
            relleno -> Color.White
            habilitado -> MaterialTheme.colorScheme.onSurface
            else -> GrisBorde
        }
        Icon(
            imageVector = icono,
            contentDescription = null,
            tint = colorIcono,
            modifier = Modifier.size(16.dp)
        )
    }
}