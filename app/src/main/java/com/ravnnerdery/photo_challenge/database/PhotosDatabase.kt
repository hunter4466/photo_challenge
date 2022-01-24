package com.ravnnerdery.photo_challenge.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ravnnerdery.photo_challenge.database.tables.Photo

@Database(entities = [Photo::class], version = 1, exportSchema = false)
abstract class PostsDatabase: RoomDatabase() {
    abstract val databaseDao: DatabaseDao

    companion object{
        @Volatile
        private var INSTANCE: PostsDatabase? = null

        fun getInstance(context: Context) : PostsDatabase {
            synchronized(this){
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        PostsDatabase::class.java,
                        "Photos_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}