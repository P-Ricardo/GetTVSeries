package com.example.gettvseries.models;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CinematographicService {

    @GET("movie/top_rated")
    Call<CinematographicsResponse> getMovies(
    @Query("api_key") String apiKey,
    @Query("language") String language,
    @Query("page") int page);

}
