package com.ravnnerdery.photo_challenge.photoList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.repository.PhotosRepository

class PhotoListViewModel(private val repo : PhotosRepository) : ViewModel() {

    private val _navigateToSnapshot = MutableLiveData<Long?>()
    var currentPosition: Int? = 0

    val navigateToSnapshot
        get() = _navigateToSnapshot

    fun startRefreshingData(){
        repo.loadFromApiAndSetIntoDatabase()
    }

    fun onPhotoClicked(id: Long){
        _navigateToSnapshot.value = id
    }

    fun onSnapshotNavigated() {
        _navigateToSnapshot.value = null
    }

    fun allPhotos() = repo.allPhotosFromDatabase()
}