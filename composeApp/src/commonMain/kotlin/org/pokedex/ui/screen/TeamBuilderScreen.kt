package org.pokedex.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.pokedex.data.local.entity.FavoritePokemonEntity
import org.pokedex.di.AppDependencies
import org.pokedex.viewmodel.TeamBuilderViewModel

@Composable
fun TeamBuilderScreen(onPokemonClick: (Int) -> Unit) {
    val viewModel = viewModel { TeamBuilderViewModel(AppDependencies.pokemonRepository) }
    val team by viewModel.team.collectAsStateWithLifecycle()

    if (team.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Seu time está vazio. Adicione alguns Pokémons!")
        }
    } else {
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(team, key = { it.id }) { entity ->
                TeamPokemonCard(
                    entity = entity,
                    onClick = { onPokemonClick(entity.id) },
                    onRemove = { viewModel.removeFromTeam(entity.id) }
                )
            }
        }
    }
}

@Composable
private fun TeamPokemonCard(
    entity: FavoritePokemonEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    ElevatedCard(
        onClick = onClick,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            AsyncImage(
                model = entity.imageUrl,
                contentDescription = entity.name,
                modifier = Modifier.size(72.dp)
            )

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "#${entity.id.toString().padStart(3, '0')} ${entity.name}",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = entity.types.replace(",", " / "),
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        text = "Capturado em:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = entity.captureLocation,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            IconButton(onClick = onRemove) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Remover do time",
                    tint = MaterialTheme.colorScheme.error
                )
            }
        }
    }
}
