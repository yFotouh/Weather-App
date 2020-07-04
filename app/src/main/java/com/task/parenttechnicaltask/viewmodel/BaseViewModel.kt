package com.task.parenttechnicaltask.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {
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