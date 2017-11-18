package cz.muni.fi.pv256.movio2.uco_410034;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.media.MediaBrowserCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import butterknife.BindBool;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.MovieDetail.MovieDetailFragment;
import cz.muni.fi.pv256.movio2.uco_410034.MovieList.MovieListFragment;
import cz.muni.fi.pv256.movio2.uco_410034.MovieList.MovieSelectedListener;

public class MainActivity extends AppCompatActivity implements MovieSelectedListener{

    @BindView(R.id.rootLayout) DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.contentLayout) FrameLayout mContentLayout;
    @Nullable @BindView(R.id.tabletContentLayout) ConstraintLayout mTabletContentLayout;
    @Nullable @BindView(R.id.leftContentLayout) FrameLayout mLeftContentLayout;
    @Nullable @BindView(R.id.rightContentLayout) FrameLayout mRightContentLayout;
    @BindView(R.id.emptyView) ViewStub mEmptyView;

    @BindString(R.string.fragment_movie_list_tag) String fragmentMovieListTag;
    @BindString(R.string.fragment_movie_detail_tag) String fragmentMovieDetailTag;
    @BindString(R.string.shared_pref_name) String sharedPrefName;
    @BindString(R.string.shared_pref_style_key) String sharedPrefStyleKey;
    @BindString(R.string.bundle_movie_categories_key) String mBundleMovieCategoriesKey;
    @BindString(R.string.bundle_movie_key) String mBundleMovieKey;
    @BindBool(R.bool.isTablet) boolean mIsTablet;
    @BindString(R.string.empty_list_no_connection) String mEmptyListNoConnection;
    @BindString(R.string.empty_list_no_data) String mEmptyListNoData;

    private ActionBarDrawerToggle mDrawerToggle;

    private SparseArray<MovieCategory> mMovieCategories = new SparseArray<>(2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE);
        if(BuildConfig.FLAVOR.equals("alternative")) {
            setTheme(prefs.getInt(getString(R.string.shared_pref_style_key), R.style.AppTheme_1));
        }
        else {
            setTheme(prefs.getInt(getString(R.string.shared_pref_style_key), R.style.AppTheme_2));
        }
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        if (savedInstanceState != null) {
            mMovieCategories = savedInstanceState.getSparseParcelableArray(mBundleMovieCategoriesKey);
        }
        else {
            fillDummyData();
        }

        MovieService movieService = new MovieService(((App)getApplication()).getApiKey());
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();
        movieService.getMostPopularMovies(new Locale("en-US"), monthAgo, now, "Current Most Popular Movies");

        setUpDrawer();

        if(isDataEmpty()) {
            setUpEmptyView();
        }
        else {
            setUpFragments(savedInstanceState);
        }
    }

    @Override
    public void onBackPressed() {
        if(!mIsTablet && getSupportFragmentManager().findFragmentByTag(fragmentMovieDetailTag) != null) {
            Bundle bundle = new Bundle();
            bundle.putSparseParcelableArray(mBundleMovieCategoriesKey, mMovieCategories);
            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setArguments(bundle);
            movieListFragment.setMovieSelectedListener(this);
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, movieListFragment, fragmentMovieListTag).commit();
            mDrawerToggle.setDrawerIndicatorEnabled(true);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        switch (item.getItemId()) {
            case android.R.id.home:
                Bundle bundle = new Bundle();
                bundle.putSparseParcelableArray(mBundleMovieCategoriesKey, mMovieCategories);
                MovieListFragment movieListFragment = new MovieListFragment();
                movieListFragment.setArguments(bundle);
                movieListFragment.setMovieSelectedListener(this);
                getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, movieListFragment, fragmentMovieListTag).commit();
                mDrawerToggle.setDrawerIndicatorEnabled(true);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSparseParcelableArray(mBundleMovieCategoriesKey, mMovieCategories);
    }

    private void setUpDrawer() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open_drawer, R.string.close_drawer) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    private void setUpFragments(Bundle savedInstanceState) {
        if(savedInstanceState == null) {

            Bundle bundle;
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (mIsTablet) {
                MovieListFragment movieListFragment = new MovieListFragment();
                bundle = new Bundle();
                bundle.putSparseParcelableArray(mBundleMovieCategoriesKey, mMovieCategories);
                movieListFragment.setArguments(bundle);
                movieListFragment.setMovieSelectedListener(this);
                fragmentTransaction.add(R.id.leftContentLayout, movieListFragment, fragmentMovieListTag);
                MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
                bundle = new Bundle();
                bundle.putParcelable(mBundleMovieKey, mMovieCategories.get(0).getMovieList()[0]);
                movieDetailFragment.setArguments(bundle);
                fragmentTransaction.add(R.id.rightContentLayout, movieDetailFragment, fragmentMovieDetailTag);
            } else {
                MovieListFragment movieListFragment = new MovieListFragment();
                bundle = new Bundle();
                bundle.putSparseParcelableArray(mBundleMovieCategoriesKey, mMovieCategories);
                movieListFragment.setArguments(bundle);
                movieListFragment.setMovieSelectedListener(this);
                fragmentTransaction.add(R.id.contentLayout, movieListFragment, fragmentMovieListTag);
            }
            fragmentTransaction.commit();
        }
        else {
            Fragment movieListFragment = getSupportFragmentManager().findFragmentByTag(fragmentMovieListTag);
            if(movieListFragment != null)
                ((MovieListFragment)movieListFragment).setMovieSelectedListener(this);
        }
    }

    @OnClick(R.id.drawerChangeStyleButton)
    public void drawerChangeStyleButtonClick(View view) {
        SharedPreferences prefs = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        int themeId;
        if(BuildConfig.FLAVOR.equals("alternative")) {
            themeId = prefs.getInt(sharedPrefStyleKey, R.style.AppTheme_2);
        }
        else {
            themeId = prefs.getInt(sharedPrefStyleKey, R.style.AppTheme_1);
        }
        switch (themeId) {
            case R.style.AppTheme_2:
                prefs.edit().putInt(sharedPrefStyleKey, R.style.AppTheme_1).apply();
                break;
            case R.style.AppTheme_1:
            default:
                prefs.edit().putInt(sharedPrefStyleKey, R.style.AppTheme_2).apply();
                break;
        }
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void fillDummyData() {
        Random random = new Random();
        for (int i = 0; i < 2; i++) {
            String name = "Dummy Category #" + i;
            MovieCategory category = new MovieCategory();
            category.setCategoryName(name);
            Movie[] movies = new Movie[6];
            for (int j = 0; j < 6; j++) {
                Movie movie = new Movie();
                movie.setTitle("DummyMovie #" + i + "." + j);
                movie.setReleaseDate(random.nextInt(2018-1950) + 1950);
                movie.setPopularity(random.nextFloat() * 5);
                movies[j] = movie;
            }
            category.setMovieList(movies);
            mMovieCategories.put(i, category);
        }
    }

    @Override
    public void onMovieSelected(Movie movie) {

        MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable(mBundleMovieKey, movie);
        movieDetailFragment.setArguments(bundle);
        if(mIsTablet) {
            getSupportFragmentManager().beginTransaction().replace(R.id.rightContentLayout, movieDetailFragment, fragmentMovieDetailTag).commit();
        }
        else {
            getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout, movieDetailFragment, fragmentMovieDetailTag).commit();
            mDrawerToggle.setDrawerIndicatorEnabled(false);
        }
    }

    private boolean isDataEmpty() {
        if(mMovieCategories == null)
            return true;
        for (int i = 0; i<mMovieCategories.size(); i++) {
            if (mMovieCategories.get(i) != null)
                return false;
        }
        return true;
    }

    private void setUpEmptyView() {
        String emptyLabelText = mEmptyListNoData;
        ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null == activeNetwork) {
            emptyLabelText = mEmptyListNoConnection;
        }
        if(mIsTablet) {
            mLeftContentLayout.setVisibility(View.GONE);
            mRightContentLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        else {
            mContentLayout.setVisibility(View.GONE);
            mEmptyView.setVisibility(View.VISIBLE);
        }
        ((TextView) findViewById(R.id.emptyListLabel)).setText(emptyLabelText);
    }
}
