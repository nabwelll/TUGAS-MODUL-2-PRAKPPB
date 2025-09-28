package com.example.praktikumppb.model

data class CharacterListResponse(
    val data: List<Character>
)

data class CharacterResponse(
    val data: Character
)

data class Character(
    val mal_id: Int,
    val name: String,
    val images: CharacterImages,
    val about: String? = null,
    val nicknames: List<String>? = null,
    val favorites: Int? = null
)

data class CharacterImages(
    val jpg: CharacterJpg,
    val webp: CharacterWebp? = null
)

data class CharacterJpg(
    val image_url: String,
    val small_image_url: String? = null
)

data class CharacterWebp(
    val image_url: String,
    val small_image_url: String? = null
)