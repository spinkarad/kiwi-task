package rsp.example.kiwitask.data

import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.functions.BiFunction
import rsp.example.kiwitask.utils.FlightDate
import rsp.example.kiwitask.data.db.FlightsDao
import rsp.example.kiwitask.data.entities.Flight
import rsp.example.kiwitask.data.entities.ImgUrl
import rsp.example.kiwitask.data.entities.Location
import rsp.example.kiwitask.data.prefs.LocalPrefs
import rsp.example.kiwitask.data.remote.FlightsRemoteService
import rsp.example.kiwitask.utils.composeSchedulers
import rsp.example.kiwitask.utils.ioThread
import rsp.example.kiwitask.utils.switchIfEmptyList

/**
 * Created by Radek Špinka on 15.07.2019.
 */
class FlightsRepository(
    private val remoteSource: FlightsRemoteService,
    private val localSource: FlightsDao,
    private val locationsRepo: LocationsRepository,
    private val imageUrlRepo: ImageUrlRepository,
    private val prefs: LocalPrefs
) {

    companion object {
        const val DEFAULT_FLIGHTS_QUERY_WEEKS = 5
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

    private fun getFlightsFromApi(): Single<List<Flight>> {
        return locationsRepo.getRandomLocations().flatMap {
            Observable.fromIterable(it)
                .flatMapSingle { loc -> getFlightAndImageUrl(loc) }.toList()
        }.doOnSuccess { onFlightsSynced(it) }
    }

    private fun getFlightAndImageUrl(loc: Location): Single<Flight> {
        return getFlightNextSixWeeks(loc)
            .zipWith<ImgUrl, Flight>(
                imageUrlRepo.getLocationImgUrl(loc.name),
                BiFunction { flight, imgUrl -> flight.apply { locationImgUrl = imgUrl.url } })
            .composeSchedulers()
    }

    private fun getFlightNextSixWeeks(
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
        ioThread { localSource.deleteAndInsert(flights) }
        prefs.lastFlightsSyncDate = FlightDate.today
        locationsRepo.updateOfferedDate(flights.map { it.data.mapIdto })
    }
}