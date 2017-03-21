package com.example.android.mymovies.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by Rafa on 3/20/17.
 */

public class MoviesContract {
    // Constants
    public static final String CONTENT_AUTHORITY = "com.example.android.mymovies";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final class MovieEntry implements BaseColumns {
        public static final String TABLE_MOVIES = "movies";
        public static final String _ID = "_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_DATE = "release_date";
        public static final String COLUMN_POSTER = "poster_path";
        public static final String COLUMN_VOTE_AVERGAGE = "vote_average";
        public static final String COLUMN_SYNOPSIS = "synopsis";
        public static final String COLUMN_FAVORITE = "favorite";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(TABLE_MOVIES).build();

    }
}
