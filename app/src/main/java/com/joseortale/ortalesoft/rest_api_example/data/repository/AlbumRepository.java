package com.joseortale.ortalesoft.rest_api_example.data.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import com.joseortale.ortalesoft.rest_api_example.data.api.ApiEndpoints;
import com.joseortale.ortalesoft.rest_api_example.data.api.RetrofitClient;
import com.joseortale.ortalesoft.rest_api_example.data.database.AlbumDao;
import com.joseortale.ortalesoft.rest_api_example.data.database.AppDatabase;
import com.joseortale.ortalesoft.rest_api_example.helpers.Resource;
import com.joseortale.ortalesoft.rest_api_example.model.Album;

import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlbumRepository {
    private static AlbumRepository instance;

    private final ApiEndpoints apiEndpoints;

    private MutableLiveData<Resource<Album>> albumData;
    private AlbumDao albumDao;
    private AppDatabase appDatabase;

    private AlbumRepository(Context context) {
        apiEndpoints = RetrofitClient.getClient();
        appDatabase = Room.databaseBuilder(context, AppDatabase.class, "albums").allowMainThreadQueries().build();
    }

    public static AlbumRepository getInstance(Context context) {
        if (instance == null) {
            instance = new AlbumRepository(context);
        }

        return instance;
    }

    public MutableLiveData<Resource<Album>> getAlbums() {
        albumData = new MutableLiveData<>();
        albumDao = appDatabase.albumDao();

        apiEndpoints.getAlbums().enqueue(new Callback<List<Album>>() {
            List<Album> albumList = albumDao.getAll();

            @Override
            public void onResponse(Call<List<Album>> call, Response<List<Album>> response) {
                if (response.isSuccessful()) {
                    List<Album> albumsResponse = response.body();

                    if (albumsResponse != null) {
                        albumData.setValue(Resource.successWithList(albumsResponse));

                        for (Album album : albumsResponse) {
                            if (album.getId().equals(albumDao.getById(album.getId()).getId())) {
                                albumDao.update(album);
                            }

                            else {
                                albumDao.insertAll(album);
                            }
                        }
                    } else {
                        if (response.code() == 401) {
                            albumData.setValue(Resource.error("Not authorised"));
                        } else {
                            albumData.setValue(Resource.successWithList(albumDao.getAll()));
                        }
                    }
                } else {
                    if (albumList.isEmpty()) {
                        albumData.setValue(Resource.successWithList(albumDao.getAll()));
                    } else {
                        for (Album album : albumList) {
                            albumDao.insertAll(album);
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Album>> call, Throwable t) {
                if (albumDao.getAll().isEmpty()) {
                    albumData.setValue(Resource.error(Objects.requireNonNull(t.getMessage())));
                } else {
                    albumData.setValue(Resource.successWithList(albumDao.getAll()));
                }
            }
        });

        return albumData;
    }
}
