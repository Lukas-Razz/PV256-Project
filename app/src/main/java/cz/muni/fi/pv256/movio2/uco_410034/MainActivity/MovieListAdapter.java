package cz.muni.fi.pv256.movio2.uco_410034.MainActivity;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> {

    private Movie[] mMovieList;

    public MovieListAdapter(Movie[] movieList) {
        mMovieList = movieList;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main_movie, parent, false);
        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return mMovieList.length;
    }
}
