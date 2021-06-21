package com.joseortale.ortalesoft.rest_api_example.data.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.joseortale.ortalesoft.rest_api_example.model.Album;

@Database(entities = {Album.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract AlbumDao albumDao();
}
