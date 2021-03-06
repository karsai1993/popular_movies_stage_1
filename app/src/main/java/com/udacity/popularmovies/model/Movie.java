package com.udacity.popularmovies.model;

/**
 * Class for storing data for each movie
 */
public class Movie {
    private String mTitle;
    private String mOriginalTitle;
    private String mPosterImagePathEnding;
    private String mBackdropImagePathEnding;
    private String mOverview;
    private String mAverageVote;
    private String mReleaseDate;

    /**
     * Constructor created to store relevant information for each movie
     * @param title
     * @param originalTitle
     * @param posterImagePathEnding
     * @param backdropImagePathEnding
     * @param overview
     * @param averageVote
     * @param releaseDate
     */
    public Movie(String title,
                 String originalTitle,
                 String posterImagePathEnding,
                 String backdropImagePathEnding,
                 String overview,
                 String averageVote,
                 String releaseDate) {
        this.mTitle = title;
        this.mOriginalTitle = originalTitle;
        this.mPosterImagePathEnding = posterImagePathEnding;
        this.mBackdropImagePathEnding = backdropImagePathEnding;
        this.mOverview = overview;
        this.mAverageVote = averageVote;
        this.mReleaseDate = releaseDate;
    }

    /**
     * Function to return the title of selected movie
     * @return title of selected movie
     */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Function to set the title of selected movie if necessary
     * @param title
     */
    public void setTitle(String title) {
        this.mTitle = title;
    }

    /**
     * Function to return the original title of selected movie
     * @return original title of selected movie
     */
    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    /**
     * Function to set the original title of selected movie if necessary
     * @param originalTitle
     */
    public void setOriginalTitle(String originalTitle) {
        this.mOriginalTitle = originalTitle;
    }

    /**
     * Function to return the poster image url ending of selected movie
     * @return poster image url ending of selected movie
     */
    public String getPosterImagePathEnding() {
        return mPosterImagePathEnding;
    }

    /**
     * Function to set the poster image url ending of selected movie if necessary
     * @param posterImagePathEnding
     */
    public void setPosterImagePathEnding(String posterImagePathEnding) {
        this.mPosterImagePathEnding = posterImagePathEnding;
    }

    /**
     * Function to return the backdrop image url ending of selected movie
     * @return backdrop image url ending of selected movie
     */
    public String getBackdropImagePathEnding() {
        return mBackdropImagePathEnding;
    }

    /**
     * Function to set the backdrop image url ending of selected movie if necessary
     * @param backdropImagePathEnding
     */
    public void setBackdropImagePathEnding(String backdropImagePathEnding) {
        this.mBackdropImagePathEnding = backdropImagePathEnding;
    }

    /**
     * Function to return the overview of selected movie
     * @return overview of selected movie
     */
    public String getOverview() {
        return mOverview;
    }

    /**
     * Function to set the overview of selected movie if necessary
     * @param overview
     */
    public void setOverview(String overview) {
        this.mOverview = overview;
    }

    /**
     * Function to return the average vote of selected movie
     * @return average vote of selected movie
     */
    public String getAverageVote() {
        return mAverageVote;
    }

    /**
     * Function to set the average vote of selected movie if necessary
     * @param averageVote
     */
    public void setAverageVote(String averageVote) {
        this.mAverageVote = averageVote;
    }

    /**
     * Function to return the release date of selected movie
     * @return release date of selected movie
     */
    public String getReleaseDate() {
        return mReleaseDate;
    }

    /**
     * Function to set the release date of selected movie if necessary
     * @param releaseDate
     */
    public void setReleaseDate(String releaseDate) {
        this.mReleaseDate = releaseDate;
    }
}
