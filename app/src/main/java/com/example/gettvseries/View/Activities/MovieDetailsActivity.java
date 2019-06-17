package com.example.gettvseries.View.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;
import com.example.gettvseries.R;
import com.example.gettvseries.Utils.Month;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView title;
    private TextView releaseDate;
    private TextView ratingValue;
    private TextView runtime;
    private TextView overview;
    private ImageView backdrop;
    private RatingBar ratingBar;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = findViewById(R.id.txt_title);
        releaseDate = findViewById(R.id.txt_release_date);
        ratingValue = findViewById(R.id.rating_value);
        overview = findViewById(R.id.txt_overview);
        runtime = findViewById(R.id.txt_runtime);

        Button buttonAdd = findViewById(R.id.btn_add_favorites);
        backdrop = findViewById(R.id.backdrop_img);
        ratingBar = findViewById(R.id.rb_movie);

        Intent intent = getIntent();
        Bundle bundle = getIntent().getExtras();
        String movieId = "";
        movieId = bundle != null ? bundle.getString("MovieIdKey") : "";

        loadContent(movieId);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adicionar no firebase o filme

            }
        });

    }

    private void loadContent(String movieId) {

        RetrofitConfig.getService().getMovie(
                movieId,
                Constant.API_KEY,
                Constant.LANGUAGE
        ).enqueue(new Callback<Movie>() {
            @Override
            public void onResponse(Call<Movie> call, Response<Movie> response) {

                if (response.body() != null) {

                    movie = response.body();
                    setScreenContent(movie);
                }
            }

            @Override
            public void onFailure(Call<Movie> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "No connection", Toast.LENGTH_SHORT).show();
                Log.d("Error connection", t.getMessage());
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void setScreenContent(Movie movie) {

        title.setText(movie.getTitle());
        releaseDate.setText(dateFormatter(movie.getReleaseDate()));
        overview.setText(movie.getOverview());
        runtime.setText("Runtime: " + (movie.getRuntime() != null ? hourFormatter(movie.getRuntime()) : 0));

        ratingValue.setText(movie.getVoteAverage().toString());
        ratingBar.setRating(movie.getVoteAverage().floatValue()/2);
        ratingBar.setStepSize(0.01f);

        Glide
                .with(getApplicationContext())
                .load(Constant.IMAGE_URL + movie.getBackdropPath())
                .into(backdrop);
    }

    private String hourFormatter(Integer runtime) {

        int hours = runtime / 60;
        int minutes = runtime % 60;

        if (hours == 0)
            return runtime + " min";

        return hours + "h" + (minutes > 10 ? minutes : "0" + minutes);
    }


    @SuppressWarnings("StringBufferReplaceableByString")
    private String dateFormatter(String releaseDate) {

        StringBuilder date = new StringBuilder();
        date.append(releaseDate.substring(8)).append(" ");              // day
        date.append(getMonth(releaseDate.substring(5, 7))).append(" "); // month
        date.append(releaseDate.substring(0, 4));                       // year

        return date.toString();
    }

    private String getMonth(String substring) {

        String month = "";
        int i = substring.charAt(0) == '0' ?
                Character.getNumericValue(substring.charAt(1)) : Integer.parseInt(substring);

        i--;
        Month m = Month.values()[i];

        switch (m) {
            case JANUARY:
                month = "January";
                break;
            case FEBRUARY:
                month = "February";
                break;
            case MARCH:
                month = "March";
                break;
            case APRIL:
                month = "April";
                break;
            case MAY:
                month = "May";
                break;
            case JUNE:
                month = "June";
                break;
            case JULY:
                month = "July";
                break;
            case AUGUST:
                month = "August";
                break;
            case SEPTEMBER:
                month = "September";
                break;
            case OCTOBER:
                month = "October";
                break;
            case NOVEMBER:
                month = "November";
                break;
            case DECEMBER:
                month = "December";
                break;

        }
        return month;
    }


}
