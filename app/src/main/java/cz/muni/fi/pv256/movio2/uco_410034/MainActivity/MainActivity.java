package cz.muni.fi.pv256.movio2.uco_410034.MainActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.movieListView) RecyclerView mMovieListView;
    @BindView(R.id.rootLayout) DrawerLayout mDrawerLayout;

    @BindString(R.string.shared_pref_name) String sharedPrefName;
    @BindString(R.string.shared_pref_style_key) String sharedPrefStyleKey;

    ActionBarDrawerToggle mDrawerToggle;

    Movie[] mMovieList = new Movie[6];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SharedPreferences prefs = this.getSharedPreferences(getString(R.string.shared_pref_name), Context.MODE_PRIVATE);
        setTheme(prefs.getInt(getString(R.string.shared_pref_style_key), R.style.AppTheme_1));
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setUpDrawer();

        MovieListAdapter movieListAdapter = new MovieListAdapter(mMovieList);
        RecyclerView.LayoutManager movieListLayoutManager = new LinearLayoutManager(getApplicationContext());
        mMovieListView.setLayoutManager(movieListLayoutManager);
        mMovieListView.setAdapter(movieListAdapter);

        fillDummyData();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
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

    @OnClick(R.id.drawerChangeStyleButton)
    public void drawerChangeStyleButtonClick(View view) {
        SharedPreferences prefs = this.getSharedPreferences(sharedPrefName, Context.MODE_PRIVATE);
        int themeId = prefs.getInt(sharedPrefStyleKey, R.style.AppTheme_1);
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
        for (int i = 0; i < 6; i++) {
            Movie movie = new Movie();
            mMovieList[i] = movie;
        }
    }
}
