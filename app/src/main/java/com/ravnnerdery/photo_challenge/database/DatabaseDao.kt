package com.ravnnerdery.photo_challenge.database;

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ravnnerdery.photo_challenge.database.tables.Photo

@Dao
interface DatabaseDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPhoto(photo: Photo)

    @Query("SELECT * from photo_table LIMIT 100")
    fun getPhotos(): LiveData<List<Photo>>

    @Query("SELECT url from photo_table WHERE id = :key")
    fun getSinglePhoto(key: Long): String
}
