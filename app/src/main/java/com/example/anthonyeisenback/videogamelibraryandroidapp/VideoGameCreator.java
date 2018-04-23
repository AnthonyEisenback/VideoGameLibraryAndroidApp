package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.util.Date;

@Entity
public class VideoGameCreator {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gameName;
    private String gameGenre;
    private String gameDescription;
    private boolean isCheckedOut;
    private Date date;

    @NonNull
    public int getId() {
        return id;
    }

    public void setId(@NonNull int id) {
        this.id = id;
    }

    public boolean isCheckedOut() {
        return isCheckedOut;
    }

    public void setCheckedOut(boolean checkedOut) {
        isCheckedOut = checkedOut;
    }

    public VideoGameCreator(String gameName, String gameGenre, String gameDescription, boolean isCheckedOut, Date date) {
        this.gameName = gameName;
        this.gameGenre = gameGenre;
        this.gameDescription = gameDescription;
        this.isCheckedOut = isCheckedOut;
        this.date = date;
    }



    public String getGameName() {
        return gameName;
    }

    public String getGameGenre() {
        return gameGenre;
    }

    public String getGameDescription() {
        return gameDescription;
    }


    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}


