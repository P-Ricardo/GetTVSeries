package com.example.gettvseries;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerButtons;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerButtons = view.findViewById(R.id.recycler_buttons);
        recyclerButtons.setHasFixedSize(true);
        recyclerButtons.setLayoutManager(
                new LinearLayoutManager(
                       getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));
        GenreButtonsAdapter adapter = new GenreButtonsAdapter(getGenres());

        adapter.setOnGenreListener(new OnGenreListener() {
            @Override
            public void onGenreClick(View v, int position) {
                Log.d("TAG Click", "onGenreClick: " + position);
            }

            @Override
            public void onLongGenreClick(View v, int position) {
                Log.d("TAG Click", "onLongGenreClick: " + position);
            }
        });

        recyclerButtons.setAdapter(adapter);

        return view;

    }

    private List<String> getGenres() {

        ArrayList<String> list = new ArrayList<>();

        list.add("Action");
        list.add("Adventure");
        list.add("Animation");
        list.add("Comedy");
        list.add("Fantasy");
        list.add("Drama");
        list.add("Horror");
        list.add("Romance");
        list.add("Sci-fi");

        return list;
    }

}
