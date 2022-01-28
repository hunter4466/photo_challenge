package com.ravnnerdery.photo_challenge.database.tables;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photo_table")
class Photo(
    @PrimaryKey(autoGenerate = false)
    var id: Long,
    @ColumnInfo(name = "title")
    var title: String,
    @ColumnInfo(name = "url")
    var url: String,
    @ColumnInfo(name = "thumbnailUrl")
    var thumbnailUrl: String
)
