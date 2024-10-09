package com.chunmaru.task.presentation.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chunmaru.task.data.model.Video
import com.chunmaru.task.domain.repository.VideoRepository
import com.chunmaru.task.domain.usecase.GetVideosUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getVideosUseCase: GetVideosUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<List<Video>>(emptyList())
    val state = _state.asStateFlow()

    private val _currentVideoIndex = MutableStateFlow<Int?>(null)
    val currentVideoIndex = _currentVideoIndex.asStateFlow()


    private val _errorState = MutableStateFlow<Boolean>(false)
    val errorState = _errorState.asStateFlow()


    fun setCurrentVideo(videoIndex: Int) {
        _currentVideoIndex.value = videoIndex
    }

    init {
        fetchVideos()
    }


    fun nextVideo() {
        _currentVideoIndex.value?.let { currentIndex ->
            if (currentIndex < _state.value.size - 1) {
                _currentVideoIndex.value = currentIndex + 1
                Log.d("MainViewModel", "Current video index changed to: ${_currentVideoIndex.value}")
            }
        }
    }

    fun previousVideo() {
        _currentVideoIndex.value?.let { currentIndex ->
            if (currentIndex > 0) {
                _currentVideoIndex.value = currentIndex - 1
                Log.d("MainViewModel", "Current video index changed to: ${_currentVideoIndex.value}")
            }
        }
    }




    fun reload() {
        fetchVideos()
    }

    private fun fetchVideos() {
        viewModelScope.launch {
            try {
                val videos = withContext(Dispatchers.IO) {
                    getVideosUseCase.invoke()
                }
                _errorState.value = false
                _state.value = videos
            } catch (e: Exception) {
                _errorState.value = true
            }
        }
    }

}