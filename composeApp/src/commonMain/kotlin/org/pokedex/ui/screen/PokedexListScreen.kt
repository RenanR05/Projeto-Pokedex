package org.pokedex.ui.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.pokedex.data.repository.PokemonRepository
import org.pokedex.ui.components.PokemonCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PokedexListScreen(
    onPokemonClick: (Int) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    val pokemonList = remember { PokemonRepository.getPokemonList() }
    
    val filteredList = remember(searchQuery) {
        if (searchQuery.isBlank()) {
            pokemonList
        } else {
            pokemonList.filter { it.name.contains(searchQuery, ignoreCase = true) }
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        SearchBar(
            query = searchQuery,
            onQueryChange = { searchQuery = it },
            onSearch = {},
            active = false,
            onActiveChange = {},
            placeholder = { Text("Pesquisar Pokémon...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {}

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            contentPadding = PaddingValues(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(filteredList) { pokemon ->
                PokemonCard(
                    pokemon = pokemon,
                    onClick = { onPokemonClick(pokemon.id) }
                )
            }
        }
    }
}
