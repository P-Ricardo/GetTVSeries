package com.example.gettvseries.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.gettvseries.R;

public class MoviesByGenreActivity extends AppCompatActivity {

    private int genreId;
    private TextView genreText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies_by_genre);

        Bundle bundle = this.getIntent().getExtras();
        genreId = (int) bundle.getSerializable("genre_id");

        genreText = findViewById(R.id.textViewMovieGenre);
        String string = String.valueOf(genreId);
        genreText.setText(string);


    }
}
