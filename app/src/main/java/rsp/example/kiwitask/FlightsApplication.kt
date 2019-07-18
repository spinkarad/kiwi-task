package rsp.example.kiwitask

import android.app.Application
import com.facebook.stetho.Stetho
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import rsp.example.kiwitask.di.appModule
import rsp.example.kiwitask.di.databaseModule
import rsp.example.kiwitask.di.remoteModule

/**
 * Created by Radek Špinka on 15.07.2019.
 */

class FlightsApplication : Application() {

    override fun onCreate() {
        super.onCreate()
Stetho.initializeWithDefaults(this)
        startKoin {
            androidContext(this@FlightsApplication)
            modules(listOf(remoteModule, appModule, databaseModule))
        }
    }
}