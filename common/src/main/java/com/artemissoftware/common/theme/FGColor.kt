package com.artemissoftware.common.theme

import androidx.compose.material.Colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@get:Composable
val Colors.secondaryBackground: Color
    get() = if (isLight) Color(0xffffffff) else Color(0xff252525)


@get:Composable
val Colors.primaryText: Color
    get() = if (isLight) Color(0xff616161) else Color(0xfff3f5fb)

val Orange = Color(0xFFFFC55C)
val Red = Color(0xFFFC2E20)
val RedOrange = Color(0xFFFF4500)

val SuccessGreen = Color(0xFF4BCA81)
val ErrorRed = Color(0xFFCC3300)
val AlertYellow = Color(0xFFFFcc00)
val InfoBlue = Color(0xFF840B5ED)


val ToggleBlue = Color(0xFF800AAEE)


val Purple80 = Color(0xFFf3e5f5)
val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)

val LightBlue = Color(0xDDF6F9FF)
val PlateGrey = Color(0x26F6F9FF)