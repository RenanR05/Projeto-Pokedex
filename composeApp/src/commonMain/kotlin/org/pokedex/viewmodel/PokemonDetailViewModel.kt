package org.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.pokedex.data.model.Pokemon
import org.pokedex.data.repository.PokemonRepository

sealed interface PokemonDetailState {
    data object Loading : PokemonDetailState
    data class Success(val pokemon: Pokemon, val isInTeam: Boolean) : PokemonDetailState
    data class Error(val message: String) : PokemonDetailState
}

class PokemonDetailViewModel(
    private val repository: PokemonRepository,
    private val pokemonId: Int
) : ViewModel() {

    private val _state = MutableStateFlow<PokemonDetailState>(PokemonDetailState.Loading)
    val state: StateFlow<PokemonDetailState> = _state.asStateFlow()

    init {
        loadDetail()
    }

    private fun loadDetail() {
        viewModelScope.launch {
            _state.value = PokemonDetailState.Loading
            try {
                val pokemon = repository.fetchPokemonDetail(pokemonId)
                val inTeam = repository.isInTeam(pokemonId)
                _state.value = PokemonDetailState.Success(pokemon, inTeam)
            } catch (e: Exception) {
                _state.value = PokemonDetailState.Error("Erro ao carregar detalhes: ${e.message}")
            }
        }
    }

    fun addToTeam(pokemon: Pokemon, latitude: Double, longitude: Double, photoPath: String?) {
        viewModelScope.launch {
            repository.addToTeam(pokemon, latitude, longitude, photoPath)
            _state.update { current ->
                if (current is PokemonDetailState.Success) current.copy(isInTeam = true) else current
            }
        }
    }

    fun removeFromTeam(pokemonId: Int) {
        viewModelScope.launch {
            repository.removeFromTeam(pokemonId)
            _state.update { current ->
                if (current is PokemonDetailState.Success) current.copy(isInTeam = false) else current
            }
        }
    }
}
