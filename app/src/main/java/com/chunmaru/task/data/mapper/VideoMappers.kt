package com.chunmaru.task.data.mapper

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.data.database.entity.VideoEntity

fun Video.toEntity(): VideoEntity {
    return VideoEntity(
        title = this.title,
        description = this.description,
        sources = this.sources[0],
        subtitle = this.subtitle,
        thumb = this.thumb,
    )
}

fun List<Video>.toEntityList(): List<VideoEntity> {
    return this.map { it.toEntity() }
}

fun VideoEntity.toVideo(): Video {
    return Video(
        title = this.title,
        description = this.description,
        subtitle = this.subtitle,
        thumb = this.thumb,
        sources = listOf(this.sources)
    )
}

fun List<VideoEntity>.toVideoList(): List<Video> {
    return this.map { it.toVideo() }
}


