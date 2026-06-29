package org.pokedex.di

import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import org.pokedex.data.local.AppDatabase
import org.pokedex.data.local.getDatabaseBuilder
import org.pokedex.data.remote.PokeApiService
import org.pokedex.data.repository.PokemonRepositoryImpl

object AppDependencies {

    val database: AppDatabase by lazy {
        getDatabaseBuilder()
            .setDriver(BundledSQLiteDriver())
            .fallbackToDestructiveMigration(dropAllTables = true)
            .build()
    }

    val pokeApiService: PokeApiService by lazy { PokeApiService() }

    val pokemonRepository: PokemonRepositoryImpl by lazy {
        PokemonRepositoryImpl(
            apiService = pokeApiService,
            cacheDao = database.pokemonCacheDao(),
            favoriteDao = database.favoritePokemonDao()
        )
    }
}
