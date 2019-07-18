package rsp.example.kiwitask.utils

import java.util.concurrent.Executors

/**
 * Created by Radek Špinka on 16.07.2019.
 */

private val IO_EXECUTOR = Executors.newSingleThreadExecutor()

fun ioThread(f: () ->Unit) {
    IO_EXECUTOR.execute(f)
}