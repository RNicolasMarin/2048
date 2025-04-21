package com.example.two_zero_four_eight.presentation.ui.game

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.domain.models.CurrentRecordData
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.MultiDevicePreview
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.LANDSCAPE
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.PORTRAIT
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.components.CurrentsRecordsBoard
import com.example.two_zero_four_eight.presentation.design_system.components.IconButtonPrevious
import com.example.two_zero_four_eight.presentation.design_system.components.IconButtonStartAgain
import com.example.two_zero_four_eight.presentation.design_system.components.LabelText
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.screenContentOrientation
import com.example.two_zero_four_eight.presentation.ui.game.GameAction.OnMoveNumbers
import com.example.two_zero_four_eight.presentation.ui.game.GameAction.OnPreviousBoard
import com.example.two_zero_four_eight.presentation.ui.game.GameAction.OnStartGame
import com.example.two_zero_four_eight.presentation.ui.game.components.BoardGame
import com.example.two_zero_four_eight.presentation.design_system.Green7
import com.example.two_zero_four_eight.presentation.design_system.ObserveAsEvents
import com.example.two_zero_four_eight.presentation.design_system.movements.DragGesturesDirectionDetector

@Composable
fun GameScreenRoot(
    onGameOver: (CurrentRecordData, CurrentRecordData, Int) -> Unit,
    onYouWin: (CurrentRecordData, CurrentRecordData) -> Unit,
    viewModel: GameViewModel = hiltViewModel()
) {
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is GameEvent.GameOver -> onGameOver(event.numberCurrentRecord, event.scoreCurrentRecord, event.numberToWin)
            is GameEvent.YouWin -> onYouWin(event.numberCurrentRecord, event.scoreCurrentRecord)
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    GameScreen(
        state = state,
        onAction = viewModel::onAction
    )
}

@Composable
fun GameScreen(
    state: GameState,
    dimens: Dimens = MaterialTheme.dimens,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    onAction: (GameAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    DragGesturesDirectionDetector(
        modifier = modifier.fillMaxSize(),
        onDirectionDetected = {
            onAction(OnMoveNumbers(it))
        }
    ) {
        if (screenContentOrientation == LANDSCAPE) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Green7)
                    .padding(dimens.screenPadding)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    LabelText(
                        text = stringResource(id = R.string.app_name),
                        modifier = Modifier
                            .height(dimens.iconButtonSize),
                    )

                    Spacer(modifier = Modifier.height(dimens.screenPadding * 2))

                    CurrentsRecordsBoard(
                        dataNumber = state.currentState.numberCurrentRecord,
                        dataScore = state.currentState.scoreCurrentRecord,
                        isLoading = state.isLoading
                    )

                    Spacer(modifier = Modifier.height(dimens.screenPadding * 2))

                    GameScreenButtons(
                        dimens = dimens,
                        onAction = onAction
                    )
                }
                Spacer(modifier = Modifier.width(dimens.screenPadding))
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .weight(1f)
                ) {
                    BoardGame(
                        data = state.simpleBoard,
                        isLoading = state.isLoading
                    )
                }
            }
        } else {
            val vertical = if (screenContentOrientation == PORTRAIT) Arrangement.SpaceBetween else Arrangement.Center

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = vertical,
                modifier = Modifier
                    .fillMaxSize()
                    .background(Green7)
                    .padding(dimens.screenPadding)
            ) {
                if (screenContentOrientation == PORTRAIT) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        LabelText(
                            text = stringResource(id = R.string.app_name),
                            modifier = Modifier
                                .height(dimens.iconButtonSize),
                        )
                        Spacer(modifier = Modifier.width(dimens.screenPadding))
                        CurrentsRecordsBoard(
                            dataNumber = state.currentState.numberCurrentRecord,
                            dataScore = state.currentState.scoreCurrentRecord,
                            isLoading = state.isLoading
                        )
                    }
                    Spacer(modifier = Modifier.height(dimens.screenPadding))
                }

                BoardGame(
                    data = state.simpleBoard,
                    isLoading = state.isLoading
                )

                if (screenContentOrientation == PORTRAIT) {
                    Spacer(modifier = Modifier.height(dimens.screenPadding))
                    GameScreenButtons(
                        dimens = dimens,
                        onAction = onAction,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(dimens.screenPadding))
                }

            }
        }
    }
}

@Composable
fun GameScreenButtons(
    dimens: Dimens = MaterialTheme.dimens,
    onAction: (GameAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
    ) {
        IconButtonPrevious(
            onClick = { onAction(OnPreviousBoard) }
        )

        Spacer(modifier = Modifier.width(dimens.screenPadding * 2))

        IconButtonStartAgain(
            onClick = { onAction(OnStartGame()) }
        )
    }
}

@MultiDevicePreview
@Composable
private fun GameScreenPreview() {
    val board = mutableListOf(mutableListOf(2,3,-1), mutableListOf(2,3,-1), mutableListOf(2,3,-1))
    val simple = listOf(2,4,-1,4,8,-1,2,-1,4)
    TwoZeroFourEightTheme {
        GameScreen(
            state = GameState(
                simpleBoard = simple,
                currentState = SingleGameState(
                    board = board,
                    numberCurrentRecord = CurrentRecordData(
                        currentValue = 32,
                        recordValue = 1024
                    ),
                    scoreCurrentRecord = CurrentRecordData(
                        currentValue = 999999,
                        recordValue = 999999
                    )
                ),
                isLoading = false,
                boardSize = 3
            ),
            modifier = Modifier.fillMaxSize(),
            onAction = {}
        )
    }
}