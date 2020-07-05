package com.task.parenttechnicaltask.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.Bindable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.parenttechnicaltask.AppClass
import com.task.parenttechnicaltask.model.dto.response.WeatherResult
import com.task.parenttechnicaltask.model.repository.CityRepository
import com.task.parenttechnicaltask.ui.wrappers.CityWeatherWrapper
import com.task.parenttechnicaltask.ui.wrappers.CityWrapper
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.koin.androidx.scope.compat.ScopeCompat

class CityViewModel(cityRepository: CityRepository) : BaseViewModel() {
    private var cityRepository: CityRepository? = null
    private val mutableLiveData =
        SingleLiveEvent<CityWrapper>()

    val dataSource =
        MutableLiveData<List<String>>(emptyList<String>())

    val autoCompleteTv = MutableLiveData<String>("")
    val selectedCity = MutableLiveData<String>("")

    init {
        this.cityRepository = cityRepository
    }

    fun getCities(): LiveData<CityWrapper> {
        coroutineScope.launch(Dispatchers.Main) {
            var cities = fetchCities()
//            dataSource = cities.citiesName
            dataSource.value = cities.citiesName
        }
        return mutableLiveData
    }

    //    fun itemSelected(value: String) {
//        Log.d("", "")
////        selectedCity.observe(ScopeCompat.lifecycleScope(this))
//    }
    fun makeToast(text: String) {
        Toast.makeText(AppClass.instance, "Hello World", Toast.LENGTH_LONG).show()
//        uNameLiveData.value = "3adel"
    }

    suspend fun fetchCities(): CityWrapper {
        return withContext(Dispatchers.IO)
        {
            cityRepository!!.getAllCities()
        }!!
    }

    val selected = object : AdapterView.OnItemSelectedListener {
        override fun onItemSelected(
            adapterView: AdapterView<*>?,
            view: View,
            i: Int,
            l: Long
        ) {
            Log.d("", "")
        }

        override fun onNothingSelected(adapterView: AdapterView<*>?) {}
    }
}