package com.artemissoftware.firegallery.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.firegallery.MainEvents
import com.artemissoftware.firegallery.navigation.graphs.HomeNavigationGraph
import com.artemissoftware.firegallery.ui.FGScaffoldState
import com.artemissoftware.firegallery.ui.ManageUIEvents
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberAnimatedNavController(),
    scaffoldState: FGScaffoldState
) {

    FGScaffold(
        fgUiScaffoldState = scaffoldState,
        bottomBarItems = scaffoldState.bottomBarDestinations.value,
        navController = navController
    ) {
        HomeNavigationGraph(navController = navController, scaffoldState = scaffoldState)
        viewModel.onTriggerEvent(MainEvents.ExecuteDeepLink)
    }

    ManageUIEvents(
        uiEvent = viewModel.uiEvent,
        scaffoldState = scaffoldState,
        onDeepLink = {
            scaffoldState.executeDeepLink(navController)
        }
    )

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    //HomeScreen()
}