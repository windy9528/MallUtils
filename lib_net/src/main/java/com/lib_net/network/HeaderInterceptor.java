package com.lib_net.network;

import android.content.Intent;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * date:2019/7/9
 * name:windy
 * function:  自定义头部拦截器
 */
public class HeaderInterceptor implements Interceptor {

    private static HeaderInterceptor instance;
    private static Request newRequest;

    @Override
    public Response intercept(Chain chain) throws IOException {

        Request originalRequest = chain.request();  //原始请求对象

        newRequest = originalRequest.newBuilder()
                .build();

        Response proceed = chain.proceed(newRequest);

        return proceed;
    }

    public static HeaderInterceptor getInstance() {
        if (instance == null) {
            synchronized (HeaderInterceptor.class) {
                if (instance == null) {
                    instance = new HeaderInterceptor();
                }
            }
        }
        return instance;
    }

    public static Request getNewRequest(Object... args) {

        String userId = String.valueOf(args[0]);
        String sessionId = String.valueOf(args[1]);

        newRequest
                .newBuilder()
                .addHeader("userId",userId)
                .addHeader("sessionId",sessionId)
                .build();

        return newRequest;
    }
}
