package com.joseortale.ortalesoft.rest_api_example.data.api;

import com.joseortale.ortalesoft.rest_api_example.model.Album;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiEndpoints {
    @GET("albums")
    Call<List<Album>> getAlbums();
}
