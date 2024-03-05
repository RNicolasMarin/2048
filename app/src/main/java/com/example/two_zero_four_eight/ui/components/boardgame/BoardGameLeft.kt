package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.two_zero_four_eight.model.CurrentRecordData
import com.example.two_zero_four_eight.model.GameStatus
import com.example.two_zero_four_eight.ui.screens.UiSectionSizesLandscape

@Composable
fun BoardGameLeft(
    gameStatus: GameStatus,
    uiSectionSizes: UiSectionSizesLandscape,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BoardGameTop(
            singlePartHeight = uiSectionSizes.singlePartHeight,
            dataNumber = CurrentRecordData(currentValue = 32, recordValue = 1024),
            dataScore = CurrentRecordData(currentValue = 6, recordValue = 3260),
            modifier = Modifier.height(uiSectionSizes.topHeight)
        )
        BoardGameBottom(
            gameStatus = gameStatus,
            singlePartHeight = uiSectionSizes.singlePartHeight,
        )
    }
}