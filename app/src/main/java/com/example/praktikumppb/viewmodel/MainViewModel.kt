package com.example.praktikumppb.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.praktikumppb.model.Anime
import com.example.praktikumppb.model.Character
import com.example.praktikumppb.model.Genre
import com.example.praktikumppb.network.ApiClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class UiState<T>(
    val data: T? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)

class MainViewModel : ViewModel() {
    // Anime state
    private val _animeList = MutableStateFlow(UiState<List<Anime>>())
    val animeList: StateFlow<UiState<List<Anime>>> = _animeList.asStateFlow()

    private val _animeDetail = MutableStateFlow(UiState<Anime>())
    val animeDetail: StateFlow<UiState<Anime>> = _animeDetail.asStateFlow()

    // Character state
    private val _characterList = MutableStateFlow(UiState<List<Character>>())
    val characterList: StateFlow<UiState<List<Character>>> = _characterList.asStateFlow()

    private val _characterDetail = MutableStateFlow(UiState<Character>())
    val characterDetail: StateFlow<UiState<Character>> = _characterDetail.asStateFlow()

    // Genre state
    private val _genreList = MutableStateFlow(UiState<List<Genre>>())
    val genreList: StateFlow<UiState<List<Genre>>> = _genreList.asStateFlow()

    // Search and filter states
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedGenre = MutableStateFlow<Genre?>(null)
    val selectedGenre: StateFlow<Genre?> = _selectedGenre.asStateFlow()

    // Anime functions
    fun fetchTopAnime() {
        viewModelScope.launch {
            _animeList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getTopAnime()
                _animeList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _animeList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun fetchAnimeDetail(id: Int) {
        viewModelScope.launch {
            _animeDetail.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getAnimeById(id)
                _animeDetail.value = UiState(data = response.data)
            } catch (e: Exception) {
                _animeDetail.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun searchAnime(query: String) {
        _searchQuery.value = query
        if (query.isEmpty()) {
            fetchTopAnime()
            return
        }
        
        viewModelScope.launch {
            _animeList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.searchAnime(query)
                _animeList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _animeList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun filterAnimeByGenre(genre: Genre?) {
        _selectedGenre.value = genre
        if (genre == null) {
            fetchTopAnime()
            return
        }
        
        viewModelScope.launch {
            _animeList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getAnimeByGenre(genre.mal_id)
                _animeList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _animeList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun sortAnime(ascending: Boolean) {
        val currentData = _animeList.value.data ?: return
        val sortedData = if (ascending) {
            currentData.sortedBy { it.title }
        } else {
            currentData.sortedByDescending { it.title }
        }
        _animeList.value = _animeList.value.copy(data = sortedData)
    }

    // Character functions
    fun fetchTopCharacters() {
        viewModelScope.launch {
            _characterList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getTopCharacters()
                _characterList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _characterList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun fetchCharacterDetail(id: Int) {
        viewModelScope.launch {
            _characterDetail.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getCharacterById(id)
                _characterDetail.value = UiState(data = response.data)
            } catch (e: Exception) {
                _characterDetail.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun searchCharacters(query: String) {
        if (query.isEmpty()) {
            fetchTopCharacters()
            return
        }
        
        viewModelScope.launch {
            _characterList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.searchCharacters(query)
                _characterList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _characterList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun sortCharacters(ascending: Boolean) {
        val currentData = _characterList.value.data ?: return
        val sortedData = if (ascending) {
            currentData.sortedBy { it.name }
        } else {
            currentData.sortedByDescending { it.name }
        }
        _characterList.value = _characterList.value.copy(data = sortedData)
    }

    // Genre functions
    fun fetchGenres() {
        viewModelScope.launch {
            _genreList.value = UiState(isLoading = true)
            try {
                val response = ApiClient.service.getAnimeGenres()
                _genreList.value = UiState(data = response.data)
            } catch (e: Exception) {
                _genreList.value = UiState(error = e.message ?: "Unknown error")
            }
        }
    }

    fun clearAnimeDetail() {
        _animeDetail.value = UiState()
    }

    fun clearCharacterDetail() {
        _characterDetail.value = UiState()
    }
}