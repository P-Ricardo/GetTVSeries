package com.example.gettvseries.View.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gettvseries.Adapter.MoviesAdapter;
import com.example.gettvseries.Firebase.ConfigurationFirebase;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.R;
import com.example.gettvseries.Utils.Base64Custom;
import com.example.gettvseries.View.Activities.MovieDetailsActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FavoritesFragment extends Fragment {

    public View view;
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private MoviesAdapter adapter;
    private List<Movie> movieList = new ArrayList<>();

    private FirebaseAuth authentication = ConfigurationFirebase.getFirebaseAuthentication();
    private DatabaseReference firebaseRef = ConfigurationFirebase.getFirebaseDatabase();
    private DatabaseReference userRef;
    private ValueEventListener userValueEventListener;


    public FavoritesFragment() {
    }

    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        adapter = new MoviesAdapter();
        progressBar = view.findViewById(R.id.progress_pop);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_favorite);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onClick(View v, int position) {

                Bundle bundleMovieId = new Bundle();
                bundleMovieId.putString("MovieIdKey", String.valueOf(adapter.get(position).getId()));
                Intent in = new Intent(getActivity(), MovieDetailsActivity.class);
                in.putExtras(bundleMovieId);
                startActivity(in);

            }
        });
        getMoviesFirebase();
        recyclerView.setAdapter(adapter);

    }

    private void getMoviesFirebase(){

        String userEmail = authentication.getCurrentUser().getEmail();
        String userId = Base64Custom.codeBase64(userEmail);
        userRef = firebaseRef.child("users").child(userId).child("movies");

        userValueEventListener = userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                movieList.clear();

                for (DataSnapshot data: dataSnapshot.getChildren()) {

                    Movie movie = data.getValue(Movie.class);
                    movie.setKey(data.getKey());
                    movieList.add(movie);
                }
                progressBar.setVisibility(View.GONE);
                adapter.insertMovies(movieList);
                adapter.notifyDataSetChanged();
                //progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }

}
