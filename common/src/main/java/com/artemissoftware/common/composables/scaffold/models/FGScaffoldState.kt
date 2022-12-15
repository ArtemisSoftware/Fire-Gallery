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


    fun showDialog(dialogType: DialogType) {
        dialog.value = dialogType
    }

    fun closeDialog() {
        dialog.value = null
    }

    fun setBottomBarDestinations(items: List<BottomBarItem>) {
        bottomBarDestinations.value = items
    }


    fun changeCurrentPositionBottomBar_final(
        destination: BaseDestinations,
        navController: NavHostController
    ){
        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
                _currentPositionBottomBar.value = indexOfFirst { it.route == destination.getRoutel() }
                currentbottomBarItemLolo.value = destination.getRoutel()
                navController?.changeGraph(destination.withArgs("lolollo"))
            }
        }
    }


    fun changeCurrentPositionBottomBar_final_TESTE_TESTE(
        route: String,
        navController: NavHostController
    ){
        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
//                _currentPositionBottomBar.value = 2
//                currentbottomBarItemLolo.value = route
                navController.changeGraph(route)
            }
        }
    }


    protected fun executeDeepLink( //TODO: arranjar nome melhor para isto. Já não está relacionado com deeplink
        destinationWithArguments: Pair<BaseDestinations, List<String>>?,
        navController: NavHostController?
    ) {

        destinationWithArguments?.let { destinationWithArguments ->

            val destination = destinationWithArguments.first
            val route = destinationWithArguments.toDestinationRoute()

            try {

                if(isBottomNavigationDestination(destination = destination)){
                    navController?.let {
                        changeCurrentPositionBottomBar_final_BASE(destination = destination, navController = it, route = route)
                    }
                    return
                }
                else{
                    navController?.navigate(route)
                }
            }
            catch(e: IllegalArgumentException){
                //TODO: solucao mais bonita, mas a que está é boa
                //navController.navigate(it)
            }

        }

    }







    protected fun executeDeepLink(
        destination: BaseDestinations,
        navController: NavHostController?
    ) {

        try {
            with(bottomBarDestinations.value) {

                if (isNotEmpty()) {

                    if(bottomBarDestinations.value.any { it.route == destination.getRoutel() }){

                        navController?.let {
                            changeCurrentPositionBottomBar_final(destination = destination, navController = it)
                        }
                        return
                    }
                }
            }

            navController?.navigate(destination.getRoutel())
        }
        catch(e: IllegalArgumentException){
            //TODO: solucao mais bonita, mas a que está é boa
            //navController.navigate(it)
        }
    }


    //-----------------------------------------------------


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
                return any { it.route == destination.getRoutel() }
            }
        }

        return false
    }


    private fun changeCurrentPositionBottomBar_final_BASE(
        destination: BaseDestinations,
        route: String,
        navController: NavHostController
    ){
        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
                _currentPositionBottomBar.value = indexOfFirst { it.route == destination.getRoutel() }
                currentbottomBarItemLolo.value = destination.getRoutel()
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


    var currentbottomBarItem = mutableStateOf<BottomBarItem?>(null)
        private set
    var currentbottomBarItemLolo = mutableStateOf<String?>(null)
        private set


    private val _currentPositionBottomBar = mutableStateOf(0)
    val currentPositionBottomBar: Int get() = _currentPositionBottomBar.value

    fun loloo(position: Int){
        _currentPositionBottomBar.value = position
    }


    fun changeCurrentPositionBottomBar_(
        position: Int,
        destination: String,
        navController: NavHostController?
    ) {

        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {

                _currentPositionBottomBar.value = position
                currentbottomBarItemLolo.value = destination
                //currentbottomBarItem.value = bottomBarItems.value[position]
               navController?.changeGraph(destination)

            }
        }
    }

    fun changeCurrentPositionBottomBar(
        destination: BaseDestinations,
        navController: NavHostController?
    ) {

        with(bottomBarDestinations.value) {

            if (isNotEmpty()) {
                _currentPositionBottomBar.value = indexOfFirst { it.route == destination.getRoutel() }
                currentbottomBarItemLolo.value = destination.route
                //currentbottomBarItem.value = bottomBarItems.value[_currentPositionBottomBar.value]

                navController?.changeGraph(destination.getRoutel())
            }
        }
    }



    var dddd = -1

    var deepLink = mutableStateOf<Uri?>(null)
        private set


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


    fun changeCurrentPositionBottomBar_vv(
        position: Int,
        destination: String,
        navController: NavHostController?,
        uri: Uri
    ) {
        _currentPositionBottomBar.value = position
        currentbottomBarItemLolo.value = destination
        //currentbottomBarItem.value = bottomBarItems.value[position]
        navController!!.navigate(uri)

    }





    //---------------------
    //-------------------------------



}