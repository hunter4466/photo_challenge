package com.ravnnerdery.photo_challenge.enlargedPhoto

import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.repository.PhotosRepository

class EnlargedPhotoViewModel(private val repo : PhotosRepository) : ViewModel() {
    var currentPosition: Int? = 0
    fun allPhotos() = repo.allPhotosFromDatabase()
}