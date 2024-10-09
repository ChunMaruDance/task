package com.chunmaru.task.di

import android.content.Context
import androidx.room.Room
import com.chunmaru.task.data.api.VideoApi
import com.chunmaru.task.data.database.VideoDatabase
import com.chunmaru.task.data.database.dao.VideoDao
import com.chunmaru.task.data.repository.VideoRepositoryImpl
import com.chunmaru.task.domain.repository.VideoRepository
import com.chunmaru.task.domain.usecase.GetVideosUseCase
import com.chunmaru.task.domain.usecase.GetVideosUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class Module {

    @Provides
    @Singleton
    fun provideVideoRepository(videoApi: VideoApi, videoDao: VideoDao): VideoRepository {
        return VideoRepositoryImpl(videoApi, videoDao)
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): VideoDatabase {
        return Room.databaseBuilder(
            context,
            VideoDatabase::class.java,
            "video_database"
        ).build()
    }

    @Provides
    fun provideVideoDao(database: VideoDatabase): VideoDao {
        return database.videoDao()
    }

    @Provides
    @Singleton
    fun provideGetVideosUseCase(videoRepository: VideoRepository): GetVideosUseCase {
        return GetVideosUseCaseImpl(videoRepository)
    }


}