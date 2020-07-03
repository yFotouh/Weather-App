package com.task.parenttechnicaltask.network.interceptors;


import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticationInterceptor implements Interceptor {


    public AuthenticationInterceptor() {
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = null;
//        token = AppPrefs.get().getToken();
//        if (token == null) {
//            token = "123";
//        }
//        Request request = chain.request();
//        request.url().newBuilder().addQueryParameter("appid","1edc9b8317c194deb1e3969f23831694").build();
        Request request = chain.request();
        HttpUrl url = request.url().newBuilder().addQueryParameter("appid","1edc9b8317c194deb1e3969f23831694").build();
        request = request.newBuilder().url(url).build();
        return chain.proceed(request);
    }
}