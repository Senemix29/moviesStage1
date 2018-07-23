package br.com.natanximenes.stage1.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;
import br.com.natanximenes.stage1.utils.NetworkUtils;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    private ImageView posterImage;
    private OnMovieItemClickListener movieItemClickListener;

    public MovieViewHolder(View itemView, final OnMovieItemClickListener movieItemClickListener) {
        super(itemView);
        posterImage = itemView.findViewById(R.id.item_movie_view_holder_poster);
        posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                movieItemClickListener.onMovieItemClick(getAdapterPosition());
            }
        });
    }

    public void bind(Movie movie) {
        Picasso.with(itemView.getContext()).load(
                NetworkUtils.BASE_IMAGE_URL + movie.getMoviePosterUrl())
                .into(posterImage);
    }

    public interface OnMovieItemClickListener {
        void onMovieItemClick(int position);
    }
}
