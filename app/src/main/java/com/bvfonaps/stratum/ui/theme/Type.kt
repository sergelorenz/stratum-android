package com.bvfonaps.stratum.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bvfonaps.stratum.R


val Kantumruy = FontFamily(
    Font(R.font.kantumruy_regular),
    Font(R.font.kantumruy_bold, FontWeight.Bold),
    Font(R.font.kantumruy_light, FontWeight.Light)
)

val AppTypography = Typography(
    titleLarge = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Bold,
        fontSize = 64.sp
    ),
    displayLarge = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp
    ),
    displaySmall = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp
    ),
    labelMedium = TextStyle(
        fontFamily = Kantumruy,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
)
