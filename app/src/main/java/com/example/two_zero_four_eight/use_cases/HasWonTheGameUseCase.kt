package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.model.GameState
import com.example.two_zero_four_eight.model.GameStatus.*

class HasWonTheGameUseCase {

    /**
     * If the status is [PLAYING] it checks if there's any cell with [nextHighNumber],
     * in that case changes the status to [YOU_WIN] and duplicate the [nextHighNumber] value.
     * **/
    fun checkIfHasWonTheGame(
        gameState: GameState
    ): GameState = with(gameState) {

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