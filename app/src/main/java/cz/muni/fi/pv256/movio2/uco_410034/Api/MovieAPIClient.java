package cz.muni.fi.pv256.movio2.uco_410034.Api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.concurrent.FutureTask;

import cz.muni.fi.pv256.movio2.uco_410034.Api.Model.DiscoverMovies;
import cz.muni.fi.pv256.movio2.uco_410034.Api.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieQuery;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lukas on 25.11.2017.
 */

public class MovieAPIClient {

    private static final String TAG = "MovieAPIClient";

    private static final String BASE_URL = "https://api.themoviedb.org/3/";

    public DiscoverMovies discoverMovies(String apiKey, MovieQuery query) throws IOException {

        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String language = query.getLanguage() != null ? query.getLanguage().getDisplayLanguage() : null;
        String from = query.getFrom() != null ? dateFormat.format(query.getFrom()) : null;
        String to = query.getTo() != null ? dateFormat.format(query.getTo()) : null;
        String certificationCountry = query.getRating() != null ? "US" : null;
        String certification = query.getRating() != null ? query.getRating() : null;

        Call<DiscoverMovies> call = movieAPI.discoverMovies(apiKey, "popularity.desc", "false", "false", language, from, to, certificationCountry, certification);
        try {
            Log.i(TAG, call.request().toString());
            Response<DiscoverMovies> response = call.execute();
            Log.i(TAG, response.toString());
            if(response.isSuccessful() && response.body().results.length > 0) {
                return response.body();
            }
            else {
                if(response.errorBody() != null)
                    throw new IOException("API discoverMovies failed" + response.errorBody().toString());
                else
                    throw new IOException("API discoverMovies failed");
            }
        }
        catch (IOException ex) {
            throw ex;
        }
    }

    public Movie getMovie(String apiKey, int id) throws IOException {
        Gson gson = new GsonBuilder().setLenient().create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MovieAPI movieAPI = retrofit.create(MovieAPI.class);


        Call<Movie> call = movieAPI.getMovie(apiKey, id);
        try {
            Log.i(TAG, call.request().toString());
            Response<Movie> response = call.execute();
            Log.i(TAG, response.toString());
            if(response.isSuccessful() && response.body() != null) {
                return response.body();
            }
            else {
                if(response.errorBody() != null)
                    throw new IOException("API getMovie failed" + response.errorBody().toString());
                else
                    throw new IOException("API getMovie failed");
            }
        }
        catch (IOException ex) {
            throw ex;
        }
    }
}
