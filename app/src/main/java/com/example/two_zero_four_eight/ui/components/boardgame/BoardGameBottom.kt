package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.data.model.GameStatus
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.ui.theme.Black

@Composable
fun BoardGameBottom(
    gameStatus: GameStatus,
    singlePartHeight: Dp
) {
    Spacer(modifier = Modifier.height(singlePartHeight))

    val textMessageId = when (gameStatus) {
        PLAYING -> R.string.message_empty
        GAME_OVER -> R.string.message_game_over
        YOU_WIN -> R.string.message_you_win
    }
    Text(
        text = stringResource(textMessageId),
        color = Black,
        style = MaterialTheme.typography.displaySmall,
        modifier = Modifier
            .fillMaxHeight()
            .wrapContentHeight()
    )
}