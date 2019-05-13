package com.example.gettvseries.models;

import java.util.List;

public class Episode extends Cinematographic {

    private String name;
    private String airDate;

    public Episode(String posterPath, List<Integer> genreIds, String originalLanguage, String backdropPath,
                   String overview, Double popularity, Integer voteCount, Double voteAverage,
                   Integer id, boolean favorite, long idDatabase, String name, String airDate) {
        super(posterPath, genreIds, originalLanguage, backdropPath, overview, popularity, voteCount, voteAverage, id, favorite, idDatabase);
        this.name = name;
        this.airDate = airDate;
    }

    public Episode(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}
