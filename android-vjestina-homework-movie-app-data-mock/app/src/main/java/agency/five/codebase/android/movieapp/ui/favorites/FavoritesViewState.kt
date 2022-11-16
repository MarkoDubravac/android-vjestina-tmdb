package agency.five.codebase.android.movieapp.ui.favorites

import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState

class FavoritesMovieViewState(
    val id: Int,
    val movieCardViewState: MovieCardViewState,
)

class FavoritesViewState(
    val favoriteMovies: List<FavoritesMovieViewState>
)
