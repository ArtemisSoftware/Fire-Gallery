package com.artemissoftware.firegallery.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.artemissoftware.common.composables.scaffold.FGScaffold
import com.artemissoftware.firegallery.navigation.graphs.HomeNavigationGraph
import com.artemissoftware.firegallery.ui.FG_ScaffoldState


@Composable
fun HomeScreen(
    navController: NavHostController = rememberNavController(),
    scaffoldState: FG_ScaffoldState
) {

    FGScaffold(
        fgScaffoldState = scaffoldState,
        bottomBarItems = scaffoldState.bottomBarItems.value,
        navController = navController
    ) {
        HomeNavigationGraph(navController = navController, scaffoldState = scaffoldState)

        scaffoldState.executeDeepLink(navController)
    }

}


@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {

    //HomeScreen()
}