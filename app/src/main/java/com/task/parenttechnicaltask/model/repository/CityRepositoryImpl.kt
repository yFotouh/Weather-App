package com.task.parenttechnicaltask.model.repository

import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.parenttechnicaltask.AppClass
import com.task.parenttechnicaltask.utils.AppPrefs
import com.task.parenttechnicaltask.utils.FileSystemHelper
import com.task.parenttechnicaltask.utils.SingleLiveEvent
import com.task.parenttechnicaltask.ui.wrappers.ACity
import com.task.parenttechnicaltask.ui.wrappers.CityWrapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.reflect.Type


class CityRepositoryImpl : BaseRepository(), CityRepository {
    private val mutableLiveData =
        SingleLiveEvent<CityWrapper>()

    override fun getAllCities(): LiveData<CityWrapper> {

        coroutineScope.launch(Dispatchers.Main) {
            var cityWrapper = getTheCities()
            mutableLiveData.value = cityWrapper
        }
        return mutableLiveData
    }

    suspend fun getTheCities(): CityWrapper {

        return withContext(Dispatchers.IO)
        {
            var prefs = AppPrefs.get()
            var cityWrapper = prefs.cities
            if (cityWrapper == null) {
                cityWrapper = CityWrapper()
                var citiesString =
                    FileSystemHelper.readFileFromAssests(AppClass.instance, "cities.json");
                val gson = Gson()
                val listType: Type = object : TypeToken<List<ACity?>?>() {}.type
                val allCities: List<ACity> =
                    gson.fromJson<List<ACity>>(citiesString, listType)
                var set = HashSet<String>()
                var list = ArrayList<String>()
                for ((index, value) in allCities.withIndex()) {
                    if (value.name.contains(" ") == false && set.add(value.name) == true)
                        list.add(value.name)
                }
                cityWrapper.citiesName = list
                prefs.cities = cityWrapper

            }
            return@withContext cityWrapper
        }
    }
}