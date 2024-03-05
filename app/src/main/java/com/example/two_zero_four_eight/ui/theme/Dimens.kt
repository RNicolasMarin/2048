package com.example.two_zero_four_eight.ui.theme

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class Dimens(
    val outerPadding: Dp = 14.dp,
    val innerPadding: Dp = 10.dp,
    val corners: Dp = 10.dp,
    val boardInnerPadding: Dp = 3.dp,
    val nameHorizontalPadding: Dp = 20.dp,
    val currentRecordWidthMin: Dp = 220.dp,
    val currentRecordWidthMax: Dp = 280.dp,
    val currentRecordPaddingHorizontal: Dp = 6.dp,
    val currentRecordPaddingVertical: Dp = 3.dp,
    val currentRecordPaddingBetween: Dp = 10.dp
)

val CompactSmallDimens = Dimens()//phone

val CompactMediumDimens = Dimens(
    nameHorizontalPadding = 26.dp,
    currentRecordPaddingBetween = 20.dp
)
val CompactDimens = Dimens(

)
val MediumDimens = Dimens(

)
val ExpandedDimens = Dimens(

)
