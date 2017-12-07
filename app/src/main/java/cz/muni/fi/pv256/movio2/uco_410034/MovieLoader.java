package cz.muni.fi.pv256.movio2.uco_410034;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Db.Dao.MovieDao;
import cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie;

/**
 * Created by lukas on 07.12.2017.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>>{

    public static final String TAG = "MovieLoader";

    private Context mContext;
    private MovieDao mMovieDao;

    private List<Movie> mData;

    public MovieLoader(Context context, MovieDao movieDao) {
        super(context);
        mContext = context;
        mMovieDao = movieDao;
    }

    @Override
    public List<Movie> loadInBackground() {
        mData = mMovieDao.getAll();
        return mData != null ? mData : new ArrayList<Movie>();
    }

    @Override
    protected void onStartLoading() {
        if(mData != null) {
            deliverResult(mData);
        }
        else {
            forceLoad();
        }
    }
}
