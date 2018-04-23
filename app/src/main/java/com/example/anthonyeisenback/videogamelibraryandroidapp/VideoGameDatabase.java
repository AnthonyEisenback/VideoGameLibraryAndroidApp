package com.example.anthonyeisenback.videogamelibraryandroidapp;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;

@Database(entities = VideoGameCreator.class, version = 2)
@TypeConverters(DateConverter.class)

public abstract class VideoGameDatabase extends RoomDatabase{

    public abstract VideoGameDAO videoGameDAO();


}
