package com.example.gettvseries;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.gettvseries.activity.MoviesByGenreActivity;
import com.example.gettvseries.activity.SeriesByGenreListActivity;
import com.example.gettvseries.models.Cinematographic;
import com.example.gettvseries.models.CinematographicListAdapter;
import com.example.gettvseries.models.CinematographicsResponse;
import com.example.gettvseries.models.Client;
import com.example.gettvseries.models.Constant;
import com.example.gettvseries.models.Genre;
import com.example.gettvseries.models.GenreService;
import com.example.gettvseries.models.GenresResponse;
import com.example.gettvseries.models.OnItemClickListener;
import com.example.gettvseries.models.PaginationScrollListener;
import com.example.gettvseries.models.TheMoviesDbapi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private RecyclerView recyclerButtons, recyclerTopRated;
    private GenreButtonsAdapter adapter;
    private CinematographicListAdapter madapter;
    private View mView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_home, container, false);

        return mView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getGenres();
        getTopRated();
    }

    private void getGenres() {



        Call<GenresResponse> call = Client.getGenresService().getBGenres(Constant.API_KEY);


        call.enqueue(new Callback<GenresResponse>() {


            @Override
            public void onResponse(Call<GenresResponse> call, Response<GenresResponse> response) {


                assert response.body() != null;
                List<Genre> genres = response.body().getGenres();

                setupLista(genres);

            }
            @Override
            public void onFailure(Call<GenresResponse> call, Throwable t) {


                Toast.makeText(getContext(), "Deu pau", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void setupLista(final List<Genre> genres) {

        recyclerButtons = mView.findViewById(R.id.recycler_buttons);
        recyclerButtons.setHasFixedSize(true);
        recyclerButtons.setLayoutManager(
                new LinearLayoutManager(
                        getContext(),
                        LinearLayoutManager.HORIZONTAL,
                        false));


        adapter = new GenreButtonsAdapter(genres);

        adapter.setOnGenreListener(new OnGenreListener() {
            @Override
            public void onGenreClick(View v, int position) {

                Bundle bundle = new Bundle();
                Intent intent = new Intent(getContext(), MoviesByGenreActivity.class);
                bundle.putSerializable("genre_id", genres.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);
            }

            @Override
            public void onLongGenreClick(View v, int position) {
                Log.d("TAG Click", "onLongGenreClick: " + position);
            }
        });

        recyclerButtons.setAdapter(adapter);
    }
    private void getTopRated(){

        Call<CinematographicsResponse> call = Client.getCinematographicService().getMovies(Constant.API_KEY, "pt-BR", 1);

        call.enqueue(new Callback<CinematographicsResponse>() {
            @Override
            public void onResponse(Call<CinematographicsResponse> call, Response<CinematographicsResponse> response) {

                assert response.body() != null;

                List<Cinematographic> movies = response.body().getMovies();
                setupTopRated(movies);
            }

            @Override
            public void onFailure(Call<CinematographicsResponse> call, Throwable t) {

            }
        });


    }
    private void setupTopRated(List<Cinematographic> movies){

        recyclerTopRated = mView.findViewById(R.id.recycler_top_rated);
        LinearLayoutManager layout = new LinearLayoutManager(
                getContext(),
                LinearLayoutManager.HORIZONTAL,
                false);
        recyclerTopRated.setHasFixedSize(true);
        recyclerTopRated.setLayoutManager(layout);
        madapter = new CinematographicListAdapter(movies);
        recyclerTopRated.setAdapter(madapter);
        madapter.setClickListener(new OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {

                startActivity(new Intent(getContext(), SeriesByGenreListActivity.class));
            }
        });
    }
}
