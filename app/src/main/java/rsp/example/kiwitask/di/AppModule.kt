package rsp.example.kiwitask.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import rsp.example.kiwitask.data.FlightsRepository
import rsp.example.kiwitask.data.ImageUrlRepository
import rsp.example.kiwitask.data.LocationsRepository
import rsp.example.kiwitask.data.prefs.LocalPrefs
import rsp.example.kiwitask.ui.base.flights.FlightsViewModel

/**
 * Created by Radek Špinka on 15.07.2019.
 */

val appModule = module {

    single { LocationsRepository(get(), get()) }
    single { FlightsRepository(get(), get(), get(), get(), get()) }
    single { ImageUrlRepository(get()) }

    single { LocalPrefs(get()) }
}