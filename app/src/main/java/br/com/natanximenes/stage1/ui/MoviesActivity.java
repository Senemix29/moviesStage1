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
import static br.com.natanximenes.stage1.utils.NetworkUtils.TOP_RATED;

public class MoviesActivity extends AppCompatActivity implements MoviesRetrieverAsyncTask
        .OnMoviesRetrievedListener, MovieViewHolder.OnMovieItemClickListener, View.OnClickListener {
    public static final String MOVIE_LIST = "MOVIE_LIST";
    public static final String CURRENT_SORT_TYPE = "CURRENT_SORT_TYPE";

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private Group errorGroup;
    private AppCompatButton retryButton;

    private ArrayList<Movie> movies;
    private String currentSortType = POPULAR;
    private MoviesRetrieverAsyncTask moviesRetrieverAsyncTask;

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

        retryButton.setOnClickListener(this);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        setupRecyclerView();

        if (savedInstanceState != null) {
            final List<Movie> movieList = savedInstanceState.getParcelableArrayList(MOVIE_LIST);
            currentSortType = savedInstanceState.getString(CURRENT_SORT_TYPE);

            showData();
            showMovieList(movieList);
            return;
        }

        retrieveMovies(currentSortType);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(MOVIE_LIST, movies);
        outState.putString(CURRENT_SORT_TYPE, currentSortType);
        super.onSaveInstanceState(outState);
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
        final int id = item.getItemId();

        if (id == R.id.menu_movies_sort_by_popularity) {
            currentSortType = POPULAR;
            retrieveMovies(POPULAR);
            return true;
        }

        if (id == R.id.menu_movies_sort_by_top_rated) {
            currentSortType = TOP_RATED;
            retrieveMovies(TOP_RATED);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.content_movies_button_retry) {
            retrieveMovies(currentSortType);
        }
    }

    @Override
    public void onMovieItemClick(int position) {
        final Intent intent = new Intent(this, MovieDetailsActivity.class);
        intent.putExtra(MOVIE_KEY, movies.get(position));
        startActivity(intent);
    }

    @Override
    public void onMoviesRetrieved(@Nullable List<Movie> moviesRetrieved) {
        showData();
        showMovieList(moviesRetrieved);
    }

    @Override
    public void onError() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                showErrorState();
            }
        });
    }

    private void setupRecyclerView() {
        moviesAdapter = new MoviesAdapter(movies, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);
    }

    private void showData() {
        recyclerView.setVisibility(VISIBLE);
        progressBar.setVisibility(GONE);
        errorGroup.setVisibility(GONE);
    }

    private void hideData() {
        errorGroup.setVisibility(GONE);
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(VISIBLE);
    }

    private void showErrorState(){
        recyclerView.setVisibility(GONE);
        progressBar.setVisibility(GONE);
        errorGroup.setVisibility(VISIBLE);
    }

    public void showMovieList(@Nullable List<Movie> movieList) {
        movies.clear();
        movies.addAll(movieList);
        moviesAdapter.setMovieList(movieList);
        moviesAdapter.notifyDataSetChanged();
    }

    private void retrieveMovies(@Nullable String sortType) {
        hideData();

        if (sortType == null) {
            sortType = POPULAR;
        }
        moviesRetrieverAsyncTask = new MoviesRetrieverAsyncTask(this);
        moviesRetrieverAsyncTask.execute(sortType);
    }
}
