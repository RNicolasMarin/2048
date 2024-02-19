package com.example.two_zero_four_eight.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Center
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.model.GameStatus.*
import com.example.two_zero_four_eight.ui.TwoZeroFourEightViewModel
import com.example.two_zero_four_eight.ui.components.AppName
import com.example.two_zero_four_eight.ui.components.AppNameDefaultHeight
import com.example.two_zero_four_eight.ui.components.IconButton
import com.example.two_zero_four_eight.ui.components.IconButtonHeight
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.Green7
import com.example.two_zero_four_eight.ui.theme.Grey2
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.example.two_zero_four_eight.ui.utils.getCellData
import com.example.two_zero_four_eight.use_cases.DEFAULT_VALUE

val corners = 10.dp
val paddings_inside_board = 3.dp
val paddings_outside_board = 18.dp
val SpaceBeforeBoard = 50.dp

@Composable
fun BoardGameScreen(viewModel: TwoZeroFourEightViewModel, uiState: GameState) {
    var currentDirection by remember { mutableStateOf(NONE) }
    val configuration = LocalConfiguration.current
    val uiBoardSize = configuration.screenWidthDp.dp.minus(paddings_outside_board + paddings_outside_board)
    val reStartButtonY = paddings_outside_board + AppNameDefaultHeight + SpaceBeforeBoard + uiBoardSize + 10.dp
    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Green7),
            horizontalAlignment = CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        start = paddings_outside_board,
                        end = paddings_outside_board,
                        top = paddings_outside_board,
                        bottom = SpaceBeforeBoard
                    ),
                horizontalArrangement = Arrangement.Start
            ) {
                AppName()
            }

            BoardGame(uiState.board, currentDirection, uiBoardSize)
            Spacer(modifier = Modifier.height(IconButtonHeight + 10.dp))

            val textMessageId = when (uiState.gameStatus) {
                PLAYING -> R.string.message_empty
                GAME_OVER -> R.string.message_game_over
                YOU_WIN -> R.string.message_you_win
            }
            Text(
                text = stringResource(textMessageId),
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxHeight()
                    .wrapContentHeight()
            )

        }
        DragGesturesDirectionDetector(
            modifier = Modifier
                .fillMaxSize(),
            onDirectionDetected = {
                currentDirection = it
                viewModel.moveNumbers(it)
            }
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Center
            ) {
                IconButton(
                    modifier = Modifier.offset(y = reStartButtonY),
                    onClick = {
                        viewModel.startNewGame()
                    }
                )
            }
        }
    }
}

/**
 * [currentDirection] allows to refresh the table. Check hot to fix this bug.
 * **/
@Composable
fun BoardGame(tableData: List<List<Int>>, currentDirection: MovementDirection, uiBoardSize: Dp) {
    LazyColumn(
        modifier = Modifier
            .width(uiBoardSize)
            .height(uiBoardSize)
            .background(
                color = Grey2,
                shape = RoundedCornerShape(corners)
            )
            .padding(paddings_inside_board)
    ) {
        items(
            items = tableData,
        ) { row ->
            BoardGameRow(rowData = row, uiBoardSize - paddings_inside_board - paddings_inside_board)
        }
    }
}

@Composable
fun BoardGameRow(rowData: List<Int>, uiBoardSize: Dp) {
    val uiCellSize = uiBoardSize.div(rowData.size)

    LazyRow(
        modifier = Modifier
            .width(uiBoardSize)
            .height(uiCellSize)
    ) {
        items(
            items = rowData,
        ) { cellData ->
            BoardGameCell(cellData, uiCellSize)
        }
    }
}

@Composable
fun BoardGameCell(cellNumber: Int, uiCellSize: Dp) {
    val cellData = getCellData(cellNumber)
    Box(modifier = Modifier
        .width(uiCellSize)
        .height(uiCellSize)
        .padding(paddings_inside_board)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(corners),
                    color = cellData.backgroundColor
                )
                .border(
                    shape = RoundedCornerShape(corners),
                    color = Black,
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                fontSize = 25.sp,
                color = cellData.textColor,
                fontWeight = FontWeight.Bold,
                text = if (cellNumber == DEFAULT_VALUE) "" else cellNumber.toString()
            )
        }
    }
}
