package cz.muni.fi.pv256.movio2.uco_410034;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;

/**
 * Created by lukas on 18.11.2017.
 */

public enum MovieDataHolder {
    INSTANCE;

    private List<DataUpdateListener> mDataUpdateListeners = new ArrayList<>(2);
    private SparseArray<MovieCategory> mMovieCategories = new SparseArray<>(2);

    public SparseArray<MovieCategory> getMovieCategories() {
        return mMovieCategories;
    }

    public void setMovieCategories(SparseArray<MovieCategory> movieCategories) {
        this.mMovieCategories = movieCategories;
        for(int i=0; i<mDataUpdateListeners.size(); i++)
            mDataUpdateListeners.get(i).onDataUpdate(this.mMovieCategories);
    }

    public boolean isDataEmpty() {
        if(mMovieCategories == null)
            return true;
        for (int i = 0; i<mMovieCategories.size(); i++) {
            if (mMovieCategories.get(i) != null)
                return false;
        }
        return true;
    }

    public void subscribeDataUpdateListener(DataUpdateListener dataUpdateListener) {
        mDataUpdateListeners.add(dataUpdateListener);
    }

    public void unsubscribeDataUpdateListener(DataUpdateListener dataUpdateListener) {
        mDataUpdateListeners.remove(dataUpdateListener);
    }
}
