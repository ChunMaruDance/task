package com.chunmaru.task.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("videos")
data class VideoEntity(
    @PrimaryKey  val title: String,
    val description: String,
    val sources: String,
    val subtitle: String,
    val thumb: String,

)

