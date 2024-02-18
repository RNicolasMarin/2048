package com.example.two_zero_four_eight.ui.utils

import androidx.compose.ui.graphics.Color
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.Green1
import com.example.two_zero_four_eight.ui.theme.Green2
import com.example.two_zero_four_eight.ui.theme.Green3
import com.example.two_zero_four_eight.ui.theme.Green4
import com.example.two_zero_four_eight.ui.theme.Green5
import com.example.two_zero_four_eight.ui.theme.Green6
import com.example.two_zero_four_eight.ui.theme.Green7
import com.example.two_zero_four_eight.ui.theme.Grey1
import com.example.two_zero_four_eight.ui.theme.White
import com.example.two_zero_four_eight.use_cases.DEFAULT_VALUE

data class CellData(
    val backgroundColor: Color,
    val textColor: Color
)

fun getCellData(cellData: Int): CellData {
    val backgroundColor = when (cellData) {
        DEFAULT_VALUE -> Grey1
        2 -> Green7
        4 -> Green6
        8 -> Green5
        16 -> Green4
        32 -> Green3
        64 -> Green2
        128 -> Green1

        256 -> Green7
        512 -> Green6
        1024 -> Green5
        2048 -> Green4
        4096 -> Green3
        8192 -> Green2

        else -> Green7
    }
    val textColor = when (cellData) {
        2, 4, 8, 16 -> Black
        32, 64, 128 -> White

        256, 512, 1024, 2048 -> Black
        4096, 8192 -> White

        else -> Black
    }
    return CellData(
        backgroundColor = backgroundColor,
        textColor = textColor
    )
}