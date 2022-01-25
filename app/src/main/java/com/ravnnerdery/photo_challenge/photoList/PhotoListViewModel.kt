package com.ravnnerdery.photo_challenge.photoList

import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.PhotosRepository

class PhotoListViewModel(private val repo : PhotosRepository) : ViewModel() {
    fun allPhotos() = repo.allPhotosFromDatabase()
}