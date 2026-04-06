package org.pokedex

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import org.pokedex.navigation.HomeRoute
import org.pokedex.navigation.PokedexListRoute
import org.pokedex.navigation.PokemonDetailRoute
import org.pokedex.navigation.TeamBuilderRoute
import org.pokedex.platform.getPlatform
import org.pokedex.ui.screen.HomeScreen
import org.pokedex.ui.screen.PokedexListScreen
import org.pokedex.ui.screen.PokemonDetailScreen
import org.pokedex.ui.screen.TeamBuilderScreen
import org.pokedex.ui.theme.PokedexTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    val platform = getPlatform()
    PokedexTheme {
        val navController = rememberNavController()
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination

        Scaffold(
            topBar = {
                val title = when {
                    currentDestination?.route?.contains("HomeRoute") == true -> "Pokédex"
                    currentDestination?.route?.contains("PokedexListRoute") == true -> "Pokémons"
                    currentDestination?.route?.contains("PokemonDetailRoute") == true -> "Detalhes"
                    currentDestination?.route?.contains("TeamBuilderRoute") == true -> "Meu Time"
                    else -> "Pokédex"
                }
                
                if (platform.isIos) {
                    // Estilo mais limpo para iOS (TopAppBar padrão do sistema)
                    TopAppBar(
                        title = { Text(title) }
                    )
                } else {
                    // Estilo Pokedex clássico para Android
                    CenterAlignedTopAppBar(
                        title = { Text(title) },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    )
                }
            },
            bottomBar = {
                NavigationBar(
                    containerColor = if (platform.isIos) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.surfaceVariant
                ) {
                    NavigationBarItem(
                        selected = currentDestination?.route?.contains("HomeRoute") == true,
                        onClick = { navController.navigate(HomeRoute) { popUpTo(HomeRoute) { inclusive = true } } },
                        icon = { Icon(Icons.Default.Home, contentDescription = null) },
                        label = { Text("Início") }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route?.contains("PokedexListRoute") == true,
                        onClick = { navController.navigate(PokedexListRoute) },
                        icon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null) },
                        label = { Text("Pokedex") }
                    )
                    NavigationBarItem(
                        selected = currentDestination?.route?.contains("TeamBuilderRoute") == true,
                        onClick = { navController.navigate(TeamBuilderRoute) },
                        icon = { Icon(Icons.Default.Person, contentDescription = null) },
                        label = { Text("Meu Time") }
                    )
                }
            }
        ) { paddingValues ->
            NavHost(
                navController = navController,
                startDestination = HomeRoute,
                modifier = Modifier.padding(paddingValues)
            ) {
                composable<HomeRoute> {
                    HomeScreen(
                        onExploreClick = { navController.navigate(PokedexListRoute) },
                        onTeamClick = { navController.navigate(TeamBuilderRoute) }
                    )
                }
                composable<PokedexListRoute> {
                    PokedexListScreen(
                        onPokemonClick = { id -> 
                            navController.navigate(PokemonDetailRoute(pokemonId = id)) 
                        }
                    )
                }
                composable<PokemonDetailRoute> { backStackEntry ->
                    val route: PokemonDetailRoute = backStackEntry.toRoute()
                    PokemonDetailScreen(
                        pokemonId = route.pokemonId,
                        onBackClick = { navController.popBackStack() }
                    )
                }
                composable<TeamBuilderRoute> {
                    TeamBuilderScreen(
                        onPokemonClick = { id ->
                            navController.navigate(PokemonDetailRoute(pokemonId = id))
                        }
                    )
                }
            }
        }
    }
}
