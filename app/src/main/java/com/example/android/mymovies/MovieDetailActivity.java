package com.example.android.mymovies;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String MOVIE_POSTER_URL = "http://image.tmdb.org/t/p/w185/";

    private ImageView mPoster;
    private TextView mOverview;
    private TextView mRating;
    private TextView mReleaseDate;

    private Movie mMovie;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);

        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) setContentView(R.layout.movie_detail);
        else setContentView(R.layout.movie_detail_l);

        mPoster = (ImageView) findViewById(R.id.movie_poster);
        mOverview = (TextView) findViewById(R.id.tv_overview);
        mRating = (TextView) findViewById(R.id.tv_rating);
        mReleaseDate = (TextView) findViewById(R.id.tv_release_date);

        Intent intentForThisActivity = getIntent();

        if (intentForThisActivity != null) {
            if (intentForThisActivity.hasExtra(getString(R.string.movie_extra)))
                mMovie = Parcels.unwrap(intentForThisActivity.getParcelableExtra(getString(R.string.movie_extra)));
                setTitle(mMovie.getOriginalTitle());
                Picasso.with(this).load(MOVIE_POSTER_URL + mMovie.getPosterPath()).into(mPoster);
                mOverview.setText(mMovie.getOverview());
                mRating.setText(getString(R.string.user_rating, mMovie.getVoteAverage()));
                mReleaseDate.setText(getString(R.string.release_date, mMovie.MONTH, mMovie.DAY, mMovie.YEAR));
        }


    }
}
