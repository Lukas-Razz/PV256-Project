package cz.muni.fi.pv256.movio2.uco_410034.Db.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

/**
 * Created by lukas on 06.12.2017.
 */

@Entity(tableName = "movie")
public class Movie {

    @ColumnInfo(name = "release_date")
    private long mReleaseDate;

    @ColumnInfo(name = "cover_path")
    private String mCoverPath;

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "title")
    private String mTitle;

    @ColumnInfo(name = "backdrop")
    private String mBackdrop;

    @ColumnInfo(name = "popularity")
    private float mPopularity;

    @ColumnInfo(name = "description")
    private String mDescription;

    public long getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(long releaseDate) {
        mReleaseDate = releaseDate;
    }

    public String getCoverPath() {
        return mCoverPath;
    }

    public void setCoverPath(String coverPath) {
        mCoverPath = coverPath;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getBackdrop() {
        return mBackdrop;
    }

    public void setBackdrop(String backdrop) {
        mBackdrop = backdrop;
    }

    public float getPopularity() {
        return mPopularity;
    }

    public void setPopularity(float popularity) {
        mPopularity = popularity;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (getReleaseDate() != movie.getReleaseDate()) return false;
        if (Float.compare(movie.getPopularity(), getPopularity()) != 0) return false;
        if (getCoverPath() != null ? !getCoverPath().equals(movie.getCoverPath()) : movie.getCoverPath() != null)
            return false;
        if (!getTitle().equals(movie.getTitle())) return false;
        if (getBackdrop() != null ? !getBackdrop().equals(movie.getBackdrop()) : movie.getBackdrop() != null)
            return false;
        return getDescription() != null ? getDescription().equals(movie.getDescription()) : movie.getDescription() == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (getReleaseDate() ^ (getReleaseDate() >>> 32));
        result = 31 * result + (getCoverPath() != null ? getCoverPath().hashCode() : 0);
        result = 31 * result + getTitle().hashCode();
        result = 31 * result + (getBackdrop() != null ? getBackdrop().hashCode() : 0);
        result = 31 * result + (getPopularity() != +0.0f ? Float.floatToIntBits(getPopularity()) : 0);
        result = 31 * result + (getDescription() != null ? getDescription().hashCode() : 0);
        return result;
    }
}
