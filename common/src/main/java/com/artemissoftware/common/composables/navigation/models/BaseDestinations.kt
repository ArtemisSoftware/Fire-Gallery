package com.artemissoftware.common.composables.navigation.models

import android.net.Uri
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavDeepLink
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.google.gson.Gson

abstract class BaseDestinations(
    private val route: String,
    private val baseDeepLink: String? = null,
    private val customArguments: List<CustomArguments> = emptyList()
) {
    val fullRoute: String = buildString {
        append(route)
        customArguments.forEachIndexed { index, custom ->
            val symbol = if (index == 0) "?" else "&"
            append("$symbol${custom.key}={${custom.key}}")
        }
    }

    private val link: String = buildString {
        append(baseDeepLink)
        customArguments.forEachIndexed { index, custom ->
            val symbol = if (index == 0) "/" else "&"
            append("$symbol${custom.key}={${custom.key}}")
        }
    }

    val deepLink: List<NavDeepLink> = listOf(navDeepLink { uriPattern = link })

    val arguments: List<NamedNavArgument> = customArguments.map {
        navArgument(it.key) {
            type = it.type
            nullable = it.nullable
        }
    }

    fun withArgs(vararg args: Any?): String {
        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                val symbol = if (index == 0) "?" else "&"
                append("$symbol${customArguments[index].key}=$arg")
            }
        }
    }

    fun withCustomArgs(vararg args: Any?): String {

        return buildString {
            append(route)
            args.forEachIndexed { index, arg ->
                val json = Uri.encode(Gson().toJson(arg))
                val symbol = if (index == 0) "?" else "&"
                append("$symbol${customArguments[index].key}=$json")
            }
        }
    }

}