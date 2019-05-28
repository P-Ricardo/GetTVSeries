package com.example.gettvseries.models;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GenreService {

    @GET("genre/{tagType}/list")
    Observable<GenresResponse> getGenres(
            @Path("tagType") String tagType,
            @Query("api_key") String apiKey
    );

    @GET("genre/movie/list")
    Call<GenresResponse> getBGenres(
            @Query("api_key")String api_key);

}
