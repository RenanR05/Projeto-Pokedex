package org.pokedex.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.pokedex.data.repository.PokemonRepository

class TeamBuilderViewModel(
    private val repository: PokemonRepository
) : ViewModel() {

    val team = repository.observeTeam().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    fun removeFromTeam(pokemonId: Int) {
        viewModelScope.launch { repository.removeFromTeam(pokemonId) }
    }
}
