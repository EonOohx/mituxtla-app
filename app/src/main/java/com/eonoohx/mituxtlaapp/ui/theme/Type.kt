package com.eonoohx.mituxtlaapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.eonoohx.mituxtlaapp.R

val andikaBold = FontFamily(Font(resId = R.font.andika_bold))
val daysOneRegular = FontFamily(Font(resId = R.font.days_one))

// Set of Material typography styles to start with
val Typography = Typography(
    headlineLarge = Typography().headlineLarge.copy(
        fontFamily = daysOneRegular,
        fontWeight = FontWeight.Bold
    ),
    headlineMedium = Typography().headlineMedium.copy(
        fontFamily = daysOneRegular,
        fontWeight = FontWeight.Bold
    ),
    bodyLarge = Typography().bodyLarge.copy(
        fontFamily = andikaBold,
        fontSize = 20.sp
    ),
    labelLarge = Typography().labelLarge.copy(
        fontFamily = andikaBold
    )
)