package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.SingleGameState

class HasWonTheGameUseCase {

    /**
     * If the status is [PLAYING] it checks if there's any cell with [gameState.numberToWin],
     * in that case changes the status to [YOU_WIN] and duplicate the [gameState.numberToWin] value.
     * **/
    fun checkIfHasWonTheGame(
        gameState: SingleGameState
    ): SingleGameState = with(gameState) {

        if (gameStatus != PLAYING) return this

        val hasTheNextHighNumber = hasTheNextHighNumber(board, numberToWin)
        if (!hasTheNextHighNumber) return this

        this.apply {
            gameStatus = YOU_WIN
            numberToWin *= 2
        }

        return this
    }

    /**
     * Searches within [boardGame] for a cell with the value [nextHighNumber]
     * **/
    private fun hasTheNextHighNumber(boardGame: MutableList<MutableList<Int>>, nextHighNumber: Int): Boolean {
        val result = boardGame.flatten().firstOrNull { number -> number == nextHighNumber }
        return result != null
    }
}