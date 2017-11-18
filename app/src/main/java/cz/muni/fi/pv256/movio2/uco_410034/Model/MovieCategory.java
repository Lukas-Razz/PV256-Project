package cz.muni.fi.pv256.movio2.uco_410034.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

/**
 * Created by lukas on 12.10.2017.
 */

public class MovieCategory implements Parcelable {

    private String mCategoryName = "";
    private Movie[] mMovieList = new Movie[6];

    public String getCategoryName() {
        return mCategoryName;
    }

    public void setCategoryName(@NonNull String categoryName) {
        this.mCategoryName = categoryName;
    }

    public Movie[] getMovieList() {
        return mMovieList;
    }

    public void setMovieList(@NonNull Movie[] movieList) {
        if(movieList.length != 6)
            throw new IllegalArgumentException("Movie list must contain exactly 6 movies.");

        this.mMovieList = movieList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mCategoryName);
        dest.writeTypedArray(this.mMovieList, flags);
    }

    public MovieCategory() {
    }

    protected MovieCategory(Parcel in) {
        this.mCategoryName = in.readString();
        this.mMovieList = in.createTypedArray(Movie.CREATOR);
    }

    public static final Parcelable.Creator<MovieCategory> CREATOR = new Parcelable.Creator<MovieCategory>() {
        @Override
        public MovieCategory createFromParcel(Parcel source) {
            return new MovieCategory(source);
        }

        @Override
        public MovieCategory[] newArray(int size) {
            return new MovieCategory[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieCategory that = (MovieCategory) o;

        return mCategoryName.equals(that.mCategoryName);
    }

    @Override
    public int hashCode() {
        return mCategoryName.hashCode();
    }
}
