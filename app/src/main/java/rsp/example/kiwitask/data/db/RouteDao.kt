package rsp.example.kiwitask.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import rsp.example.kiwitask.data.entities.Leg

/**
 * Created by Radek Špinka on 20.07.2019.
 */
@Dao
abstract class RouteDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertRoute(moods: List<Leg>)
}