package com.example.gettvseries.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gettvseries.Model.Entity.Genre;
import com.example.gettvseries.R;

import java.util.ArrayList;
import java.util.List;

public class GenresAdapter extends RecyclerView.Adapter<GenresAdapter.ViewHolder> {

    public interface OnGenreClickListener {
        void onClick(View v, int position);
    }

    private List<Genre> genres = new ArrayList<>();
    private OnGenreClickListener onGenreClickListener;

    @NonNull
    @Override
    public GenresAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_genre_button,
                viewGroup,
                false));
    }

    @Override
    public void onBindViewHolder(@NonNull GenresAdapter.ViewHolder viewHolder, int i) {

        Genre g = genres.get(i);

        //viewHolder.id.setText(g.getId());
        viewHolder.name.setText(g.getName());

        viewHolder.bindClick(i);
    }

    @Override
    public int getItemCount() {
        return genres != null ? genres.size() : 0;
    }

    public void setOnGenreClickListener(GenresAdapter.OnGenreClickListener onGenreClickListener) {
        this.onGenreClickListener = onGenreClickListener;
    }

    public void insertGenres(List<Genre> genres) {
        int size = this.genres.size();
        this.genres.addAll(genres);
        notifyItemRangeInserted(size, this.genres.size());
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.txt_bnt_genre);
        }

        void bindClick(final int position) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onGenreClickListener != null) {
                        onGenreClickListener.onClick(itemView, position);
                    }
                }
            });
        }
    }

    public Genre get(int position){
        return genres.get(position);
    }




}
