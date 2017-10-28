package cz.muni.fi.pv256.movio2.uco_410034.MovieList;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 27.10.2017.
 */

public class MovieView extends ConstraintLayout{

    @BindView(R.id.movieImage) ImageView mMovieImage;
    @BindView(R.id.moviePanel) View mMoviePanel;
    @BindView(R.id.movieLabel) TextView mMovieLabel;
    @BindView(R.id.movieRatingValue) TextView mMovieRatingValue;

    public MovieView(Context context) {
        super(context);
        initialize(context);
    }

    public MovieView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize(context);
    }

    public MovieView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initialize(context);
    }

    private void initialize(Context context) {
        inflate(context, R.layout.fragment_movie_list_movie, this);
        ButterKnife.bind(this);
    }

    public ImageView getMovieImage() {
        return mMovieImage;
    }

    public View getMoviePanel() {
        return mMoviePanel;
    }

    public TextView getMovieLabel() {
        return mMovieLabel;
    }

    public TextView getMovieRatingValue() {
        return mMovieRatingValue;
    }
}
