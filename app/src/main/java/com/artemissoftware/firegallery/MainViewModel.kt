package com.artemissoftware.firegallery

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.*
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.navigation.mapper.toBottomBarItem
import com.artemissoftware.common.composables.navigation.models.BaseDestination
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.domain.usecases.authentication.GetUserUseCase
import com.artemissoftware.firegallery.navigation.routes.destinations.DestinationRoutes
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
import com.artemissoftware.firegallery.ui.FGScaffoldState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getUserUseCase: GetUserUseCase
): FGBaseEventViewModel<MainEvents>() {

    val scaffoldState by lazy { FGScaffoldState(viewModelScope) }

    init {
        getUser()
    }

    private fun getUser(){

        viewModelScope.launch {

            getUserUseCase.invoke().collectLatest { user ->

                scaffoldState.setUser(user = user)

                with(DestinationRoutes.HomeGraph){

                    val bottomBarItems = user?.let {

                        listOf(
                            gallery,
                            favorites,
                            tinderGallery,
                            profile
                        )

                    } ?: kotlin.run {
                        listOf(
                            gallery,
                            profile
                        )
                    }

                    setBottomBarItems(bottomBarItems)
                }
            }
        }
    }


    private fun setBottomBarItems(items: List<BaseDestination>) {

        val bottomBarItems = mutableListOf<BottomBarItem>()

        with(DestinationRoutes.HomeGraph) {

            items.forEach {

                when (it) {

                    gallery -> {
                        bottomBarItems.add(
                            gallery.toBottomBarItem(
                                title = R.string.gallery,
                                activeIcon = Icons.Default.Place,
                                inactiveIcon = Icons.Outlined.Place
                            )
                        )
                    }
                    favorites -> {
                        bottomBarItems.add(
                            favorites.toBottomBarItem(
                                title = R.string.favorite,
                                activeIcon = Icons.Default.Favorite,
                                inactiveIcon = Icons.Outlined.Favorite
                            )
                        )
                    }

                    tinderGallery -> {
                        bottomBarItems.add(
                            tinderGallery.toBottomBarItem(
                                title = R.string.tinder,
                                activeIcon = Icons.Default.Search,
                                inactiveIcon = Icons.Outlined.Search
                            )
                        )
                    }

                    profile -> {
                        bottomBarItems.add(
                            profile.toBottomBarItem(
                                title = R.string.profile,
                                activeIcon = Icons.Default.Person,
                                inactiveIcon = Icons.Outlined.Person
                            )
                        )
                    }


                    else -> {}
                }
            }

            scaffoldState.setBottomBarDestinations(bottomBarItems)
        }
    }


}