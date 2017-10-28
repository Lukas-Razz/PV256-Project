package cz.muni.fi.pv256.movio2.uco_410034.MovieList;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.Model.MovieCategory;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movieCategoryLabel) TextView mMovieCategoryLabel;
    @BindViews({R.id.movie_0, R.id.movie_1, R.id.movie_2, R.id.movie_3, R.id.movie_4, R.id.movie_5}) MovieView[] mMovieViews;
    @BindColor(R.color.divider) int mDefaultColor;

    private MovieButtonClickListener mMovieButtonClickListener;

    public MovieListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        for(int i=0; i < mMovieViews.length; i++) {
            setOnClickToView(mMovieViews[i]);
        }
    }

    public void setMovieCategory(MovieCategory movieCategory) {
        mMovieCategoryLabel.setText(movieCategory.getCategoryName());
        for(int i=0; i < mMovieViews.length; i++) {
            Movie movie = movieCategory.getMovieList()[i];
            mMovieViews[i].getMovieLabel().setText(movie.getTitle());
            mMovieViews[i].getMovieRatingValue().setText(String.format("%.1f/5", movie.getPopularity()));

            View panel = mMovieViews[i].getMoviePanel();
            Bitmap dummyImage = BitmapFactory.decodeResource(itemView.getResources(), R.mipmap.ic_launcher);
            int panelBackgroudColor = mDefaultColor;
            if (dummyImage != null && !dummyImage.isRecycled()) {
                panelBackgroudColor = Palette.from(dummyImage).generate().getVibrantColor(mDefaultColor);
            }
            panel.setBackgroundColor(panelBackgroudColor);
        }

    }

    public void setMovieButtonClickListener(MovieButtonClickListener movieButtonClickListener) {
        this.mMovieButtonClickListener = movieButtonClickListener;
    }

    private void setOnClickToView(View view) {
        view.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                int id = Integer.parseInt((String)v.getTag());
                mMovieButtonClickListener.onMovieButtonClick(id, getAdapterPosition());
            }
        });
    }
}
