package com.example.two_zero_four_eight.ui.screens

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.model.CurrentRecordData
import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGame
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameBottom
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameBottomButtons
import com.example.two_zero_four_eight.ui.components.boardgame.BoardGameTop
import com.example.two_zero_four_eight.ui.theme.Green7
import com.example.two_zero_four_eight.ui.theme.dimens
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection

@Composable
fun BoardGameScreenPortrait(
    uiState: GameState,
    currentDirection: MovementDirection,
    showAllSections: Boolean,
    setCurrentDirection: (MovementDirection) -> Unit,
    moveNumbers: (MovementDirection) -> Unit,
    startNewGame: () -> Unit
) {
    val uiSectionSizes = getUiSectionSizesPortrait(LocalConfiguration.current, MaterialTheme.dimens.outerPadding, showAllSections)

    Box {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Green7),
        ) {
            if (showAllSections) {
                BoardGameTop(
                    singlePartHeight = uiSectionSizes.singlePartHeight,
                    dataNumber = uiState.numberCurrentRecord,
                    dataScore = CurrentRecordData(currentValue = 6, recordValue = 3260),
                    modifier = Modifier.height(uiSectionSizes.topHeight)
                )
            }

            BoardGame(
                tableData = uiState.board,
                currentDirection = currentDirection,
                boardGameSize = uiSectionSizes.boardGameSize,
                modifier = Modifier.height(uiSectionSizes.boardGameHeight)
            )

            if (showAllSections) {
                BoardGameBottom(
                    gameStatus = uiState.gameStatus,
                    singlePartHeight = uiSectionSizes.singlePartHeight,
                )
            }
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
                    boardGameHeight = uiSectionSizes.boardGameHeight,
                    singlePartHeight = uiSectionSizes.singlePartHeight,
                    modifier = Modifier.fillMaxWidth(),
                    startNewGame = { startNewGame() }
                )
            }
        }
    }
}

fun getUiSectionSizesPortrait(
    configuration: Configuration,
    outerPadding: Dp,
    showAllSections: Boolean
): UiSectionSizesPortrait {
    val totalHeight = configuration.screenHeightDp.dp

    val singlePartHeight = totalHeight.times(0.1f)
    val topHeight = totalHeight.times(0.3f)
    val bottomHeight = totalHeight.times(0.2f)
    val boardGameHeight = totalHeight.times(if (showAllSections) 0.5f else 1f)

    val width = configuration.screenWidthDp.dp.minus(outerPadding + outerPadding)
    val boardGameSize = if (width <= boardGameHeight) width else boardGameHeight

    return UiSectionSizesPortrait(
        singlePartHeight = singlePartHeight,
        topHeight = topHeight,
        boardGameHeight = boardGameHeight,
        boardGameSize = boardGameSize,
        bottomHeight = bottomHeight,
    )
}

data class UiSectionSizesPortrait(
    val singlePartHeight: Dp,
    val topHeight: Dp,
    val boardGameHeight: Dp,
    val boardGameSize: Dp,
    val bottomHeight: Dp
)