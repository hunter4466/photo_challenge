package com.ravnnerdery.photo_challenge.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.ravnnerdery.photo_challenge.database.PhotosDatabase
import com.ravnnerdery.photo_challenge.database.tables.Photo
import com.ravnnerdery.photo_challenge.enlargedPhoto.EnlargedPhotoViewModel
import com.ravnnerdery.photo_challenge.network.PhotosApi
import com.ravnnerdery.photo_challenge.photoList.PhotoListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

interface PhotosRepository {
    fun allPhotosFromDatabase(): LiveData<List<Photo>>
    fun loadFromApiAndSetIntoDatabase()
}
class PhotosRepositoryImpl(application: Application) : PhotosRepository {
    private var viewModelJob = Job()
    private val uiScope = CoroutineScope(Dispatchers.Main + viewModelJob)
    init {
        loadFromApiAndSetIntoDatabase()
    }
    private val databaseInstance = PhotosDatabase.getInstance(application).databaseDao

    override fun allPhotosFromDatabase(): LiveData<List<Photo>> = databaseInstance.getPhotos()

    override fun loadFromApiAndSetIntoDatabase() {
        PhotosApi.retrofitService.getPhotos().enqueue(object : Callback<List<Photo>> {
            override fun onResponse(call: Call<List<Photo>>, response: Response<List<Photo>>) {
                response.body()?.forEach { elm ->
                    addPostToDatabase(elm.id, elm.title, elm.url, elm.thumbnailUrl)
                }
            }

            override fun onFailure(call: Call<List<Photo>>, t: Throwable) {
                println(t.message)
            }
        })
    }
    private fun addPostToDatabase(id: Long, title: String, url: String, thumbUrl: String) {
        uiScope.launch(Dispatchers.IO) {
            val newPhoto = Photo(id, title, url, thumbUrl)
            databaseInstance.insertPhoto(newPhoto)
        }
    }
}

val appModule = module {
    single<PhotosRepository> { PhotosRepositoryImpl(androidApplication()) }
    viewModel{ PhotoListViewModel(get()) }
    viewModel{ EnlargedPhotoViewModel(get()) }
}