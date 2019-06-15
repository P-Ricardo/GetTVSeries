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
    private TextView overview;
    private ImageView backdrop;
    private RatingBar ratingBar;
    private Button buttonAdd;
    private String movieId;
    private List<Movie> movies = new ArrayList<>();
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        title = findViewById(R.id.txt_title);
        releaseDate = findViewById(R.id.txt_release_date);
        ratingValue = findViewById(R.id.rating_value);
        overview = findViewById(R.id.txt_overview);

        buttonAdd = findViewById(R.id.btn_add_favorites);
        backdrop = findViewById(R.id.backdrop_img);
        ratingBar = findViewById(R.id.rb_movie);

        intent = getIntent();
        Movie movie = intent.getParcelableExtra("getting movieID");

        if(movie != null){
            Log.d("Movies Activity", "Esta nullllll");
        }

        loadContent(movie.getId().toString());

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // adicionar no firebase o filme
                Log.d("TagButton", "Olha aeeee");
            }
        });

    }

    private void loadContent(String movieId) {

        RetrofitConfig.getService().getMovie(
                movieId,
                Constant.API_KEY,
                Constant.LANGUAGE
        ).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.body() != null) {

                    movies = response.body().getResults();
                    if (movies != null)
                        setScreenContent(movies.get(0));
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
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

        ratingValue.setText(movie.getVoteAverage().toString());
        ratingBar.setRating(movie.getVoteAverage().floatValue());

        Glide
                .with(getApplicationContext())
                .load(Constant.IMAGE_URL + movie.getBackdropPath())
                .into(backdrop);
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
