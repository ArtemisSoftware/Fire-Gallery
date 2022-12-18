package com.artemissoftware.common.composables.navigation.models

import androidx.annotation.StringRes
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomBarItem(@StringRes val title: Int,
                         val activeIcon: ImageVector,
                         val inactiveIcon: ImageVector,
                         val destination: BaseDestination)
