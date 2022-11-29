package com.artemissoftware.firegallery.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.routes.destinations.Destination
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.FGBaseEvents
import com.artemissoftware.firegallery.ui.ManageUIEvents

interface NavigationRoute<E: FGBaseEvents, T : FGBaseEventViewModel<E>> {

    fun getDestination(): Destination

    /**
     * Returns the screen's ViewModel. Needs to be overridden so that Hilt can generate code for the factory for the ViewModel class.
     */
    @Composable
    fun viewModel(): T


    /**
     * Returns the screen's content.
     */
    @Composable
    fun Content(viewModel: T)




    /**
     * Generates the composable for this route.
     */
    fun composable(
        navGraphBuilder: NavGraphBuilder,
        scaffoldState: FGScaffoldState,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = getDestination().getRoutel(),
            arguments = getDestination().arguments
        ) {

            val viewModel = viewModel()

            ManageUIEvents(
                uiEvent = viewModel.uiEvent,
                scaffoldState = scaffoldState,
                onNavigatePopUpTo = {
                    navController.navigate(it.destinationRoute) {
                        popUpTo(it.currentRoute) {
                            inclusive = true
                        }
                    }
                },
                onPopBackStack = {
                    navController.popBackStack()
                },
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