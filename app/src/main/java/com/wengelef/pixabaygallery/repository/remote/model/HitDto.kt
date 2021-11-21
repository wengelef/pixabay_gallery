package com.wengelef.pixabaygallery.repository.remote.model

import com.google.gson.annotations.SerializedName

data class HitDto(
    @SerializedName("id") val id: String,
    @SerializedName("previewURL") val previewUrl: String,
    @SerializedName("largeImageURL") val imageUrl: String,
    @SerializedName("user") val user: String,
    @SerializedName("tags") val tags: String,
    @SerializedName("likes") val likes: Int,
    @SerializedName("downloads") val downloads: Int,
    @SerializedName("comments") val comments: Int,
)