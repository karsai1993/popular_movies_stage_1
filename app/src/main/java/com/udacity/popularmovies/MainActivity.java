package com.udacity.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utils.JsonUtils;
import com.udacity.popularmovies.utils.NetworkUtils;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Class for implementing starter point of the application
 */
public class MainActivity extends AppCompatActivity implements SharedPreferences
        .OnSharedPreferenceChangeListener, MovieAdapter.ListItemClickListener {

    /**
     * butterknife is a third party library which is used here to binding the ids to fields easier
     * reference: http://jakewharton.github.io/butterknife/
     */
    @BindView(R.id.rv_movies)
    RecyclerView movieRecyclerView;
    @BindView(R.id.tv_movie_request_fetch_error)
    TextView fetchErrorTextView;
    @BindView(R.id.tv_movie_request_network_error)
    TextView networkErrorTextView;
    @BindView(R.id.pb_movie_request_loading)
    ProgressBar loadingProgressBar;

    private List<Movie> mMovieList;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mUnbinder = ButterKnife.bind(this);
        int columnNumber = 3;
        GridLayoutManager layoutManager = new GridLayoutManager(this, columnNumber);
        movieRecyclerView.setLayoutManager(layoutManager);
        movieRecyclerView.setHasFixedSize(true);
        applySortingOrderBasedOnSharedPreferences();
    }

    /**
     * Function to register shared preferences listener, and to launch the sorting execution
     */
    private void applySortingOrderBasedOnSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(this);
        executeSortingBasedOnPreference(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    /**
     * Function to execute http request based on sorting requirement including network checking
     * @param sharedPreferences
     */
    private void executeSortingBasedOnPreference(SharedPreferences sharedPreferences) {
        if (!NetworkUtils.isNetworkAvailable(this)) {
            showNetworkError();
        } else {
            showMovieData();
            String sortingOrderPreferenceValue = sharedPreferences.getString(
                    getString(R.string.pref_sort_order_key),
                    getString(R.string.pref_sort_order_by_popularity_value)
            );
            new MovieDataGeneratorTask().execute(sortingOrderPreferenceValue);
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
        if (id == R.id.action_settings) {
            Intent settingsActivityIntent = new Intent(MainActivity.this,
                    SettingsActivity.class);
            startActivity(settingsActivityIntent);
        }
        if (id == R.id.action_refresh_main_menu) {
            applySortingOrderBasedOnSharedPreferences();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.pref_sort_order_key))) {
            executeSortingBasedOnPreference(sharedPreferences);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
        mUnbinder.unbind();
    }

    /**
     * Function to make the poster of each movie visible
     */
    private void showMovieData() {
        fetchErrorTextView.setVisibility(View.INVISIBLE);
        networkErrorTextView.setVisibility(View.INVISIBLE);
        movieRecyclerView.setVisibility(View.VISIBLE);
    }

    /**
     * Function to make the error message referring to problem with http request visible
     */
    private void showFetchError() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        networkErrorTextView.setVisibility(View.INVISIBLE);
        fetchErrorTextView.setVisibility(View.VISIBLE);
    }

    /**
     * Function to make the error message referring to problem with internet connection visible
     */
    private void showNetworkError() {
        movieRecyclerView.setVisibility(View.INVISIBLE);
        fetchErrorTextView.setVisibility(View.INVISIBLE);
        networkErrorTextView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onListItemClick(int index) {
        Movie selectedMovie = mMovieList.get(index);
        Intent detailActivityIntent = new Intent(MainActivity.this,
                DetailActivity.class);
        detailActivityIntent.putExtra(CommonApplicationConstants.BACKDROP_PATH_KEY,
                selectedMovie.getBackdropImagePathEnding());
        detailActivityIntent.putExtra(CommonApplicationConstants.POSTER_PATH_KEY,
                selectedMovie.getPosterImagePathEnding());
        detailActivityIntent.putExtra(CommonApplicationConstants.TITLE_KEY,
                selectedMovie.getTitle());
        detailActivityIntent.putExtra(CommonApplicationConstants.ORIGINAL_TITLE_KEY,
                selectedMovie.getOriginalTitle());
        detailActivityIntent.putExtra(CommonApplicationConstants.AVERAGE_VOTE_KEY,
                selectedMovie.getAverageVote());
        detailActivityIntent.putExtra(CommonApplicationConstants.OVERVIEW_KEY,
                selectedMovie.getOverview());
        detailActivityIntent.putExtra(CommonApplicationConstants.RELEASE_DATE_KEY,
                selectedMovie.getReleaseDate());
        startActivity(detailActivityIntent);
    }

    /**
     * Class for implementing background process for http request
     */
    public class MovieDataGeneratorTask extends AsyncTask<String, Void, List<Movie>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<Movie> doInBackground(String... args) {
            if (args.length == 0) {
                return null;
            }
            URL movieRequestUrl = NetworkUtils.buildUrlForSorting(args[0]);
            try {
                String movieResponseAsJson = NetworkUtils.getResponseFromUrl(movieRequestUrl);
                return JsonUtils.generateMovieDataListFromJson(movieResponseAsJson);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            } catch (JSONException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(List<Movie> movieList) {
            super.onPostExecute(movieList);
            loadingProgressBar.setVisibility(View.INVISIBLE);
            if (movieList != null) {
                showMovieData();
                mMovieList = movieList;
                MovieAdapter movieAdapter = new MovieAdapter(MainActivity.this, mMovieList);
                movieRecyclerView.setAdapter(movieAdapter);
            } else {
                showFetchError();
            }
        }
    }
}
