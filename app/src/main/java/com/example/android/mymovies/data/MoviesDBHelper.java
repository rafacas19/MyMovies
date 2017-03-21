package com.example.android.mymovies.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Rafa on 3/20/17.
 */

public class MoviesDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "movies.db";
    private static final int DATABASE_VERSION = 1;

    public MoviesDBHelper(Context context) { super(context, DATABASE_NAME, null, DATABASE_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_MOVIES_TABLE = "CREATE TABLE " +
                MoviesContract.MovieEntry.TABLE_MOVIES + "(" + MoviesContract.MovieEntry._ID +
                " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                MoviesContract.MovieEntry.COLUMN_TITLE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_DATE + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_VOTE_AVERGAGE + " REAL NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_SYNOPSIS + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_POSTER + " TEXT NOT NULL, " +
                MoviesContract.MovieEntry.COLUMN_FAVORITE + " flag INTEGER DEFAULT 0)";

        db.execSQL(SQL_CREATE_MOVIES_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MoviesContract.MovieEntry.TABLE_MOVIES);
        db.execSQL("DELETE FROM SQLITE_SEQUENCE WHERE NAME = '" +
                MoviesContract.MovieEntry.TABLE_MOVIES + "'");

        onCreate(db);
    }
}
