package com.chunmaru.task.domain.usecase

import com.chunmaru.task.data.model.Video
import com.chunmaru.task.data.repository.FakeVideoRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class GetVideosUseCaseImplTest {

    private lateinit var getVideosUseCase: GetVideosUseCase
    private lateinit var fakeVideoRepositoryImpl: FakeVideoRepositoryImpl

    @Before
    fun setUp() {

        fakeVideoRepositoryImpl = FakeVideoRepositoryImpl()
        getVideosUseCase = GetVideosUseCaseImpl(fakeVideoRepositoryImpl)

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

        runBlocking {
            fakeVideoRepositoryImpl.saveVideos(testList)
        }

    }


    @Test
    fun `stupid test GetVideosUseCaseImpl, except list of Videos`() = runBlocking {

        val data = getVideosUseCase.invoke()
        assert(fakeVideoRepositoryImpl.getVideos() == data)

    }


}