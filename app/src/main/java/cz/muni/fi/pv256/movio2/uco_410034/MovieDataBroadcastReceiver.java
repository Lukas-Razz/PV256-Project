package cz.muni.fi.pv256.movio2.uco_410034;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.SparseArray;

import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;

/**
 * Created by lukas on 25.11.2017.
 */

public class MovieDataBroadcastReceiver extends BroadcastReceiver {

    public static final String ACTION_RESPONSE_KEY = "movieDataReceivedKey";
    public static final String PARAM_MOVIE_CATEGORIES_KEY = "paramMovieCategoriesKey";

    @Override
    public void onReceive(Context context, Intent intent) {

        Parcelable[] parcelableCategories = intent.getParcelableArrayExtra(PARAM_MOVIE_CATEGORIES_KEY);
        MovieCategory[] categories = new MovieCategory[parcelableCategories.length];
        for(int i=0; i<categories.length; i++) {
            categories[i] = (MovieCategory)parcelableCategories[i];
        }

        SparseArray<MovieCategory> data = new SparseArray<>(categories.length);
        for (int i = 0; i < categories.length; i++) {
            data.put(i, categories[i]);
        }
        MovieDataHolder.INSTANCE.setMovieCategories(data);
    }
}
