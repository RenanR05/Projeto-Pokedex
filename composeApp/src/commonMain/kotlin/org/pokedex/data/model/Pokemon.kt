package org.pokedex.data.model

import kotlinx.serialization.Serializable

@Serializable
enum class PokemonType(val displayName: String, val color: Long) {
    NORMAL("Normal", 0xFFA8A878),
    FIRE("Fire", 0xFFF08030),
    WATER("Water", 0xFF6890F0),
    GRASS("Grass", 0xFF78C850),
    ELECTRIC("Electric", 0xFFF8D030),
    ICE("Ice", 0xFF98D8D8),
    FIGHTING("Fighting", 0xFFC03028),
    POISON("Poison", 0xFFA040A0),
    GROUND("Ground", 0xFFE0C068),
    FLYING("Flying", 0xFFA890F0),
    PSYCHIC("Psychic", 0xFFF85888),
    BUG("Bug", 0xFFA8B820),
    ROCK("Rock", 0xFFB8A038),
    GHOST("Ghost", 0xFF705898),
    DRAGON("Dragon", 0xFF7038F8),
    DARK("Dark", 0xFF705848),
    STEEL("Steel", 0xFFB8B8D0),
    FAIRY("Fairy", 0xFFEE99AC)
}

@Serializable
data class PokemonStats(
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val spAtk: Int,
    val spDef: Int,
    val speed: Int
)

@Serializable
data class Pokemon(
    val id: Int,
    val name: String,
    val types: List<PokemonType>,
    val description: String,
    val imageUrl: String,
    val stats: PokemonStats
)
