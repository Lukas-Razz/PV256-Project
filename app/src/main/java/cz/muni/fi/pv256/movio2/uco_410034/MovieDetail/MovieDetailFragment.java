package cz.muni.fi.pv256.movio2.uco_410034.MovieDetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 18.10.2017.
 */

public class MovieDetailFragment extends Fragment {

    @BindView(R.id.movieDetailAppBarLayout) AppBarLayout mMovieDetailAppBarLayout;
    @BindView(R.id.movieDetailCollapsingLayout) CollapsingToolbarLayout mMovieDetailCollapsingLayout;
    @BindView(R.id.movieDetailCollapsingToolbar) Toolbar mMovieDetailCollapsingToolbar;
    @BindView(R.id.movieDetailCollapsingMovieTitleLabel) TextView mMovieDetailCollapsingMovieTitleLabel;
    @BindView(R.id.movieDetailCollapsingMovieDateLabel) TextView mMovieDetailCollapsingMovieDateLabel;
    @BindView(R.id.movieDetailCollapsingMovieDirectorLabel) TextView mMovieDetailCollapsingMovieDirectorLabel;
    @BindView(R.id.movieDetailDescription) TextView mMovieDetailDescription;
    @BindView(R.id.movieDetailCollapsingUpperImage) ImageView mMovieDetailCollapsingUpperImage;
    @BindView(R.id.movieDetailCollapsingMovieImage) ImageView mMovieDetailCollapsingMovieImage;

    @BindString(R.string.bundle_movie_key) String mBundleMovieKey;

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
            mMovieDetailCollapsingMovieDirectorLabel.setText("Director Name");
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
        return view;
    }
}
