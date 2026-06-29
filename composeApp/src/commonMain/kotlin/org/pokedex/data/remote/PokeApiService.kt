package org.pokedex.data.remote

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json
import org.pokedex.data.remote.dto.PokemonDetailResponse
import org.pokedex.data.remote.dto.PokemonListResponse
import org.pokedex.data.remote.dto.PokemonSpeciesResponse

class PokeApiService {

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }

    private val baseUrl = "https://pokeapi.co/api/v2"

    suspend fun getPokemonList(limit: Int = 1500, offset: Int = 0): PokemonListResponse =
        client.get("$baseUrl/pokemon") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body()

    suspend fun getPokemonDetail(id: Int): PokemonDetailResponse =
        client.get("$baseUrl/pokemon/$id").body()

    suspend fun getPokemonSpecies(id: Int): PokemonSpeciesResponse =
        client.get("$baseUrl/pokemon-species/$id").body()
}
