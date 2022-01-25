package com.ravnnerdery.photo_challenge.photoList

import androidx.lifecycle.ViewModel
import com.ravnnerdery.photo_challenge.PhotosRepository

class PhotoListViewModel(val repo: PhotosRepository) : ViewModel() {

    val allPhotos = repo.allPhotosFromDatabase()

}