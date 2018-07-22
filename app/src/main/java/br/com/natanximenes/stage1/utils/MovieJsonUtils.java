package br.com.natanximenes.stage1.utils;

import android.support.annotation.Nullable;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.natanximenes.stage1.domain.Movie;

import static br.com.natanximenes.stage1.domain.Movie.MOVIE_POSTER_PROP;
import static br.com.natanximenes.stage1.domain.Movie.RELEASE_DATE;
import static br.com.natanximenes.stage1.domain.Movie.SYNOPSIS_PROP;
import static br.com.natanximenes.stage1.domain.Movie.TITLE_PROP;
import static br.com.natanximenes.stage1.domain.Movie.USER_RATING_PROP;

public class MovieJsonUtils {
    private static final String ERROR_PROP = "status_code";
    private static final String MOVIES_PROP = "results";

    @Nullable
    public static List<Movie> getMovieListFromJson(String jsonString) throws JSONException {
        final List<Movie> movieList = new ArrayList<>();
        final JSONObject moviesJSON = new JSONObject(jsonString);

        if (moviesJSON.has(ERROR_PROP)) {
            return null;
        }

        final JSONArray moviesJSONArray = moviesJSON.getJSONArray(MOVIES_PROP);

        if (moviesJSONArray == null) {
            return null;
        }

        for (int i = 0; i < moviesJSONArray.length(); i++) {
            final JSONObject movieJSONObject = moviesJSONArray.getJSONObject(i);
            if (movieJSONObject == null){
                return null;
            }

            movieList.add(new Movie(
                    movieJSONObject.getString(TITLE_PROP),
                    movieJSONObject.getString(MOVIE_POSTER_PROP),
                    movieJSONObject.getString(SYNOPSIS_PROP),
                    movieJSONObject.getDouble(USER_RATING_PROP),
                    movieJSONObject.getString(RELEASE_DATE)));
        }

        return movieList;
    }
}
