package com.example.two_zero_four_eight.ui.screens

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.ui.TwoZeroFourEightViewModel
import com.example.two_zero_four_eight.ui.theme.isBothCompact
import com.example.two_zero_four_eight.ui.theme.isPortrait
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

@Composable
fun BoardGameScreen(viewModel: TwoZeroFourEightViewModel, uiState: GameState) {
    var currentDirection by remember { mutableStateOf(NONE) }
    val showAllSections = !MaterialTheme.isBothCompact

    if (MaterialTheme.isPortrait) {
        BoardGameScreenPortrait(uiState.currentState, currentDirection, showAllSections, uiState.isLoading,
            setCurrentDirection = { currentDirection = it },
            moveNumbers = { viewModel.moveNumbers(it) },
            previousBoard = { viewModel.previousBoard() },
            startNewGame = { viewModel.startNewGame() }
        )
    } else {
        BoardGameScreenLandscape(uiState.currentState, currentDirection, showAllSections, uiState.isLoading,
            setCurrentDirection = { currentDirection = it },
            moveNumbers = { viewModel.moveNumbers(it) },
            previousBoard = { viewModel.previousBoard() },
            startNewGame = { viewModel.startNewGame() }
        )
    }
}