package com.chunmaru.task.presentation.screens

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableLongState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.chunmaru.task.data.model.Video
import kotlinx.coroutines.delay


@Composable
fun VideoPlayerScreen(viewModel: MainViewModel) {
    val videoList = viewModel.state.collectAsState()
    val currentVideoIndex by viewModel.currentVideoIndex.collectAsState()
    val playbackPosition = rememberSaveable { mutableLongStateOf(0L) }

    currentVideoIndex?.let { index ->
        if (videoList.value.isNotEmpty()) {
            val currentVideo = videoList.value[index]
            VideoPlayerContent(currentVideo, playbackPosition, viewModel)
        }
    }
}

@Composable
fun VideoPlayerContent(currentVideo: Video, playbackPosition: MutableLongState, viewModel: MainViewModel) {
    val context = LocalContext.current
    val player = remember {
        ExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(currentVideo.sources[0])
            setMediaItem(mediaItem)
        }
    }

    val playerView = remember { PlayerView(context) }
    playerView.player = player

    val isPlaying = remember { mutableStateOf(true) }
    val sliderPosition = remember { mutableStateOf(0f) }

    LaunchedEffect(currentVideo) {
        val mediaItem = MediaItem.fromUri(currentVideo.sources[0])
        player.setMediaItem(mediaItem)
        player.prepare()
        player.seekTo(playbackPosition.longValue)
        player.playWhenReady = true
    }

    UpdateSliderPosition(player, sliderPosition)

    HandleLifecycleEvents(player, playbackPosition, isPlaying)

    VideoPlayerUI(sliderPosition, playerView, viewModel, playbackPosition, player, isPlaying.value) { newPlayingState ->
        isPlaying.value = newPlayingState
    }
}

@Composable
fun UpdateSliderPosition(player: ExoPlayer, sliderPosition: MutableState<Float>) {
    LaunchedEffect(player) {
        while (true) {
            val currentPos = player.currentPosition.toFloat()
            val totalDuration = player.duration.toFloat()
            sliderPosition.value = if (totalDuration > 0) {
                currentPos / totalDuration
            } else {
                0f
            }
            delay(500)
        }
    }
}

@Composable
fun HandleLifecycleEvents(player: ExoPlayer, playbackPosition: MutableLongState, isPlaying: MutableState<Boolean>) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_PAUSE -> {
                    isPlaying.value = false
                    player.playWhenReady = false
                }
                Lifecycle.Event.ON_RESUME -> {
                    isPlaying.value = true
                    player.playWhenReady = true
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            playbackPosition.longValue = player.currentPosition
            player.release()
        }
    }
}

@Composable
fun VideoPlayerUI(
    sliderPosition: MutableState<Float>,
    playerView: PlayerView,
    viewModel: MainViewModel,
    playbackPosition: MutableLongState,
    player: ExoPlayer,
    isPlaying: Boolean,
    onPlayingStateChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AndroidView(
            factory = { playerView },
            modifier = Modifier.weight(1f),
        )

        ControlButtons(viewModel, playbackPosition, player, onPlayingStateChange, isPlaying)
        VideoSlider(sliderPosition, player)
    }
}

@Composable
fun ControlButtons(
    viewModel: MainViewModel,
    playbackPosition: MutableLongState,
    player: ExoPlayer,
    onPlayingStateChange: (Boolean) -> Unit,
    isPlaying: Boolean
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(onClick = {
            viewModel.previousVideo()
            playbackPosition.longValue = 0L
        }) {
            Text("Previous")
        }

        Button(onClick = {
            onPlayingStateChange(!isPlaying)
            player.playWhenReady = !isPlaying
        }) {
            Text(if (isPlaying) "Pause" else "Play")
        }

        Button(onClick = {
            viewModel.nextVideo()
            playbackPosition.longValue = 0L
        }) {
            Text("Next")
        }
    }
}

@Composable
fun VideoSlider(sliderPosition: MutableState<Float>, player: ExoPlayer) {
    Slider(
        value = sliderPosition.value,
        onValueChange = { newValue ->
            sliderPosition.value = newValue
            val newPosition = (newValue * player.duration).toLong()
            player.seekTo(newPosition)
        },
        modifier = Modifier.fillMaxWidth()
    )
}

