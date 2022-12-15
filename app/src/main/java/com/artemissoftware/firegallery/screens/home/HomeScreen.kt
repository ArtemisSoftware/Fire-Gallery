package com.artemissoftware.firegallery.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.firegallery.MainEvents
import com.artemissoftware.firegallery.navigation.graphs.HomeNavigationGraph
import com.artemissoftware.firegallery.ui.FG_ScaffoldState
import com.artemissoftware.firegallery.ui.ManageUIEvents


@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    navController: NavHostController = rememberNavController(),
    scaffoldState: FG_ScaffoldState
) {

    FGScaffold(
        fgScaffoldState = scaffoldState,
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