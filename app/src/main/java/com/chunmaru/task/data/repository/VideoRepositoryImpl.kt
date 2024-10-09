package com.chunmaru.task.data.repository

import com.chunmaru.task.data.api.VideoApi
import com.chunmaru.task.data.database.dao.VideoDao
import com.chunmaru.task.data.mapper.toEntityList
import com.chunmaru.task.data.mapper.toVideoList
import com.chunmaru.task.data.model.MediaResponse
import com.chunmaru.task.data.model.Video
import com.chunmaru.task.domain.repository.VideoRepository
import com.google.gson.Gson

class VideoRepositoryImpl(
    private val videoApi: VideoApi,
    private val videoDao: VideoDao
) : VideoRepository {

    override suspend fun getVideos(): List<Video> {
        return try {
            val response = videoApi.getVideos()
            val videos = parseVideoResponse(response)
            saveVideos(videos)
            videos
        } catch (e: Exception) {
            getVideosFromDb()
        }
    }

    override suspend fun saveVideos(videos: List<Video>) {
        videoDao.insertVideos(videos.toEntityList())
    }

    override suspend fun getVideosFromDb(): List<Video> {
        val videos = videoDao.getVideos()
        return videos.toVideoList()
    }

    private fun parseVideoResponse(response: String): List<Video> {
        val jsonString = response.substringAfter("var mediaJSON = ").trim()
        val cleanedJsonString = jsonString.removeSuffix(";")

        val gson = Gson()
        val mediaJson = gson.fromJson(cleanedJsonString, MediaResponse::class.java)

        return mediaJson.categories.firstOrNull()?.videos ?: emptyList()
    }
}