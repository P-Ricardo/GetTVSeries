package com.example.gettvseries.View.Fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gettvseries.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class MyListFragment extends Fragment {


    public MyListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_my_list, container, false);

        return view;
    }

}
