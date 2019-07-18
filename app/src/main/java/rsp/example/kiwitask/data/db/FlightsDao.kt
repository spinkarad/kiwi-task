package rsp.example.kiwitask.data.db

import androidx.room.*
import io.reactivex.Single
import rsp.example.kiwitask.data.entities.Flight

/**
 * Created by Radek Špinka on 16.07.2019.
 */
@Dao
abstract class FlightsDao {
    /**
     * Insert
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    protected abstract fun insertFlights(moods: List<Flight>)

    /**
     * Query
     */
    @Query("SELECT * FROM Flight")
    abstract fun getFlights(): Single<List<Flight>>

    /**
     * Delete
     */
    @Query("DELETE FROM Flight")
    protected abstract fun deleteFlights()


    /**
     * Delete all and insert
     */
    @Transaction
    open fun deleteAndInsert(flights: List<Flight>) {
        deleteFlights()
        insertFlights(flights)

    }


}