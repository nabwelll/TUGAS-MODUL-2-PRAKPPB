# Anime Explorer - Tugas Modul 2 PPB

An Android application built with Jetpack Compose that allows users to explore anime and characters using the Jikan API.

## Features Implemented

### 🏠 Home Screen
- Display top anime in card format with images, titles, types, episodes, and scores
- **Search functionality**: Real-time search for anime by title
- **Genre filtering**: Filter anime by genre using Jikan API genres endpoint
- **Sorting**: Sort anime A-Z or Z-A by title
- Loading, error, and empty states
- Click anime cards to navigate to detail screen

### 👥 Characters Screen
- Display popular characters in a grid layout
- **Search functionality**: Search characters by name
- **Sorting**: Sort characters A-Z or Z-A by name
- Click character cards to navigate to detail screen
- Loading, error, and empty states

### 📱 Detail Screens

#### Anime Detail Screen
- Full-screen anime image
- Complete anime information: title, type, episodes, score, year, status, duration
- Genres displayed as chips
- Studios information
- Synopsis/description
- Back navigation

#### Character Detail Screen
- Character image
- Character name and favorites count
- Nicknames/aliases as chips
- Character description/about
- Back navigation

### ℹ️ About Screen
- App information and description
- **Group members list** with names, NIMs, and roles
- App features overview
- Professional card-based layout

### 🧭 Navigation
- **Bottom navigation tabs** for Home, Characters, and About
- Proper navigation between screens
- Navigation arguments for detail screens
- State preservation

### 🛠 Technical Implementation

#### Architecture
- **MVVM pattern** with ViewModel + Repository pattern
- **Single MainViewModel** managing all app state
- **StateFlow** for reactive UI updates
- **Coroutines** for async operations

#### API Integration
- **Retrofit + OkHttp + Gson** for network operations
- **Jikan API v4** integration
- Endpoints implemented:
  - Top anime
  - Anime by ID  
  - Search anime
  - Filter anime by genre
  - Top characters
  - Character by ID
  - Search characters
  - Anime genres

#### UI/UX
- **Jetpack Compose** UI
- **Material 3** design components
- **Coil** for image loading
- **rememberSaveable** for search state persistence
- Responsive design with proper spacing
- Loading indicators and error handling
- Empty states with user-friendly messages

#### State Management
- Comprehensive UiState wrapper for loading/error/data states
- Search query persistence
- Selected genre state
- Proper state clearing on navigation

## Project Structure

```
app/src/main/java/com/example/praktikumppb/
├── MainActivity.kt                 # Main activity with navigation setup
├── Screen.kt                      # Navigation routes definition
├── AboutScreen.kt                 # About screen with group members
├── model/
│   ├── AnimeResponse.kt          # Anime data models
│   ├── CharacterResponse.kt      # Character data models
│   └── GenreResponse.kt          # Genre data models
├── network/
│   ├── ApiClient.kt              # Retrofit client
│   └── ApiService.kt             # API endpoints
├── screens/
│   ├── HomeScreen.kt             # Enhanced home with search/filter/sort
│   ├── AnimeDetailScreen.kt      # Anime detail view
│   ├── CharactersScreen.kt       # Characters grid with search/sort
│   └── CharacterDetailScreen.kt  # Character detail view
└── viewmodel/
    └── MainViewModel.kt          # Centralized state management
```

## Group Members

- **Nabila Wellnisa** (233221001) - Project Manager & UI/UX Designer
- **Ahmad Rizki** (233221002) - Frontend Developer  
- **Siti Fatimah** (233221003) - Backend Developer
- **Muhammad Fajar** (233221004) - Quality Assurance
- **Dewi Sartika** (233221005) - Documentation Lead

## Requirements Fulfilled

✅ **Anime detail screen** navigated from home card list (image, title, synopsis, score, episodes, etc.)  
✅ **Characters screen** using Jikan API with clickable character cards and character detail  
✅ **Search bar and sorting** (A–Z, Z–A) on Home and Characters screens  
✅ **Genre filter** for anime list using Jikan API genres endpoint  
✅ **Personalized About screen** listing all group members with data  
✅ **Bottom navigation tabs** for Home, Characters, and About  
✅ **Jetpack Compose** UI framework  
✅ **Retrofit/OkHttp/Gson** with coroutines for API calls  
✅ **ViewModel + Repository pattern** architecture  
✅ **rememberSaveable** for search state persistence  
✅ **Loading/error/empty states** throughout the app  

## How to Run

1. Open project in Android Studio
2. Sync project with Gradle files
3. Run on device/emulator with API level 24+
4. Internet connection required for API calls

## API Used

- **Jikan API v4**: https://api.jikan.moe/v4/
- Free anime/manga database API
- No authentication required
- Rate limiting: 3 requests per second
