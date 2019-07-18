package rsp.example.kiwitask.data.entities

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

/**
 * Created by Radek Špinka on 14.07.2019.
 */
@Entity
data class Flight(
    @PrimaryKey
    val id: String,
    @Embedded(prefix = "data_")
    val `data`: Data,
    val currency: String,
    var locationImgUrl: String? = null
)

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
    val deep_link: String,
    val distance: Double,
    val flyFrom: String,
    val flyTo: String,
    val fly_duration: String,
    val nightsInDest: Int?,
    val price: Int,
    val return_duration: String?
)

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