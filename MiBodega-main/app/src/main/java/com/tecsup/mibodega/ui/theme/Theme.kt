package com.tecsup.mibodega.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,
    background = Blanco,
    onBackground = AzulTexto,
    surface = Blanco,
    onSurface = AzulTexto,
    surfaceVariant = GrisClaro,
    onSurfaceVariant = GrisTexto,
    outline = GrisBorde,
    error = RojoPrecio
)

private val DarkColorScheme = darkColorScheme(
    primary = VerdeBodega,
    onPrimary = Blanco,
    secondary = AzulEnlace,
    background = NegroFondo, // O utiliza un color oscuro como Color(0xFF121212)
    onBackground = Blanco,
    surface = NegroSuperficie, // O Color(0xFF1E1E1E)
    onSurface = Blanco,
    surfaceVariant = GrisOscuro, // O Color(0xFF2C2C2C)
    onSurfaceVariant = GrisClaro,
    outline = GrisBorde,
    error = RojoPrecio
)

@Composable
fun BodegaTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colors,
        typography = BodegaTypography,
        content = content
    )
}