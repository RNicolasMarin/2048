package com.example.two_zero_four_eight.presentation.ui.win_or_lose

import androidx.activity.compose.BackHandler
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.domain.models.CurrentRecordData
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.MultiDevicePreview
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.LANDSCAPE
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.PORTRAIT
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.SIMPLIFIED
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.components.CurrentsRecordsBoard
import com.example.two_zero_four_eight.presentation.design_system.components.LabelText
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.screenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.ui.win_or_lose.components.BottomButtonGameOver
import com.example.two_zero_four_eight.presentation.design_system.components.BottomWideButton
import com.example.two_zero_four_eight.presentation.design_system.Black
import com.example.two_zero_four_eight.presentation.design_system.Green7

@Composable
fun WinOrLoseScreen(
    @StringRes titleRes: Int,
    numberToShow: Int,
    numberCurrentRecord: CurrentRecordData,
    scoreCurrentRecord: CurrentRecordData,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    textStyle: TextStyle = MaterialTheme.typographies.text40Bold,
    dimens: Dimens = MaterialTheme.dimens,
    onBackButtonPressed: () -> Unit,
    bottomButton: @Composable () -> Unit
) {
    BackHandler {
        // Custom back button behavior
        onBackButtonPressed()
    }

    val bottom = when (screenContentOrientation) {
        PORTRAIT -> dimens.screenPaddingBottom1
        else -> dimens.screenPadding
    }
    val top = when (screenContentOrientation) {
        PORTRAIT -> dimens.screenPaddingTop1
        else -> dimens.screenPadding
    }
    val startEnd = if (screenContentOrientation == SIMPLIFIED) dimens.screenPadding else dimens.screenPadding * 2

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Green7)
            .padding(
                top = top,
                bottom = bottom,
                start = startEnd,
                end = startEnd,
            ),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = titleRes),
            color = Black,
            style = textStyle
        )

        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (screenContentOrientation == LANDSCAPE || screenContentOrientation == PORTRAIT) {
                val labelHeight = dimens.iconButtonSize.times(if (screenContentOrientation == PORTRAIT) 1.5f else 1f)

                LabelText(
                    text = numberToShow.toString(),
                    modifier = Modifier
                        .height(labelHeight),
                )

                Spacer(modifier = Modifier.height(dimens.screenPadding * 2))
            }

            CurrentsRecordsBoard(
                dataNumber = numberCurrentRecord,
                dataScore = scoreCurrentRecord,
                isLoading = false
            )
        }

        bottomButton()
    }
}

@Composable
fun GameOverScreen(
    numberToShow: Int,
    numberCurrentRecord: CurrentRecordData,
    scoreCurrentRecord: CurrentRecordData,
    onBackButtonPressed: () -> Unit
) {
    WinOrLoseScreen(
        titleRes = R.string.game_over_title,
        numberToShow = numberToShow,
        numberCurrentRecord = numberCurrentRecord,
        scoreCurrentRecord = scoreCurrentRecord,
        onBackButtonPressed = onBackButtonPressed,
        bottomButton = {
            BottomButtonGameOver(
                goBackFromGameOver = onBackButtonPressed
            )
        }
    )
}

@Composable
fun YouWinScreen(
    numberToShow: Int,
    numberCurrentRecord: CurrentRecordData,
    scoreCurrentRecord: CurrentRecordData,
    onBackButtonPressed: () -> Unit
) {
    WinOrLoseScreen(
        titleRes = R.string.you_win_title,
        numberToShow = numberToShow,
        numberCurrentRecord = numberCurrentRecord,
        scoreCurrentRecord = scoreCurrentRecord,
        onBackButtonPressed = onBackButtonPressed,
        bottomButton = {
            BottomWideButton(
                id = R.string.continue_button,
                goBackFromYouWin = onBackButtonPressed
            )
        }
    )
}

@MultiDevicePreview
@Composable
private fun GameOverScreenPreview() {
    TwoZeroFourEightTheme {
        GameOverScreen(
            numberToShow = 2048,
            numberCurrentRecord = CurrentRecordData(32, 1024),
            scoreCurrentRecord = CurrentRecordData(152, 16000),
            onBackButtonPressed = {  }
        )
    }
}

@MultiDevicePreview
@Composable
private fun YouWinScreenPreview() {
    TwoZeroFourEightTheme {
        YouWinScreen(
            numberToShow = 2048,
            numberCurrentRecord = CurrentRecordData(32, 1024),
            scoreCurrentRecord = CurrentRecordData(152, 16000),
            onBackButtonPressed = {  }
        )
    }
}