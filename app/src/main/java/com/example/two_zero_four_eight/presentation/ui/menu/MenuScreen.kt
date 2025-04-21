package com.example.two_zero_four_eight.presentation.ui.menu

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.domain.models.BoardSize.THREE_X_THREE
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.MultiDevicePreview
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.LANDSCAPE
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.PORTRAIT
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.components.IconButtonNext
import com.example.two_zero_four_eight.presentation.design_system.components.IconButtonPrevious
import com.example.two_zero_four_eight.presentation.design_system.components.LabelText
import com.example.two_zero_four_eight.presentation.design_system.components.WideButton
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.screenContentOrientation
import com.example.two_zero_four_eight.presentation.ui.game.components.BoardGame
import com.example.two_zero_four_eight.presentation.ui.menu.MenuAction.OnNextSize
import com.example.two_zero_four_eight.presentation.ui.menu.MenuAction.OnPreviousSize
import com.example.two_zero_four_eight.presentation.ui.menu.MenuAction.OnRecords
import com.example.two_zero_four_eight.presentation.ui.menu.MenuAction.OnStartGame
import com.example.two_zero_four_eight.presentation.design_system.Green7

@Composable
fun MenuScreenRoot(
    onStartGame: (Int) -> Unit,
    onRecords: () -> Unit,
    viewModel: MenuViewModel = hiltViewModel()
) {
    MenuScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is OnStartGame -> onStartGame(action.size)
                is OnRecords -> onRecords()
                else -> Unit
            }
            viewModel.onAction(action)
        },
    )
}

@Composable
fun MenuScreen(
    state: MenuState,
    dimens: Dimens = MaterialTheme.dimens,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    modifier: Modifier = Modifier,
    onAction: (MenuAction) -> Unit
) {
    val screenPadding = dimens.screenPadding
    if (screenContentOrientation == LANDSCAPE) {
        Row(
            modifier = modifier
                .fillMaxSize()
                .background(Green7)
                .padding(screenPadding)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
            ) {
                BoardGame(
                    data = state.boardSize.cells.flatten(),
                    modifier = Modifier
                )
            }
            Spacer(modifier = Modifier
                .fillMaxHeight()
                .width(screenPadding)
            )
            MenuScreenButtons(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(0.9f)
            )
        }
    } else {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .fillMaxSize()
                .background(Green7)
                .padding(
                    horizontal = screenPadding * 2,
                    vertical = screenPadding
                )
        ) {
            if (screenContentOrientation == PORTRAIT) {
                BoardGame(
                    data = state.boardSize.cells.flatten(),
                    modifier = Modifier
                        .fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(dimens.screenPadding))
            }
            MenuScreenButtons(
                state = state,
                onAction = onAction,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}

@Composable
fun MenuScreenButtons(
    state: MenuState,
    dimens: Dimens = MaterialTheme.dimens,
    onAction: (MenuAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            IconButtonPrevious(
                modifier = Modifier,
                onClick = { onAction(OnPreviousSize) }
            )

            Spacer(modifier = Modifier.width(dimens.screenPadding * 2))

            val size = state.boardSize.size
            LabelText(
                text = "${size}X${size}",
                modifier = Modifier
                    .weight(1f)
                    .height(dimens.iconButtonSize),
            )

            Spacer(modifier = Modifier.width(dimens.screenPadding * 2))

            IconButtonNext(
                modifier = Modifier,
                onClick = { onAction(OnNextSize) }
            )
        }

        Spacer(modifier = Modifier.height(dimens.screenPadding * 2))

        WideButton(
            text = stringResource(id = R.string.button_start_game),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(OnStartGame(state.boardSize.size))
            }
        )

        WideButton(
            text = stringResource(id = R.string.button_records),
            modifier = Modifier.fillMaxWidth(),
            onClick = {
                onAction(OnRecords)
            }
        )
    }
}

@MultiDevicePreview
@Composable
private fun MenuScreenPreview() {
    TwoZeroFourEightTheme {
        MenuScreen(
            state = MenuState(
                boardSize = THREE_X_THREE
            ),
            modifier = Modifier.fillMaxSize(),
            onAction = {}
        )
    }
}