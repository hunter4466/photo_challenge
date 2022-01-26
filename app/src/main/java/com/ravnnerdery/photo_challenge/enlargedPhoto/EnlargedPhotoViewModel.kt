package com.ravnnerdery.photo_challenge.enlargedPhoto

import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.PhotosRepository

class EnlargedPhotoViewModel(private val repo : PhotosRepository) : ViewModel() {
    fun allPhotos() = repo.allPhotosFromDatabase()
}