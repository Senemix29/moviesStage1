package br.com.natanximenes.stage1.ui;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;
import br.com.natanximenes.stage1.utils.NetworkUtils;

import static android.support.v7.content.res.AppCompatResources.getDrawable;

public class MovieViewHolder extends RecyclerView.ViewHolder {
    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    private ImageView posterImage;

    public MovieViewHolder(View itemView,
                           @Nullable final OnMovieItemClickListener movieItemClickListener) {
        super(itemView);
        posterImage = itemView.findViewById(R.id.item_movie_view_holder_poster);
        posterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (movieItemClickListener != null) {
                    movieItemClickListener.onMovieItemClick(getAdapterPosition());
                }
            }
        });
    }

    public void bind(Movie movie) {
        String path = NetworkUtils.BASE_IMAGE_URL + movie.getMoviePosterUrl();
        Drawable placeholderDrawable = getDrawable(itemView.getContext(),
                R.drawable.vector_movie_poster);

        Picasso.with(itemView.getContext())
                .load(path)
                .placeholder(placeholderDrawable)
                .into(posterImage);
    }

    public interface OnMovieItemClickListener {
        void onMovieItemClick(int position);
    }
}
