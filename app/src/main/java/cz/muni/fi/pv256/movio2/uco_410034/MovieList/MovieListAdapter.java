package cz.muni.fi.pv256.movio2.uco_410034.MovieList;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListViewHolder> implements MovieButtonClickListener {

    private SparseArray<MovieCategory> mMovieCategories;

    private MovieSelectedListener mMovieSelectedListener;

    public MovieListAdapter(SparseArray<MovieCategory> movieCategories) {
        mMovieCategories = movieCategories;
    }

    @Override
    public MovieListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_list_movie_category, parent, false);
        MovieListViewHolder viewHolder = new MovieListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieListViewHolder holder, int position) {
        holder.setMovieCategory(mMovieCategories.get(position));
        holder.setMovieButtonClickListener(this);
    }

    @Override
    public int getItemCount() {
        if(mMovieCategories == null)
            return 0;
        return mMovieCategories.size();
    }

    @Override
    public void onViewRecycled(MovieListViewHolder holder) {
        holder.setMovieButtonClickListener(null);
        super.onViewRecycled(holder);
    }

    public void setMovieSelectedListener(MovieSelectedListener movieSelectedListener) {
        this.mMovieSelectedListener = movieSelectedListener;
    }

    @Override
    public void onMovieButtonClick(int movieId, int categoryId) {
        mMovieSelectedListener.onMovieSelected(mMovieCategories.get(categoryId).getMovieList()[movieId]);
    }
}
