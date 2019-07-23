package rsp.example.kiwitask.utils

import androidx.room.RoomDatabase

/**
 * Created by Radek Špinka on 20.07.2019.
 */
fun RoomDatabase.runTransaction(block: () -> Unit) {
    ioThread {
        runInTransaction {
            block()
        }
    }
}