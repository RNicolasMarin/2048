package com.example.two_zero_four_eight.presentation.ui.game

import com.example.two_zero_four_eight.presentation.design_system.movements.MovementDirection

sealed interface GameAction {

    data class OnStartGame(
        val size: Int = -1,
        val deletePreviousState: Boolean = false
    ): GameAction

    data class OnMoveNumbers(val direction: MovementDirection): GameAction

    data object OnPreviousBoard: GameAction

}