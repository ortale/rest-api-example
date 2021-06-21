package com.joseortale.ortalesoft.rest_api_example.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.joseortale.ortalesoft.rest_api_example.model.Album;

import java.util.List;

@Dao
public interface AlbumDao {
    @Query("SELECT * FROM album")
    List<Album> getAll();

    @Query("SELECT * FROM album WHERE id IN (:id)")
    Album getById(int id);

    @Insert
    void insertAll(Album... albums);

    @Update
    void update(Album album);
}
