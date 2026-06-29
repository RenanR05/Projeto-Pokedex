package org.pokedex.data.repository

import kotlinx.coroutines.flow.Flow
import org.pokedex.data.local.dao.FavoritePokemonDao
import org.pokedex.data.local.dao.PokemonCacheDao
import org.pokedex.data.local.entity.FavoritePokemonEntity
import org.pokedex.data.local.entity.PokemonCacheEntity
import org.pokedex.data.model.Pokemon
import org.pokedex.data.model.PokemonStats
import org.pokedex.data.model.PokemonType
import org.pokedex.data.remote.PokeApiService

class PokemonRepositoryImpl(
    private val apiService: PokeApiService,
    private val cacheDao: PokemonCacheDao,
    private val favoriteDao: FavoritePokemonDao
) : PokemonRepository {

    override suspend fun syncIfNeeded() {
        if (cacheDao.count() > 0) return
        val response = apiService.getPokemonList(limit = 1500)
        val entities = response.results.mapNotNull { item ->
            val id = item.url.trimEnd('/').substringAfterLast('/').toIntOrNull() ?: return@mapNotNull null
            PokemonCacheEntity(
                id = id,
                name = item.name.replaceFirstChar { it.uppercase() },
                types = ""
            )
        }
        cacheDao.insertAll(entities)
    }

    override suspend fun getPage(
        query: String,
        type: String?,
        limit: Int,
        offset: Int
    ): List<PokemonCacheEntity> {
        return if (type != null) {
            cacheDao.getPageByType(query, type, limit, offset)
        } else {
            cacheDao.getPage(query, limit, offset)
        }
    }

    override suspend fun fetchPokemonDetail(id: Int): Pokemon {
        val detail = apiService.getPokemonDetail(id)
        val species = runCatching { apiService.getPokemonSpecies(id) }.getOrNull()

        val description = species
            ?.flavorTextEntries
            ?.firstOrNull { it.language.name == "en" }
            ?.flavorText
            ?.replace("\n", " ")
            ?.replace("", " ")
            ?: ""

        val types = detail.types
            .sortedBy { it.type.name }
            .mapNotNull { it.type.name.toPokemonType() }

        val statsMap = detail.stats.associate { it.stat.name to it.baseStat }

        val imageUrl = detail.sprites.other?.officialArtwork?.frontDefault
            ?: "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/${id}.png"

        val typesString = types.joinToString(",") { it.name }
        cacheDao.updateTypes(id, typesString)

        return Pokemon(
            id = detail.id,
            name = detail.name.replaceFirstChar { it.uppercase() },
            types = types,
            description = description,
            imageUrl = imageUrl,
            stats = PokemonStats(
                hp = statsMap["hp"] ?: 0,
                attack = statsMap["attack"] ?: 0,
                defense = statsMap["defense"] ?: 0,
                spAtk = statsMap["special-attack"] ?: 0,
                spDef = statsMap["special-defense"] ?: 0,
                speed = statsMap["speed"] ?: 0
            )
        )
    }

    override suspend fun addToTeam(pokemon: Pokemon, captureLocation: String) {
        favoriteDao.insert(
            FavoritePokemonEntity(
                id = pokemon.id,
                name = pokemon.name,
                types = pokemon.types.joinToString(",") { it.name },
                imageUrl = pokemon.imageUrl,
                hp = pokemon.stats.hp,
                attack = pokemon.stats.attack,
                defense = pokemon.stats.defense,
                spAtk = pokemon.stats.spAtk,
                spDef = pokemon.stats.spDef,
                speed = pokemon.stats.speed,
                captureLocation = captureLocation
            )
        )
    }

    override suspend fun removeFromTeam(pokemonId: Int) {
        favoriteDao.deleteById(pokemonId)
    }

    override suspend fun isInTeam(pokemonId: Int): Boolean =
        favoriteDao.isInTeam(pokemonId)

    override fun observeTeam(): Flow<List<FavoritePokemonEntity>> =
        favoriteDao.observeAll()
}

private fun String.toPokemonType(): PokemonType? = when (this.lowercase()) {
    "normal" -> PokemonType.NORMAL
    "fire" -> PokemonType.FIRE
    "water" -> PokemonType.WATER
    "grass" -> PokemonType.GRASS
    "electric" -> PokemonType.ELECTRIC
    "ice" -> PokemonType.ICE
    "fighting" -> PokemonType.FIGHTING
    "poison" -> PokemonType.POISON
    "ground" -> PokemonType.GROUND
    "flying" -> PokemonType.FLYING
    "psychic" -> PokemonType.PSYCHIC
    "bug" -> PokemonType.BUG
    "rock" -> PokemonType.ROCK
    "ghost" -> PokemonType.GHOST
    "dragon" -> PokemonType.DRAGON
    "dark" -> PokemonType.DARK
    "steel" -> PokemonType.STEEL
    "fairy" -> PokemonType.FAIRY
    else -> null
}
