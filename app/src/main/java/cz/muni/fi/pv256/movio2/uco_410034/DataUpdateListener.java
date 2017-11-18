package cz.muni.fi.pv256.movio2.uco_410034;

import android.util.SparseArray;

import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;

/**
 * Created by lukas on 18.11.2017.
 */

public interface DataUpdateListener {
    public void onDataUpdate(SparseArray<MovieCategory> movieCategories);
}
