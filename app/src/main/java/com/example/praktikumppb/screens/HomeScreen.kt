package com.example.praktikumppb.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Sort
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.praktikumppb.model.Anime
import com.example.praktikumppb.model.Genre
import com.example.praktikumppb.viewmodel.MainViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onAnimeClick: (Int) -> Unit,
    viewModel: MainViewModel = viewModel()
) {
    val animeList by viewModel.animeList.collectAsState()
    val genreList by viewModel.genreList.collectAsState()
    val selectedGenre by viewModel.selectedGenre.collectAsState()
    
    var searchQuery by rememberSaveable { mutableStateOf("") }
    var showSortMenu by remember { mutableStateOf(false) }
    var showGenreFilter by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        viewModel.fetchTopAnime()
        viewModel.fetchGenres()
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // Search Bar
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { 
                searchQuery = it
                viewModel.searchAnime(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            placeholder = { Text("Search anime...") },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search")
            },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = {
                        searchQuery = ""
                        viewModel.searchAnime("")
                    }) {
                        Icon(Icons.Default.Clear, contentDescription = "Clear")
                    }
                }
            }
        )

        // Filter and Sort Row
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Genre Filter
            Box {
                FilterChip(
                    onClick = { showGenreFilter = !showGenreFilter },
                    label = { 
                        Text(
                            if (selectedGenre != null) selectedGenre.name else "Genre"
                        ) 
                    },
                    selected = selectedGenre != null,
                    modifier = Modifier.weight(1f)
                )
                
                DropdownMenu(
                    expanded = showGenreFilter,
                    onDismissRequest = { showGenreFilter = false }
                ) {
                    // Clear filter option
                    DropdownMenuItem(
                        text = { Text("All Genres") },
                        onClick = {
                            viewModel.filterAnimeByGenre(null)
                            showGenreFilter = false
                        }
                    )
                    
                    genreList.data?.forEach { genre ->
                        DropdownMenuItem(
                            text = { Text(genre.name) },
                            onClick = {
                                viewModel.filterAnimeByGenre(genre)
                                showGenreFilter = false
                            }
                        )
                    }
                }
            }

            // Sort Menu
            Box {
                FilterChip(
                    onClick = { showSortMenu = !showSortMenu },
                    label = { Text("Sort") },
                    selected = false,
                    leadingIcon = {
                        Icon(Icons.Default.Sort, contentDescription = null)
                    }
                )
                
                DropdownMenu(
                    expanded = showSortMenu,
                    onDismissRequest = { showSortMenu = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("A-Z") },
                        onClick = {
                            viewModel.sortAnime(ascending = true)
                            showSortMenu = false
                        }
                    )
                    DropdownMenuItem(
                        text = { Text("Z-A") },
                        onClick = {
                            viewModel.sortAnime(ascending = false)
                            showSortMenu = false
                        }
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Content
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            when {
                animeList.isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                animeList.error != null -> {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.errorContainer
                        )
                    ) {
                        Text(
                            text = "Error: ${animeList.error}",
                            modifier = Modifier.padding(16.dp),
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            textAlign = TextAlign.Center
                        )
                    }
                }
                animeList.data.isNullOrEmpty() -> {
                    Card(
                        modifier = Modifier
                            .align(Alignment.Center)
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant
                        )
                    ) {
                        Text(
                            text = "No anime found",
                            modifier = Modifier.padding(16.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
                else -> {
                    LazyColumn(
                        contentPadding = PaddingValues(horizontal = 16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        items(animeList.data) { anime ->
                            AnimeCard(
                                anime = anime,
                                onClick = { onAnimeClick(anime.mal_id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun AnimeCard(
    anime: Anime,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier.padding(12.dp)
        ) {
            // Anime Image
            Card(
                modifier = Modifier.size(80.dp),
                elevation = CardDefaults.cardElevation(2.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(anime.images.jpg.image_url),
                    contentDescription = anime.title,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )
            }
            
            Spacer(modifier = Modifier.width(12.dp))
            
            // Anime Info
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = anime.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        text = "Type: ${anime.type ?: "Unknown"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Episodes: ${anime.episodes ?: "Unknown"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                
                Spacer(modifier = Modifier.height(4.dp))
                
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "Score: ${anime.score ?: "N/A"}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Medium
                    )
                    
                    if (anime.year != null) {
                        Text(
                            text = "${anime.year}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}