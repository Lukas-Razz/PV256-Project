package cz.muni.fi.pv256.movio2.uco_410034.MovieList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 17.10.2017.
 */

public class MovieListFragment extends Fragment implements MovieSelectedListener{

    @BindView(R.id.movieListView) RecyclerView mMovieListView;

    @BindString(R.string.bundle_movie_categories_key) String mBundleMovieCategoriesKey;

    private MovieSelectedListener mMovieSelectedListener;
    private MovieListAdapter movieListAdapter;

    private SparseArray<MovieCategory> mMovieCategories;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list , container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        mMovieCategories = bundle.getSparseParcelableArray(mBundleMovieCategoriesKey);

        movieListAdapter = new MovieListAdapter(mMovieCategories);
        movieListAdapter.setMovieSelectedListener(this);
        RecyclerView.LayoutManager movieListLayoutManager = new LinearLayoutManager(inflater.getContext());
        mMovieListView.setLayoutManager(movieListLayoutManager);
        mMovieListView.setAdapter(movieListAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        mMovieSelectedListener = null;
        movieListAdapter.setMovieSelectedListener(null);
        super.onDestroy();
    }

    public void setMovieSelectedListener(MovieSelectedListener movieSelectedListener) {
        this.mMovieSelectedListener = movieSelectedListener;
    }

    @Override
    public void onMovieSelected(Movie movie) {
        mMovieSelectedListener.onMovieSelected(movie);
    }
}
