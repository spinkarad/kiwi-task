package rsp.example.kiwitask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Observable
import io.reactivex.Single
import rsp.example.kiwitask.data.entities.Location
import rsp.example.kiwitask.utils.FlightDate

/**
 * Created by Radek Špinka on 15.07.2019.
 */
@Dao
interface LocationsDao {
    /**
     * Insert
     */

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocations(moods: List<Location>)


    /**
     * Query
     */

    /**
     * get locations by [excludedDate] which has default value yesterday to avoid
     * repeating locations in two following days
     */
    @Query("SELECT * FROM Location WHERE offeredDate != :excludedDate")
    fun getLocations(excludedDate: String = FlightDate.yesterday.toString()): Single<List<Location>>


    /**
     * Update
     */

    @Query("UPDATE Location SET offeredDate = :date WHERE id IN (:ids)")
    fun updateSelectedDate(ids: List<String>, date: String = FlightDate.today.toString())


}