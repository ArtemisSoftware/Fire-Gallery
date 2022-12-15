package com.artemissoftware.firegallery.navigation.routes

import android.app.Activity
import android.content.Intent
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
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

            AuthenticationChecker(
                scaffoldState = scaffoldState,
                required = requiresAuthentication(),
                finalRoute = it.arguments,
                redirect = {
                    navController.popBackStack()
                    navController.navigate(it)
                },
                content = {

                    val viewModel = viewModel()

                    Content(viewModel)

                    ManageUIEvents(
                        uiEvent = viewModel.uiEvent,
                        scaffoldState = scaffoldState,
                        onNavigatePopUpTo = { event->
                            navController.popBackStack(event.destinationRoute, inclusive = false, saveState = false)
                        },
                        onPopBackStack = {
                            navController.popBackStack()
                        },
                        onRedirect = { event->
                            navController.popBackStack()
                            scaffoldState.redirect(event.route, navController)
                        },
                        onNavigate =  { event->
                            navController.navigate(event.route)
                        },
                        onChangeCurrentPositionBottomBar = { event->
                            scaffoldState.changeCurrentPositionBottomBar(event.destination, navController = navController)
                        },
                        onFinishAndStartActivity = { event->

                            val intent = Intent(context, event.activity)
                            context.startActivity(scaffoldState.updateIntent(intent))
                            (context as? Activity)?.finish()
                        },
                        onDeepLink = {
                            scaffoldState.changeCurrentPositionBottomBar_(
                                1,
                                DestinationRoutes.HomeGraph.favorites.route,
                                navController
                            )
                        }
                    )
                }
            )
        }
    }

}