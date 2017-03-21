package com.example.android.mymovies;

import org.parceler.Parcel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Rafa on 1/22/17.
 */

@Parcel
public class Movie {

    String mOriginalTitle;
    String mPosterPath;
    String mOverview;
    double mVoteAverage;
    String mReleaseDate;
    String DAY;
    String MONTH;
    String YEAR;
    int mFavorite;

    public Movie(){}

    public Movie(String title, String posterPath, String overview, double voteAverage, String relaseDate) {
        mOriginalTitle = title;
        mPosterPath = posterPath;
        mOverview = overview;
        mVoteAverage = voteAverage;
        mReleaseDate = relaseDate;
        MONTH = relaseDate.substring(5,7);
        DAY = relaseDate.substring(8,10);
        YEAR = relaseDate.substring(0,4);
        mFavorite = 0;
    }


    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getPosterPath() {
        return mPosterPath;
    }

    public String getOverview() {
        return mOverview;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public int getFavorite() { return mFavorite; }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String convertToDate(String stringDate) {
        Date newDate = null;
        SimpleDateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        try {
            newDate = format.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return newDate.toString();
    }
}
