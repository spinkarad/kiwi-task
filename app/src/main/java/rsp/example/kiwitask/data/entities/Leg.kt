package rsp.example.kiwitask.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Radek Špinka on 19.07.2019.
 */

@Entity
data class Leg(
    @PrimaryKey
    val id: String,
    var flightId: String,
    @field:Json(name = "return")
    val isReturn: Int,
    val aTime: Long,
    val aTimeUTC: Int,
    val airline: String,
    val bags_recheck_required: Boolean,
    val cityFrom: String,
    val cityTo: String,
    val combination_id: String,
    val dTime: Long,
    val dTimeUTC: Int,
    val fare_basis: String,
    val fare_category: String,
    val fare_classes: String,
    val fare_family: String,
    val flight_no: Int,
    val flyFrom: String,
    val flyTo: String,
    val found_on: String,
    val guarantee: Boolean,
    val last_seen: Int,
    val latFrom: Double,
    val latTo: Double,
    val lngFrom: Double,
    val lngTo: Double,
    val mapIdfrom: String,
    val mapIdto: String,
    val operating_carrier: String,
    val operating_flight_no: String,
    val original_return: Int,
    val price: Int,
    val refresh_timestamp: Int,
    val source: String,
    val vehicle_type: String
){

    val from: String
        get() = "$cityFrom, $flyFrom"

    val to: String
        get() = "$cityTo, $flyTo"

}

