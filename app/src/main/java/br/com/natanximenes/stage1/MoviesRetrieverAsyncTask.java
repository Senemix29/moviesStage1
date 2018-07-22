package br.com.natanximenes.stage1;

import android.os.AsyncTask;
import android.support.annotation.Nullable;

import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.List;

import br.com.natanximenes.stage1.utils.NetworkUtils;

import static br.com.natanximenes.stage1.utils.MovieJsonUtils.getMovieListFromJson;
import static br.com.natanximenes.stage1.utils.NetworkUtils.getResponseFromHttpUrl;

public class MoviesRetrieverAsyncTask extends AsyncTask<String, Void, List<Movie>> {
    @Nullable
    private OnMoviesRetrievedListener onMoviesRetrievedListener;

    public MoviesRetrieverAsyncTask(@Nullable OnMoviesRetrievedListener onMoviesRetrievedListener) {
        this.onMoviesRetrievedListener = onMoviesRetrievedListener;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        final URL url = NetworkUtils.buildUrl(params[0]);
        try {
            return getMovieListFromJson(getResponseFromHttpUrl(url));
        } catch (IOException e) {
            if (onMoviesRetrievedListener != null) {
                onMoviesRetrievedListener.onError();
            }
        } catch (JSONException e) {
            if (onMoviesRetrievedListener != null) {
                onMoviesRetrievedListener.onError();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> movies) {
        if (onMoviesRetrievedListener != null) {
            onMoviesRetrievedListener.onMoviesRetrieved(movies);
        }
    }

    public interface OnMoviesRetrievedListener {
        void onMoviesRetrieved(@Nullable List<Movie> movies);
        void onError();
    }

}
