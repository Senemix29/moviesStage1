package br.com.natanximenes.stage1.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;
import android.widget.TextView;

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
        .OnMoviesRetrievedListener, MovieViewHolder.OnMovieItemClickListener {
    private MoviesRetrieverAsyncTask moviesRetrieverAsyncTask;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private TextView errorTextView;

    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.content_movies_recycler_view);
        toolbar = findViewById(R.id.toolbar);
        progressBar = findViewById(R.id.progressBar);
        errorTextView = findViewById(R.id.content_movies_textview_error);

        setSupportActionBar(toolbar);
        setupRecyclerView();

        moviesRetrieverAsyncTask = new MoviesRetrieverAsyncTask(this);
        moviesRetrieverAsyncTask.execute(POPULAR);
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
    public void onMoviesRetrieved(@Nullable List<Movie> movies) {
        recyclerView.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        errorTextView.setVisibility(GONE);

        this.movies = (ArrayList<Movie>) movies;
        moviesAdapter.setMovieList(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        errorTextView.setVisibility(VISIBLE);
    }

    @Override
    public void onMovieItemClick(int position) {
        Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movies.get(position));
        startActivity(intent);
    }

    private void setupRecyclerView() {
        moviesAdapter = new MoviesAdapter(movies, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);
    }
}
