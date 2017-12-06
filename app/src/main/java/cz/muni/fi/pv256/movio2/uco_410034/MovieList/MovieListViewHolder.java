package cz.muni.fi.pv256.movio2.uco_410034.MovieList;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieListViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.movieCategoryLabel) TextView mMovieCategoryLabel;

    private MovieButtonClickListener mMovieButtonClickListener;

    public MovieListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void setMovieCategoryLabel(String categoryName) {
        mMovieCategoryLabel.setText(categoryName);
    }

    public void setMovieButtonClickListener(MovieButtonClickListener movieButtonClickListener) {
        this.mMovieButtonClickListener = movieButtonClickListener;
    }

    @OnClick({R.id.movie_0_0, R.id.movie_1_0, R.id.movie_2_0, R.id.movie_0_1, R.id.movie_1_1, R.id.movie_2_1,})
    public void onMovieTileClick(ImageButton imageButton) {
        mMovieButtonClickListener.onMovieButtonClick(Integer.parseInt((String)imageButton.getTag()), getAdapterPosition());
    }
}
