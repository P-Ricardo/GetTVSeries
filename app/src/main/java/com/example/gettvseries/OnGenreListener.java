package com.example.gettvseries;

import android.view.View;

interface OnGenreListener {

    void onGenreClick(View v, int position);
    void onLongGenreClick(View v, int position);
}