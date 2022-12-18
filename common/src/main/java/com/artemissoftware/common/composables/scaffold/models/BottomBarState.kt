package com.artemissoftware.common.composables.scaffold.models

import com.artemissoftware.common.composables.navigation.models.BaseDestination

data class BottomBarState(
    private var currentPosition: Int = 0,
    var currentRoute: String? = null,
    var currentDestination: BaseDestination? = null
){

    fun update(currentPosition: Int, currentDestination: BaseDestination){
        this.currentPosition = currentPosition
        this.currentDestination = currentDestination
    }

}
