package com.chunmaru.task.data.repository

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.domain.repository.VideoRepository

class FakeVideoRepositoryImpl : VideoRepository {

    private val data = mutableListOf<Video>()

    override suspend fun getVideos(): List<Video> {
        return data
    }

    override suspend fun saveVideos(videos: List<Video>) {
        data.addAll(videos)
    }

    override suspend fun getVideosFromDb(): List<Video> {
        return data
    }
}