package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.Grey2
import com.example.two_zero_four_eight.ui.theme.dimens
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.getCellData
import com.example.two_zero_four_eight.use_cases.DEFAULT_VALUE

/**
 * [currentDirection] allows to refresh the table. Check hot to fix this bug.
 * **/
@Composable
fun BoardGame(
    tableData: List<List<Int>>,
    currentDirection: MovementDirection,
    boardGameSize: Dp,
    modifier: Modifier = Modifier
) {
    val boardInnerPadding = MaterialTheme.dimens.boardInnerPadding
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier.fillMaxWidth()
    ) {
        LazyColumn(
            modifier = Modifier
                .width(boardGameSize)
                .height(boardGameSize)
                .background(
                    color = Grey2,
                    shape = RoundedCornerShape(MaterialTheme.dimens.corners)
                )
                .padding(boardInnerPadding)
        ) {
            items(
                items = tableData,
            ) { row ->
                BoardGameRow(rowData = row, boardGameSize - boardInnerPadding.times(2))
            }
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
        .padding(MaterialTheme.dimens.boardInnerPadding)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    shape = RoundedCornerShape(MaterialTheme.dimens.corners),
                    color = cellData.backgroundColor
                )
                .border(
                    shape = RoundedCornerShape(MaterialTheme.dimens.corners),
                    color = Black,
                    width = 1.dp
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = if (cellNumber == DEFAULT_VALUE) "" else cellNumber.toString(),
                color = cellData.textColor,
                style = MaterialTheme.typography.displaySmall
            )
        }
    }
}