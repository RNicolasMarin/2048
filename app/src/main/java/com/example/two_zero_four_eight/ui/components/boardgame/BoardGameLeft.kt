package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.ui.screens.UiSectionSizesLandscape

@Composable
fun BoardGameLeft(
    uiState: GameState,
    uiSectionSizes: UiSectionSizesLandscape,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        BoardGameTop(
            singlePartHeight = uiSectionSizes.singlePartHeight,
            dataNumber = uiState.numberCurrentRecord,
            dataScore = uiState.scoreCurrentRecord,
            modifier = Modifier.height(uiSectionSizes.topHeight)
        )
        BoardGameBottom(
            gameStatus = uiState.gameStatus,
            singlePartHeight = uiSectionSizes.singlePartHeight,
        )
    }
}