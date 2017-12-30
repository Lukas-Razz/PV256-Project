package cz.muni.fi.pv256.movio2.uco_410034.Sync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SyncRequest;
import android.content.SyncResult;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv256.movio2.uco_410034.Api.MovieAPIClient;
import cz.muni.fi.pv256.movio2.uco_410034.App;
import cz.muni.fi.pv256.movio2.uco_410034.Db.Dao.MovieDao;
import cz.muni.fi.pv256.movio2.uco_410034.MainActivity;
import cz.muni.fi.pv256.movio2.uco_410034.Mapper.MovieMapper;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.MovieDataHolder;
import cz.muni.fi.pv256.movio2.uco_410034.R;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by lukas on 10.12.2017.
 */

public class FavoriteMovieSyncAdapter extends AbstractThreadedSyncAdapter {

    private static final String TAG = "FavoriteMovieSyncAdapte";

    public static final int SYNC_INTERVAL = 60 * 60 * 24;
    public static final int SYNC_FLEXTIME = SYNC_INTERVAL / 3;

    private MovieDao mMovieDao;
    private String mApiKey;

    public FavoriteMovieSyncAdapter(Context context, boolean autoInitialize, MovieDao movieDao, String apiKey) {
        super(context, autoInitialize);
        this.mMovieDao = movieDao;
        this.mApiKey = apiKey;
    }

    public static void configurePeriodicSync(Context context, int syncInterval, int flexTime) {
        Account account = getSyncAccount(context);
        String authority = context.getString(R.string.sync_content_authority);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SyncRequest request = new SyncRequest.Builder()
                    .syncPeriodic(syncInterval, flexTime)
                    .setSyncAdapter(account, authority)
                    .setExtras(Bundle.EMPTY)
                    .build();
            ContentResolver.requestSync(request);
        } else {
            ContentResolver.addPeriodicSync(account, authority, Bundle.EMPTY, syncInterval);
        }
    }

    public static void syncImmediately(Context context) {
        Bundle bundle = new Bundle();
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
        bundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
        ContentResolver.requestSync(getSyncAccount(context), context.getString(R.string.sync_content_authority), bundle);
    }

    public static void initializeSyncAdapter(Context context) {
        getSyncAccount(context);
    }

    /*
        Fake account
     */
    public static Account getSyncAccount(Context context) {
        AccountManager accountManager = (AccountManager) context.getSystemService(Context.ACCOUNT_SERVICE);
        Account newAccount = new Account(context.getString(R.string.app_name), context.getString(R.string.sync_account_type));

        if (null == accountManager.getPassword(newAccount)) {
            if (!accountManager.addAccountExplicitly(newAccount, "", null)) {
                return null;
            }
            ContentResolver.setIsSyncable(newAccount, context.getString(R.string.sync_content_authority), 1);
            onAccountCreated(newAccount, context);
        }
        return newAccount;
    }

    private static void onAccountCreated(Account newAccount, Context context) {
        FavoriteMovieSyncAdapter.configurePeriodicSync(context, SYNC_INTERVAL, SYNC_FLEXTIME);
        ContentResolver.setSyncAutomatically(newAccount, context.getString(R.string.sync_content_authority), true);
        syncImmediately(context);
    }

    @Override
    public void onPerformSync(Account account, Bundle extras, String authority, ContentProviderClient provider, SyncResult syncResult) {
        Log.i(TAG, "Syncing...");
        MovieAPIClient movieAPIClient = new MovieAPIClient();
        List<Movie> favoriteMovies = MovieDataHolder.INSTANCE.getFavoriteMovies();
        boolean isOutOfDate = false;
        for(Movie movie : favoriteMovies) {
            try {
                Movie newMovie = MovieMapper.INSTANCE.apiMovieToMovie(movieAPIClient.getMovie(mApiKey, (int) movie.getId()));
                if (!movie.equals(newMovie)) {
                    Log.i(TAG, "Movie " + movie.toString() + " is out of date");
                    mMovieDao.update(MovieMapper.INSTANCE.movieToDbMovie(newMovie));
                    isOutOfDate = true;
                } else {
                    Log.i(TAG, "Movie " + movie.toString() + " is up to date");
                }
            } catch (IOException ex) {
                Log.e(TAG, ex.toString());
            }
        }
        if(isOutOfDate)
            showNotification("Movie updated", "Favorite movies were updated.");
    }

    //TODO: duplicity, see MovieDataIntentService
    private void showNotification(@NonNull String title, @NonNull String text) {
        Intent intent = new Intent(getContext(), MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(getContext(), 0, intent, 0);
        Notification.Builder notificationBuilder  = new Notification.Builder(getContext())
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true);

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager =  (NotificationManager)getContext().getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
