package com.example.gettvseries.Model.Services.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitConfig {


    private static volatile Retrofit mRetrofit;

    private RetrofitConfig() {
        throw new RuntimeException("RuntimeException");
    }

    public static Service getService() {

        synchronized (RetrofitConfig.class) {

            if (mRetrofit == null) {


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
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
            }
        }

        return mRetrofit.create(Service.class);
    }


}

