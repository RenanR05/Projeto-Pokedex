package org.pokedex.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_pokemon")
data class FavoritePokemonEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val types: String,
    val imageUrl: String,
    val hp: Int,
    val attack: Int,
    val defense: Int,
    val spAtk: Int,
    val spDef: Int,
    val speed: Int,
    val captureLocation: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val photoPath: String? = null
)
