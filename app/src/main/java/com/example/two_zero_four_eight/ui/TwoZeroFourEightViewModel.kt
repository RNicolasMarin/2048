package com.example.two_zero_four_eight.ui

import androidx.lifecycle.ViewModel
import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.use_cases.CreateBoardGameUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class TwoZeroFourEightViewModel: ViewModel() {

    ////inject
    private val boardGameUseCases = CreateBoardGameUseCase()

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    init {
        startNewGame()
    }

    ////check where to get this value from later on
    private fun startNewGame() {
        _gameState.update {
            it.copy(
                board = boardGameUseCases.createBoardGame(3)
            )
        }

    }


    fun moveNumbers(direction: MovementDirection) {

    }
}