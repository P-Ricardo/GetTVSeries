package com.example.gettvseries.Adapter;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

public class PaginationScrollListener extends RecyclerView.OnScrollListener {

    public interface OnScrollListener{

        void loadMoreItems();
    }

    private boolean mIsScrolling;
    private LinearLayoutManager mLayoutManager;
    private PaginationScrollListener.OnScrollListener mListener;

    public PaginationScrollListener(LinearLayoutManager layoutManager, PaginationScrollListener.OnScrollListener onScrollListener) {
        this.mLayoutManager = layoutManager;
        mListener = onScrollListener;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        mIsScrolling = true;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);


        int visibleItemCount = mLayoutManager.getChildCount();
        int totalItemCount = mLayoutManager.getItemCount();
        int firstVisibleItemPosition = mLayoutManager.findFirstVisibleItemPosition();

        if (mIsScrolling &&
                (visibleItemCount + firstVisibleItemPosition + 10) >= totalItemCount &&
                firstVisibleItemPosition >= 0) {

            mIsScrolling = false;

            if(mListener!=null)
                mListener.loadMoreItems();
        }

    }
}
