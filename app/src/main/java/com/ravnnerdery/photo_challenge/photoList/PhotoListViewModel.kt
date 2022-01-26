package com.ravnnerdery.photo_challenge.photoList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.PhotosRepository

class PhotoListViewModel(private val repo : PhotosRepository) : ViewModel() {

    private val _navigateToSnapshot = MutableLiveData<Long?>()

    val navigateToSnapshot
        get() = _navigateToSnapshot

    fun onPhotoClicked(id: Long){
        _navigateToSnapshot.value = id
    }

    fun onSnapshotNavigated() {
        _navigateToSnapshot.value = null
    }

    fun allPhotos() = repo.allPhotosFromDatabase()
}