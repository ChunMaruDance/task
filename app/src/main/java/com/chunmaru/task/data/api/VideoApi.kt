package com.chunmaru.task.data.api

import com.chunmaru.task.data.model.MediaResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers


interface VideoApi {

    @Headers("Content-Type: application/json")
    @GET("jsturgis/3b19447b304616f18657/raw/a8c1f60074542d28fa8da4fe58c3788610803a65/gistfile1.txt")
    suspend fun getVideos(): String
}
