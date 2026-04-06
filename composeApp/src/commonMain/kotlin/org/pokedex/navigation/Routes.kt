package org.pokedex.navigation

import kotlinx.serialization.Serializable

@Serializable
object HomeRoute

@Serializable
object PokedexListRoute

@Serializable
data class PokemonDetailRoute(val pokemonId: Int)

@Serializable
object TeamBuilderRoute
