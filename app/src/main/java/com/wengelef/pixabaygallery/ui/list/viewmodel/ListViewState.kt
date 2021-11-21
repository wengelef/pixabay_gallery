package com.wengelef.pixabaygallery.ui.list.viewmodel

import com.wengelef.pixabaygallery.data.model.Photo

sealed interface ListViewState
object Idle : ListViewState
object Loading : ListViewState
data class Result(val photos: List<Photo>) : ListViewState
object Error : ListViewState