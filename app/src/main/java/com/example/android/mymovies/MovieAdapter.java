package com.example.android.mymovies;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.nfc.Tag;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CursorAdapter;
import android.widget.ImageView;

import com.example.android.mymovies.data.MoviesContract;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Rafa on 1/22/17.
 */

//public class MovieAdapter extends ArrayAdapter<Movie> {
//    private final static String TAG = MovieAdapter.class.getSimpleName();
//    private final static String MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
//    private List<Movie> mMovieData;
//
//    public MovieAdapter(Activity context, List<Movie> movieList) {
//        super(context, 0, movieList);
//        mMovieData = movieList;
//    }
//
//    public View getView(int position, View convertView, ViewGroup parent) {
//        Movie movie = getItem(position);
//
//        if (convertView == null) {
//            convertView = LayoutInflater.from(getContext())
//                                .inflate(R.layout.movie_item, parent, false);
//        }
//
//        ImageView moviePoster = (ImageView) convertView.findViewById(R.id.movie_image);
//        Picasso.with(getContext()).load(MOVIE_POSTER_URL + movie.getPosterPath())
//                    .into(moviePoster);
//
//        return convertView;
//    }
//
//
//}

public class MovieAdapter extends CursorAdapter {
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private final static String MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";
    private Context mContext;
    private static int sLoader;

    public MovieAdapter(Context context, Cursor cursor, int flags, int loaderID) {
        super(context, cursor, flags);
        mContext = context;
        sLoader = loaderID;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int layoutId = R.layout.movie_item;
        View view = LayoutInflater.from(context).inflate(layoutId, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        int posterIndex = cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_POSTER);
        String poster = cursor.getString(posterIndex);
        ImageView moviePoster = (ImageView) view.findViewById(R.id.movie_image);
        Picasso.with(context).load(MOVIE_POSTER_URL + poster)
                .into(moviePoster);
    }
}
