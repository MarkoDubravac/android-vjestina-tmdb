package agency.five.codebase.android.movieapp.ui.home.mapper

import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.ui.component.MovieCardViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelTextViewState
import agency.five.codebase.android.movieapp.ui.component.MovieCategoryLabelViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieCategoryViewState
import agency.five.codebase.android.movieapp.ui.home.HomeMovieViewState

class HomeScreenMapperImpl : HomeScreenMapper {
    override fun toHomeMovieCategoryViewState(
        movieCategories: List<MovieCategory>,
        selectedMovieCategory: MovieCategory,
        movies: List<Movie>,
    ) = HomeMovieCategoryViewState(
        movieCategories = movieCategories.map {
            toMovieCategoryLabelViewState(
                movieCategory = it,
                selectedMovieCategory,
            )
        },
        movies = movies.map { toHomeMovieViewState(it) },
    )

    private fun toHomeMovieViewState(movie: Movie) = HomeMovieViewState(
        id = movie.id,
        movieCardViewState = MovieCardViewState(
            movieId = movie.id,
            imageUrl = movie.imageUrl,
            isFavorite = movie.isFavorite,
        ),
    )

    private fun toMovieCategoryLabelViewState(
        movieCategory: MovieCategory,
        selectedMovieCategory: MovieCategory,
    ) = MovieCategoryLabelViewState(
        id = movieCategory.ordinal,
        isSelected = movieCategory == selectedMovieCategory,
        categoryText = MovieCategoryLabelTextViewState.InputAsResourceState(movieCategory.textRes),
    )
}
