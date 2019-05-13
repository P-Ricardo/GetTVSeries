package com.example.gettvseries.models;

import java.util.ArrayList;
import java.util.List;


public class Series extends Cinematographic
{

    private String originalName;
    private String name;
    private List<String> originCountry = new ArrayList<>();
    private String firstAirDate;

    public Series(String posterPath, List<Integer> genreIds, String originalLanguage, String backdropPath, String overview,
                  Double popularity, Integer voteCount, Double voteAverage, Integer id, boolean favorite,
                  long idDatabase, String originalName, String name, List<String> originCountry, String firstAirDate) {
        super(posterPath, genreIds, originalLanguage, backdropPath, overview, popularity, voteCount, voteAverage, id, favorite, idDatabase);
        this.originalName = originalName;
        this.name = name;
        this.originCountry = originCountry;
        this.firstAirDate = firstAirDate;
    }

    public Series(){}

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getOriginCountry() {
        return originCountry;
    }

    public void setOriginCountry(List<String> originCountry) {
        this.originCountry = originCountry;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }
}