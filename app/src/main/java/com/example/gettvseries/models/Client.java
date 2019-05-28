package com.example.gettvseries.models;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

    private static volatile Retrofit mRetrofit;

    public Client() {
        throw new  RuntimeException("Use getCinematographicService() or getGenresService()");
    }

    private static <T> T getService(Class<T> service) {


        synchronized (Client.class){


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

        return mRetrofit.create(service);
    }

    public static GenreService getGenresService(){
        return getService(GenreService.class);
    }

    /*

    public static Retrofit retrofit = null;


    public static Retrofit getClient(){

        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(Constant.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    */
}
