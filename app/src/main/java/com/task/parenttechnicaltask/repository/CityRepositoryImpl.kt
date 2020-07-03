package com.task.parenttechnicaltask.repository

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.task.parenttechnicaltask.AppClass
import com.task.parenttechnicaltask.utils.AppPrefs
import com.task.parenttechnicaltask.utils.FileSystemHelper
import com.task.parenttechnicaltask.wrappers.ACity
import com.task.parenttechnicaltask.wrappers.CityWrapper
import java.lang.reflect.Type


class CityRepositoryImpl : CityRepository {
    override fun getAllCities(): CityWrapper {
        var prefs = AppPrefs.get()
        var cities=prefs.cities
        if (cities == null) {

            var cities =
                FileSystemHelper.readFileFromAssests(AppClass.getInstance(), "cities.json");
            val gson = Gson()
            val listType: Type = object : TypeToken<List<ACity?>?>() {}.type
            val allCities: List<ACity> =
                gson.fromJson<List<ACity>>(cities, listType)
            var list=ArrayList<String>()
            for ((index, value) in allCities.withIndex()) {
                if(value.name.contains(" ")==false)
                list.add(value.name)
            }
            var cityWrapper=CityWrapper()
            cityWrapper.citiesName=list
            prefs.cities=cityWrapper
            return cityWrapper
        }
        else return cities
    }
}