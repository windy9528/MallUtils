package com.lib_net.network;

import android.net.Uri;

import com.lib_core.common.Constant;
import com.lib_core.utils.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * date:2019/7/9
 * name:windy
 * function:
 */
public class RetrofitUtil {

    private static volatile RetrofitUtil instance;
    private Retrofit retrofit;
    private OkHttpClient okHttpClient;

    private RetrofitUtil(String url) {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        if (!Constant.isRelease) {  //如果为开发环境
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        } else {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        }
        //混存
//        long maxCacheSize = 100 * 1024 * 1024;//100m
//        File httpCacheDirectory = new File(Environment.get, "okhttpCache");
        okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .connectTimeout(10, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .addNetworkInterceptor(httpLoggingInterceptor)
                .addInterceptor(new HeaderInterceptor())//头部拦截器
//                .cache()//缓存
                .build();
        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 单例调用，双重锁模式
     *
     * @return
     */
    public static RetrofitUtil getInstance(String url) {
        if (instance == null) {
            synchronized (RetrofitUtil.class) {
                if (instance == null) {
                    instance = new RetrofitUtil(url);
                }
            }
        }
        return instance;
    }

    /**
     * 动态代理模式，创建请求接口类
     *
     * @param service
     * @param <T>
     * @return
     */
    public <T> T create(final Class<T> service) {
        return retrofit.create(service);
    }
}
