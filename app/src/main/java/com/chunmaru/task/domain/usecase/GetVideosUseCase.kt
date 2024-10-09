package com.chunmaru.task.domain.usecase

import com.chunmaru.task.data.model.Video

interface GetVideosUseCase {
    suspend operator fun invoke(): List<Video>
}