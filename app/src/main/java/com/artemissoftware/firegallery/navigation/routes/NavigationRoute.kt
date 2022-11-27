package com.artemissoftware.firegallery.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.FGBaseEvents
import com.artemissoftware.firegallery.ui.ManageUIEvents

interface NavigationRoute<E: FGBaseEvents, T : FGBaseEventViewModel<E>> {

    /**
     * Returns the screen's content.
     */
    @Composable
    fun Content(viewModel: T)

    /**
     * Returns the screen's ViewModel. Needs to be overridden so that Hilt can generate code for the factory for the ViewModel class.
     */
    @Composable
    fun viewModel(): T

    fun getDestination(): Destination


    /**
     * Generates the composable for this route.
     */
    fun composable(
        navGraphBuilder: NavGraphBuilder,
        scaffoldState: FGScaffoldState,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = getDestination().route,
            arguments = getDestination().arguments
        ) {

            val viewModel = viewModel()

            ManageUIEvents(
                uiEvent = viewModel.uiEvent,
                scaffoldState = scaffoldState,
                onNavigate =  {
                    navController.navigate(it.route)
                },
                onChangeCurrentPositionBottomBar = {
                    scaffoldState.changeCurrentPositionBottomBar(it.destination, navController = navController)
                }
            )

            Content(viewModel)
        }
    }

}