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
import kotlinx.coroutines.CoroutineScope

class FGScaffoldState(
    private val scope: CoroutineScope
) {


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
//
//    fun showBottomBar() {
//        _isShowingBottomBar.value = true
//    }
//
//    fun hideBottomBar() {
//        _isShowingBottomBar.value = false
//    }
//
//    fun changeModeSnackBar(mode: EDPSnackbarMode) {
//        _snackbarMode.value = mode
//    }
//
//    fun hideSearch() {
//        _openSearch.value = false
//    }
//
//    fun hideProgress() {
//        _openProgress.value = false
//    }
//
//    fun hideConsumptions() {
//        _openConsumptions.value = false
//    }
//
//    @ExperimentalAnimationApi
//    fun changeCurrentPositionBottomBar(
//        position: Int,
//        navController: NavHostController?,
//        openSearch: Boolean = false,
//        openProgress: Boolean = false,
//        openConsumptions: Boolean = false
//    ) {
//        _currentPositionBottomBar.value = position
//        _openSearch.value = openSearch
//        _openProgress.value = openProgress
//        _openConsumptions.value = openConsumptions
//
//        when (position) {
//            MainActivity.MENU_HOME -> navController?.changeGraph(MainDestinations.Home.route)
//            MainActivity.MENU_ROUTE_PLANNER -> navController?.changeGraph(MainDestinations.RoutePlanner.route)
//            MainActivity.MENU_FAVORITES -> navController?.changeGraph(MainDestinations.Favorites.route)
//            MainActivity.MENU_HISTORY -> navController?.changeGraph(MainDestinations.History.route)
//            MainActivity.MENU_PROFILE -> navController?.changeGraph(MainDestinations.Profile.route)
//        }
//
//        bottomSheetState.hide()
//    }



    ///---------

    var bottomBarItems = mutableStateOf<List<BottomBarItem>>(emptyList())
        private set

    var currentbottomBarItem = mutableStateOf<BottomBarItem?>(null)
        private set
    var currentbottomBarItemLolo = mutableStateOf<String?>(null)
        private set

    var modalVisible = mutableStateOf<DialogType?>(null)
        private set

    private val _currentPositionBottomBar = mutableStateOf(0)
    val currentPositionBottomBar: Int get() = _currentPositionBottomBar.value

    fun showDialog(dialogType: DialogType) {
        modalVisible.value = dialogType
    }

    fun closeDialog() {
        modalVisible.value = null
    }

    fun changeCurrentPositionBottomBar_(
        position: Int,
        destination: String,
        navController: NavHostController?
    ) {
        _currentPositionBottomBar.value = position
        currentbottomBarItemLolo.value = destination
        //currentbottomBarItem.value = bottomBarItems.value[position]
        navController?.changeGraph(destination)

    }

    fun changeCurrentPositionBottomBar(
        destination: BaseDestinations,
        navController: NavHostController?
    ) {
        with(bottomBarItems.value) {

            if (isNotEmpty()) {
                _currentPositionBottomBar.value = indexOfFirst { it.route == destination.route }
                currentbottomBarItemLolo.value = destination.route
                //currentbottomBarItem.value = bottomBarItems.value[_currentPositionBottomBar.value]
                navController?.changeGraph(destination.route)
            }
        }
    }


    fun setBottomBarDestinations(items: List<BottomBarItem>) {
        bottomBarItems.value = items
    }


    var _deepLink = mutableStateOf<Uri?>(null)
        private set

    fun setDeepLink(intent: Intent){
        val action = intent.action
        val uri = intent.data

        intent.extras?.let {
            var link = it.getString("deep_link")
            _deepLink.value = Uri.parse(link)
        }

    }


    fun executeDeepLink(navController: NavHostController){
        _deepLink.value?.let{
            navController.navigate(it)
        }

        _deepLink.value = null
    }

}