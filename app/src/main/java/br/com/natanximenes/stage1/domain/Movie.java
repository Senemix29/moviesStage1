package br.com.natanximenes.stage1.domain;

import android.os.Parcel;
import android.os.Parcelable;

public class Movie implements Parcelable {
    public static final String TITLE_PROP = "title";
    public static final String MOVIE_POSTER_PROP = "poster_path";
    public static final String SYNOPSIS_PROP = "overview";
    public static final String USER_RATING_PROP = "vote_average";
    public static final String RELEASE_DATE = "release_date";
    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    protected Movie(Parcel in) {
        title = in.readString();
        moviePosterUrl = in.readString();
        synopsis = in.readString();
        if (in.readByte() == 0) {
            userRating = null;
        } else {
            userRating = in.readDouble();
        }
        releaseDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(moviePosterUrl);
        parcel.writeString(synopsis);
        if (userRating == null) {
            parcel.writeByte((byte) 0);
        } else {
            parcel.writeByte((byte) 1);
            parcel.writeDouble(userRating);
        }
        parcel.writeString(releaseDate);
    }

    @Override
    public int describeContents() {
        return 0;
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
