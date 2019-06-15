package com.example.gettvseries.View.Fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
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

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class SearchFragment extends Fragment{

    private View view;
    private RecyclerView recyclerView;
    private int currentPage = 1;
    private MoviesAdapter adapter;
    private SearchView inputSearch;
    private ProgressBar progressBar;
    private AppCompatTextView helper;
    private SearchView search;

    public SearchFragment() {
        // Requires empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_search, container, false);
//        inputSearch = view.findViewById(R.id.input_search);
        adapter = new MoviesAdapter();

        progressBar = view.findViewById(R.id.progress);
        helper = view.findViewById(R.id.movie_helper);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        incrementPage(currentPage);

        recyclerView = view.findViewById(R.id.rv_search);
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


        RetrofitConfig.getService().getMovieSearch(
                Constant.API_KEY,
                Constant.LANGUAGE,
                "",
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

//    private void bindSearchView() {
//        mSearchView.setHint("Search for movies here");
//        mSearchView.setOnTextChangeListener(new SearchView.OnTextChangeListener() {
//
//            @Override
//            public void onSuggestion(String suggestion) {
//
//                adapter.clear();
//                changeSearchText(suggestion);
//                mMessageHelper.setVisibility(View.GONE);
//                mList.setVisibility(View.VISIBLE);
//
//                Log.d("tag", "onSuggestion: ");
//            }
//
//            @Override
//            public void onSubmitted(String submitted) {
//
//            }
//
//            @Override
//            public void onCleared() {
//
//                mAdapter.clear();
//                mAdapter.notifyDataSetChanged();
//                mViewModel.setQuery("");
//                mProgress.setVisibility(View.GONE);
//                mList.setVisibility(View.GONE);
//                mMessageHelper.setText(R.string.text_help);
//                mMessageHelper.setVisibility(View.VISIBLE);
//
//            }
//        });
//    }
//
//    private void changeSearchText(String s) {
//        mProgress.setVisibility(View.VISIBLE);
//        mViewModel.setQuery(s);
//
//    }

}
