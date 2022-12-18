package com.artemissoftware.common.composables.navigation

import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import com.artemissoftware.common.util.ScreenTransitions.slideInLeft
import com.artemissoftware.common.util.ScreenTransitions.slideInRight
import com.artemissoftware.common.util.ScreenTransitions.slideOutRight
import com.artemissoftware.common.util.ScreenTransitions.slideOutVertically
import com.google.accompanist.navigation.animation.composable

@ExperimentalAnimationApi
fun NavGraphBuilder.animatedComposable(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    durationMillis: Int = 500,
    content: @Composable AnimatedVisibilityScope.(NavBackStackEntry) -> Unit
) {
    composable(
        route = route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
        enterTransition = { slideInRight(durationMillis) },
        exitTransition = { slideOutVertically(durationMillis) },
        popEnterTransition = { slideInLeft(durationMillis) },
        popExitTransition = { slideOutRight(durationMillis) },
    )
}
