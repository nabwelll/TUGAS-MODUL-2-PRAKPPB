package com.example.praktikumppb.network

import com.example.praktikumppb.model.AnimeListResponse
import com.example.praktikumppb.model.AnimeResponse
import com.example.praktikumppb.model.CharacterListResponse
import com.example.praktikumppb.model.CharacterResponse
import com.example.praktikumppb.model.GenreListResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    // Anime endpoints
    @GET("top/anime")
    suspend fun getTopAnime(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): AnimeListResponse

    @GET("anime/{id}")
    suspend fun getAnimeById(@Path("id") id: Int): AnimeResponse

    @GET("anime")
    suspend fun searchAnime(@Query("q") query: String): AnimeListResponse

    @GET("anime")
    suspend fun getAnimeByGenre(@Query("genres") genreId: Int): AnimeListResponse

    // Character endpoints
    @GET("top/characters")
    suspend fun getTopCharacters(
        @Query("page") page: Int = 1,
        @Query("limit") limit: Int = 25
    ): CharacterListResponse

    @GET("characters/{id}")
    suspend fun getCharacterById(@Path("id") id: Int): CharacterResponse

    @GET("characters")
    suspend fun searchCharacters(@Query("q") query: String): CharacterListResponse

    // Genre endpoints
    @GET("genres/anime")
    suspend fun getAnimeGenres(): GenreListResponse
}
