package br.com.natanximenes.stage1.ui;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;

import static br.com.natanximenes.stage1.ui.MovieViewHolder.OnMovieItemClickListener;

public class MoviesAdapter extends RecyclerView.Adapter<MovieViewHolder> {
    private List<Movie> movieList;
    private OnMovieItemClickListener onMovieItemClickListener;

    public MoviesAdapter(List<Movie> movieList, OnMovieItemClickListener onMovieItemClickListener) {
        this.movieList = movieList;
    }

    public void setOnMovieItemClickListener(@Nullable OnMovieItemClickListener
                                                    onMovieItemClickListener) {
        this.onMovieItemClickListener = onMovieItemClickListener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MovieViewHolder(
                LayoutInflater
                        .from(parent.getContext())
                        .inflate(R.layout.item_movie_view_holder, parent, false),
                onMovieItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }
}
