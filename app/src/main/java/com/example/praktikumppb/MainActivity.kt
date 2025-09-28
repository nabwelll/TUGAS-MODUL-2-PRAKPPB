package com.example.praktikumppb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.praktikumppb.screens.AnimeDetailScreen
import com.example.praktikumppb.screens.CharacterDetailScreen
import com.example.praktikumppb.screens.CharactersScreen
import com.example.praktikumppb.screens.HomeScreen
import com.example.praktikumppb.ui.theme.PraktikumPPBTheme
import com.example.praktikumppb.viewmodel.MainViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PraktikumPPBTheme {
                AnimeApp()
            }
        }
    }
}

@Composable
fun AnimeApp() {
    val navController = rememberNavController()
    val mainViewModel: MainViewModel = viewModel()
    
    // Define bottom navigation items (only the main screens)
    val bottomNavItems = listOf(Screen.Home, Screen.Characters, Screen.About)

    Scaffold(
        bottomBar = {
            NavigationBar {
                val currentBackStack by navController.currentBackStackEntryAsState()
                val currentRoute = currentBackStack?.destination?.route

                bottomNavItems.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            when (screen) {
                                Screen.Home -> Icon(Icons.Default.Home, contentDescription = "Home")
                                Screen.Characters -> Icon(Icons.Default.Person, contentDescription = "Characters") 
                                Screen.About -> Icon(Icons.Default.Info, contentDescription = "About")
                                else -> Icon(Icons.Default.Home, contentDescription = screen.title)
                            }
                        },
                        label = { Text(screen.title) },
                        selected = currentRoute == screen.route,
                        onClick = {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Home Screen
            composable(Screen.Home.route) {
                HomeScreen(
                    onAnimeClick = { animeId ->
                        navController.navigate(Screen.AnimeDetail.createRoute(animeId))
                    },
                    viewModel = mainViewModel
                )
            }
            
            // Characters Screen
            composable(Screen.Characters.route) {
                CharactersScreen(
                    onCharacterClick = { characterId ->
                        navController.navigate(Screen.CharacterDetail.createRoute(characterId))
                    },
                    viewModel = mainViewModel
                )
            }
            
            // About Screen
            composable(Screen.About.route) {
                AboutScreen()
            }
            
            // Anime Detail Screen
            composable(
                route = Screen.AnimeDetail.route,
                arguments = listOf(navArgument("animeId") { type = NavType.IntType })
            ) { backStackEntry ->
                val animeId = backStackEntry.arguments?.getInt("animeId") ?: 0
                AnimeDetailScreen(
                    animeId = animeId,
                    onBackClick = { navController.popBackStack() },
                    viewModel = mainViewModel
                )
            }
            
            // Character Detail Screen
            composable(
                route = Screen.CharacterDetail.route,
                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
            ) { backStackEntry ->
                val characterId = backStackEntry.arguments?.getInt("characterId") ?: 0
                CharacterDetailScreen(
                    characterId = characterId,
                    onBackClick = { navController.popBackStack() },
                    viewModel = mainViewModel
                )
            }
        }
    }
}