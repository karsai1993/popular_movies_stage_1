package com.udacity.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.udacity.popularmovies.utils.ImageUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Class for implementing detail view of each movie
 */
public class DetailActivity extends AppCompatActivity {

    private Unbinder mUnbinder;
    private String mTitle;
    private String mOriginalTitle;
    private String mBackgroundImagePathEnding;
    private String mPosterImagePathEnding;
    private String mOverview;
    private String mAverageVote;
    private String mReleaseDate;

    private final String DATA_NOT_AVAILABLE = "DNA";

    /**
     * butterknife is a third party library which is used here to binding the ids to fields easier
     * reference: http://jakewharton.github.io/butterknife/
     */
    @BindView(R.id.iv_detail_activity_backdrop)
    ImageView backdropImageView;
    @BindView(R.id.iv_detail_activity_poster)
    ImageView posterImageView;
    @BindView(R.id.tv_title)
    TextView titleTextView;
    @BindView(R.id.tv_original_title)
    TextView originalTitleTextView;
    @BindView(R.id.tv_original_title_label)
    TextView originalTitleLabelTextView;
    @BindView(R.id.tv_average_vote)
    TextView averageVoteTextView;
    @BindView(R.id.tv_release_date)
    TextView releaseDateTextView;
    @BindView(R.id.tv_overview)
    TextView overviewTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mUnbinder = ButterKnife.bind(this);
        receiveIntentValues(getIntent());
        populateUI();
    }

    /**
     * Function to get the intent values from parent class, and to store them in appropriate fields
     * @param receivedIntent
     */
    private void receiveIntentValues(Intent receivedIntent) {
        mBackgroundImagePathEnding = receivedIntent.getStringExtra(
                CommonApplicationConstants.BACKDROP_PATH_KEY);
        mPosterImagePathEnding = receivedIntent.getStringExtra(
                CommonApplicationConstants.POSTER_PATH_KEY);
        mTitle = receivedIntent.getStringExtra(CommonApplicationConstants.TITLE_KEY);
        mOriginalTitle = receivedIntent.getStringExtra(
                CommonApplicationConstants.ORIGINAL_TITLE_KEY);
        mOverview = receivedIntent.getStringExtra(CommonApplicationConstants.OVERVIEW_KEY);
        mAverageVote = receivedIntent.getStringExtra(CommonApplicationConstants.AVERAGE_VOTE_KEY);
        mReleaseDate = receivedIntent.getStringExtra(CommonApplicationConstants.RELEASE_DATE_KEY);
    }

    /**
     * Function to set the values of all views in the layout
     * NOTE: Original title is only shown when it does not match with the title.
     * It usually occurs when the original title of the certain movie does not have English
     * original title.
     */
    private void populateUI() {
        ImageUtils.loadImage(this, mPosterImagePathEnding, posterImageView);
        ImageUtils.loadImage(this, mBackgroundImagePathEnding, backdropImageView);
        textViewValueHandler(titleTextView, mTitle);
        textViewValueHandler(averageVoteTextView, mAverageVote);
        textViewValueHandler(overviewTextView, mOverview);
        releaseDateHandler();

        if (mTitle.equals(mOriginalTitle)) {
            originalTitleLabelTextView.setVisibility(View.GONE);
            originalTitleTextView.setVisibility(View.GONE);
        } else if (mOriginalTitle.isEmpty()) {
            originalTitleTextView.setText(DATA_NOT_AVAILABLE);
        } else {
            originalTitleTextView.setText(mOriginalTitle);
        }
    }

    /**
     * Function to handle text view values based on the corresponding field values
     * @param textView
     * @param value
     */
    private void textViewValueHandler(TextView textView, String value) {
        if (value.isEmpty()) {
            textView.setText(DATA_NOT_AVAILABLE);
        } else {
            textView.setText(value);
        }
    }

    /**
     * Function to provide a more readable date format for each movie including the
     * weekday, day, month and year of its release.
     */
    private void releaseDateHandler() {
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat outputFormat =
                new SimpleDateFormat("EEE, d MMM yyyy", Locale.US);
        try {
            Date releaseDate = inputFormat.parse(mReleaseDate);
            String formattedDate = outputFormat.format(releaseDate);
            releaseDateTextView.setText(formattedDate);
        } catch (ParseException e) {
            textViewValueHandler(releaseDateTextView, mReleaseDate);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mUnbinder.unbind();
    }
}
