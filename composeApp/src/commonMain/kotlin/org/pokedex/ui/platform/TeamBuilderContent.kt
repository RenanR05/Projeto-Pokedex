package org.pokedex.ui.platform

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.pokedex.data.model.Pokemon

@Composable
expect fun TeamBuilderContent(
    team: List<Pokemon>,
    onRemove: (Pokemon) -> Unit,
    onPokemonClick: (Int) -> Unit
)
