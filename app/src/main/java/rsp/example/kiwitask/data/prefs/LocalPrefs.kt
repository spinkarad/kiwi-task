package rsp.example.kiwitask.data.prefs

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import rsp.example.kiwitask.utils.FlightDate

/**
 * Created by Radek Špinka on 16.07.2019.
 */
class LocalPrefs(context: Context) {

    private val prefs: SharedPreferences by lazy {
        PreferenceManager.getDefaultSharedPreferences(context)
    }

    companion object {
        const val LAST_FLIGHTS_SYNC_DATE = "last_flights_sync_date"
    }

    var lastFlightsSyncDate: FlightDate
        get() = FlightDate(
            prefs.getString(LAST_FLIGHTS_SYNC_DATE, "") ?: ""
        )
        set(value) = prefs.edit().putString(LAST_FLIGHTS_SYNC_DATE, value.toString()).apply()
}