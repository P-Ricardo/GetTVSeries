package com.example.gettvseries.View.Fragments;


import android.view.View;
import android.widget.ProgressBar;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gettvseries.Adapter.MoviesAdapter;
import com.example.searchlibrary.SearchView;


public class SearchFragment extends Fragment{

    private View view;
    private int currentPage = 1;
    private MoviesAdapter adapter;
    private ProgressBar progressBar;
    private SearchView search;
    private RecyclerView recyclerView;

    public SearchFragment() {
        // Requires empty public constructor
    }

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }


//    @Override
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        view = inflater.inflate(R.layout.fragment_search, container, false);
//        adapter = new MoviesAdapter();
//        progressBar = view.findViewById(R.id.progress);
//        search = view.findViewById(R.id.sv_search);
//
//        return view;
//    }
//
//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//
//        recyclerView = view.findViewById(R.id.rv_search);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.addItemDecoration(new DividerItemDecoration(Objects.requireNonNull(getContext()), RecyclerView.VERTICAL));
//
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(
//                getContext(),
//                RecyclerView.VERTICAL,
//                false
//        );
//
//        recyclerView.setLayoutManager(linearLayoutManager);
//
//        recyclerView.addOnScrollListener(new PaginationScrollListener(
//                linearLayoutManager, new PaginationScrollListener.OnScrollListener() {
//            @Override
//            public void loadMoreItems() {
//
////                loadJson(currentPage, search.);
//            }
//        }
//        ));
//
//        adapter.setOnMovieClickListener(new MoviesAdapter.OnMovieClickListener() {
//            @Override
//            public void onClick(View v, int position) {
//                if(adapter.getItemCount() != 0 && position != -1){
//
//                    loadContent(position);
//                }
//            }
//
//            private void loadContent(int position) {
//
//                Bundle bundleMovieId = new Bundle();
//                bundleMovieId.putString("MovieIdKey", String.valueOf(adapter.get(position).getId()));
//                Intent in = new Intent(getActivity(), MovieDetailsActivity.class);
//                in.putExtras(bundleMovieId);
//                startActivity(in);
//            }
//        });
//
//        recyclerView.setAdapter(adapter);
//
//    }
//
//    private void loadJson(int page, final String query) {
//
//        RetrofitConfig.getService().getMovieSearch(
//                Constant.API_KEY,
//                Constant.LANGUAGE,
//                query,
//                false,
//                page)
//                .enqueue(new Callback<MovieResponse>() {
//
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//
//                if (response.body() != null) {
//
//                    List<Movie> movies = response.body().getResults();
//                    if (movies != null){
//
//                        progressBar.setVisibility(View.GONE);
//                        adapter.insertMovies(movies);
//                    }
//                    else
//                        Log.d("TAG response", "onResponse: is null");
//
//                } else
//                    Toast.makeText(getContext(), "Code: " + response.code(),
//                            Toast.LENGTH_SHORT).show();
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                Toast.makeText(getContext(), "No connection", Toast.LENGTH_SHORT).show();
//                Log.d("Error connection", t.getMessage());
//            }
//        });
//
//        currentPage = page;
//    }
//
//    private void bindSearchView(){
//
//        search.setHint("Search for any movies...");
//        search.setOnTextChangeListener(new SearchView.OnTextChangeListener() {
//            @Override
//            public void onSuggestion(String suggestion) {
//
//                adapter.clear();
//                recyclerView.setVisibility(View.VISIBLE);
//                changeSearch(suggestion);
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
//                adapter.clear();
//                loadJson(1, "");
//                adapter.notifyDataSetChanged();
//                recyclerView.setVisibility(View.GONE);
//
//            }
//        });
//
//    }
//
//    private void changeSearch(String suggestion, int page) {
//        progressBar.setVisibility(View.VISIBLE);
//        loadJson(page, suggestion);
//    }


}
