package com.task.parenttechnicaltask.viewmodel

import android.util.Log
import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*

open class BaseViewModel : ViewModel() {
    val progressVisibility = MutableLiveData<Int>()
    protected val parentJob = Job()
    protected val coroutineExceptionHandler: CoroutineExceptionHandler =
        CoroutineExceptionHandler { _, throwable ->
            coroutineScope.launch(Dispatchers.Main) {
                Log.e("error", "error");
            }

            GlobalScope.launch {
                println("Caught $throwable")
                progressVisibility.postValue(View.GONE)
            }
        }

    protected val coroutineScope = CoroutineScope(
        Dispatchers.Main + parentJob +
                coroutineExceptionHandler
    )
}