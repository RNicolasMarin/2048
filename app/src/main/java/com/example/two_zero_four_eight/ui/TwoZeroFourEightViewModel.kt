package com.example.two_zero_four_eight.ui

import androidx.lifecycle.ViewModel
import com.example.two_zero_four_eight.model.CurrentRecordData
import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.model.GameStatus.*
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.example.two_zero_four_eight.use_cases.CreateBoardGameUseCase
import com.example.two_zero_four_eight.use_cases.MoveNumbersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

const val DEFAULT_NUMBER_TO_WIN = 16

@HiltViewModel
class TwoZeroFourEightViewModel @Inject constructor(
    private val boardGameUseCases: CreateBoardGameUseCase,
    private val moveNumbersUseCase: MoveNumbersUseCase
) : ViewModel() {

    private val _gameState = MutableStateFlow(GameState())
    val gameState = _gameState.asStateFlow()

    init {
        startNewGame()
    }

    ////check where to get the size value from later on
    fun startNewGame() {
        _gameState.update {
            it.copy(
                board = boardGameUseCases.createBoardGame(3),
                gameStatus = PLAYING,
                numberToWin = DEFAULT_NUMBER_TO_WIN,
                numberCurrentRecord = CurrentRecordData()
            )
        }
    }

    fun moveNumbers(direction: MovementDirection)  {
        if (direction == NONE) return

        val newBoard = moveNumbersUseCase.moveNumbers(direction, _gameState.value)

        _gameState.update {
            it.copy(
                board = newBoard.board,
                gameStatus = newBoard.gameStatus,
                numberToWin = newBoard.numberToWin,
                numberCurrentRecord = newBoard.numberCurrentRecord
            )
        }
    }
}