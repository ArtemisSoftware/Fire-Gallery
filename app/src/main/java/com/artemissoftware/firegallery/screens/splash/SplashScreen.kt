package com.artemissoftware.firegallery.screens.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.firegallery.screens.splash.composables.Logo

@Composable
fun SplashScreen(
    viewModel: SplashViewModel
) {

    BuildSplashScreen(
        events = viewModel::onTriggerEvent
    )
}

@Composable
private fun BuildSplashScreen(
    events: ((SplashEvents) -> Unit)? = null
) {
    FGScaffold {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,

            ) {

            Logo(
                onAnimationFinish = {
                    events?.invoke(SplashEvents.AnimationConcluded)
                },
                modifier = Modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SplashScreenPreview() {

    //SplashScreen()
}