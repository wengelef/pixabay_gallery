package com.wengelef.pixabaygallery.data.repository

import com.wengelef.pixabaygallery.data.model.Photo

interface PhotoRepository {
    suspend fun search(query: String): Result<List<Photo>>
    suspend fun getDetail(id: String): Result<Photo>
}