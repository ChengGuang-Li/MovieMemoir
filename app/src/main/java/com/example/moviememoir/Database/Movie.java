package com.example.moviememoir.Database;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Movie {
    @PrimaryKey
    @NonNull
    private String movieId;

    @ColumnInfo(name = "movieName")
    private String movieName;

    @ColumnInfo(name = "releaseDate")
    private String releaseDate;

    @ColumnInfo(name = "addDateTime")
    private String addDateTime;

    @Ignore
    public Movie(String movieId){
        this.movieId = movieId;
    }

    public Movie(String movieId, String movieName, String releaseDate,String addDateTime) {
        this.movieId = movieId;
        this.movieName = movieName;
        this.releaseDate = releaseDate;
        this.addDateTime = addDateTime;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(int uid) {
        this.movieId= movieId;
    }

    public String getMovieName() {
        return movieName;
    }

    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getAddDateTime() {
        return addDateTime;
    }

    public void setAddDateTime (String addDate) {
        this.addDateTime = addDate;
    }
}
