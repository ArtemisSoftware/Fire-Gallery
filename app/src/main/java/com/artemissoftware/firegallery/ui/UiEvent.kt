package com.artemissoftware.firegallery.ui

import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.navigation.models.BaseDestination

sealed class UiEvent {

    data class ShowDialog(val dialogType: DialogType): UiEvent()

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()
    data class NavigatePopUpTo(val currentRoute: String, val destinationRoute: String): UiEvent()

    data class ChangeCurrentPositionBottomBar(val destination: BaseDestination): UiEvent()

    data class FinishAndStartActivity(val activity: Class<*>): UiEvent()
    object DeepLink: UiEvent()

    data class Redirect(val route: String): UiEvent()
}
