package com.artemissoftware.firegallery.ui

import com.artemissoftware.common.composables.dialog.models.DialogType

sealed class UiEvent {
    data class ShowErrorDialog(val title: String, val message: String): UiEvent()
    data class ShowInfoDialog(val title: String, val message: String): UiEvent()

    data class ShowDialog(val dialogType: DialogType): UiEvent()

    object PopBackStack: UiEvent()

    data class Navigate(val route: String): UiEvent()
}
