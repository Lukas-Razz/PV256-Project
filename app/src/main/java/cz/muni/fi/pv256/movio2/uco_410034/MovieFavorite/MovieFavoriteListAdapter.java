package cz.muni.fi.pv256.movio2.uco_410034.MovieFavorite;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.MovieList.MovieSelectedListener;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieFavoriteListAdapter extends RecyclerView.Adapter<MovieFavoriteListViewHolder> implements MovieFavoriteButtonClickListener {

    private List<Movie> mMovies;

    private MovieSelectedListener mMovieSelectedListener;

    public MovieFavoriteListAdapter(List<Movie> movies) {
        mMovies = movies;
    }

    @Override
    public MovieFavoriteListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_movie_favorite_movie, parent, false);
        MovieFavoriteListViewHolder viewHolder = new MovieFavoriteListViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MovieFavoriteListViewHolder holder, int position) {
        holder.setMovie(mMovies.get(position));
        holder.setMovieButtonClickListener(this);
    }

    @Override
    public int getItemCount() {
        if(mMovies == null)
            return 0;
        return mMovies.size();
    }

    @Override
    public void onViewRecycled(MovieFavoriteListViewHolder holder) {
        holder.setMovieButtonClickListener(null);
        super.onViewRecycled(holder);
    }

    public void setMovieSelectedListener(MovieSelectedListener movieSelectedListener) {
        this.mMovieSelectedListener = movieSelectedListener;
    }

    @Override
    public void onMovieButtonClick(int movieId) {
        mMovieSelectedListener.onMovieSelected(mMovies.get(movieId));
    }
}
