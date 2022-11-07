package com.artemissoftware.firegallery.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.artemissoftware.common.composables.dialog.models.DialogOptions
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.R
import com.artemissoftware.firegallery.screens.splash.SplashEvents
import com.artemissoftware.firegallery.screens.splash.SplashViewModel
import com.artemissoftware.firegallery.screens.splash.composables.Logo
import com.artemissoftware.firegallery.ui.UiEvent
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SplashScreen(
    scaffoldState: FGScaffoldState,
    onAnimationFinish: () -> Unit = {}
) {

    val viewModel: SplashViewModel = hiltViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {

        viewModel.uiEvent.collectLatest { event ->
            when(event) {
                is UiEvent.ShowErrorDialog -> {

                    val dialogType = DialogType.Error(
                        title = event.title,
                        description = event.message,
                        dialogOptions = DialogOptions(
                            confirmationTextId = R.string.retry,
                            confirmation = {
                                viewModel.onTriggerEvent(SplashEvents.LoadSplash)
                            }
                        )
                    )

                    scaffoldState.showDialog(dialogType)
                }
                else ->{}
            }

        }
    }

    LaunchedEffect(key1 = state.value.dataLoaded){
        if(state.value.dataLoaded) onAnimationFinish.invoke()
    }

    BuildSplashScreen(
        scaffoldState = scaffoldState,
        onAnimationFinish = onAnimationFinish)
}

@Composable
private fun BuildSplashScreen(
    scaffoldState: FGScaffoldState,
    onAnimationFinish: () -> Unit = {}
) {
    FGScaffold(
        fgScaffoldState = scaffoldState
    ) {

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,

            ) {

            Logo(
                onAnimationFinish = {},
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