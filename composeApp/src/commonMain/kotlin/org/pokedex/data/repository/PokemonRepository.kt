package org.pokedex.data.repository

import org.pokedex.data.model.Pokemon
import org.pokedex.data.model.PokemonStats
import org.pokedex.data.model.PokemonType

object PokemonRepository {

    private fun spriteUrl(id: Int): String =
        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/official-artwork/$id.png"

    fun getPokemonList(): List<Pokemon> = listOf(
        Pokemon(
            id = 1, name = "Bulbasaur",
            types = listOf(PokemonType.GRASS, PokemonType.POISON),
            description = "A strange seed was planted on its back at birth. The plant sprouts and grows with this Pokémon.",
            imageUrl = spriteUrl(1),
            stats = PokemonStats(hp = 45, attack = 49, defense = 49, spAtk = 65, spDef = 65, speed = 45)
        ),
        Pokemon(
            id = 4, name = "Charmander",
            types = listOf(PokemonType.FIRE),
            description = "Obviously prefers hot places. When it rains, steam is said to spout from the tip of its tail.",
            imageUrl = spriteUrl(4),
            stats = PokemonStats(hp = 39, attack = 52, defense = 43, spAtk = 60, spDef = 50, speed = 65)
        ),
        Pokemon(
            id = 6, name = "Charizard",
            types = listOf(PokemonType.FIRE, PokemonType.FLYING),
            description = "Spits fire that is hot enough to melt boulders. Known to cause forest fires unintentionally.",
            imageUrl = spriteUrl(6),
            stats = PokemonStats(hp = 78, attack = 84, defense = 78, spAtk = 109, spDef = 85, speed = 100)
        ),
        Pokemon(
            id = 7, name = "Squirtle",
            types = listOf(PokemonType.WATER),
            description = "After birth, its back swells and hardens into a shell. Powerfully sprays foam from its mouth.",
            imageUrl = spriteUrl(7),
            stats = PokemonStats(hp = 44, attack = 48, defense = 65, spAtk = 50, spDef = 64, speed = 43)
        ),
        Pokemon(
            id = 25, name = "Pikachu",
            types = listOf(PokemonType.ELECTRIC),
            description = "When several of these Pokémon gather, their electricity can build and cause lightning storms.",
            imageUrl = spriteUrl(25),
            stats = PokemonStats(hp = 35, attack = 55, defense = 40, spAtk = 50, spDef = 50, speed = 90)
        ),
        Pokemon(
            id = 39, name = "Jigglypuff",
            types = listOf(PokemonType.NORMAL, PokemonType.FAIRY),
            description = "When its huge eyes waver, it sings a mysteriously soothing melody that lulls its enemies to sleep.",
            imageUrl = spriteUrl(39),
            stats = PokemonStats(hp = 115, attack = 45, defense = 20, spAtk = 45, spDef = 25, speed = 20)
        ),
        Pokemon(
            id = 94, name = "Gengar",
            types = listOf(PokemonType.GHOST, PokemonType.POISON),
            description = "Under a full moon, this Pokémon likes to mimic the shadows of people and laugh at their fright.",
            imageUrl = spriteUrl(94),
            stats = PokemonStats(hp = 60, attack = 65, defense = 60, spAtk = 130, spDef = 75, speed = 110)
        ),
        Pokemon(
            id = 133, name = "Eevee",
            types = listOf(PokemonType.NORMAL),
            description = "Its genetic code is irregular. It may mutate if it is exposed to radiation from element stones.",
            imageUrl = spriteUrl(133),
            stats = PokemonStats(hp = 55, attack = 55, defense = 50, spAtk = 45, spDef = 65, speed = 55)
        ),
        Pokemon(
            id = 143, name = "Snorlax",
            types = listOf(PokemonType.NORMAL),
            description = "Very lazy. Just eats and sleeps. As its rotund bulk builds, it becomes steadily more slothful.",
            imageUrl = spriteUrl(143),
            stats = PokemonStats(hp = 160, attack = 110, defense = 65, spAtk = 65, spDef = 110, speed = 30)
        ),
        Pokemon(
            id = 150, name = "Mewtwo",
            types = listOf(PokemonType.PSYCHIC),
            description = "It was created by a scientist after years of horrific gene-splicing and DNA-engineering experiments.",
            imageUrl = spriteUrl(150),
            stats = PokemonStats(hp = 106, attack = 110, defense = 90, spAtk = 154, spDef = 90, speed = 130)
        ),
        Pokemon(
            id = 149, name = "Dragonite",
            types = listOf(PokemonType.DRAGON, PokemonType.FLYING),
            description = "It is said to make its home somewhere in the sea. It guides crews of shipwrecks to shore.",
            imageUrl = spriteUrl(149),
            stats = PokemonStats(hp = 91, attack = 134, defense = 95, spAtk = 100, spDef = 100, speed = 80)
        ),
        Pokemon(
            id = 448, name = "Lucario",
            types = listOf(PokemonType.FIGHTING, PokemonType.STEEL),
            description = "It has the ability to sense the auras of all things. It understands human speech.",
            imageUrl = spriteUrl(448),
            stats = PokemonStats(hp = 70, attack = 110, defense = 70, spAtk = 115, spDef = 70, speed = 90)
        ),
        Pokemon(
            id = 282, name = "Gardevoir",
            types = listOf(PokemonType.PSYCHIC, PokemonType.FAIRY),
            description = "It has the power to predict the future. Its power peaks when it is protecting its Trainer.",
            imageUrl = spriteUrl(282),
            stats = PokemonStats(hp = 68, attack = 65, defense = 65, spAtk = 125, spDef = 115, speed = 80)
        ),
        Pokemon(
            id = 445, name = "Garchomp",
            types = listOf(PokemonType.DRAGON, PokemonType.GROUND),
            description = "When it folds up its body and extends its wings, it looks like a jet plane. It flies at sonic speed.",
            imageUrl = spriteUrl(445),
            stats = PokemonStats(hp = 108, attack = 130, defense = 95, spAtk = 80, spDef = 85, speed = 102)
        ),
        Pokemon(
            id = 384, name = "Rayquaza",
            types = listOf(PokemonType.DRAGON, PokemonType.FLYING),
            description = "It lives in the ozone layer far above the clouds and cannot be seen from the ground.",
            imageUrl = spriteUrl(384),
            stats = PokemonStats(hp = 105, attack = 150, defense = 90, spAtk = 150, spDef = 90, speed = 95)
        )
    )

    fun getPokemonById(id: Int): Pokemon? = getPokemonList().find { it.id == id }
}
