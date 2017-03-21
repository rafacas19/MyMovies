package com.example.android.mymovies;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.os.AsyncTask;

import java.util.List;

/**
 * Created by Rafa on 1/22/17.
 */

public class MovieLoader extends AsyncTaskLoader<List<Movie>> {

    private static final String TAG = MovieLoader.class.getSimpleName();
    private String mUrl;

    public MovieLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Movie> loadInBackground() {
        if (mUrl == null) return null;

        List<Movie> movieList = Utils.fetchMovieData(mUrl);

        return movieList;
    }
}
