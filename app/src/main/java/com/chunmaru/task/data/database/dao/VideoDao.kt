package com.chunmaru.task.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.chunmaru.task.data.database.entity.VideoEntity

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideos(videos: List<VideoEntity>)

    @Query("SELECT * FROM videos")
    suspend fun getVideos(): List<VideoEntity>

    @Query("DELETE FROM videos")
    suspend fun deleteAllVideos()
}