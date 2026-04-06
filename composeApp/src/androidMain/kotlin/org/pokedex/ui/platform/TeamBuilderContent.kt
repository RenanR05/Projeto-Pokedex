package org.pokedex.ui.platform

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.pokedex.data.model.Pokemon
import org.pokedex.ui.theme.getTypeColor

@Composable
actual fun TeamBuilderContent(
    team: List<Pokemon>,
    onRemove: (Pokemon) -> Unit,
    onPokemonClick: (Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(team) { pokemon ->
            val mainTypeColor = getTypeColor(pokemon.types.first())
            
            ElevatedCard(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onPokemonClick(pokemon.id) },
                shape = MaterialTheme.shapes.medium
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = pokemon.imageUrl,
                        contentDescription = pokemon.name,
                        modifier = Modifier.size(64.dp)
                    )
                    
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(horizontal = 16.dp)
                    ) {
                        Text(
                            text = pokemon.name,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = "#${pokemon.id.toString().padStart(3, '0')}",
                            fontSize = 14.sp,
                            color = mainTypeColor
                        )
                    }
                    
                    IconButton(onClick = { onRemove(pokemon) }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Remover",
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
            }
        }
    }
}
