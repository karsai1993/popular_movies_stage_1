package com.udacity.popularmovies;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.udacity.popularmovies.model.Movie;
import com.udacity.popularmovies.utils.ImageUtils;

import java.util.List;

/**
 * Created by Laci on 17/02/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context mContext;
    private List<Movie> mMovieList;
    private ListItemClickListener mOnClickListener;

    public MovieAdapter(Context context, List<Movie> moviesList) {
        this.mContext = context;
        this.mMovieList = moviesList;
        this.mOnClickListener = (ListItemClickListener) context;
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int movieListItemId = R.layout.movie_list_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean isNeededToAttachToParentImmediately = false;
        View view = inflater.inflate(movieListItemId, parent, isNeededToAttachToParentImmediately);
        MovieViewHolder movieViewHolder = new MovieViewHolder(view);
        return movieViewHolder;
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        String posterImagePathEnding = mMovieList.get(position).getPosterImagePathEnding();
        ImageUtils.loadImage(mContext, posterImagePathEnding, holder.movieImageView);
    }

    @Override
    public int getItemCount() {
        return mMovieList.size();
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView movieImageView;

        public MovieViewHolder(View itemView) {
            super(itemView);

            movieImageView = itemView.findViewById(R.id.iv_movie_list_item);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            mOnClickListener.onListItemClick(index);
        }
    }

    public interface ListItemClickListener {
        void onListItemClick(int index);
    }
}
