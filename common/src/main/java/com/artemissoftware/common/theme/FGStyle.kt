package com.artemissoftware.common.theme

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.artemissoftware.common.R

object FGStyle {


//    private val Roboto = FontFamily(
//        Font(R.font.roboto_regular),
//        Font(R.font.roboto_medium, FontWeight.W500),
//        Font(R.font.roboto_bold, FontWeight.Bold)
//    )
//
//    val TextRoboto = TextStyle(
//        fontFamily = Roboto,
//        fontSize = 14.sp
//    )
//
//    val TextRoboto10 = TextRoboto.copy(
//        fontSize = 10.sp
//    )
//
//    val TextRobotoBold = TextRoboto.copy(
//        fontWeight = FontWeight.Bold
//    )
//
//    val TextRobotoBold12 = TextRobotoBold.copy(
//        fontSize = 12.sp
//    )

    private val AlbertSansFont = FontFamily(
        Font(R.font.albertsans_regular),
        Font(R.font.albertsans_medium, FontWeight.W500),
        Font(R.font.albertsans_bold, FontWeight.Bold)
    )

    val TextAlbertSans = TextStyle(
        fontFamily = AlbertSansFont,
        fontSize = 14.sp
    )

    val TextAlbertSans12 = TextAlbertSans.copy(
        fontSize = 12.sp
    )

    val TextAlbertSansBold = TextAlbertSans.copy(
        fontWeight = FontWeight.Bold
    )

    val TextAlbertSansBold16 = TextAlbertSansBold.copy(
        fontSize = 16.sp
    )

    val TextAlbertSansBold28 = TextAlbertSansBold.copy(
        fontSize = 28.sp
    )

    val TextAlbertSansMedium = TextAlbertSans.copy(
        fontWeight = FontWeight.W500
    )


    private val Oswald = FontFamily(
        Font(R.font.oswald_regular),
        Font(R.font.oswald_medium, FontWeight.W500),
        Font(R.font.oswald_bold, FontWeight.Bold)
    )

    val TextOswald = TextStyle(
        fontFamily = Oswald,
        fontSize = 14.sp
    )

    val TextOswaldBold = TextOswald.copy(
        fontWeight = FontWeight.Bold
    )

    val TextOswaldMedium = TextOswald.copy(
        fontWeight = FontWeight.W500
    )

    val TextOswaldBold36 = TextOswaldBold.copy(
        fontSize = 36.sp
    )
}
