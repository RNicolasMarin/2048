package com.example.two_zero_four_eight.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.data.model.SingleGameState
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGame
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameBottomButtons
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameLeft
import com.example.two_zero_four_eight.ui.theme.Green7
import com.example.two_zero_four_eight.ui.theme.dimens
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection

@Composable
fun BoardGameScreenLandscape(
    uiState: SingleGameState,
    currentDirection: MovementDirection,
    showAllSections: Boolean,
    isLoading: Boolean,
    setCurrentDirection: (MovementDirection) -> Unit,
    moveNumbers: (MovementDirection) -> Unit,
    previousBoard: () -> Unit,
    startNewGame: () -> Unit
) {
    val uiSectionSizes = getUiSectionSizesLandscape(LocalConfiguration.current, MaterialTheme.dimens.outerPadding, showAllSections)

    Box {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Green7)
        ) {
            if (showAllSections) {
                BoardGameLeft(
                    uiState = uiState,
                    uiSectionSizes = uiSectionSizes,
                    isLoading = isLoading,
                    modifier = Modifier
                        .width(uiSectionSizes.singlePartWidth)
                )
            }
            BoardGame(
                tableData = uiState.board,
                currentDirection = currentDirection,
                boardGameSize = uiSectionSizes.boardGameSize,
                isLoading = isLoading,
                modifier = Modifier
                    .width(uiSectionSizes.singlePartWidth)
                    .height(uiSectionSizes.boardGameHeight)
            )
        }
        DragGesturesDirectionDetector(
            modifier = Modifier.fillMaxSize(),
            onDirectionDetected = {
                setCurrentDirection(it)
                moveNumbers(it)
            }
        ) {
            if (showAllSections) {
                BoardGameBottomButtons(
                    topHeight = uiSectionSizes.topHeight,
                    boardGameHeight = 0.dp,
                    singlePartHeight = uiSectionSizes.singlePartHeight,
                    modifier = Modifier.width(uiSectionSizes.singlePartWidth),
                    previousBoard = { previousBoard() },
                    startNewGame = { startNewGame() }
                )
            }
        }
    }
}

fun getUiSectionSizesLandscape(
    configuration: Configuration,
    outerPadding: Dp,
    showAllSections: Boolean
): UiSectionSizesLandscape {
    val totalHeight = configuration.screenHeightDp.dp
    val totalWidth = configuration.screenWidthDp.dp

    val singlePartHeight = totalHeight.times(0.2f)
    val topHeight = totalHeight.times(0.6f)
    val bottomHeight = totalHeight.times(0.4f)
    val boardGameHeight = totalHeight.times(1f)

    val width = totalWidth.div(if (showAllSections) 2 else 1).minus(outerPadding + outerPadding)
    val boardGameSize = if (width <= boardGameHeight) width else boardGameHeight

    val singlePartWidth = totalWidth.div(if (showAllSections) 2 else 1)

    return UiSectionSizesLandscape(
        singlePartHeight = singlePartHeight,
        topHeight = topHeight,
        boardGameHeight = boardGameHeight,
        boardGameSize = boardGameSize,
        bottomHeight = bottomHeight,
        singlePartWidth = singlePartWidth
    )
}

data class UiSectionSizesLandscape(
    val singlePartHeight: Dp,
    val topHeight: Dp,
    val boardGameHeight: Dp,
    val boardGameSize: Dp,
    val bottomHeight: Dp,
    val singlePartWidth: Dp
)