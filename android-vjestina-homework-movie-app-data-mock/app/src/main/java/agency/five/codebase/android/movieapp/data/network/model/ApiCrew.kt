package agency.five.codebase.android.movieapp.data.network.model

import agency.five.codebase.android.movieapp.model.Crewman
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ApiCrew(
    @SerialName("id") val id: Int,
    @SerialName("job") val job: String,
    @SerialName("name") val name: String,
) {
    fun toCrew() = Crewman(
        id = id,
        name = name,
        job = job,
    )
}