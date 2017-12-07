package cz.muni.fi.pv256.movio2.uco_410034.MovieFavorite;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import butterknife.BindColor;
import butterknife.ButterKnife;
import cz.muni.fi.pv256.movio2.uco_410034.Model.Movie;
import cz.muni.fi.pv256.movio2.uco_410034.R;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieFavoriteListViewHolder extends RecyclerView.ViewHolder {

    ConstraintLayout mMovieView;
    @BindColor(R.color.divider) int mDefaultColor;

    private MovieFavoriteButtonClickListener mMovieButtonClickListener;

    public MovieFavoriteListViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
        mMovieView = (ConstraintLayout)itemView;
        mMovieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMovieButtonClickListener.onMovieButtonClick(getAdapterPosition());
            }
        });
    }

    public void setMovie(Movie movie) {
        ((TextView)mMovieView.findViewById(R.id.movieLabel)).setText(movie.getTitle());
        ((TextView)mMovieView.findViewById(R.id.movieRatingValue)).setText(String.format("%.1f/5", movie.getPopularity()));
        mMovieView.findViewById(R.id.moviePanel).setBackgroundColor(mDefaultColor);
        final Target picassoTarget = new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                ((ImageView)mMovieView.findViewById(R.id.movieImage)).setImageBitmap(bitmap);
                View panel = mMovieView.findViewById(R.id.moviePanel);
                if (bitmap != null && !bitmap.isRecycled()) {
                    panel.setBackgroundColor(Palette.from(bitmap).generate().getVibrantColor(mDefaultColor));
                }
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };
        mMovieView.findViewById(R.id.movieImage).setTag(picassoTarget);
        Picasso.with(this.itemView.getContext())
                .load(String.format("https://image.tmdb.org/t/p/w250_and_h141_bestv2/%s", movie.getBackdrop()))
                .into(picassoTarget);
    }

    public void setMovieButtonClickListener(MovieFavoriteButtonClickListener movieButtonClickListener) {
        this.mMovieButtonClickListener = movieButtonClickListener;
    }
}
