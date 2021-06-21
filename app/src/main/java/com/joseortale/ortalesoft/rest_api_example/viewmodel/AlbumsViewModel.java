package com.joseortale.ortalesoft.rest_api_example.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.joseortale.ortalesoft.rest_api_example.data.repository.AlbumRepository;
import com.joseortale.ortalesoft.rest_api_example.helpers.Resource;
import com.joseortale.ortalesoft.rest_api_example.model.Album;

public class AlbumsViewModel extends ViewModel {
    private MutableLiveData<Resource<Album>> albumData;
    private AlbumRepository albumRepository;

    public void getAlbums(Context context) {
        albumRepository = AlbumRepository.getInstance(context);
        albumData = albumRepository.getAlbums();
    }

    public LiveData<Resource<Album>> getAlbumsRepository() {
        return albumData;
    }
}
