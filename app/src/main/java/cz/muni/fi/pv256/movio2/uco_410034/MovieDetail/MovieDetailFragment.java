package cz.muni.fi.pv256.movio2.uco_410034.MovieDetail;

import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindDrawable;
import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.muni.fi.pv256.movio2.uco_410034.Db.MovieDatabase;
import cz.muni.fi.pv256.movio2.uco_410034.FavoriteDataUpdateListener;
import cz.muni.fi.pv256.movio2.uco_410034.Mapper.MovieMapper;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.MovieDataHolder;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 18.10.2017.
 */

public class MovieDetailFragment extends Fragment implements FavoriteDataUpdateListener {

    @BindView(R.id.movieDetailAppBarLayout) AppBarLayout mMovieDetailAppBarLayout;
    @BindView(R.id.movieDetailCollapsingLayout) CollapsingToolbarLayout mMovieDetailCollapsingLayout;
    @BindView(R.id.movieDetailCollapsingToolbar) Toolbar mMovieDetailCollapsingToolbar;
    @BindView(R.id.movieDetailCollapsingMovieTitleLabel) TextView mMovieDetailCollapsingMovieTitleLabel;
    @BindView(R.id.movieDetailCollapsingMovieDateLabel) TextView mMovieDetailCollapsingMovieDateLabel;
    @BindView(R.id.movieDetailCollapsingMovieDirectorLabel) TextView mMovieDetailCollapsingMovieDirectorLabel;
    @BindView(R.id.movieDetailDescription) TextView mMovieDetailDescription;
    @BindView(R.id.movieDetailCollapsingUpperImage) ImageView mMovieDetailCollapsingUpperImage;
    @BindView(R.id.movieDetailCollapsingMovieImage) ImageView mMovieDetailCollapsingMovieImage;
    @BindView(R.id.floatingActionButton) FloatingActionButton mFloatingActionButton;

    @BindString(R.string.bundle_movie_key) String mBundleMovieKey;
    @BindDrawable(R.drawable.ic_add_white_24px) Drawable mAddIcon;
    @BindDrawable(R.drawable.ic_remove_white_24px) Drawable mRemoveIcon;

    private Movie mMovie;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_detail , container, false);
        ButterKnife.bind(this, view);

        Bundle bundle = getArguments();
        if(bundle != null) {
            mMovie = bundle.getParcelable(mBundleMovieKey);

            mMovieDetailCollapsingMovieTitleLabel.setText(mMovie.getTitle());
            mMovieDetailCollapsingMovieDateLabel.setText(new SimpleDateFormat("yyyy-mm-dd").format(new Date(mMovie.getReleaseDate())));
            mMovieDetailDescription.setText(mMovie.getDescription());
            Picasso.with(getContext())
                    .load(String.format("https://image.tmdb.org/t/p/w250_and_h141_bestv2/%s", mMovie.getBackdrop()))
                    .into(mMovieDetailCollapsingUpperImage);
            Picasso.with(getContext())
                    .load(String.format("https://image.tmdb.org/t/p/w640/%s", mMovie.getCoverPath()))
                    .into(mMovieDetailCollapsingMovieImage);

            mMovieDetailAppBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (verticalOffset == -mMovieDetailCollapsingLayout.getHeight() + mMovieDetailCollapsingToolbar.getHeight()) {
                        mMovieDetailCollapsingLayout.setTitle(mMovie.getTitle());
                    } else {
                        mMovieDetailCollapsingLayout.setTitle("");
                    }
                }
            });
        }
        MovieDataHolder.INSTANCE.subscribeFavoriteDataUpdateListener(this);
        if(MovieDataHolder.INSTANCE.getFavoriteMovies().contains(mMovie)) {
            mFloatingActionButton.setImageDrawable(mRemoveIcon);
        }
        else {
            mFloatingActionButton.setImageDrawable(mAddIcon);
        }
        return view;
    }

    @OnClick(R.id.floatingActionButton)
    public void onFloatingActionButtonClick() {
        final boolean favorite = MovieDataHolder.INSTANCE.getFavoriteMovies().contains(mMovie);
        cz.muni.fi.pv256.movio2.uco_410034.Db.Model.Movie m = MovieMapper.INSTANCE.movieToDbMovie(mMovie);
        new AsyncTask<Movie,Void,Void>() {
            @Override
            protected Void doInBackground(Movie... movies) {
                if(favorite) {
                    MovieDatabase.getInstance(getContext()).movieDao().delete(MovieMapper.INSTANCE.movieToDbMovie(movies));
                }
                else {
                    MovieDatabase.getInstance(getContext()).movieDao().insert(MovieMapper.INSTANCE.movieToDbMovie(movies));
                }
                return null;
            }
        }.execute(mMovie);
    }

    @Override
    public void onDestroy() {
        MovieDataHolder.INSTANCE.unsubscribeFavoriteDataUpdateListener(this);
        super.onDestroy();
    }

    @Override
    public void onDataUpdate(List<Movie> movies) {
        if(movies.contains(mMovie)) {
            mFloatingActionButton.setImageDrawable(mRemoveIcon);
        }
        else {
            mFloatingActionButton.setImageDrawable(mAddIcon);
        }
    }
}
