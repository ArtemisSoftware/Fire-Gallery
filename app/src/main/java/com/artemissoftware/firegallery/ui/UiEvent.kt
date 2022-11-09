package com.artemissoftware.firegallery.ui

import com.artemissoftware.common.composables.dialog.models.DialogType
import com.artemissoftware.common.composables.navigation.models.BaseDestinations

sealed class UiEvent {

    data class ShowDialog(val dialogType: DialogType): UiEvent()

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()

    data class ChangeCurrentPositionBottomBar(val destination: BaseDestinations): UiEvent()
}
