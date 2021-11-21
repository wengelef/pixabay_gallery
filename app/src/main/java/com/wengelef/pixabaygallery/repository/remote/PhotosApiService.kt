package com.wengelef.pixabaygallery.repository.remote

import com.wengelef.pixabaygallery.repository.remote.model.PhotosResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PhotosApiService {
    @GET("/api")
    suspend fun searchImages(
        @Query("key") key: String,
        @Query("q", encoded = true) query: String
    ): PhotosResponse
}
