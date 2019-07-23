package rsp.example.kiwitask.data.entities

import androidx.room.*
import com.squareup.moshi.Json

/**
 * Created by Radek Špinka on 14.07.2019.
 */
@Entity
class Flight(
    @PrimaryKey
    val id: String,
    @Embedded(prefix = "data_")
    val data: Data,
    val currency: String,
    var locationImgUrl: String? = null
) {

    val locationName: String
        get() = "${data.cityTo}, ${data.countryTo.name}"

    val from: String
        get() = "${data.cityFrom}, ${data.flyFrom}"

    val to: String
        get() = "${data.cityTo}, ${data.flyTo}"
}


data class Data(
    val id: String,
    val aTime: Long,
    val aTimeUTC: Long,
    val booking_token: String,
    val cityFrom: String,
    val cityTo: String,
    @Embedded(prefix = "conversion_")
    val conversion: Conversion,
    @Embedded(prefix = "country_from_")
    val countryFrom: CountryFrom,
    @Embedded(prefix = "country_to_")
    val countryTo: CountryTo,
    val dTime: Long,
    val mapIdto: String,
    val dTimeUTC: Long,
    @field:Json(name = "deep_link")
    val deepLink: String,
    val distance: Double,
    val flyFrom: String,
    val flyTo: String,
    @field:Json(name = "fly_duration")
    val flyDuration: String,
    val nightsInDest: Int?,
    val price: Int,
    @field:Json(name = "return_duration")
    val returnDuration: String?
){
    @Ignore
    var route: List<Leg> = emptyList()
}

class FlightWithAllLegs {
    @Embedded
    var flight: Flight?=null

    @Relation(parentColumn = "id", entityColumn = "flightId")
    var route: List<Leg> = emptyList()

    val returnTimestamp: Long
    get()=route.first { it.isReturn==1 }.dTime

    val returnFrom: String
        get()=route.first { it.isReturn==1 }.let {
          "${it.cityFrom}, ${it.flyFrom}"
        }

    val returnTo: String
        get()=route.last { it.isReturn==1 }.let {
            "${it.cityTo}, ${it.flyTo}"
        }

    val firstForwardId:String
        get()=route.first().id

    val firstBackId:String
        get()=route.first{it.isReturn==1}.id
}

data class Conversion(
    val EUR: Int
)

data class CountryFrom(
    val code: String,
    val name: String
)

data class CountryTo(
    val code: String,
    val name: String
)