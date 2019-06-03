package com.example.gettvseries.Model.Services.Api;

import com.example.gettvseries.Model.Services.Responses.GenreResponse;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("movie/upcoming")
    Call<MovieResponse> getUpcoming(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("page") int pageIndex
    );

    @GET("genre/movie/list")
    Call<GenreResponse> getGenres(
            @Query("api_key")String apiKey,
            @Query("language") String language
    );

    @GET("discover/movie")
    Call<MovieResponse> getMoviesByGenre(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("sort_by") String sortBy,
            @Query("with_genres") String genreId
    );

    @GET("search/movie")
    Call<MovieResponse> getMovieSearch(
            @Query("api_key") String apiKey,
            @Query("language") String language,
            @Query("query") String query,
            @Query("include_adult") boolean adult,
            @Query("page") int page
    );

}
