package com.example.gettvseries.View.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.example.gettvseries.Firebase.ConfigFirebase;
import com.example.gettvseries.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;

public class Presentation extends IntroActivity {

    FirebaseAuth authentication;

    public Presentation() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);



        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()

                .background(R.color.colorAccent)
                .fragment(R.layout.intro_1)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(R.color.colorAccent)
                .fragment(R.layout.intro_2)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(R.color.colorAccent)
                .fragment(R.layout.intro_3)
                .build());
        addSlide(new FragmentSlide.Builder()

                .background(R.color.colorAccent)
                .fragment(R.layout.intro_4)
                .canGoForward(false)
                .build());
    }

    public void openLogin(View view){

        view.setClickable(false);
        view.setFocusable(false);
        startActivity(new Intent(Presentation.this, LoginActivity.class));
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();

        authentication = ConfigFirebase.getFirebaseAuthentication();
        FirebaseUser currentUser = authentication.getCurrentUser();

        if (currentUser != null){

            startActivity(new Intent(Presentation.this, MainActivity.class));
        }

    }
}
