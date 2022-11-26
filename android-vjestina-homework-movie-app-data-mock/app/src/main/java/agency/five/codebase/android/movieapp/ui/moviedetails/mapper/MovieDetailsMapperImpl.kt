package agency.five.codebase.android.movieapp.ui.moviedetails.mapper

import agency.five.codebase.android.movieapp.model.Actor
import agency.five.codebase.android.movieapp.model.Crewman
import agency.five.codebase.android.movieapp.model.MovieDetails
import agency.five.codebase.android.movieapp.ui.component.ActorCardViewState
import agency.five.codebase.android.movieapp.ui.component.CrewItemViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.ActorViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.CrewmanViewState
import agency.five.codebase.android.movieapp.ui.moviedetails.MovieDetailsViewState

class MovieDetailsMapperImpl : MovieDetailsMapper {
    override fun toMovieDetailsViewState(movieDetails: MovieDetails) = MovieDetailsViewState(
        id = movieDetails.movie.id,
        imageUrl = movieDetails.movie.imageUrl.toString(),
        voteAverage = movieDetails.voteAverage,
        title = movieDetails.movie.title,
        overview = movieDetails.movie.overview,
        isFavorite = movieDetails.movie.isFavorite,
        crew = movieDetails.crew.map { toCrewmanViewState(it) },
        cast = movieDetails.cast.map { toActorViewState(it) },
    )

    private fun toActorViewState(actor: Actor) = ActorViewState(
        actorCardViewState = ActorCardViewState(
            imageUrl = actor.imageUrl.toString(),
            name = actor.name,
            character = actor.character,
        )
    )

    private fun toCrewmanViewState(crewman: Crewman) = CrewmanViewState(
        crewItemViewState = CrewItemViewState(
            crewman.name,
            crewman.job,
        )
    )
}
