package org.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import org.pokedex.data.local.dao.FavoritePokemonDao
import org.pokedex.data.local.dao.PokemonCacheDao
import org.pokedex.data.local.entity.FavoritePokemonEntity
import org.pokedex.data.local.entity.PokemonCacheEntity

@Database(
    entities = [PokemonCacheEntity::class, FavoritePokemonEntity::class],
    version = 1,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun pokemonCacheDao(): PokemonCacheDao
    abstract fun favoritePokemonDao(): FavoritePokemonDao
}
