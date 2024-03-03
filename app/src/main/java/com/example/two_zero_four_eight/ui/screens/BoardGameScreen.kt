package com.example.two_zero_four_eight.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.model.CurrentRecordData
import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.ui.TwoZeroFourEightViewModel
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGame
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameBottom
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameBottomButtons
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameTop
import com.example.two_zero_four_eight.ui.theme.Green7
import com.example.two_zero_four_eight.ui.theme.dimens
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

@Composable
fun BoardGameScreen(viewModel: TwoZeroFourEightViewModel, uiState: GameState) {
    var currentDirection by remember { mutableStateOf(NONE) }
    val uiSectionSizes = getUiSectionSizes(LocalConfiguration.current, MaterialTheme.dimens.outerPadding)

    Box {
        Column(
            horizontalAlignment = CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Green7),
        ) {
            BoardGameTop(
                singlePartHeight = uiSectionSizes.singlePartHeight,
                dataNumber = CurrentRecordData(currentValue = 32, recordValue = 1024),
                dataScore = CurrentRecordData(currentValue = 6, recordValue = 3260),
                modifier = Modifier.height(uiSectionSizes.topHeight)
            )

            BoardGame(
                tableData = uiState.board,
                currentDirection = currentDirection,
                boardGameSize = uiSectionSizes.boardGameSize,
                modifier = Modifier.height(uiSectionSizes.boardGameHeight)
            )

            BoardGameBottom(
                gameStatus = uiState.gameStatus,
                singlePartHeight = uiSectionSizes.singlePartHeight,
            )
        }
        DragGesturesDirectionDetector(
            modifier = Modifier.fillMaxSize(),
            onDirectionDetected = {
                currentDirection = it
                viewModel.moveNumbers(it)
            }
        ) {
            BoardGameBottomButtons(uiSectionSizes) { viewModel.startNewGame() }
        }
    }
}

data class UiSectionSizes(
    val singlePartHeight: Dp,
    val topHeight: Dp,
    val boardGameHeight: Dp,
    val boardGameSize: Dp,
    val bottomHeight: Dp
)

fun getUiSectionSizes(configuration: Configuration, outerPadding: Dp): UiSectionSizes {
    val totalHeight = configuration.screenHeightDp.dp

    val singlePartHeight = totalHeight.times(0.1f)
    val topHeight = totalHeight.times(0.3f)
    val boardGameHeight = totalHeight.times(0.5f)
    val bottomHeight = totalHeight.times(0.2f)

    val width = configuration.screenWidthDp.dp.minus(outerPadding + outerPadding)
    val boardGameSize = if (width <= boardGameHeight) width else boardGameHeight

    return UiSectionSizes(
        singlePartHeight = singlePartHeight,
        topHeight = topHeight,
        boardGameHeight = boardGameHeight,
        boardGameSize = boardGameSize,
        bottomHeight = bottomHeight,
    )
}