package com.chunmaru.task.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.chunmaru.task.presentation.screens.MainScreen
import com.chunmaru.task.presentation.screens.MainViewModel
import com.chunmaru.task.presentation.screens.VideoPlayerScreen

@Composable
fun SetupNavHost(navController: NavHostController, mainViewModel: MainViewModel) {

    NavHost(navController = navController, startDestination = Screens.VideoList.name) {

        composable(
            route = Screens.VideoList.name
        ) { MainScreen(mainViewModel = mainViewModel, navController) }
        composable(
            route = Screens.Video.name
        ) { VideoPlayerScreen(viewModel = mainViewModel) }

    }


}