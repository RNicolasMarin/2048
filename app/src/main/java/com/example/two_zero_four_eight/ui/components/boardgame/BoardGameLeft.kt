package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.example.two_zero_four_eight.data.model.SingleGameState
import com.example.two_zero_four_eight.ui.screens.UiSectionSizesLandscape

/**
 * Component used to render the left side of the screen on landscape.
 * **/
@Composable
fun BoardGameLeft(
    uiState: SingleGameState,
    uiSectionSizes: UiSectionSizesLandscape,
    isLoading: Boolean,
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
            isLoading = isLoading,
            modifier = Modifier.height(uiSectionSizes.topHeight)
        )
        BoardGameBottom(
            gameStatus = uiState.gameStatus,
            singlePartHeight = uiSectionSizes.singlePartHeight,
        )
    }
}