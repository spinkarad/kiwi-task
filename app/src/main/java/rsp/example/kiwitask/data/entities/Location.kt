package rsp.example.kiwitask.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Radek Špinka on 15.07.2019.
 */

data class LocationsResponse(
    val locations: List<Location>
)

@Entity
data class Location(
    @PrimaryKey
    val id: String,
    val code: String,
    val name: String,
    val type: String,
    var offeredDate: String=""
)