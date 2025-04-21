package com.example.two_zero_four_eight.presentation.ui.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.two_zero_four_eight.presentation.design_system.movements.MovementDirection
import com.example.two_zero_four_eight.presentation.design_system.movements.MovementDirection.*
import com.example.two_zero_four_eight.domain.use_cases.CreateBoardGameUseCase
import com.example.two_zero_four_eight.domain.use_cases.MoveNumbersUseCase
import com.example.two_zero_four_eight.presentation.ui.game.GameAction.*
import com.example.two_zero_four_eight.presentation.ui.game.GameEvent.*
import com.example.two_zero_four_eight.presentation.ui.game.GameStatus.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

const val DEFAULT_NUMBER_TO_WIN = 16

@HiltViewModel
class GameViewModel @Inject constructor(
    private val boardGameUseCases: CreateBoardGameUseCase,
    private val moveNumbersUseCase: MoveNumbersUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(GameState())
    val state: StateFlow<GameState> = _state

    private val eventChannel = Channel<GameEvent>()
    val events = eventChannel.receiveAsFlow()

    fun onAction(action: GameAction) {
        when(action) {
            is OnMoveNumbers -> moveNumbers(action.direction)
            OnPreviousBoard -> previousBoard()
            is OnStartGame -> startNewGame(action.size, action.deletePreviousState)
        }
    }

    private fun startNewGame(size: Int, deletePreviousState: Boolean = false) = viewModelScope.launch {
        val sizeToUse = if (size != -1) size else state.value.boardSize

        _state.value = state.value.copy(
            boardSize = sizeToUse,
            isLoading = true
        )

        val current = state.value.currentState
        val newBoard = boardGameUseCases.createBoardGame(current, sizeToUse, deletePreviousState)

        with(newBoard) {
            _state.value = state.value.copy(
                simpleBoard = currentState.board.flatten(),
                currentState = currentState,
                previousState = previousState,
                originalBestValues = originalBestValues,
                isLoading = false
            )
        }
    }

    private fun moveNumbers(direction: MovementDirection) = viewModelScope.launch {
        if (direction == NONE || state.value.currentState.gameStatus != PLAYING) return@launch

        val newBoard = moveNumbersUseCase.moveNumbers(direction, state.value)

        with(newBoard) {
            _state.value = state.value.copy(
                simpleBoard = currentState.board.flatten(),
                currentState = currentState,
                previousState = previousState,
                originalBestValues = originalBestValues
            )

            with(currentState) {
                when (gameStatus) {
                    GAME_OVER -> {
                        eventChannel.send(GameOver(
                            numberCurrentRecord = numberCurrentRecord,
                            scoreCurrentRecord = scoreCurrentRecord,
                            numberToWin = numberToWin
                        ))
                    }
                    YOU_WIN -> {
                        eventChannel.send(YouWin(
                            numberCurrentRecord = numberCurrentRecord,
                            scoreCurrentRecord = scoreCurrentRecord,
                        ))
                    }
                    PLAYING -> {}
                }
            }
        }
    }

    private fun previousBoard() = viewModelScope.launch {
        val previous = state.value.previousState?.copy() ?: return@launch

        with(previous) {
            _state.value = state.value.copy(
                simpleBoard = board.flatten(),
                currentState = this,
                previousState = null,
            )
        }
    }

    fun updateCurrentGameStatus(status: GameStatus) {
        _state.value = state.value.copy(
            currentState = state.value.currentState.apply {
                gameStatus = status
            }
        )
    }
}