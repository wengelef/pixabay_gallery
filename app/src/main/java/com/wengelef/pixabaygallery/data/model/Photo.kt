package com.wengelef.pixabaygallery.data.model

data class Photo(
    val id: String,
    val previewUrl: String,
    val imageUrl: String,
    val user: String,
    val tags: String,
    val numberOfLikes: Int,
    val numberOfDownloads: Int,
    val numberOfComments: Int,
)
