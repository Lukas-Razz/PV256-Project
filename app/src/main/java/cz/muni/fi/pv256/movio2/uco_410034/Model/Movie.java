package cz.muni.fi.pv256.movio2.uco_410034.Model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lukas on 18.10.2017.
 */

public class Movie implements Parcelable {

    private long mReleaseDate;
    private String mCoverPath;
    private String mTitle;
    private String mBackdrop;
    private float mPopularity;
    private String mDescription;

    public long getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        this.mReleaseDate = releaseDate;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        this.mCoverPath = coverPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setTitle(String title) {
        this.mTitle = title;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        this.mBackdrop = backdrop;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        this.mPopularity = popularity;
    }

    public void setDescription(String description) {
        this.mDescription = description;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(this.mReleaseDate);
        dest.writeString(this.mCoverPath);
        dest.writeString(this.mTitle);
        dest.writeString(this.mBackdrop);
        dest.writeFloat(this.mPopularity);
        dest.writeString(this.mDescription);
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.mReleaseDate = in.readLong();
        this.mCoverPath = in.readString();
        this.mTitle = in.readString();
        this.mBackdrop = in.readString();
        this.mPopularity = in.readFloat();
        this.mDescription = in.readString();
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (getReleaseDate() != movie.getReleaseDate()) return false;
        if (Float.compare(movie.getPopularity(), getPopularity()) != 0) return false;
        if (getCoverPath() != null ? !getCoverPath().equals(movie.getCoverPath()) : movie.getCoverPath() != null)
            return false;
        if (getTitle() != null ? !getTitle().equals(movie.getTitle()) : movie.getTitle() != null)
            return false;
        if (getBackdrop() != null ? !getBackdrop().equals(movie.getBackdrop()) : movie.getBackdrop() != null)
            return false;
        return getDescription() != null ? getDescription().equals(movie.getDescription()) : movie.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getReleaseDate() ^ (getReleaseDate() >>> 32));
        result = 31 * result + (getCoverPath() != null ? getCoverPath().hashCode() : 0);
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getBackdrop() != null ? getBackdrop().hashCode() : 0);
        result = 31 * result + (getPopularity() != +0.0f ? Float.floatToIntBits(getPopularity()) : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
