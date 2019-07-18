package rsp.example.kiwitask.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Radek Špinka on 15.07.2019.
 */
class FlightDate {
    private val value: String

    constructor(date: String) {
        value = date
    }

    private constructor(daysFromNow: Int) {
        value = getFromToday(daysFromNow)
    }

    private val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
    private val calendar = Calendar.getInstance()

    override fun toString(): String {
        return value
    }

    companion object {
        val today: FlightDate
            get() = FlightDate(0)

        val tomorrow: FlightDate
            get() = FlightDate(0)

        val yesterday: FlightDate
            get() = FlightDate(-1)

        fun weeks(count: Int): FlightDate =
            FlightDate(count * 7)
    }

    private fun getFromToday(days: Int): String {
        return calendar.run {
            add(Calendar.DAY_OF_YEAR, days)
            dateFormat.format(time)
        }
    }

    val isToday: Boolean
        get() = value == today.value

}