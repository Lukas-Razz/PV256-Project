package cz.muni.fi.pv256.movio2.uco_410034;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Db.Dao.MovieDao;
import cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie;

/**
 * Created by lukas on 07.12.2017.
 */

public class MovieLoader extends AsyncTaskLoader<LiveData<List<Movie>>> {

    public static final String TAG = "MovieLoader";

    private Context mContext;
    private MovieDao mMovieDao;

    private LiveData<List<Movie>> mData;

    public MovieLoader(Context context, MovieDao movieDao) {
        super(context);
        mContext = context;
        mMovieDao = movieDao;
    }

    @Override
    public LiveData<List<Movie>> loadInBackground() {
        Log.i(TAG, "loadInBackground");
        mData = mMovieDao.liveGetAll();
        return mData;
    }

    @Override
    protected void onStartLoading() {
        Log.i(TAG, "onStartLoading");
        if(mData != null) {
            deliverResult(mData);
        }
        else {
            forceLoad();
        }
    }
}
