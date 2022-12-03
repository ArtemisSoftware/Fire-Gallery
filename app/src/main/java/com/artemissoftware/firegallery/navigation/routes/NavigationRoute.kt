package com.artemissoftware.firegallery.navigation.routes

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.firegallery.navigation.routes.destinations.Destination
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import com.artemissoftware.firegallery.ui.*

interface NavigationRoute<E: FGBaseEvents, T : FGBaseEventViewModel<E>> {

    fun getDestination(): Destination

    fun requiresAuthentication(): Boolean = false

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
        scaffoldState: FG_ScaffoldState,
        navController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = getDestination().getRoutel(),
            arguments = getDestination().arguments,
            deepLinks = getDestination().deepLink
        ) {

            val context = LocalContext.current

            val viewModel = viewModel()

            AuthenticationChecker(
                scaffoldState = scaffoldState,
                required = requiresAuthentication(),
                redirect = {
                    navController.navigate(DestinationRoutes.ProfileGraph.login.withArgs(this.getDestination().getRoutel()))
                }
            )

            ManageUIEvents(
                uiEvent = viewModel.uiEvent,
                scaffoldState = scaffoldState,
                onNavigatePopUpTo = {

                    navController.popBackStack(it.destinationRoute, inclusive = false, saveState = false)

                                    //navController?.changeGraph(it.destinationRoute)
                    //--navController.navigate(it.destinationRoute) { popUpToTop(navController) }
                    //navController.popBackStack(route = it.destinationRoute, inclusive = false)
//                    navController.navigate(it.destinationRoute) {
//                        popUpTo(it.currentRoute) {
//                            inclusive = true
//                        }
//                    }

                },
                onPopBackStack = {
                    navController.popBackStack()
                },
                onPopBackStackInclusive = {
                    navController.popBackStack(route = it.route, inclusive = true)
                },
                onNavigate =  {
                    navController.navigate(it.route)
                },
                onChangeCurrentPositionBottomBar = {
                    scaffoldState.changeCurrentPositionBottomBar(it.destination, navController = navController)
                },
                onFinishAndStartActivity = {

                    val intent = Intent(context, it.activity)
                    context.startActivity(scaffoldState.updateIntent(intent))
                    (context as? Activity)?.finish()
                },
            )

            Content(viewModel)
        }
    }

}