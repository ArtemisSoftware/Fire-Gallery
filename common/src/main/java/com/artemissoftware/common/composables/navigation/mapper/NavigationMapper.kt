package com.artemissoftware.common.composables.navigation.mapper

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector
import com.artemissoftware.common.composables.navigation.models.BaseDestination
import com.artemissoftware.common.composables.navigation.models.BottomBarItem

fun BaseDestination.toBottomBarItem(
    @StringRes title: Int,
    activeIcon: ImageVector,
    inactiveIcon: ImageVector
): BottomBarItem{

    return BottomBarItem(
        destination = this,
        title = title,
        activeIcon = activeIcon,
        inactiveIcon = inactiveIcon
    )
}