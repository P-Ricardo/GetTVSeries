package com.example.gettvseries.View.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gettvseries.Adapter.MoviesAdapter;
import com.example.gettvseries.Adapter.PaginationScrollListener;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;
import com.example.gettvseries.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieGenreFragment extends Fragment {

    private static final String ARG_QUERY = "genreQuery";
    private static final String ARG_TITLE = "genreTitle";

    private View view;
    private RecyclerView recyclerView;
    private int currentPage = 1;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private String query = getArguments() != null ? getArguments().getString(ARG_QUERY) : "";
    private String genreName = getArguments() != null ? getArguments().getString(ARG_TITLE) : "Error";
    private TextView toolbarGenreName;
    private String genreMoviesId;

    public MovieGenreFragment() {
        // Requires empty public constructor
    }

    public static MovieGenreFragment newInstance(String genreId, String genreTitle) {
        MovieGenreFragment fragment = new MovieGenreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_QUERY, genreId);
        args.putString(ARG_TITLE, genreTitle);
        fragment.setArguments(args);

        return fragment;
    }



    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        //toolbarGenreName = view.findViewById(R.id.toolbar_movie_name_genre);
        //toolbarGenreName.setText(getArguments().getString("NameGenreKey"));
        genreMoviesId = getArguments().getString("IdGenreKey");
        view = inflater.inflate(R.layout.fragment_movie_genre, container, false);
        adapter = new MoviesAdapter();
        progressBar = view.findViewById(R.id.progress_list);

        return view;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
        incrementPage(currentPage);

        recyclerView = view.findViewById(R.id.rv_movie_genre);
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

        RetrofitConfig.getService().getMoviesByGenre(
                Constant.API_KEY,
                Constant.LANGUAGE,
                Constant.SORT_BY_MORE_POPULARITY,
                page,
                genreMoviesId
        ).enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if (response.body() != null) {

                    List<Movie> movies = response.body().getResults();
                    if (movies != null) {

                        progressBar.setVisibility(View.GONE);
                        adapter.insertMovies(movies);
                    } else
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
