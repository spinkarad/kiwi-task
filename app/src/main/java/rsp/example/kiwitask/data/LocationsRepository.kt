package rsp.example.kiwitask.data

import io.reactivex.Single
import rsp.example.kiwitask.data.db.LocationsDao
import rsp.example.kiwitask.data.entities.Location
import rsp.example.kiwitask.data.remote.FlightsRemoteService
import rsp.example.kiwitask.utils.FlightDate
import rsp.example.kiwitask.utils.composeSchedulers
import rsp.example.kiwitask.utils.ioThread
import rsp.example.kiwitask.utils.switchIfEmptyList

/**
 * Created by Radek Špinka on 14.07.2019.
 */

class LocationsRepository(
    private val remoteSource: FlightsRemoteService,
    private val localSource: LocationsDao
) {

    companion object {
        const val DEFAULT_LOCATIONS_COUNT = 5
    }

    /**
     * get random locations from db
     * when locations aren't persisted switch to api call
     */
    fun getRandomLocations(count: Int = DEFAULT_LOCATIONS_COUNT): Single<List<Location>> {
        return localSource.getLocations()
            .doOnSuccess { println(it) }
            .switchIfEmptyList(getLocationsFromApi())
            .map { it.shuffled().take(count) }
    }

    fun updateOfferedDate(ids: List<String>) {
        ioThread { localSource.updateSelectedDate(ids) }
    }

    /**
     * get locations from api
     */
    private fun getLocationsFromApi(): Single<List<Location>> {
        return remoteSource.getLocations().map { it.locations }
            .doOnSuccess(localSource::insertLocations)
    }
}