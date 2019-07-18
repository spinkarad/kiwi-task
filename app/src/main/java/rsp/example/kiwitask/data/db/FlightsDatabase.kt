package rsp.example.kiwitask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import rsp.example.kiwitask.data.entities.Flight
import rsp.example.kiwitask.data.entities.Location

/**
 * Created by Radek Špinka on 15.07.2019.
 */

@Database(entities = [Location::class, Flight::class], version = 1)
abstract class FlightsDatabase : RoomDatabase() {
    abstract fun locationsDao(): LocationsDao
    abstract fun flightsDao(): FlightsDao
}