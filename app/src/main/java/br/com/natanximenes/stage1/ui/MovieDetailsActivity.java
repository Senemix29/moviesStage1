package br.com.natanximenes.stage1.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;

import static br.com.natanximenes.stage1.utils.NetworkUtils.BASE_IMAGE_URL;

public class MovieDetailsActivity extends AppCompatActivity {
    public static final String MOVIE_KEY = "movie";
    private TextView titleTextView;
    private TextView relaseDateTextView;
    private TextView ratingTextView;
    private TextView synopsisTextView;
    private ImageView posterImageView;
    private CollapsingToolbarLayout collapsingToolbarLayout;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        relaseDateTextView = findViewById(R.id.content_movie_detail_textview_release_date);
        titleTextView = findViewById(R.id.content_movie_detail_textview_title);
        ratingTextView = findViewById(R.id.content_movie_detail_textview_user_rating);
        synopsisTextView = findViewById(R.id.content_movie_detail_textview_synopsis);
        posterImageView = findViewById(R.id.activity_movie_detail_poster);
        collapsingToolbarLayout = findViewById(R.id.activity_movie_detail_collapasing_toolbar);

        collapsingToolbarLayout.setTitle(getString(R.string.content_movie_detail_title));
        collapsingToolbarLayout.setExpandedTitleColor(Color.TRANSPARENT);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            movie = extras.getParcelable(MOVIE_KEY);
        }

        if (movie != null) {
            String path = BASE_IMAGE_URL + movie.getMoviePosterUrl();
            Picasso.with(this)
                    .load(path)
                    .placeholder(R.drawable.vector_movie_poster)
                    .into(posterImageView);

            titleTextView.setText(movie.getTitle());
            relaseDateTextView.setText(getString(R.string.content_movie_detail_release_date,
                    movie.getReleaseDate()));
            ratingTextView.setText(getString(R.string.content_movie_detail_user_rating,
                    movie.getUserRating().toString()));
            synopsisTextView.setText(movie.getSynopsis());
        }
    }
}
