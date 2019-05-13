package com.example.gettvseries.models;

import java.util.List;

public class Season extends Cinematographic {

    private List<Episode> episodes;
    private String name;
    private Integer seasonNumber;
    private String airDate;

    public Season(String posterPath, List<Integer> genreIds, String originalLanguage, String backdropPath, String overview, Double popularity, Integer voteCount, Double voteAverage, Integer id, boolean favorite, long idDatabase,
                  List<Episode> episodes, String name, Integer seasonNumber, String airDate) {
        super(posterPath, genreIds, originalLanguage, backdropPath, overview, popularity,
                voteCount, voteAverage, id, favorite, idDatabase);
        this.episodes = episodes;
        this.name = name;
        this.seasonNumber = seasonNumber;
        this.airDate = airDate;
    }

    public Season(){}

    public List<Episode> getEpisodes() {
        return episodes;
    }

    public void setEpisodes(List<Episode> episodes) {
        this.episodes = episodes;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(Integer seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}
