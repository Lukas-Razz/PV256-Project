package cz.muni.fi.pv256.movio2.uco_410034;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;

/**
 * Created by lukas on 18.11.2017.
 */

public enum MovieDataHolder {
    INSTANCE;

    private List<DiscoverDataUpdateListener> mDiscoverDataUpdateListeners = new ArrayList<>(2);
    private SparseArray<MovieCategory> mMovieCategories = new SparseArray<>(2);
    private List<FavoriteDataUpdateListener> mFavoriteDataUpdateListeners = new ArrayList<>(2);
    private List<Movie> mFavoriteMovies = new ArrayList<>();

    public SparseArray<MovieCategory> getMovieCategories() {
        return mMovieCategories;
    }

    public void setMovieCategories(SparseArray<MovieCategory> movieCategories) {
        this.mMovieCategories = movieCategories;
        for(int i=0; i<mDiscoverDataUpdateListeners.size(); i++)
            mDiscoverDataUpdateListeners.get(i).onDataUpdate(this.mMovieCategories);
    }

    public List<Movie> getFavoriteMovies() {
        return mFavoriteMovies;
    }

    public void setFavoriteMovies(List<Movie> favoriteMovies) {
        this.mFavoriteMovies = favoriteMovies;
        for(int i=0; i<mFavoriteDataUpdateListeners.size(); i++)
            mFavoriteDataUpdateListeners.get(i).onDataUpdate(this.mFavoriteMovies);
    }

    public boolean isDiscoverDataEmpty() {
        if(mMovieCategories == null)
            return true;
        for (int i = 0; i<mMovieCategories.size(); i++) {
            if (mMovieCategories.get(i) != null)
                return false;
        }
        return true;
    }

    public void subscribeDiscoverDataUpdateListener(DiscoverDataUpdateListener dataUpdateListener) {
        mDiscoverDataUpdateListeners.add(dataUpdateListener);
    }

    public void unsubscribeDiscoverDataUpdateListener(DiscoverDataUpdateListener dataUpdateListener) {
        mDiscoverDataUpdateListeners.remove(dataUpdateListener);
    }

    public void subscribeFavoriteDataUpdateListener(FavoriteDataUpdateListener dataUpdateListener) {
        mFavoriteDataUpdateListeners.add(dataUpdateListener);
    }

    public void unsubscribeFavoriteDataUpdateListener(FavoriteDataUpdateListener dataUpdateListener) {
        mFavoriteDataUpdateListeners.remove(dataUpdateListener);
    }
}
