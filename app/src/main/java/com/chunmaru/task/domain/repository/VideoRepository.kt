package com.chunmaru.task.domain.repository

import com.chunmaru.task.data.model.Video

interface VideoRepository {
    suspend fun getVideosFromApi(): List<Video>
    suspend fun saveVideos(videos: List<Video>)
    suspend fun getVideosFromDb(): List<Video>
}