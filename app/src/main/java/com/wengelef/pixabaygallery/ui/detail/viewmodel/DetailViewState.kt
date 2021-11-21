package com.wengelef.pixabaygallery.ui.detail.viewmodel

import com.wengelef.pixabaygallery.data.model.Photo

sealed interface DetailViewState
object Error : DetailViewState
data class Result(val photo: Photo) : DetailViewState