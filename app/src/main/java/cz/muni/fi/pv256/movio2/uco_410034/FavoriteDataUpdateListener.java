package cz.muni.fi.pv256.movio2.uco_410034;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;

/**
 * Created by lukas on 07.12.2017.
 */

public interface FavoriteDataUpdateListener {
    public void onDataUpdate(List<Movie> movies);
}
