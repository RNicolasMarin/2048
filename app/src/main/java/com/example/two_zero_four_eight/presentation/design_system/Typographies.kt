package com.example.two_zero_four_eight.presentation.design_system

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

data class Typographies(
    val text40Bold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 40.sp,
        lineHeight = 41.sp,
        letterSpacing = 0.5.sp
    ),
    val text24Bold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp
    ),
    val text24Regular: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
        lineHeight = 25.sp,
        letterSpacing = 0.5.sp
    ),
    val text20Bold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 21.sp,
        letterSpacing = 0.5.sp
    ),
    val text16Bold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 17.sp,
        letterSpacing = 0.5.sp
    ),
    val text14SemiBold: TextStyle = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.SemiBold,
        fontSize = 14.sp,
        lineHeight = 15.sp,
        letterSpacing = 0.5.sp
    )
)