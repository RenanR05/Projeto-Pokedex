package org.pokedex.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import org.pokedex.data.model.PokemonType
import org.pokedex.platform.getPlatform

val PokedexRed = Color(0xFFDC0A2D)
val PokedexDarkRed = Color(0xFF9B0000)
val DarkBackground = Color(0xFF1A1A2E)
val DarkSurface = Color(0xFF16213E)
val DarkSurfaceVariant = Color(0xFF0F3460)
val AccentBlue = Color(0xFF53A8B6)
val TextWhite = Color(0xFFF5F5F5)
val TextGray = Color(0xFFB0B0B0)

// Cores para o iOS (Light/Clean style)
val IosBlue = Color(0xFF007AFF)
val IosGray = Color(0xFF8E8E93)
val IosBackground = Color(0xFFF2F2F7)

private val DarkColorScheme = darkColorScheme(
    primary = PokedexRed,
    onPrimary = Color.White,
    primaryContainer = PokedexDarkRed,
    secondary = AccentBlue,
    onSecondary = Color.White,
    background = DarkBackground,
    onBackground = TextWhite,
    surface = DarkSurface,
    onSurface = TextWhite,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = TextGray,
    error = Color(0xFFCF6679)
)

private val IosColorScheme = lightColorScheme(
    primary = IosBlue,
    onPrimary = Color.White,
    background = IosBackground,
    surface = Color.White,
    onSurface = Color.Black,
    secondary = IosGray
)

fun getTypeColor(type: PokemonType): Color = Color(type.color)

@Composable
fun PokedexTheme(content: @Composable () -> Unit) {
    val platform = getPlatform()
    val colorScheme = if (platform.isIos) IosColorScheme else DarkColorScheme
    
    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}
