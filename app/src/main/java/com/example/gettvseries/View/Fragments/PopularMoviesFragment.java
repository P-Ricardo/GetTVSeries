package com.example.gettvseries.View.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gettvseries.Adapter.PaginationScrollListener;
import com.example.gettvseries.Adapter.MoviesAdapter;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;
import com.example.gettvseries.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PopularMoviesFragment extends Fragment {

    private View view;
    private RecyclerView recyclerView;
    private int currentPage = 1;
    private MoviesAdapter adapter;

    public PopularMoviesFragment() {
        // Requires empty public constructor
    }

    public static PopularMoviesFragment newInstance() {
        return new PopularMoviesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_popular, container, false);
        adapter = new MoviesAdapter();

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        incrementPage(currentPage);

        recyclerView = view.findViewById(R.id.rv_popular);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), RecyclerView.VERTICAL));

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false
        );

        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addOnScrollListener(new PaginationScrollListener(
                linearLayoutManager, new PaginationScrollListener.OnScrollListener() {
            @Override
            public void loadMoreItems() {

                incrementPage(currentPage + 1);
            }
        }
        ));

        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onClick(View v, int position) {
                Log.d("TAG", "Movie CLicked :" + (position + 1));
            }
        });

        recyclerView.setAdapter(adapter);
    }

    private void incrementPage(int page) {

        RetrofitConfig.getService().getPopularMovies(
                Constant.API_KEY,
                Constant.LANGUAGE,
                page).enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.body() != null) {

                    List<Movie> movies = response.body().getResults();
                    if (movies != null){

                        // hide progress bar
                        adapter.insertMovies(movies);
                    }
                    else
                        Log.d("TAG response", "onResponse: is null");

                } else
                    Toast.makeText(getContext(), "Code: " + response.code(),
                            Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Toast.makeText(getContext(), "No connection", Toast.LENGTH_SHORT).show();
                Log.d("Error connection", t.getMessage());
            }
        });

        currentPage = page;
    }
}
