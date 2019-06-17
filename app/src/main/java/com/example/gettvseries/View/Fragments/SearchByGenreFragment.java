package com.example.gettvseries.View.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gettvseries.Adapter.GenresAdapter;
import com.example.gettvseries.Model.Entity.Genre;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Responses.GenreResponse;
import com.example.gettvseries.R;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchByGenreFragment extends Fragment {


    private View view;
    private RecyclerView recyclerView;
    private GenresAdapter adapter;
    private ProgressBar progressBar;
    private MovieGenreFragment genreFragment;
    private List<Genre> mGenres;

    public SearchByGenreFragment() {
    }

    public static SearchByGenreFragment newInstance(){return new SearchByGenreFragment();}


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        view = inflater.inflate(R.layout.fragment_genre_search, container, false);
        adapter = new GenresAdapter();
        progressBar = view.findViewById(R.id.progress_genres);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = view.findViewById(R.id.rv_search_genre);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
                getContext(),
                RecyclerView.VERTICAL,
                false);
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter.setOnGenreClickListener(new GenresAdapter.OnGenreClickListener() {
            @Override
            public void onClick(View v, int position) {

                //Toast.makeText(getContext(), mGenres.get(position).getName(), Toast.LENGTH_SHORT).show();
                Bundle pGenreValues = new Bundle();
                //pGenreValues.putString("NameGenreKey", String.valueOf(mGenres.get(position).getName()));
                pGenreValues.putString("IdGenreKey",String.valueOf(mGenres.get(position).getId()));
                Fragment fragment = new MovieGenreFragment();
                fragment.setArguments(pGenreValues);
                getFragmentManager().beginTransaction().replace(R.id.fragment_container, fragment).commit();

            }
        });
        getGenres();
        recyclerView.setAdapter(adapter);

    }

    private void getGenres(){


        RetrofitConfig.getService().getGenres(
                Constant.API_KEY,
                Constant.LANGUAGE)
                .enqueue(new Callback<GenreResponse>() {
                    @Override
                    public void onResponse(Call<GenreResponse> call, Response<GenreResponse> response) {

                        if (response.body() != null){

                            List<Genre> genres = response.body().getGenres();
                            mGenres = genres;

                            if (genres != null){

                                progressBar.setVisibility(View.GONE);
                                adapter.insertGenres(genres);
                            }
                            else
                                Log.d("TAG response", "onResponse: is null");

                        }else
                            Toast.makeText(getContext(), "Code: " + response.code(),
                                    Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<GenreResponse> call, Throwable t) {

                        Toast.makeText(getContext(), "No connection", Toast.LENGTH_SHORT).show();
                        Log.d("Error connection", t.getMessage());
                    }
                });



    }




}