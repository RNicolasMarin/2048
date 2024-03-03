package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.ui.components.IconButton
import com.example.two_zero_four_eight.ui.screens.UiSectionSizes
import com.example.two_zero_four_eight.ui.theme.dimens

@Composable
fun BoardGameBottomButtons(
    uiSectionSizes: UiSectionSizes,
    startNewGame: () -> Unit
) {
    val offset = uiSectionSizes.topHeight + uiSectionSizes.boardGameHeight + MaterialTheme.dimens.innerPadding
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .offset(y = offset)
            .fillMaxWidth()
    ) {
        IconButton(
            size = uiSectionSizes.singlePartHeight - MaterialTheme.dimens.innerPadding,
            contentDescription = stringResource(id = R.string.start_again_button_description),
            onClick = {
                startNewGame()
            }
        )

        Spacer(modifier = Modifier.width(MaterialTheme.dimens.innerPadding))

        IconButton(
            size = uiSectionSizes.singlePartHeight - MaterialTheme.dimens.innerPadding,
            contentDescription = stringResource(id = R.string.start_again_button_description),
            onClick = {
                startNewGame()
            }
        )
    }
}