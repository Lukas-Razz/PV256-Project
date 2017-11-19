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
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

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

public class MainActivity extends AppCompatActivity implements MovieSelectedListener, DataUpdateListener{

    private static final String TAG = "MainActivity";

    @BindView(R.id.rootLayout) DrawerLayout mDrawerLayout;
    @Nullable @BindView(R.id.contentLayout) FrameLayout mContentLayout;
    @Nullable @BindView(R.id.tabletContentLayout) ConstraintLayout mTabletContentLayout;
    @Nullable @BindView(R.id.leftContentLayout) FrameLayout mLeftContentLayout;
    @Nullable @BindView(R.id.rightContentLayout) FrameLayout mRightContentLayout;
    @BindView(R.id.emptyViewStub) ViewStub mEmptyViewStub;

    @BindString(R.string.fragment_movie_list_tag) String fragmentMovieListTag;
    @BindString(R.string.fragment_movie_detail_tag) String fragmentMovieDetailTag;
    @BindString(R.string.shared_pref_name) String sharedPrefName;
    @BindString(R.string.shared_pref_style_key) String sharedPrefStyleKey;
    @BindString(R.string.bundle_movie_key) String mBundleMovieKey;
    @BindBool(R.bool.isTablet) boolean mIsTablet;
    @BindString(R.string.empty_list_no_connection) String mEmptyListNoConnection;
    @BindString(R.string.empty_list_no_data) String mEmptyListNoData;

    private ActionBarDrawerToggle mDrawerToggle;

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

        DataHolder.INSTANCE.subscribeDataUpdateListener(this);

        MovieAPIClient movieAPIClient = new MovieAPIClient(((App)getApplication()).getApiKey());
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MONTH, -1);
        Date monthAgo = calendar.getTime();
        movieAPIClient.getMostPopularMovies(new Locale("en-US"), monthAgo, now, "Popular in theatres");
        movieAPIClient.getMostPopularMovies("R", "Most popular 'R' rated");

        setUpDrawer();

        if(DataHolder.INSTANCE.isDataEmpty()) {
            setUpEmptyView();
        }
        else {
            setUpFragments(savedInstanceState);
        }
    }

    @Override
    protected void onDestroy() {
        DataHolder.INSTANCE.unsubscribeDataUpdateListener(this);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        if(!mIsTablet && getSupportFragmentManager().findFragmentByTag(fragmentMovieDetailTag) != null) {
            MovieListFragment movieListFragment = new MovieListFragment();
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
                MovieListFragment movieListFragment = new MovieListFragment();
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
            setUpFragments();
        }
        else {
            Fragment movieListFragment = getSupportFragmentManager().findFragmentByTag(fragmentMovieListTag);
            if(movieListFragment != null)
                ((MovieListFragment)movieListFragment).setMovieSelectedListener(this);
        }
    }

    private void setUpFragments() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (mIsTablet) {
            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setMovieSelectedListener(this);
            fragmentTransaction.add(R.id.leftContentLayout, movieListFragment, fragmentMovieListTag);
            MovieDetailFragment movieDetailFragment = new MovieDetailFragment();
            Bundle bundle = new Bundle();
            bundle.putParcelable(mBundleMovieKey, DataHolder.INSTANCE.getMovieCategories().get(0).getMovieList()[0]);
            movieDetailFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.rightContentLayout, movieDetailFragment, fragmentMovieDetailTag);
        } else {
            MovieListFragment movieListFragment = new MovieListFragment();
            movieListFragment.setMovieSelectedListener(this);
            fragmentTransaction.add(R.id.contentLayout, movieListFragment, fragmentMovieListTag);
        }
        fragmentTransaction.commit();
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

    @Override
    public void onDataUpdate(SparseArray<MovieCategory> movieCategories) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Log.i(TAG, "Updating data...");
                View emptyView = findViewById(R.id.emptyView);
                if(emptyView != null && emptyView.getVisibility() == View.VISIBLE) {
                    if(mIsTablet) {
                        mLeftContentLayout.setVisibility(View.VISIBLE);
                        mRightContentLayout.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                    else {
                        mContentLayout.setVisibility(View.VISIBLE);
                        emptyView.setVisibility(View.GONE);
                    }
                    setUpFragments();
                }
            }
        });

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
            mEmptyViewStub.setVisibility(View.VISIBLE);
        }
        else {
            mContentLayout.setVisibility(View.GONE);
            mEmptyViewStub.setVisibility(View.VISIBLE);
        }
        ((TextView) findViewById(R.id.emptyListLabel)).setText(emptyLabelText);
    }
}
