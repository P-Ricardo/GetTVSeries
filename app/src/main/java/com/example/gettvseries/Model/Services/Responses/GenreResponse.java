package com.example.gettvseries.Model.Services.Responses;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.gettvseries.Model.Entity.Genre;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class GenreResponse implements Parcelable {

    @SerializedName("genres")
    @Expose
    private List<Genre> genres = new ArrayList<>();
    public final static Parcelable.Creator<GenreResponse> CREATOR = new Creator<GenreResponse>() {

        public GenreResponse createFromParcel(Parcel in) {
            return new GenreResponse(in);
        }

        public GenreResponse[] newArray(int size) {
            return (new GenreResponse[size]);
        }

    };

    private GenreResponse(Parcel in) {
        in.readList(this.genres, (Genre.class.getClassLoader()));
    }

    public GenreResponse() {
    }

    public List<Genre> getGenres() {
        return genres;
    }

    public void setGenres(List<Genre> genres) {
        this.genres = genres;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(genres);
    }

    public int describeContents() {
        return 0;
    }
}
