package com.example.gettvseries.AsyncTask;

import android.os.AsyncTask;
import android.util.Log;

import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.Model.Services.Api.RetrofitConfig;
import com.example.gettvseries.Model.Services.Api.Service;
import com.example.gettvseries.Model.Services.Responses.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.Nullable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AsyncTaskBuscaMovies extends AsyncTask<Void, Void, List<Movie>> {

    private final String tagType;
    private final int page;
    private List<Movie> movies;
    private AsyncCallback mListener;

    public interface AsyncCallback{
        void onStart();
        void onFinish(@Nullable List<Movie> movies);
    }

    public AsyncTaskBuscaMovies(AsyncCallback mListener, String tagType, int page) {
        this.mListener = mListener;
        this.page = page;
        this.tagType = tagType;
    }

    @Override
    protected void onPreExecute() {
        if(mListener!=null) mListener.onStart();
    }

    @Override
    protected List<Movie> doInBackground(Void... aVoid) {

        Service service = RetrofitConfig.getService();

        Call<MovieResponse> movieResponseCall = null;//tirar null dps de colocr else ou default
        if(tagType.equals(Constant.TAG_TYPE_POPULAR)){
            movieResponseCall = service.getPopularMovies(
                    Constant.API_KEY,
                    Constant.LANGUAGE,
                    page);
        }


        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                if(response.body()!=null){
                    movies = response.body().getResults();
                    if(movies==null)
                        Log.d("TAGG", "onResponse: is null");
                }

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

            }
        });

        return movies;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if(mListener!=null) mListener.onFinish(movies);
    }
}
