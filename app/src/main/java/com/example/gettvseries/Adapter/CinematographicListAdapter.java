package com.example.gettvseries.Adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.gettvseries.R;
import com.example.gettvseries.Model.Services.Api.Constant;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.annotations.NonNull;

public class CinematographicListAdapter extends RecyclerView.Adapter<CinematographicListAdapter.ViewHolder> {


    private List<Cinematographic> mCinematographics;
    private OnItemClickListener mClickListener;

    public CinematographicListAdapter(List<Cinematographic> cinematographics) {
        this.mCinematographics = cinematographics;
    }

    public CinematographicListAdapter() {
        this.mCinematographics = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new ViewHolder(LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_poster,viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide
                .with(viewHolder.itemView.getContext())
                .load(Constant.BASE_URL + mCinematographics.get(i).getPosterPath())
                .into(viewHolder.iv_poster);
        Log.d("MoviesItems",Constant.BASE_URL + mCinematographics.get(i).getPosterPath());
        viewHolder.bindClick();

    }

    public void setClickListener(OnItemClickListener onItemClickListener) {
        this.mClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return (mCinematographics !=null)? mCinematographics.size():0;
    }

    public void insertItems(List<Cinematographic> ts) {
        int size = mCinematographics.size();
        mCinematographics.addAll(ts);
        notifyItemRangeInserted(size, mCinematographics.size());
    }

    public Cinematographic get(int position) {
        return mCinematographics.get(position);
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_poster;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_poster = itemView.findViewById(R.id.movie_poster_item);
        }
        void bindClick() {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mClickListener!=null)
                        mClickListener.onClick(v, getAdapterPosition());
                }
            });
        }
    }


}
