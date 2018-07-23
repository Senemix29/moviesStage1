package br.com.natanximenes.stage1.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import br.com.natanximenes.stage1.R;
import br.com.natanximenes.stage1.domain.Movie;
import br.com.natanximenes.stage1.domain.MoviesRetrieverAsyncTask;

import static br.com.natanximenes.stage1.utils.NetworkUtils.POPULAR;

public class MoviesActivity extends AppCompatActivity implements MoviesRetrieverAsyncTask
        .OnMoviesRetrievedListener, MovieViewHolder.OnMovieItemClickListener {
    private MoviesRetrieverAsyncTask moviesRetrieverAsyncTask;

    private RecyclerView recyclerView;
    private Toolbar toolbar;
    private MoviesAdapter moviesAdapter;

    private ArrayList<Movie> movies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        movies = new ArrayList<>();

        recyclerView = findViewById(R.id.content_movies_recycler_view);
        toolbar = findViewById(R.id.toolbar);

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
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMoviesRetrieved(@Nullable List<Movie> movies) {
        this.movies = (ArrayList<Movie>) movies;
        moviesAdapter.setMovieList(movies);
        moviesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onError() {

    }

    @Override
    public void onMovieItemClick(int position) {

    }

    private void setupRecyclerView() {
        moviesAdapter = new MoviesAdapter(movies, this);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(moviesAdapter);
    }
}
