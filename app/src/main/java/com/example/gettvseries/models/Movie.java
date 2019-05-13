package com.example.gettvseries.models;

import java.util.List;

public class Movie extends Cinematographic {

    private Boolean video;
    private String title;
    private String originalTitle;
    private Boolean adult;
    private String releaseDate;

    public Movie(String posterPath, List<Integer> genreIds, String originalLanguage, String backdropPath, String overview, Double popularity, Integer voteCount, Double voteAverage, Integer id, boolean favorite, long idDatabase, Boolean video, String title, String originalTitle, Boolean adult, String releaseDate) {
        super(posterPath, genreIds, originalLanguage, backdropPath, overview, popularity, voteCount, voteAverage, id, favorite, idDatabase);
        this.video = video;
        this.title = title;
        this.originalTitle = originalTitle;
        this.adult = adult;
        this.releaseDate = releaseDate;
    }

    public Movie(){}

    public Boolean getVideo() {
        return video;
    }

    public void setVideo(Boolean video) {
        this.video = video;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public Boolean getAdult() {
        return adult;
    }

    public void setAdult(Boolean adult) {
        this.adult = adult;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
}