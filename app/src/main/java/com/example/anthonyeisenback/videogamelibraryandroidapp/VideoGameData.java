package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.app.Application;
import android.arch.persistence.room.Room;

public class VideoGameData extends Application{
    private VideoGameData database;

    public static String DATABASE_NAME = "video_game_database";

    @Override
    public void onCreate() {
        super.onCreate();

        Room.databaseBuilder(getApplicationContext(),
                VideoGameDatabase.class, "database-name").build();
    }

    public VideoGameData getDatabase() {
        return database;
    }
}
