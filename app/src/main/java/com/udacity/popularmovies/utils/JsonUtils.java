package com.udacity.popularmovies.utils;

import com.udacity.popularmovies.CommonApplicationConstants;
import com.udacity.popularmovies.model.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Class for implementing json ToDos
 */
public class JsonUtils {

    private static final String RESULTS_KEY = "results";

    /**
     * Function to give the list of Movie objects based on the json response from http request
     * @param movieJsonResponse
     * @return list of Movie objects
     * @throws JSONException
     */
    public static List<Movie> generateMovieDataListFromJson(String movieJsonResponse) throws JSONException {
        JSONObject response = new JSONObject(movieJsonResponse);
        JSONArray results = response.getJSONArray(RESULTS_KEY);
        List<Movie> movieList = new ArrayList<>();
        for (int i = 0; i < results.length(); i++) {
            JSONObject currObject = results.getJSONObject(i);
            String title = currObject.optString(CommonApplicationConstants.TITLE_KEY);
            String originalTitle = currObject
                    .optString(CommonApplicationConstants.ORIGINAL_TITLE_KEY);
            String averageVote = currObject.optString(CommonApplicationConstants.AVERAGE_VOTE_KEY);
            String posterImagePath = currObject
                    .optString(CommonApplicationConstants.POSTER_PATH_KEY);
            String backdropImagePath = currObject
                    .optString(CommonApplicationConstants.BACKDROP_PATH_KEY);
            String overview = currObject.optString(CommonApplicationConstants.OVERVIEW_KEY);
            String releaseDate = currObject.optString(CommonApplicationConstants.RELEASE_DATE_KEY);
            movieList.add(new Movie(
                    title,
                    originalTitle,
                    posterImagePath,
                    backdropImagePath,
                    overview,
                    averageVote,
                    releaseDate));
        }
        return movieList;
    }
}
