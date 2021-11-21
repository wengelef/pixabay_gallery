package com.wengelef.pixabaygallery.ui.list.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wengelef.pixabaygallery.RepositoryComponent
import com.wengelef.pixabaygallery.data.repository.PhotoRepository
import kotlinx.coroutines.launch

class ListViewModel(private val repository: PhotoRepository = RepositoryComponent.photosRepository) : ViewModel() {

    private val mutableViewState = MutableLiveData<ListViewState>(Idle)
    val viewState: LiveData<ListViewState> = mutableViewState

    fun searchPhoto(query: String) {
        viewModelScope.launch {
            mutableViewState.value = Loading
            mutableViewState.value = repository.search(query)
                .fold(
                    onSuccess = ::Result,
                    onFailure = { Error }
                )
        }
    }
}
