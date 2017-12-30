package cz.muni.fi.pv256.movio2.uco_410034.MovieFavorite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.FavoriteDataUpdateListener;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.MovieDataHolder;
import cz.muni.fi.pv256.movio2.uco_410034.MovieList.MovieSelectedListener;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 17.10.2017.
 */

public class MovieFavoriteListFragment extends Fragment implements MovieSelectedListener, FavoriteDataUpdateListener {

    private static final String TAG = "MovieFavoriteListFragme";

    @BindView(R.id.movieListView) RecyclerView mMovieListView;

    private MovieSelectedListener mMovieSelectedListener;
    private MovieFavoriteListAdapter mMovieFavoriteListAdapter;

    private List<Movie> mMovies;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list , container, false);
        ButterKnife.bind(this, view);

        mMovies = MovieDataHolder.INSTANCE.getFavoriteMovies();

        mMovieFavoriteListAdapter = new MovieFavoriteListAdapter(mMovies);
        mMovieFavoriteListAdapter.setMovieSelectedListener(this);
        RecyclerView.LayoutManager movieListLayoutManager = new LinearLayoutManager(inflater.getContext());
        mMovieListView.setLayoutManager(movieListLayoutManager);
        mMovieListView.setAdapter(mMovieFavoriteListAdapter);

        return view;
    }

    @Override
    public void onDestroy() {
        mMovieSelectedListener = null;
        mMovieFavoriteListAdapter.setMovieSelectedListener(null);
        MovieDataHolder.INSTANCE.unsubscribeFavoriteDataUpdateListener(this);
        super.onDestroy();
    }

    public void setMovieSelectedListener(MovieSelectedListener movieSelectedListener) {
        this.mMovieSelectedListener = movieSelectedListener;
    }

    @Override
    public void onMovieSelected(Movie movie) {
        mMovieSelectedListener.onMovieSelected(movie);
    }

    @Override
    public void onDataUpdate(final List<Movie> movies) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Updating Data...");
                for(int i=0; i<movies.size(); i++) {
                    mMovies.add(i, movies.get(i));
                }
                mMovieFavoriteListAdapter.notifyDataSetChanged();
            }
        });
    }
}
