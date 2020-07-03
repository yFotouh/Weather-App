package com.task.parenttechnicaltask.wrappers;

import com.task.parenttechnicaltask.model.dto.response.WeatherResult;

import java.util.ArrayList;
import java.util.List;

public class CityWeatherWrapper {
    public String cityName;
    public WeatherResult weatherResult;
    public List<WeatherDay> weatherDays=new ArrayList<>();
}
