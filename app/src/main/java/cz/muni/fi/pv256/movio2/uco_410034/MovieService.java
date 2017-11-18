package cz.muni.fi.pv256.movio2.uco_410034;

import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import cz.muni.fi.pv256.movio2.uco_410034.Model.Api.DiscoverMovies;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by lukas on 17.11.2017.
 */

public class MovieService {

    private static final String TAG = "MovieService";

    private OkHttpClient mClient = new OkHttpClient();
    private String mApiKey;


    public MovieService(String apiKey) {
        this.mApiKey = apiKey;
    }

    public void getMostPopularMovies(Locale language, Date from, Date to, String label) {

        String requestTemplate = "https://api.themoviedb.org/3/discover/movie?api_key=%s&language=%s&sort_by=popularity.desc&include_adult=false&include_video=false&page=1&primary_release_date.gte=%s&primary_release_date.lte=%s";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String request = String.format(requestTemplate, this.mApiKey, language.getDisplayLanguage(), dateFormat.format(from), dateFormat.format(to));

        Log.d(TAG, "Request:  " + request);
        get(request, label);
    }

    public void getMostPopularMovies(String rating, String label) {

        String requestTemplate = "https://api.themoviedb.org/3/discover/movie?api_key=%s&certification_country=US&certification=%s&sort_by=vote_average.desc";

        String request = String.format(requestTemplate, this.mApiKey, rating);

        Log.d(TAG, "Request:  " + request);
        get(request, label);
    }

    void get(String url, final String label) {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "Failed to execute " + call.request(), e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    throw new IOException("Unexpected code " + response);
                }
                String responseString = response.body().string();
                Log.i(TAG, responseString);

                DiscoverMovies discoverMovies = new Gson().fromJson(responseString, DiscoverMovies.class);
                MovieCategory movieCategory = new MovieCategory();
                movieCategory.setCategoryName(label);
                Movie[] movieList = new Movie[6];
                for(int i=0; i<discoverMovies.results.length; i++) {
                    if (i == 6) {
                        break;
                    }
                    Movie movie = new Movie();
                    movie.setTitle(discoverMovies.results[i].title);
                    movie.setPopularity((float) ((discoverMovies.results[i].vote_average) / 2));
                    movie.setBackdrop(discoverMovies.results[i].backdrop_path);
                    movie.setCoverPath(discoverMovies.results[i].poster_path);
                    movie.setReleaseDate(Long.valueOf(discoverMovies.results[i].release_date.split("-")[0]));
                    movieList[i] = movie;
                }
                movieCategory.setMovieList(movieList);
                DataHolder.INSTANCE.putMovieCategory(movieCategory);
            }
        });
    }
}
