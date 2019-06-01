package com.example.gettvseries.Adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.gettvseries.Model.Entity.Movie;
import com.example.gettvseries.Model.Services.Api.Constant;
import com.example.gettvseries.R;

import java.util.ArrayList;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    public interface OnMovieClickListener {
        void onClick(View v, int position);
    }

    private List<Movie> movies = new ArrayList<>();
    private OnMovieClickListener onMovieClickListener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list,
                viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        Movie m = movies.get(i);

        viewHolder.title.setText(m.getTitle());
        viewHolder.overview.setText(m.getOverview());
        viewHolder.year.setText(m.getReleaseDate());

        Glide
                .with(viewHolder.itemView)
                .load(Constant.IMAGE_URL + m.getPosterPath())
                .into(viewHolder.imagePoster);
        viewHolder.bindClick(i);
    }

    @Override
    public int getItemCount() {
        return movies != null ? movies.size() : 0;
    }

    public void setOnMovieClickListener(OnMovieClickListener onMovieClickListener) {
        this.onMovieClickListener = onMovieClickListener;
    }

    public void insertMovies(List<Movie> movies) {
        int size = this.movies.size();
        this.movies.addAll(movies);
        notifyItemRangeInserted(size, this.movies.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView imagePoster;
        private TextView title;
        private TextView overview;
        private TextView year;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.movie_poster);
            title = itemView.findViewById(R.id.movie_title);
            overview = itemView.findViewById(R.id.movie_desc);
            year = itemView.findViewById(R.id.movie_year);
        }

        void bindClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onMovieClickListener != null) {
                        onMovieClickListener.onClick(itemView, position);
                    }
                }
            });
        }
    }
}
