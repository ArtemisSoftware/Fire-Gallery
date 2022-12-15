package com.artemissoftware.common.composables.scaffold.models

import android.content.Intent
import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavHostController
import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.common.composables.snackbar.state.FGSnackbarHostState
import com.artemissoftware.common.extensions.changeGraph
import com.artemissoftware.common.models.DeepLinkNavigation.DEEP_LINK
import kotlinx.coroutines.CoroutineScope

open class FGScaffoldState( //TODO: sugestões - FGUiScaffoldState
    private val scope: CoroutineScope? = null
) {


    var dialog = mutableStateOf<DialogType?>(null)
        private set

    var bottomBarDestinations = mutableStateOf<List<BottomBarItem>>(emptyList())
        private set

    var selectedBottomBarItem = mutableStateOf<BottomBarState>(BottomBarState())
        private set

    protected var deepLink = mutableStateOf<Uri?>(null)
        private set



    fun showDialog(dialogType: DialogType) {
        dialog.value = dialogType
    }

    fun closeDialog() {
        dialog.value = null
    }

    fun setBottomBarDestinations(items: List<BottomBarItem>) {
        bottomBarDestinations.value = items
    }



    fun setIntent(intent: Intent){

        val action = intent.action
        val uri = intent.data

        if(action == Intent.ACTION_VIEW && uri != null ){
            deepLink.value = uri
        }
        else{
            intent.extras?.getString(DEEP_LINK)?.let { link->
                deepLink.value = Uri.parse(link)
            }
        }
    }

    fun updateIntent(intent: Intent) : Intent{

        deepLink.value?.let { uriDeepLink->

            val updatedIntent = intent.apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
                putExtra(DEEP_LINK, uriDeepLink.toString())
            }

            return updatedIntent

        } ?: kotlin.run {
            return intent
        }
    }


    fun changeCurrentPositionBottomBar(
        destination: BaseDestinations,
        navController: NavHostController?
    ) {

        navController?.let {
            changeCurrentPositionBottomBar(
                destination = destination,
                route = destination.route,
                navController = it
            )
        }
    }



    fun getSelectedBottomBarDestination(defaultDestination: BaseDestinations): BaseDestinations {
        return selectedBottomBarItem.value.currentDestination ?: defaultDestination
    }



    protected fun executeDeepLink(
        destinationWithArguments: Pair<BaseDestinations, List<String>>?,
        navController: NavHostController?
    ) {

        destinationWithArguments?.let { destinationWithArguments ->

            val destination = destinationWithArguments.first
            val route = destinationWithArguments.toDestinationRoute()

            try {

                if(isBottomNavigationDestination(destination = destination)){
                    navController?.let {
                        changeCurrentPositionBottomBar(destination = destination, navController = it, route = route)
                    }
                    return
                }
                else{
                    navController?.navigate(route)
                }
            }
            catch(e: IllegalArgumentException){

            }
        }
    }





    private fun Pair<BaseDestinations, List<String>>.toDestinationRoute() : String{

        val destination = this.first

        return if(this.second.isNotEmpty()) {
            destination.withArgs(this.second)
        }
        else destination.getRoutel()
    }


    private fun isBottomNavigationDestination(destination: BaseDestinations) : Boolean{

        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
                return any { it.destination.getRoutel() == destination.getRoutel() }
            }
        }

        return false
    }




    private fun changeCurrentPositionBottomBar(
        destination: BaseDestinations,
        route: String,
        navController: NavHostController
    ){
        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
                selectedBottomBarItem.value.update(
                    currentPosition = indexOfFirst { it.destination.route == destination.getRoutel() },
                    currentDestination = destination
                )
                navController.changeGraph(route)
            }
        }
    }





    //-----------------------------------------------------




    //TODO: refactor this class

//    private val _scaffoldState = BottomSheetScaffoldState(
//        DrawerState(DrawerValue.Closed),
//        BottomSheetState(
//            initialValue = BottomSheetValue.Collapsed,
//            animationSpec = SwipeableDefaults.AnimationSpec,
//            confirmStateChange = { true }
//        ),
//        SnackbarHostState()
//    )
//    private val _bottomSheetState by lazy { EDPBottomSheetState(_scaffoldState.bottomSheetState) }
    private val _snackbarHostState = FGSnackbarHostState()
//    private val _snackbarMode = mutableStateOf(EDPSnackbarMode.INFORMATIVE)
//    private val _isShowingBottomBar = mutableStateOf(true)
//    private val _currentPositionBottomBar = mutableStateOf(0)
    //    private val _openSearch = mutableStateOf(false)
//    private val _openProgress = mutableStateOf(false)
//    private val _openConsumptions = mutableStateOf(false)

//    val state = _scaffoldState
//    val bottomSheetState = _bottomSheetState
    val snackbarHostState = _snackbarHostState
//    val snackbarMode: EDPSnackbarMode get() = _snackbarMode.value

//    val openSearch: Boolean get() = _openSearch.value
//    val openProgress: Boolean get() = _openProgress.value
//    val openConsumptions: Boolean get() = _openConsumptions.value
//    val isShowingBottomBar: Boolean get() = _isShowingBottomBar.value
//    val currentPositionBottomBar: Int get() = _currentPositionBottomBar.value







    private fun showSnackbar(
//        mode: EDPSnackbarMode,
        message: String,
        actionText: String? = null,
//        onAction: (() -> Unit)?,
//        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
//        scope.launch {
//
//            snackbarHostState.showSnackbar(mode, message, actionText, duration).apply {
//                if (this == SnackbarResult.ActionPerformed) {
//                    onAction?.invoke()
//                }
//            }
//        }
    }
//
//    fun showInformative(
//        message: String,
//        actionText: String? = null,
//        onAction: (() -> Unit)? = null,
//        duration: SnackbarDuration = SnackbarDuration.Short
//    ) {
//        showSnackbar(EDPSnackbarMode.INFORMATIVE, message, actionText, onAction, duration)
//    }
//
//    fun showError(
//        message: String,
//        actionText: String? = null,
//        onAction: (() -> Unit)? = null,
//        duration: SnackbarDuration = SnackbarDuration.Short
//    ) {
//        showSnackbar(EDPSnackbarMode.ERROR, message, actionText, onAction, duration)
//    }

    fun showSuccess(
        message: String,
        actionText: String? = null,
//        onAction: (() -> Unit)? = null,
//        duration: SnackbarDuration = SnackbarDuration.Short
    ) {
        showSnackbar(
//            EDPSnackbarMode.SUCCESS,
            message,
            actionText,
//            onAction,
//            duration
        )
    }



}