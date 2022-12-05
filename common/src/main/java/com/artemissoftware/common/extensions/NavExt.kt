package com.artemissoftware.common.extensions

import android.net.Uri
import androidx.navigation.NavHostController

fun NavHostController.changeGraph(route: String) {
    navigate(route) {
        if (this@changeGraph.backQueue.isNotEmpty()) {
            this@changeGraph.backQueue.last().destination.route?.let { route ->
                popUpTo(route) {
                    inclusive = true
                }
            }
        }
    }
}

fun NavHostController.changeGraph(route: Uri) {
    navigate(route).apply {
        if (this@changeGraph.backQueue.isNotEmpty()) {
            this@changeGraph.backQueue.last().destination.route?.let { route ->
                popBackStack(route, inclusive = false, saveState = false)
//                popUpTo(route) {
//                    inclusive = true
//                }
            }
        }
    }
}