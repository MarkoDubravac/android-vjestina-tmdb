package agency.five.codebase.android.movieapp.data.repository

import agency.five.codebase.android.movieapp.data.database.DbFavoriteMovie
import agency.five.codebase.android.movieapp.data.database.FavoriteMovieDao
import agency.five.codebase.android.movieapp.data.network.BASE_IMAGE_URL
import agency.five.codebase.android.movieapp.data.network.MovieService
import agency.five.codebase.android.movieapp.model.Movie
import agency.five.codebase.android.movieapp.model.MovieCategory
import agency.five.codebase.android.movieapp.model.MovieDetails
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

internal class MovieRepositoryImpl(
    private val movieService: MovieService,
    private val movieDao: FavoriteMovieDao,
    private val bgDispatcher: CoroutineDispatcher,
) : MovieRepository {

    @OptIn(ExperimentalCoroutinesApi::class)
    private val moviesByCategory: Map<MovieCategory, Flow<List<Movie>>> =
        MovieCategory.values().associateWith { movieCategory ->
            flow {
                val movieResponse = when (movieCategory) {
                    MovieCategory.PLAYING_MOVIES -> movieService.fetchNowPlayingMovies()
                    MovieCategory.PLAYING_TV -> movieService.fetchTopRatedMovies()
                    MovieCategory.POPULAR_STREAMING -> movieService.fetchPopularMovies()
                    MovieCategory.POPULAR_ON_TV -> movieService.fetchTopRatedMovies()
                    MovieCategory.POPULAR_FOR_RENT -> movieService.fetchNowPlayingMovies()
                    MovieCategory.POPULAR_IN_THEATERS -> movieService.fetchUpcomingMovies()
                    MovieCategory.UPCOMING_TODAY -> movieService.fetchUpcomingMovies()
                    MovieCategory.UPCOMING_THIS_WEEK -> movieService.fetchPopularMovies()
                }
                emit(movieResponse.movies)
            }.flatMapLatest { apiMovies ->
                movieDao.favorites().map { favoriteMovies ->
                    apiMovies.map { apiMovie ->
                        apiMovie.toMovie(isFavorite = favoriteMovies.any { it.id == apiMovie.id })
                    }
                }
            }.shareIn(
                scope = CoroutineScope(bgDispatcher),
                started = SharingStarted.WhileSubscribed(1000L),
                replay = 1,
            )
        }

    private val favorites = movieDao.favorites().map {
        it.map { dbFavoriteMovie ->
            Movie(
                id = dbFavoriteMovie.id,
                imageUrl = dbFavoriteMovie.posterUrl,
                title = "",
                overview = "",
                isFavorite = true,
            )
        }
    }.shareIn(
        scope = CoroutineScope(bgDispatcher),
        started = SharingStarted.WhileSubscribed(1000L),
        replay = 1,
    )

    override fun movies(movieCategory: MovieCategory): Flow<List<Movie>> =
        moviesByCategory[movieCategory]!!

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun movieDetails(movieId: Int): Flow<MovieDetails> = flow {
        emit(movieService.fetchMovieDetails(movieId) to movieService.fetchMovieCredits(movieId))
    }.flatMapLatest { (apiMovieDetails, apiMovieCredits) ->
        movieDao.favorites().map { favoriteMovies ->
            apiMovieDetails.toMovieDetails(
                isFavorite = favoriteMovies.any { it.id == apiMovieDetails.id },
                crew = apiMovieCredits.crew,
                cast = apiMovieCredits.cast
            )
        }
    }.flowOn(bgDispatcher)

    override fun favoriteMovies(): Flow<List<Movie>> = favorites

    override suspend fun addMovieToFavorites(movieId: Int) {
        runBlocking(bgDispatcher) {
            movieDao.insertMovie(
                DbFavoriteMovie(
                    id = movieId, posterUrl = "$BASE_IMAGE_URL/${findMovie(movieId)?.imageUrl}"
                )
            )
        }
    }

    private suspend fun findMovie(movieId: Int): Movie? {
        moviesByCategory.values.forEach { value ->
            val movies = value.first()
            movies.forEach {
                if (it.id == movieId) {
                    return it
                }
            }
        }
        return null
    }

    override suspend fun removeMovieFromFavorites(movieId: Int) {
        runBlocking(bgDispatcher) {
            movieDao.delete(movieId)
        }
    }

    override suspend fun toggleFavorite(movieId: Int) {
        runBlocking(bgDispatcher) {
            if (favorites.first().map(Movie::id).contains(movieId)) {
                removeMovieFromFavorites(movieId)
            } else {
                addMovieToFavorites(movieId)
            }
        }
    }
}
