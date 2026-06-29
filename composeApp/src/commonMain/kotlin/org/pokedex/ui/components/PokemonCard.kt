package org.pokedex.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import org.pokedex.data.model.PokemonType
import org.pokedex.ui.theme.getTypeColor

private fun String.toPokemonTypes(): List<PokemonType> =
    split(",").mapNotNull { name ->
        PokemonType.entries.firstOrNull { it.name.equals(name.trim(), ignoreCase = true) }
    }

private fun spriteUrl(id: Int) =
    "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

@Composable
fun PokemonCard(
    id: Int,
    name: String,
    types: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val typeList = types.toPokemonTypes()
    val mainTypeColor = if (typeList.isNotEmpty()) getTypeColor(typeList.first()) else Color(0xFFA8A878)

    ElevatedCard(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            mainTypeColor.copy(alpha = 0.8f),
                            MaterialTheme.colorScheme.surface
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "#${id.toString().padStart(3, '0')}",
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White.copy(alpha = 0.6f)
                )

                AsyncImage(
                    model = spriteUrl(id),
                    contentDescription = name,
                    modifier = Modifier
                        .weight(1f)
                        .padding(8.dp)
                )

                Text(
                    text = name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                if (typeList.isNotEmpty()) {
                    Row(
                        modifier = Modifier.padding(top = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        typeList.forEach { type ->
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = getTypeColor(type).copy(alpha = 0.3f),
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    .padding(horizontal = 8.dp, vertical = 2.dp)
                            ) {
                                Text(
                                    text = type.displayName,
                                    fontSize = 10.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
