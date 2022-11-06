package com.artemissoftware.firegallery

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
import androidx.lifecycle.viewModelScope
import com.artemissoftware.common.composables.navigation.mapper.toBottomBarItem
import com.artemissoftware.common.composables.navigation.models.BaseDestinations
import com.artemissoftware.common.composables.navigation.models.BottomBarItem
import com.artemissoftware.common.composables.scaffold.models.FGScaffoldState
import com.artemissoftware.domain.usecases.GetUserUseCase
import com.artemissoftware.firegallery.navigation.HomeDestinations
import com.artemissoftware.firegallery.ui.FGBaseEventViewModel
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
        onTriggerEvent(MainEvents.GetUser)
    }

    override fun onTriggerEvent(event: MainEvents) {
        when(event){
            is MainEvents.GetUser ->{
                getUser()
            }
        }
    }

    private fun getUser(){

        viewModelScope.launch {

            getUserUseCase.invoke().collectLatest { result ->

                val bottomBarItems = result?.let {

                    listOf(
                        HomeDestinations.Gallery,
                        HomeDestinations.Favorites,
                        HomeDestinations.Profile
                    )

                } ?: kotlin.run {
                    listOf(
                        HomeDestinations.Gallery,
                        HomeDestinations.Profile
                    )
                }

                setBottomBarItems(bottomBarItems)
            }
        }
    }


    private fun setBottomBarItems(items: List<BaseDestinations>) {

        val bottomBarItems = mutableListOf<BottomBarItem>()

        items.forEach {

            when(it){

                HomeDestinations.Gallery->{
                    bottomBarItems.add(
                        HomeDestinations.Gallery.toBottomBarItem(
                            title = R.string.gallery,
                            activeIcon = Icons.Default.Place,
                            inactiveIcon = Icons.Outlined.Place
                        )
                    )
                }
                HomeDestinations.Favorites->{
                    bottomBarItems.add(
                        HomeDestinations.Favorites.toBottomBarItem(
                            title = R.string.favorite,
                            activeIcon = Icons.Default.Favorite,
                            inactiveIcon = Icons.Outlined.Favorite
                        )
                    )
                }
                HomeDestinations.Profile->{
                    bottomBarItems.add(
                        HomeDestinations.Profile.toBottomBarItem(
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