package com.wengelef.pixabaygallery.repository.remote.model

import com.google.gson.annotations.SerializedName

data class PhotosResponse(@SerializedName("hits") val hits: List<HitDto>)
