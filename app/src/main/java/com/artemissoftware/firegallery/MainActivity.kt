package com.artemissoftware.firegallery

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.firegallery.navigation.HomeDestinations
import com.artemissoftware.firegallery.navigation.HomeNavigationGraph
import com.artemissoftware.firegallery.navigation.graphs.RootNavigationGraph
import com.artemissoftware.firegallery.screens.home.HomeScreen
import com.artemissoftware.firegallery.ui.theme.FireGalleryTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FireGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    //RootNavigationGraph(navController = rememberAnimatedNavController(), viewModel.scaffoldState)
                    //HomeNavigationGraph(navController = rememberNavController(), scaffoldState = viewModel.scaffoldState)
                    
                    HomeScreen(scaffoldState = viewModel.scaffoldState)
                }
            }
        }

    }





}
