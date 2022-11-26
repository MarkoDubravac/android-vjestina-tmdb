package agency.five.codebase.android.movieapp.ui.favorites.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesMovieViewState
import agency.five.codebase.android.movieapp.ui.favorites.FavoritesViewState

class FavoritesMapperImpl : FavoritesMapper {
    override fun toFavoritesViewState(favoriteMovies: List<Movie>) =
        FavoritesViewState(favoriteMovies = favoriteMovies.map { toFavoritesMovieViewState(it) })

    private fun toFavoritesMovieViewState(favoriteMovie: Movie) = FavoritesMovieViewState(
        id = favoriteMovie.id,
        movieCardViewState = MovieCardViewState(
            imageUrl = favoriteMovie.imageUrl,
            isFavorite = favoriteMovie.isFavorite,
        ),
    )
}
