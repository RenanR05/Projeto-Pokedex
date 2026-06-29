package org.pokedex.platform

import androidx.compose.runtime.Composable
import org.pokedex.data.model.Pokemon

data class CaptureData(
    val latitude: Double,
    val longitude: Double,
    val photoPath: String?
)

@Composable
expect fun CaptureScreen(
    pokemon: Pokemon,
    onDismiss: () -> Unit,
    onCaptureComplete: (CaptureData) -> Unit
)
