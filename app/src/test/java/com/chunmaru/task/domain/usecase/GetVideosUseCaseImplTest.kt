package com.chunmaru.task.domain.usecase

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.data.repository.FakeVideoRepositoryImpl
import com.chunmaru.task.domain.repository.VideoRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVideosUseCaseImplTest {

    private lateinit var getVideosUseCase: GetVideosUseCase
//    private lateinit var fakeVideoRepositoryImpl: FakeVideoRepositoryImpl

    private val videoRepository = mockk<VideoRepository>(relaxed = true)

    @Before
    fun setUp() {

//        fakeVideoRepositoryImpl = FakeVideoRepositoryImpl()

        val testList = mutableListOf<Video>()

        ('a'..'z').forEachIndexed { index, c ->
            testList.add(
                Video(
                    description = "none",
                    subtitle = "sub$index",
                    thumb = c.toString(),
                    sources = emptyList(),
                    title = index.toString()
                )
            )
        }

//        runBlocking {
//            fakeVideoRepositoryImpl.saveVideos(testList)
//        }

        coEvery { videoRepository.getVideos() } returns testList

        getVideosUseCase = GetVideosUseCaseImpl(videoRepository)

    }


    @Test
    fun `stupid test GetVideosUseCaseImpl, except list of Videos`() = runBlocking {

        val data = getVideosUseCase.invoke()
        assert(videoRepository.getVideos() == data)

    }


}