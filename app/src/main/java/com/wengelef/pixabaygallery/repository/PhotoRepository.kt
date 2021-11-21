package com.wengelef.pixabaygallery.repository

import com.wengelef.pixabaygallery.Database
import com.wengelef.pixabaygallery.PhotoQueries
import com.wengelef.pixabaygallery.data.model.Photo
import com.wengelef.pixabaygallery.data.repository.PhotoRepository
import com.wengelef.pixabaygallery.repository.remote.PhotosApiService
import com.wengelef.pixabaygallery.repository.remote.model.HitDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class PhotoRepositoryImpl(
    private val apiKey: String,
    private val photosApiService: PhotosApiService,
    private val database: Database,
    private val dispatcher: CoroutineDispatcher
) : PhotoRepository {

    override suspend fun search(query: String): Result<List<Photo>> = withContext(dispatcher) {
        photosApiService.runCatching { searchImages(apiKey, query) }
            .mapCatching { response ->
                database.photoQueries.transaction {
                    database.photoQueries.removeAll()
                    database.photoQueries.insertAll(response.hits)
                }
            }
            .mapCatching { database.photoQueries.getAll(::Photo).executeAsList() }
    }

    override suspend fun getDetail(id: String): Result<Photo> = withContext(dispatcher) {
        database.runCatching { photoQueries.findById(id, ::Photo).executeAsOne() }
    }

    private fun PhotoQueries.insertAll(hitDtos: List<HitDto>) = hitDtos
        .forEach { (id, previewUrl, imageUrl, user, tags, likes, downloads, comments) ->
            insert(id, previewUrl, imageUrl, user, tags, likes, downloads, comments)
        }
}