package com.artemissoftware.common.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

@get:Composable
val Colors.secondaryBackground: Color
    get() = if (isLight) Color(0xffffffff) else Color(0xff252525)


val Orange = Color(0xFFFFC55C)
val Red = Color(0xFFFC2E20)
val RedOrange = Color(0xFFFF4500)