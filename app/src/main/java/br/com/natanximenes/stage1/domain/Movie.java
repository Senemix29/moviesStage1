package br.com.natanximenes.stage1.domain;

public class Movie {
    public static final String TITLE_PROP = "title";
    public static final String MOVIE_POSTER_PROP = "poster_path";
    public static final String SYNOPSIS_PROP = "overview";
    public static final String USER_RATING_PROP = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    
    private String title;
    private String moviePosterUrl;
    private String synopsis;
    private Double userRating;
    private String releaseDate;

    public Movie(String title, String moviePosterUrl, String synopsis, Double userRating, String
            releaseDate) {
        this.title = title;
        this.moviePosterUrl = moviePosterUrl;
        this.synopsis = synopsis;
        this.userRating = userRating;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;

    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMoviePosterUrl() {
        return moviePosterUrl;
    }

    public void setMoviePosterUrl(String moviePosterUrl) {
        this.moviePosterUrl = moviePosterUrl;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }
    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Double getUserRating() {
        return userRating;
    }

    public void setUserRating(Double userRating) {
        this.userRating = userRating;
    }
}
