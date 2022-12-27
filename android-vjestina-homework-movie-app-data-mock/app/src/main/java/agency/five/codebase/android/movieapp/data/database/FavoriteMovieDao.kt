package agency.five.codebase.android.movieapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteMovieDao {
    @Query("SELECT * FROM favoriteMovieTB")
    fun favorites(): Flow<List<DbFavoriteMovie>>

    @Insert
    fun insertMovie(movie: DbFavoriteMovie)

    @Delete
    fun delete(movie: DbFavoriteMovie)
}
