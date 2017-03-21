package com.example.android.mymovies;

import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.mymovies.data.MoviesContract;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private static final int MOVIE_LOADER_ID = 1;
    private static final String MOVIE_REQUEST_URL = "http://api.themoviedb.org/3/movie/";
    private static final String API_KEY_RESOURCE = "?api_key=";
    private static final String POPULAR_EXTENSION = "popular" + API_KEY_RESOURCE;
    private static final String TOP_RATED_EXTENSION = "top_rated" + API_KEY_RESOURCE;
    private static final int MOST_POPULAR_ID = 0;
    private static final int TOP_RATED_ID = 1;
    private static final String API_KEY = "";
    private static final int CURSOR_LOADER_ID = 0;
    private List<Movie> mMovieList;
    private MovieAdapter mAdapter;
    private Cursor mData;
    private int mSortBy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new MovieFetchTask().execute();

        mAdapter = new MovieAdapter(this, mData, 0, CURSOR_LOADER_ID);

        GridView gridView = (GridView) findViewById(R.id.movie_grid);
        gridView.setAdapter(mAdapter);

//        gridView.setOnItemClickListener(
//                new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        Movie movie = mAdapter.getItem(position);
//                        Class destinationClass = MovieDetailActivity.class;
//                        Intent launchMovieDetail = new Intent(view.getContext(), destinationClass);
//                        launchMovieDetail.putExtra(getString(R.string.movie_extra), Parcels.wrap(movie));
//                        startActivity(launchMovieDetail);
//                    }
//                }
//        );

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
             LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(MOVIE_LOADER_ID, null, this);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.sortby_most_popular) {
            mSortBy = 0;
            getLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
            mAdapter.notifyDataSetChanged();
            return true;
        }

        if (id == R.id.sortby_top_rated) {
            mSortBy = 1;
            getLoaderManager().restartLoader(MOVIE_LOADER_ID,null,this);
            mAdapter.notifyDataSetChanged();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(this,
                MoviesContract.MovieEntry.CONTENT_URI,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mData = data;
        mAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        String sortCriteria = "";
        if (mSortBy == MOST_POPULAR_ID) sortCriteria = POPULAR_EXTENSION;
        else sortCriteria = TOP_RATED_EXTENSION;

        mAdapter.swapCursor(null);
    }

//    @Override
//    public void onLoadFinished(Loader<List<Movie>> loader, List<Movie> data) {
//        mAdapter.clear();
//
//
//        if (data != null && !data.isEmpty()){
//            if (mSortBy == 1){
//                Collections.sort(data, new Comparator<Movie>() {
//                    @Override
//                    public int compare(Movie m1, Movie m2) {
//                        if (m1.getVoteAverage() >= m2.getVoteAverage()) return -1;
//                        else if (m1.getVoteAverage() < m2.getVoteAverage()) return 1;
//                        return 0;
//                    }
//                });
//            }
//            mAdapter.addAll(data);
//        }
//    }

//    @Override
//    public void onLoaderReset(Loader<List<Movie>> loader) {
//        mAdapter.clear();
//    }

    public class MovieFetchTask extends AsyncTask<Void, Void, List<Movie>> {
        @Override
        protected List<Movie> doInBackground(Void... params) {
            String sortCriteria = "";
            if (mSortBy == MOST_POPULAR_ID) sortCriteria = POPULAR_EXTENSION;
            else sortCriteria = TOP_RATED_EXTENSION;

            return Utils.fetchMovieData(MOVIE_REQUEST_URL + sortCriteria + API_KEY);
        }

        @Override
        protected void onPostExecute(List<Movie> movies) {
            super.onPostExecute(movies);
            mMovieList = movies;
            insertMovieData(mMovieList);

//            mData = getContentResolver().query(
//                    MoviesContract.MovieEntry.CONTENT_URI,
//                    null,
//                    null,
//                    null,
//                    null
//            );
        }

        public void insertMovieData(List<Movie> movieList) {
            List<ContentValues> movieValues = new ArrayList<ContentValues>();
            for (Movie movie : movieList) {
                movieValues.add(createMovieContentValues(movie));
            }

            MainActivity.this.getContentResolver().bulkInsert(
                    MoviesContract.MovieEntry.CONTENT_URI,
                    movieValues.toArray(new ContentValues[movieValues.size()]));
        }

        private ContentValues createMovieContentValues(Movie movie) {
            ContentValues movieCV = new ContentValues();
            movieCV.put(MoviesContract.MovieEntry.COLUMN_TITLE, movie.getOriginalTitle());
            movieCV.put(MoviesContract.MovieEntry.COLUMN_DATE, movie.getReleaseDate()) ;
            movieCV.put(MoviesContract.MovieEntry.COLUMN_POSTER, movie.getPosterPath());
            movieCV.put(MoviesContract.MovieEntry.COLUMN_VOTE_AVERGAGE, movie.getVoteAverage());
            movieCV.put(MoviesContract.MovieEntry.COLUMN_SYNOPSIS, movie.getOverview());
            movieCV.put(MoviesContract.MovieEntry.COLUMN_FAVORITE, movie.getFavorite());

            return movieCV;
        }
    }

}
