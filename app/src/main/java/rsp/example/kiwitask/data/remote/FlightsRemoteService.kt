package rsp.example.kiwitask.data.remote

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query
import rsp.example.kiwitask.data.entities.Flight
import rsp.example.kiwitask.data.entities.LocationsResponse

/**
 * Created by Radek Špinka on 14.07.2019.
 */

interface FlightsRemoteService {

    @GET("flights?fly_from=CZ&partner=picky&limit=1&curr=CZK")
    fun getFlight(
        @Query("fly_to") flyTo: String,
        @Query("date_from") dateFrom: String,
        @Query("date_to") dateTo: String
    ): Single<Flight>

    @GET("locations?type=top_destinations&term=PRG&locale=en-US&location_types=airport&active_only=true&source_popularity=searches&fallback_popularity=bookings")
    fun getLocations(
        @Query("limit") limit: String = "50"
    ): Single<LocationsResponse>

}