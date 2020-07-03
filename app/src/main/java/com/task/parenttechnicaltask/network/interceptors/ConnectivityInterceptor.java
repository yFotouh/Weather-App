package com.task.parenttechnicaltask.network.interceptors;


import com.task.parenttechnicaltask.network.exceptions.NoConnectivityException;
import com.task.parenttechnicaltask.utils.ConnectionUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ConnectivityInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        if (!ConnectionUtil.isOnline()) {
            throw new NoConnectivityException();
        }

        Request.Builder builder = chain.request().newBuilder();
        return chain.proceed(builder.build());
    }

}
