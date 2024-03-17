package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.ui.components.IconButton
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.White
import com.example.two_zero_four_eight.ui.theme.dimens

@Composable
fun BoardGameBottomButtons(
    topHeight: Dp,
    boardGameHeight: Dp,
    singlePartHeight: Dp,
    startNewGame: () -> Unit,
    previousBoard: () -> Unit,
    modifier: Modifier = Modifier
) {
    val innerPadding = MaterialTheme.dimens.innerPadding
    val offset = topHeight + boardGameHeight + innerPadding
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .offset(y = offset)
    ) {
        IconButton(
            size = singlePartHeight - innerPadding,
            color = Black,
            contentDescription = stringResource(id = R.string.start_again_button_description),
            onClick = {
                previousBoard()
            }
        )

        Spacer(modifier = Modifier.width(innerPadding))

        IconButton(
            size = singlePartHeight - innerPadding,
            color = White,
            contentDescription = stringResource(id = R.string.start_again_button_description),
            onClick = {
                startNewGame()
            }
        )
    }
}