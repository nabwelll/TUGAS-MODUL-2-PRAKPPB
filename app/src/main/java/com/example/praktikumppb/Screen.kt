package com.example.praktikumppb

sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Home")
    object Characters : Screen("characters", "Characters")
    object About : Screen("about", "About")
    object AnimeDetail : Screen("anime_detail/{animeId}", "Anime Detail") {
        fun createRoute(animeId: Int) = "anime_detail/$animeId"
    }
    object CharacterDetail : Screen("character_detail/{characterId}", "Character Detail") {
        fun createRoute(characterId: Int) = "character_detail/$characterId"
    }
}

