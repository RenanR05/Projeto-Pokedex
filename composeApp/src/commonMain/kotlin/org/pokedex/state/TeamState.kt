package org.pokedex.state

import androidx.compose.runtime.mutableStateListOf
import org.pokedex.data.model.Pokemon

object TeamManager {
    private val _team = mutableStateListOf<Pokemon>()
    val team: List<Pokemon> get() = _team

    val maxTeamSize = 6

    fun addToTeam(pokemon: Pokemon): Boolean {
        if (_team.size >= maxTeamSize) return false
        if (_team.any { it.id == pokemon.id }) return false
        _team.add(pokemon)
        return true
    }

    fun removeFromTeam(pokemon: Pokemon) {
        _team.removeAll { it.id == pokemon.id }
    }

    fun isInTeam(pokemonId: Int): Boolean = _team.any { it.id == pokemonId }

    fun teamSize(): Int = _team.size
}
