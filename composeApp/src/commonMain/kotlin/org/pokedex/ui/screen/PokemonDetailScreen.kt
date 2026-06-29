package org.pokedex.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import org.pokedex.data.model.Pokemon
import org.pokedex.di.AppDependencies
import org.pokedex.ui.components.StatBar
import org.pokedex.ui.components.TypeBadge
import org.pokedex.ui.theme.getTypeColor
import org.pokedex.viewmodel.PokemonDetailState
import org.pokedex.viewmodel.PokemonDetailViewModel

@Composable
fun PokemonDetailScreen(
    pokemonId: Int,
    onBackClick: () -> Unit
) {
    val viewModel = viewModel(key = "detail_$pokemonId") {
        PokemonDetailViewModel(AppDependencies.pokemonRepository, pokemonId)
    }
    val state by viewModel.state.collectAsStateWithLifecycle()

    Box(modifier = Modifier.fillMaxSize()) {
        when (val s = state) {
            is PokemonDetailState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
            is PokemonDetailState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center).padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = s.message,
                        color = MaterialTheme.colorScheme.error,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(onClick = onBackClick) { Text("Voltar") }
                }
            }
            is PokemonDetailState.Success -> {
                PokemonDetailContent(
                    pokemon = s.pokemon,
                    isInTeam = s.isInTeam,
                    onAddToTeam = { pokemon, location -> viewModel.addToTeam(pokemon, location) },
                    onRemoveFromTeam = { viewModel.removeFromTeam(s.pokemon.id) }
                )
            }
        }
    }
}

@Composable
private fun PokemonDetailContent(
    pokemon: Pokemon,
    isInTeam: Boolean,
    onAddToTeam: (Pokemon, String) -> Unit,
    onRemoveFromTeam: () -> Unit
) {
    val mainTypeColor = getTypeColor(pokemon.types.firstOrNull() ?: return)
    var showCaptureDialog by remember { mutableStateOf(false) }
    var captureLocation by remember { mutableStateOf("") }

    if (showCaptureDialog) {
        AlertDialog(
            onDismissRequest = { showCaptureDialog = false },
            title = { Text("Local de Captura") },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Text("Onde você capturou ${pokemon.name}?")
                    OutlinedTextField(
                        value = captureLocation,
                        onValueChange = { captureLocation = it },
                        placeholder = { Text("Ex: Floresta de Viridian") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (captureLocation.isNotBlank()) {
                            onAddToTeam(pokemon, captureLocation)
                            showCaptureDialog = false
                            captureLocation = ""
                        }
                    }
                ) { Text("Confirmar") }
            },
            dismissButton = {
                TextButton(onClick = {
                    showCaptureDialog = false
                    captureLocation = ""
                }) { Text("Cancelar") }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(mainTypeColor, MaterialTheme.colorScheme.surface)
                    )
                ),
            contentAlignment = Alignment.Center
        ) {
            AsyncImage(
                model = pokemon.imageUrl,
                contentDescription = pokemon.name,
                modifier = Modifier.size(240.dp)
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "#${pokemon.id.toString().padStart(3, '0')}",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = mainTypeColor
            )

            Text(
                text = pokemon.name.uppercase(),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Row(
                modifier = Modifier.padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                pokemon.types.forEach { type -> TypeBadge(type = type) }
            }

            if (pokemon.description.isNotBlank()) {
                Text(
                    text = pokemon.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )
            }

            Text(
                text = "BASE STATS",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp),
                color = mainTypeColor
            )

            StatBar(name = "HP", value = pokemon.stats.hp)
            StatBar(name = "ATK", value = pokemon.stats.attack)
            StatBar(name = "DEF", value = pokemon.stats.defense)
            StatBar(name = "SATK", value = pokemon.stats.spAtk)
            StatBar(name = "SDEF", value = pokemon.stats.spDef)
            StatBar(name = "SPD", value = pokemon.stats.speed)

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (isInTeam) onRemoveFromTeam()
                    else showCaptureDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isInTeam) Color.Gray else mainTypeColor
                )
            ) {
                Text(
                    text = if (isInTeam) "Remover do Time" else "Adicionar ao Time",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
