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
    }

    public Movie() {
    }

    protected Movie(Parcel in) {
        this.mReleaseDate = in.readLong();
        this.mCoverPath = in.readString();
        this.mTitle = in.readString();
        this.mBackdrop = in.readString();
        this.mPopularity = in.readFloat();
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
}
