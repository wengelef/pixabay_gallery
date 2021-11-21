package com.wengelef.pixabaygallery

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.wengelef.pixabaygallery.data.repository.PhotoRepository
import com.wengelef.pixabaygallery.repository.PhotoRepositoryImpl
import com.wengelef.pixabaygallery.repository.remote.PhotosApiService
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RepositoryComponent {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://pixabay.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val photosRemote by lazy { retrofit.create(PhotosApiService::class.java) }

    private lateinit var driver: AndroidSqliteDriver

    private val database by lazy { Database(driver) }

    val photosRepository: PhotoRepository by lazy {
        PhotoRepositoryImpl(BuildConfig.API_KEY, photosRemote, database, Dispatchers.IO)
    }

    fun initDriver(context: Context) {
        driver = AndroidSqliteDriver(Database.Schema, context, "photos.db")
    }
}