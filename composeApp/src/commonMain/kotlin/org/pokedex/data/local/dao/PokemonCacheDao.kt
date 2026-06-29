package org.pokedex.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import org.pokedex.data.local.entity.PokemonCacheEntity

@Dao
interface PokemonCacheDao {

    @Query("SELECT COUNT(*) FROM pokemon_cache")
    suspend fun count(): Int

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(pokemons: List<PokemonCacheEntity>)

    @Query("""
        SELECT * FROM pokemon_cache
        WHERE name LIKE '%' || :query || '%'
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getPage(query: String, limit: Int, offset: Int): List<PokemonCacheEntity>

    @Query("""
        SELECT * FROM pokemon_cache
        WHERE name LIKE '%' || :query || '%'
        AND types LIKE '%' || :type || '%'
        LIMIT :limit OFFSET :offset
    """)
    suspend fun getPageByType(query: String, type: String, limit: Int, offset: Int): List<PokemonCacheEntity>

    @Query("UPDATE pokemon_cache SET types = :types WHERE id = :id")
    suspend fun updateTypes(id: Int, types: String)
}
