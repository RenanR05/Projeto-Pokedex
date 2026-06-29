package org.pokedex.data.repository

import kotlinx.coroutines.flow.Flow
import org.pokedex.data.local.entity.FavoritePokemonEntity
import org.pokedex.data.local.entity.PokemonCacheEntity
import org.pokedex.data.model.Pokemon

interface PokemonRepository {
    suspend fun syncIfNeeded()
    suspend fun getPage(query: String, type: String?, limit: Int, offset: Int): List<PokemonCacheEntity>
    suspend fun fetchPokemonDetail(id: Int): Pokemon
    suspend fun addToTeam(pokemon: Pokemon, captureLocation: String)
    suspend fun removeFromTeam(pokemonId: Int)
    suspend fun isInTeam(pokemonId: Int): Boolean
    fun observeTeam(): Flow<List<FavoritePokemonEntity>>
}
