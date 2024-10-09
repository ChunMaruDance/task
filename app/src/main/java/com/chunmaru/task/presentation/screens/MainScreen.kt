package com.chunmaru.task.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.chunmaru.task.data.model.Video
import com.chunmaru.task.presentation.navigation.Screens

@Composable
fun MainScreen(mainViewModel: MainViewModel, navController: NavHostController) {

    val videoList = mainViewModel.state.collectAsState()
    val errorState = mainViewModel.errorState.collectAsState()


    if (errorState.value) {
        ErrorScreen(onRetry = { mainViewModel.reload() })
    } else {
        if (videoList.value.isEmpty()) {
            LoadingIndicator()
        } else {

            LazyColumn {
                itemsIndexed(videoList.value) { index, video ->
                    VideoItem(video = video) {
                        mainViewModel.setCurrentVideo(index)
                        navController.navigate(Screens.Video.name)
                    }
                }
            }
        }
    }
}

@Composable
fun VideoItem(video: Video, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = video.title)
    }
}

@Composable
fun LoadingIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreen(onRetry: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "Error loading videos", color = Color.Red)
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}
