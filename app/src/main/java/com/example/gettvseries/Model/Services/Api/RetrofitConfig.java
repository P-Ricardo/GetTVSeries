package com.example.gettvseries.Model.Services.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


import android.os.CountDownTimer;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {


    private static volatile Retrofit mRetrofit;

    private RetrofitConfig() {
        throw new  RuntimeException("Use getCinematographicService() or getGenresService()");
    }


    public static Service getService() {


        synchronized (RetrofitConfig.class){


            if(mRetrofit==null){


                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

                OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                        .connectTimeout(30, TimeUnit.MINUTES)
                        .readTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .addInterceptor(httpLoggingInterceptor)
                        .build();

                mRetrofit = new Retrofit
                        .Builder()
                        .baseUrl(Constant.BASE_URL)
                        .client(okHttpClient)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        return mRetrofit.create(Service.class);
    }


}

