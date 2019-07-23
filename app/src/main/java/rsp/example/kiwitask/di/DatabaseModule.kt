package rsp.example.kiwitask.di

import androidx.room.Room
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import rsp.example.kiwitask.data.db.FlightsDatabase

/**
 * Created by Radek Špinka on 15.07.2019.
 */

val databaseModule = module {

    single {
        Room.databaseBuilder(androidContext(), FlightsDatabase::class.java, "flights-db")
            .build()
    }

    single { get<FlightsDatabase>().locationsDao() }

    single { get<FlightsDatabase>().flightsDao() }

    single { get<FlightsDatabase>().routeDao() }

}