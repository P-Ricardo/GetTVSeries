package com.example.gettvseries;

import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class GenreButtonsAdapter extends RecyclerView.Adapter<GenreButtonsAdapter.ViewHolder>{

    private List<String> mGenres;
    private OnGenreListener mListener;

    public GenreButtonsAdapter(List<String> genres) {
        this.mGenres = genres;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.genre_buttons_details,
                                viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        viewHolder.textGenre.setText(mGenres.get(i));
        viewHolder.bindClick(i);
    }

    @Override
    public int getItemCount() {
        return (mGenres!=null ? mGenres.size() : 0);
    }

    public void setOnGenreListener(OnGenreListener onGenreListener) {
        this.mListener = onGenreListener;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        AppCompatTextView textGenre;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textGenre = itemView.findViewById(R.id.textGenre);

        }

        void bindClick(final int position){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null){
                        mListener.onGenreClick(v, position);
                    }
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if(mListener!=null){
                        mListener.onLongGenreClick(v, position);
                    }
                    return true;
                }
            });

        }
    }
}