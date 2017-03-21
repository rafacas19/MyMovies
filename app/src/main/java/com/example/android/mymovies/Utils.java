package com.example.android.mymovies;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rafael Casabianca on 1/22/17.
 */

public class Utils {

    public static final String TAG = Utils.class.getSimpleName();

    public Utils(){}

    public static List<Movie> fetchMovieData(String urlRequest) {
        URL url = buildUrl(urlRequest);
        String response = null;
        try {
            response = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.e(TAG, "Problems making the http request");
        }
        return extractFeaturesFromJSON(response);
    }

    private static URL buildUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(TAG, "There was a problem loading the given URL", e);
        }
        return url;
    }

    private static String makeHTTPRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) return jsonResponse;

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, "There was a problem retrieving the JSON results", e);
        } finally {
            if (urlConnection != null) urlConnection.disconnect();
            if (inputStream != null) inputStream.close();
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException{
        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader streamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(streamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    private static List<Movie> extractFeaturesFromJSON(String apiJSON) {
        if (TextUtils.isEmpty(apiJSON)) return null;

        List<Movie> movieList = new ArrayList<>();

        try {
            JSONObject baseJSONResponse = new JSONObject(apiJSON);  // Initial response
            JSONArray movieArray = baseJSONResponse.getJSONArray("results");  // Extracts list of movies

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject movie = movieArray.getJSONObject(i); // Extract each movie

                // Extracts all features from selected movie
                String title = movie.getString("original_title");
                String poster = movie.getString("poster_path");
                String overview = movie.getString("overview");
                double rating = movie.getDouble("vote_average");
                String date = movie.getString("release_date");

                // Adds movie to the list of movies
                movieList.add(new Movie(title, poster, overview, rating, date));
            }
        } catch (JSONException e) {
            Log.e(TAG, "Problem parsing the movie JSON results", e);
        }

        return movieList;
    }
}
