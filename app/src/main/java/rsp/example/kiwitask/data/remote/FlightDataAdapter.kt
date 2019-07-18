package rsp.example.kiwitask.data.remote

import com.squareup.moshi.*
import rsp.example.kiwitask.data.entities.Data
import rsp.example.kiwitask.data.entities.Flight

/**
 * Created by Radek Špinka on 16.07.2019.
 */
class FlightAdapter {
    private val moshi: Moshi = Moshi.Builder().build()
    private val dataAdapter: JsonAdapter<Data> = moshi.adapter(Data::class.java)

    @FromJson
    fun fromJson(reader: JsonReader): Flight {
        val id: String
        lateinit var data: Data
        var currency = ""

        reader.run {
            beginObject()
            while (hasNext()) {
                when (nextName()) {
                    "data" -> data = getData()
                    "currency" -> currency = nextString()
                    else -> skipValue()
                }
            }
            endObject()
        }
        id = data.id
        return Flight(id, data, currency)
    }

    private fun JsonReader.getData(): Data {
        val reader = peekJson()
        reader.beginArray()
        val data = dataAdapter.fromJson(reader)
        reader.close()
        skipValue()
        return data ?: throw JsonDataException("data must n")
    }

}