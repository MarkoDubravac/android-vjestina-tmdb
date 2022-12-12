package agency.five.codebase.android.movieapp.mock

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object FavoritesDBMock {//look here

    val favoriteIds = MutableStateFlow(setOf<Int>());

    fun insert(movieId: Int){
        favoriteIds.value += (movieId)

    }
    fun delete(movieId: Int){
        favoriteIds.value -= (movieId)
    }

}
