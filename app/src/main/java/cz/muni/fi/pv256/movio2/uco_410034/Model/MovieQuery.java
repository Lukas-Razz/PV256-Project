package cz.muni.fi.pv256.movio2.uco_410034.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Date;
import java.util.Locale;

/**
 * Created by lukas on 25.11.2017.
 */

public class MovieQuery implements Parcelable {

    private String mLabel;
    private String mRating;
    private Date mFrom;
    private Date mTo;
    private Locale mLanguage;

    public MovieQuery() {
    }

    protected MovieQuery(Parcel in) {
        this.mLabel = in.readString();
        this.mRating = in.readString();
        long tmpMFrom = in.readLong();
        this.mFrom = tmpMFrom == -1 ? null : new Date(tmpMFrom);
        long tmpMTo = in.readLong();
        this.mTo = tmpMTo == -1 ? null : new Date(tmpMTo);
        this.mLanguage = (Locale) in.readSerializable();
    }

    public String getLabel() {
        return mLabel;
    }

    public void setLabel(String label) {
        mLabel = label;
    }

    public String getRating() {
        return mRating;
    }

    public void setRating(String rating) {
        mRating = rating;
    }

    public Date getFrom() {
        return mFrom;
    }

    public void setFrom(Date from) {
        mFrom = from;
    }

    public Date getTo() {
        return mTo;
    }

    public void setTo(Date to) {
        mTo = to;
    }

    public Locale getLanguage() {
        return mLanguage;
    }

    public void setLanguage(Locale language) {
        mLanguage = language;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mLabel);
        dest.writeString(this.mRating);
        dest.writeLong(this.mFrom != null ? this.mFrom.getTime() : -1);
        dest.writeLong(this.mTo != null ? this.mTo.getTime() : -1);
        dest.writeSerializable(this.mLanguage);
    }

    public static final Creator<MovieQuery> CREATOR = new Creator<MovieQuery>() {
        @Override
        public MovieQuery createFromParcel(Parcel source) {
            return new MovieQuery(source);
        }

        @Override
        public MovieQuery[] newArray(int size) {
            return new MovieQuery[size];
        }
    };

    @Override
    public String toString() {
        return "MovieQuery{" +
                "mLabel='" + mLabel + '\'' +
                ", mRating='" + mRating + '\'' +
                ", mFrom=" + mFrom +
                ", mTo=" + mTo +
                ", mLanguage=" + mLanguage +
                '}';
    }
}
