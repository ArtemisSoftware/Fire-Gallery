package com.artemissoftware.common.composables.scaffold.models

import com.artemissoftware.common.composables.navigation.models.BaseDestinations

data class BottomBarState(
    private var currentPosition: Int = 0,
    var currentRoute: String? = null,
    var currentDestination: BaseDestinations? = null
){

    fun update(currentPosition: Int, currentDestination: BaseDestinations){
        this.currentPosition = currentPosition
        this.currentDestination = currentDestination
    }

}
