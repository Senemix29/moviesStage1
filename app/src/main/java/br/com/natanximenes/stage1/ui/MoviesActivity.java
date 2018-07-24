package br.com.natanximenes.stage1.ui;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.Group;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;
import br.com.natanximenes.stage1.domain.MoviesRetrieverAsyncTask;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static br.com.natanximenes.stage1.ui.MovieDetailsActivity.MOVIE_KEY;
import static br.com.natanximenes.stage1.utils.NetworkUtils.POPULAR;

public class MoviesActivity extends AppCompatActivity implements MoviesRetrieverAsyncTask
        .OnMoviesRetrievedListener, MovieViewHolder.OnMovieItemClickListener, View.OnClickListener {
    private MoviesRetrieverAsyncTask moviesRetrieverAsyncTask;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private Group errorGroup;
    private AppCompatButton retryButton;
    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.content_movies_recycler_view);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.content_movies_progress_bar);
        errorGroup = findViewById(R.id.content_movies_group_error);
        retryButton = findViewById(R.id.content_movies_button_retry);

        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        setupRecyclerView();

        retrieveMovies();
    }

    @Override
    protected void onDestroy() {
        if (moviesRetrieverAsyncTask != null) {
            moviesRetrieverAsyncTask.setOnMoviesRetrievedListener(null);
        }
        moviesAdapter.setOnMovieItemClickListener(null);
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.content_movies_button_retry) {
            errorGroup.setVisibility(GONE);
            recyclerView.setVisibility(GONE);
            progressBar.setVisibility(VISIBLE);

            retrieveMovies();
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movies.get(position));
        startActivity(intent);
    }

    @Override
    public void onMoviesRetrieved(@Nullable List<Movie> moviesRetrieved) {
        recyclerView.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        errorGroup.setVisibility(GONE);

        movies.clear();
        movies.addAll(moviesRetrieved);
        moviesAdapter.setMovieList(moviesRetrieved);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        errorGroup.setVisibility(VISIBLE);
    }

    private void setupRecyclerView() {
        moviesAdapter = new MoviesAdapter(movies, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void retrieveMovies() {
        moviesRetrieverAsyncTask = new MoviesRetrieverAsyncTask(this);
        moviesRetrieverAsyncTask.execute(POPULAR);
    }
}
