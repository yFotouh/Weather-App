package com.task.parenttechnicaltask.model.repository

import android.util.Log
import kotlinx.coroutines.*

open class BaseRepository {
    protected val parentJob = Job()
    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch(Dispatchers.Main) {
                Log.e("error", "error");
            }

            GlobalScope.launch {
                println("Caught $throwable")
            }
        }

    protected val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob +
                coroutineExceptionHandler
    )
}