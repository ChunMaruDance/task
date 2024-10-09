package com.chunmaru.task.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import com.chunmaru.task.presentation.navigation.SetupNavHost
import com.chunmaru.task.presentation.screens.MainScreen
import com.chunmaru.task.presentation.screens.MainViewModel
import com.chunmaru.task.presentation.ui.theme.TaskTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TaskTheme {

                val navController = rememberNavController()
                val mainViewModel: MainViewModel = hiltViewModel()
                SetupNavHost(navController = navController,mainViewModel = mainViewModel)
            }
        }
    }
}
