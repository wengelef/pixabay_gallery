package com.wengelef.pixabaygallery.ui.detail.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wengelef.pixabaygallery.RepositoryComponent
import com.wengelef.pixabaygallery.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class DetailViewModel(private val repository: PhotoRepository = RepositoryComponent.photosRepository) : ViewModel() {

    private val mutablePhoto = MutableLiveData<DetailViewState>(null)
    val detailState: LiveData<DetailViewState> = mutablePhoto

    fun getDetail(id: String) {
        viewModelScope.launch {
            mutablePhoto.value = repository.getDetail(id)
                .fold(
                    onSuccess = ::Result,
                    onFailure = { Error }
                )
        }
    }
}