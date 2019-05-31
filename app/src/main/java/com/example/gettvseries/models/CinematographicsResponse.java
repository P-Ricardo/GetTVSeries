package com.example.gettvseries.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CinematographicsResponse {


    @SerializedName("movies")
    private List<Cinematographic> movies;

    public List<Cinematographic> getMovies() {
        return movies;
    }

    public void setMovies(List<Cinematographic> movies) {
        this.movies = movies;
    }
}
