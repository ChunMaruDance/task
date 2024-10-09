package com.chunmaru.task.domain.usecase

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.domain.repository.VideoRepository
import javax.inject.Inject

class GetVideosUseCaseImpl @Inject constructor(
    private val videoRepository: VideoRepository
) : GetVideosUseCase {

    override suspend operator fun invoke(): List<Video> {
        return videoRepository.getVideos()
    }
}