package com.chunmaru.task.domain.usecase

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.domain.repository.VideoRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVideosUseCaseImplTest {

    private lateinit var getVideosUseCase: GetVideosUseCase
    private val videoRepository = mockk<VideoRepository>(relaxed = true)

    private val testList = getTestList()

    @Before
    fun setUp() {
        getVideosUseCase = GetVideosUseCaseImpl(videoRepository)
    }

    @Test
    fun `should fetch videos from API when no data in DB`() = runBlocking {

        coEvery { videoRepository.getVideosFromApi() } returns testList

        val result = getVideosUseCase.invoke()

        coVerify { videoRepository.getVideosFromApi() }
        assertEquals(testList, result)
    }

    @Test
    fun `should fetch videos from DB when API call fails`() = runBlocking {

        coEvery { videoRepository.getVideosFromApi() } throws Exception("API error")
        coEvery { videoRepository.getVideosFromDb() } returns testList

        val result = getVideosUseCase.invoke()

        coVerify { videoRepository.getVideosFromApi() }
        coVerify { videoRepository.getVideosFromDb() }
        assertEquals(testList, result)
    }

    private fun getTestList(): List<Video> {
        return ('a'..'z').mapIndexed { index, c ->
            Video(
                description = "none",
                subtitle = "sub$index",
                thumb = c.toString(),
                sources = emptyList(),
                title = index.toString()
            )
        }
    }
}
