package com.artemissoftware.firegallery.screens.splash.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.models.DeepLinkNavigation.DEEP_LINK
import com.artemissoftware.firegallery.MainActivity
import com.artemissoftware.firegallery.navigation.graphs.RootNavigationGraph
import com.artemissoftware.firegallery.ui.theme.FireGalleryTheme
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalAnimationApi::class)
@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    private val viewModel: SplashActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.scaffoldState.setIntent(intent)

        setContent {
            FireGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Splash()
                }
            }
        }

        //startNextAndFinish()
    }


    @Composable
    private fun Splash() {
        FGScaffold(
            fgScaffoldState = viewModel.scaffoldState
        ) {

            RootNavigationGraph(
                navController = rememberAnimatedNavController(),
                viewModel.scaffoldState
            )
        }
    }

    fun startNextAndFinish(){

        val action = intent.action
        val uri = intent.data

        if(action == Intent.ACTION_VIEW && uri != null){

            val intent = Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(DEEP_LINK, uri.toString())
            }

            startActivity(intent)
            finish()
        }
    }

}