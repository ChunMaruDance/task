package com.chunmaru.task.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chunmaru.task.data.database.dao.VideoDao
import com.chunmaru.task.data.database.entity.VideoEntity

@Database(entities = [VideoEntity::class], version = 1)
abstract class VideoDatabase : RoomDatabase() {

    abstract fun videoDao(): VideoDao


}