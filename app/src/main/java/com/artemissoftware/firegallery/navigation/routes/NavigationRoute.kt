package com.artemissoftware.firegallery.navigation.routes

import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.FGBaseEvents
import com.artemissoftware.firegallery.ui.ManageUIEvents

interface NavigationRoute<E: FGBaseEvents, T : FGBaseEventViewModel<E>> {

    val route: String

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

    /**
     * Override when this page uses arguments.
     *
     * We do it here and not in the [NavigationComponent to keep it centralized]
     */
    fun getArguments(): List<NamedNavArgument> = listOf()

    fun getArguments_(): BaseDestinations

    /**
     * Generates the composable for this route.
     */
    fun composable(
        builder: NavGraphBuilder,
        scaffoldState: FGScaffoldState,
        navController: NavHostController
    ) {
        builder.composable(route, getArguments()) {
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