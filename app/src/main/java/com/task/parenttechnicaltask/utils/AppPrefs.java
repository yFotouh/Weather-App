package com.task.parenttechnicaltask.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.task.parenttechnicaltask.AppClass;
import com.task.parenttechnicaltask.model.dto.response.City;
import com.task.parenttechnicaltask.wrappers.CachedWeatherData;
import com.task.parenttechnicaltask.wrappers.CityWeatherWrapper;
import com.task.parenttechnicaltask.wrappers.CityWrapper;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class AppPrefs {
    private static AppPrefs appPrefs;
    private static final String CACHED_JOB_ID = "CACHED_JOB_ID";
    private static final String TOKEN = "TOKEN";
    private static final String CITIES = "CITIES";
    private static final String CACHED_WEATHER_DATA = "CACHED_WEATHER_DATA";
    private static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    public AppPrefs() {
        pref = PreferenceManager.getDefaultSharedPreferences(AppClass.getInstance());
        editor = pref.edit();

    }


    public void setCities(CityWrapper cities) {
        editor.putString(CITIES, new Gson().toJson(cities));
        editor.apply();
    }

    public CityWrapper getCities() {
        String json = pref.getString(CITIES, "");
        if (json.isEmpty())
            return null;
        return new Gson().fromJson(json, CityWrapper.class);
    }

    public void saveRefreshToken(String token) {
        editor.putString(REFRESH_TOKEN, token);
        editor.apply();
    }

    public String getRefreshToken() {
        return pref.getString(REFRESH_TOKEN, null);
    }

    public static AppPrefs get() {
        if (appPrefs == null)
            appPrefs = new AppPrefs();
        return appPrefs;
    }

    public <T> void saveAsJson(T data, Class<T> clazz) {
        editor.putString(clazz.getSimpleName(), new Gson().toJson(data));
        editor.apply();
    }

    public <T> T fromJson(Class<T> clazz) {
        String json = pref.getString(clazz.getSimpleName(), "");
        return new Gson().fromJson(json, clazz);
    }

    public void cacheWeatherData(@NotNull ArrayList<CityWeatherWrapper> cityWeatherWrappers) {
        CachedWeatherData data = new CachedWeatherData();
        data.cityWeatherWrappers = cityWeatherWrappers;
        editor.putString(CACHED_WEATHER_DATA, new Gson().toJson(data));
        editor.apply();
    }

    public ArrayList<CityWeatherWrapper> getCacheWeatherData() {
        String json = pref.getString(CACHED_WEATHER_DATA, "");
        if (json.isEmpty())
            return null;
        CachedWeatherData cachedWeatherData = new Gson().fromJson(json, CachedWeatherData.class);
        return cachedWeatherData.cityWeatherWrappers;
    }
}
