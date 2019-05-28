package com.example.gettvseries.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.gettvseries.R;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class Presentation extends IntroActivity {


    public Presentation() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()

                .background(android.R.color.white)
                .fragment(R.layout.intro_1)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(android.R.color.white)
                .fragment(R.layout.intro_2)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(android.R.color.white)
                .fragment(R.layout.intro_3)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(android.R.color.white)
                .fragment(R.layout.intro_4)
                .canGoForward(false)
                .build());
    }

    public void openLogin(View view){

        startActivity(new Intent(Presentation.this, LoginActivity.class));
    }
}
