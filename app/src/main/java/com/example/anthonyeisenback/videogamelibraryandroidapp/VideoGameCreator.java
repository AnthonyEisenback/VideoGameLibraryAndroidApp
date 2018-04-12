package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class VideoGameCreator {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String gameName;
    private String gameGenre;
    private String gameDescription;

    public VideoGameCreator(String gameName, String gameGenre, String gameDescription) {
        this.gameName = gameName;
        this.gameGenre = gameGenre;
        this.gameDescription = gameDescription;
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
}


