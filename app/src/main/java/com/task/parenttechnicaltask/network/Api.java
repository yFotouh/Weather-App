package com.task.parenttechnicaltask.network;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.task.parenttechnicaltask.network.api.ApiClient;
import com.task.parenttechnicaltask.network.interceptors.AuthenticationInterceptor;
import com.task.parenttechnicaltask.network.interceptors.ConnectivityInterceptor;
import com.tests.newandroid.network.api.ApiClientKt;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Api {
    private static Retrofit retrofit = null;
    private static ApiClient apiClient;
    private static  ApiClientKt apiClientKt;
    private static int REQUEST_TIMEOUT = 60;
    private static OkHttpClient okHttpClient;
    private static final String releaseUrl = "http://api.openweathermap.org/data/2.5/";
    private static final String developUrl = "http://api.openweathermap.org/data/2.5/";

    public static Retrofit getClient() {

        if (okHttpClient == null)
            initOkHttp();

        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(developUrl)
                    .client(okHttpClient)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static ApiClient getApiClient() {
        if (apiClient == null)
            apiClient = Api.getClient()
                    .create(ApiClient.class);

        return apiClient;
    }
    public static ApiClientKt getApiClientKt() {
        if (apiClientKt == null)
            apiClientKt = Api.getClient()
                    .create(ApiClientKt.class);

        return apiClientKt;
    }

    private static void initOkHttp() {
        OkHttpClient.Builder httpClient = new OkHttpClient().newBuilder()
                .connectTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(REQUEST_TIMEOUT, TimeUnit.SECONDS);

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(interceptor);
        httpClient.addInterceptor(new ConnectivityInterceptor());
        httpClient.addInterceptor(new AuthenticationInterceptor());


        okHttpClient = httpClient.build();
    }
}
