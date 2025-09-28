package com.example.praktikumppb.model

data class AnimeResponse(
    val data: Anime
)

data class Anime(
    val mal_id: Int,
    val title: String,
    val type: String?,
    val episodes: Int?,
    val score: Double?,
    val images: Images,
    val synopsis: String? = null,
    val status: String? = null,
    val year: Int? = null,
    val genres: List<Genre>? = null,
    val studios: List<Studio>? = null,
    val duration: String? = null,
    val rating: String? = null
)

data class Images(
    val jpg: Jpg
)

data class Jpg(
    val image_url: String,
    val small_image_url: String? = null,
    val large_image_url: String? = null
)

data class Genre(
    val mal_id: Int,
    val name: String,
    val type: String
)

data class Studio(
    val mal_id: Int,
    val name: String,
    val type: String
)
