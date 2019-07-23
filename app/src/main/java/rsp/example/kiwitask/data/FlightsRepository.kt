package rsp.example.kiwitask.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import rsp.example.kiwitask.data.db.FlightsDao
import rsp.example.kiwitask.data.db.FlightsDatabase
import rsp.example.kiwitask.data.db.RouteDao
import rsp.example.kiwitask.data.entities.Flight
import rsp.example.kiwitask.data.entities.FlightWithAllLegs
import rsp.example.kiwitask.data.entities.ImgUrl
import rsp.example.kiwitask.data.entities.Location
import rsp.example.kiwitask.data.prefs.LocalPrefs
import rsp.example.kiwitask.data.remote.FlightsRemoteService
import rsp.example.kiwitask.utils.*

/**
 * Created by Radek Špinka on 15.07.2019.
 */
class FlightsRepository(
    private val remoteSource: FlightsRemoteService,
    private val localSource: FlightsDao,
    private val localRouteSource: RouteDao,
    private val locationsRepo: LocationsRepository,
    private val imageUrlRepo: ImageUrlRepository,
    private val prefs: LocalPrefs,
    private val db: FlightsDatabase
) {

    companion object {
        const val DEFAULT_FLIGHTS_QUERY_WEEKS = 6
    }

    /**
     * gets flights either from db if presented and not synced today or from api
     */
    fun getFlights(): Single<List<Flight>> {
        return if (prefs.lastFlightsSyncDate.isToday) {
            localSource.getFlights().switchIfEmptyList(getFlightsFromApi())
        } else {
            getFlightsFromApi()
        }.composeSchedulers()
    }

    fun getFlightWithAllConnections(id: String): Single<FlightWithAllLegs> {
        return localSource.getFlightWithAllConnections(id)
            .composeSchedulers()
    }


    private fun getFlightsFromApi(): Single<List<Flight>> {
        return locationsRepo.getRandomLocations().flatMap {
            Observable.fromIterable(it)
                .flatMapSingle { loc -> getFlightAndImageUrl(loc) }.toList()
        }.doOnSuccess { onFlightsSynced(it) }
    }

    private fun getFlightAndImageUrl(loc: Location): Single<Flight> {
        return getFlight(loc)
            .zipWith<ImgUrl, Flight>(
                imageUrlRepo.getLocationImgUrl(loc.name),
                BiFunction { flight, imgUrl -> flight.apply { locationImgUrl = imgUrl.url } })
            .composeSchedulers()
    }

    private fun getFlight(
        loc: Location,
        weeks: Int = DEFAULT_FLIGHTS_QUERY_WEEKS
    ): Single<Flight> {
        return remoteSource.getFlight(
            flyTo = loc.code,
            dateFrom = FlightDate.tomorrow.toString(),
            dateTo = FlightDate.weeks(weeks).toString()
        )
    }

    private fun onFlightsSynced(flights: List<Flight>) {
        val routes = flights.map { it.data.route }.flatten()
        db.runTransaction {
            localSource.deleteAndInsert(flights)
            localRouteSource.insertRoute(routes)
            prefs.lastFlightsSyncDate = FlightDate.today
            locationsRepo.updateOfferedDate(flights.map { it.data.mapIdto })
        }

    }
}