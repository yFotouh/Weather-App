package com.task.parenttechnicaltask.network.api;

import com.task.parenttechnicaltask.model.dto.response.WeatherResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiClient {
    @GET("volumes/")
    Observable<WeatherResult> getBooksWithTitle(
            @Query("q") String title
    );

}
