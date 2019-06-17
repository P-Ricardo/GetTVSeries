package com.example.gettvseries.View.Fragments;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gettvseries.Adapter.MoviesAdapter;
import com.example.gettvseries.Adapter.PaginationScrollListener;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;
import com.example.gettvseries.R;
import com.example.gettvseries.View.Activities.MovieDetailsActivity;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment{

    private View view;
    private int currentPage = 1;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private SearchView search;

    public SearchFragment() {
        // Requires empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_search, container, false);
        adapter = new MoviesAdapter();
        progressBar = view.findViewById(R.id.progress);
        search = view.findViewById(R.id.sv_search);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.rv_search);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), RecyclerView.VERTICAL));

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

                loadJson(currentPage, "");

            }
        }
        ));

        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
            @Override
            public void onClick(View v, int position) {
                if(adapter.getItemCount() != 0 && position != -1){

                    loadContent(position);
                }
            }

            private void loadContent(int position) {

                Bundle bundleMovieId = new Bundle();
                bundleMovieId.putString("MovieIdKey", String.valueOf(adapter.get(position).getId()));
                Intent in = new Intent(getActivity(), MovieDetailsActivity.class);
                in.putExtras(bundleMovieId);
                startActivity(in);
            }
        });

        recyclerView.setAdapter(adapter);

    }

    private void loadJson(int page, final String query) {

        RetrofitConfig.getService().getMovieSearch(
                Constant.API_KEY,
                Constant.LANGUAGE,
                query,
                false,
                page)
                .enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.body() != null) {

                    List<Movie> movies = response.body().getResults();
                    if (movies != null){

                        progressBar.setVisibility(View.GONE);
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
