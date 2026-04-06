package org.pokedex.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pokedex.state.TeamManager
import org.pokedex.ui.platform.TeamBuilderContent

@Composable
fun TeamBuilderScreen(
    onPokemonClick: (Int) -> Unit
) {
    val team = TeamManager.team

    if (team.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Seu time está vazio. Adicione alguns Pokémons!")
        }
    } else {
        TeamBuilderContent(
            team = team,
            onRemove = { TeamManager.removeFromTeam(it) },
            onPokemonClick = onPokemonClick
        )
    }
}
