package org.pokedex.ui.platform

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.pokedex.data.model.Pokemon

@Composable
actual fun TeamBuilderContent(
    team: List<Pokemon>,
    onRemove: (Pokemon) -> Unit,
    onPokemonClick: (Int) -> Unit
) {
    // iOS Style: Cleaner, thin dividers, blurred background style
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF2F2F7)) // iOS System Gray 6
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(vertical = 16.dp)
        ) {
            items(team) { pokemon ->
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .clickable { onPokemonClick(pokemon.id) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        AsyncImage(
                            model = pokemon.imageUrl,
                            contentDescription = pokemon.name,
                            modifier = Modifier
                                .size(50.dp)
                                .clip(RoundedCornerShape(8.dp))
                        )
                        
                        Column(
                            modifier = Modifier
                                .weight(1f)
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(
                                text = pokemon.name,
                                fontSize = 17.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = Color.Black
                            )
                            Text(
                                text = "#${pokemon.id.toString().padStart(3, '0')}",
                                fontSize = 14.sp,
                                color = Color.Gray
                            )
                        }
                        
                        IconButton(
                            onClick = { onRemove(pokemon) },
                            modifier = Modifier
                                .size(28.dp)
                                .background(Color.Red, RoundedCornerShape(14.dp))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "Remove",
                                tint = Color.White,
                                modifier = Modifier.size(16.dp)
                            )
                        }
                    }
                    Divider(
                        modifier = Modifier.padding(start = 82.dp),
                        color = Color(0xFFC6C6C8), // iOS Separator
                        thickness = 0.5.dp
                    )
                }
            }
        }
    }
}
