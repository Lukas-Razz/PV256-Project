package cz.muni.fi.pv256.movio2.uco_410034;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.SparseArray;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import cz.muni.fi.pv256.movio2.uco_410034.Api.*;
import cz.muni.fi.pv256.movio2.uco_410034.Api.Model.DiscoverMovies;
import cz.muni.fi.pv256.movio2.uco_410034.Api.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieQuery;

/**
 * Created by lukas on 25.11.2017.
 */

public class MovieDataIntentService extends IntentService {

    private static final String TAG = "MovieDataIntentService";

    public static final String PARAM_QUERY_KEY = "ParamQuery";

    public MovieDataIntentService() {
        super("MovieDataIntentService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.i(TAG, "Handling intent message...");

        final Parcelable[] parcelableQueries = intent.getParcelableArrayExtra(PARAM_QUERY_KEY);
        final MovieQuery[] queries = new MovieQuery[parcelableQueries.length];
        for(int i=0; i<queries.length; i++) {
            queries[i] = (MovieQuery)parcelableQueries[i];
        }

        showNotification("Fetching data", "Application is fetching data from external API...", true);
        Log.i(TAG, "Content: " + queries.toString());

        SparseArray<FutureTask<MovieCategory>> futureCategories = new SparseArray<>(queries.length);
        for(int i=0; i<queries.length; i++) {
            final MovieQuery query = queries[i];
            FutureTask<MovieCategory> futureCategory = new FutureTask<>(new Callable<MovieCategory>() {
                @Override
                public MovieCategory call() throws Exception {
                    MovieAPIClient client = new MovieAPIClient();
                    DiscoverMovies response = client.call(((App) getApplication()).getApiKey(), query);

                    MovieCategory movieCategory = new MovieCategory();
                    movieCategory.setCategoryName(query.getLabel());
                    cz.muni.fi.pv256.movio2.uco_410034.Model.Movie[] movieList = new cz.muni.fi.pv256.movio2.uco_410034.Model.Movie[6];
                    for (int j = 0; j < response.results.length; j++) {
                        if (j == 6) {
                            break;
                        }
                        cz.muni.fi.pv256.movio2.uco_410034.Model.Movie movie = new cz.muni.fi.pv256.movio2.uco_410034.Model.Movie();
                        movie.setTitle(response.results[j].title);
                        movie.setPopularity((float) ((response.results[j].vote_average) / 2));
                        movie.setBackdrop(response.results[j].backdrop_path);
                        movie.setCoverPath(response.results[j].poster_path);
                        movie.setReleaseDate(Long.valueOf(response.results[j].release_date.split("-")[0]));
                        movie.setDescription(response.results[j].overview);
                        movieList[j] = movie;
                    }
                    movieCategory.setMovieList(movieList);
                    return movieCategory;
                }
            });
            futureCategories.put(i, futureCategory);
            futureCategory.run();
        }

        MovieCategory[] movieCategories = new MovieCategory[futureCategories.size()];
        try {
            for (int i = 0; i < futureCategories.size(); i++) {
                Log.i(TAG,"Getting task #" + String.valueOf(i) + "...");
                movieCategories[i] = futureCategories.get(i).get();
            }
            showNotification("Fetching complete", "Application has fetched all needed data.", false);
            Intent broadcastIntent = new Intent();
            broadcastIntent.setAction(MovieDataBroadcastReceiver.ACTION_RESPONSE_KEY);
            broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);
            broadcastIntent.putExtra(MovieDataBroadcastReceiver.PARAM_MOVIE_CATEGORIES_KEY, movieCategories);
            sendBroadcast(broadcastIntent);
        }
        catch (ExecutionException | InterruptedException ex) {
            Log.e(TAG, "Problem with execution: " + ex);
            showNotification("Fetching error", "Application has encountered an error, data are not available.", false);
        }
    }

    private void showNotification(@NonNull String title, @NonNull String text, boolean progress) {
        Intent intent = new Intent(this, MainActivity.class);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, 0);
        Notification.Builder notificationBuilder  = new Notification.Builder(this)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pIntent)
                .setAutoCancel(true);
        if(progress)
            notificationBuilder.setProgress(0,0,true);

        Notification notification = notificationBuilder.build();

        NotificationManager notificationManager =  (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(0, notification);
    }
}
