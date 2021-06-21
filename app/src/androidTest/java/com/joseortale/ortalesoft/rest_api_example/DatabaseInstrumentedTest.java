package com.joseortale.ortalesoft.rest_api_example;

import android.content.Context;

import androidx.room.Room;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import com.joseortale.ortalesoft.rest_api_example.data.database.AlbumDao;
import com.joseortale.ortalesoft.rest_api_example.data.database.AppDatabase;
import com.joseortale.ortalesoft.rest_api_example.data.repository.AlbumRepository;
import com.joseortale.ortalesoft.rest_api_example.model.Album;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class DatabaseInstrumentedTest {
    private AlbumDao albumDao;
    private AppDatabase appDatabase;
    private AlbumRepository albumRepository;

    @Before
    public void createDatabase() {
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        appDatabase = Room.inMemoryDatabaseBuilder(appContext, AppDatabase.class).build();
        albumRepository = AlbumRepository.getInstance(appContext);
    }

    @Test
    public void assertGetData() {
        albumDao = appDatabase.albumDao();

        insertTestData(albumDao);

        List<Album> albumList = albumDao.getAll();
        assertFalse(albumList.isEmpty());
    }

    @Test
    public void assertExists() {
        albumDao = appDatabase.albumDao();

        insertTestData(albumDao);

        Album album = albumDao.getById(1);
        int actual = album.getId();
        assertEquals(actual, 1);
    }

    @After
    public void closeDatabase() {
        appDatabase.close();
    }

    private void insertTestData(AlbumDao albumDao) {
        Album album = new Album();
        album.setId(1);
        album.setTitle("Test test");
        album.setUserId(1);

        albumDao.insertAll(album);
    }
}