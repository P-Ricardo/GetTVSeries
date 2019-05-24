package com.example.gettvseries.models;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TheMoviesDbapi {

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(
            @Query("api_key")String api_key);

}
