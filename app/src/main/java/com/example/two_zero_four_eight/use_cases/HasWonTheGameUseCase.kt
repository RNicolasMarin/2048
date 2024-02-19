package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.model.GameStatus.*
import com.example.two_zero_four_eight.use_cases.utils.MoveNumberResult

class HasWonTheGameUseCase {

    /**
     * If the status is [PLAYING] it checks if there's any cell with [nextHighNumber],
     * in that case changes the status to [YOU_WIN] and duplicate the [nextHighNumber] value.
     * **/
    fun checkIfHasWonTheGame(
        result: MoveNumberResult,
        nextHighNumber: Int
    ): MoveNumberResult {

        if (result.gameStatus != PLAYING) return result

        val hasTheNextHighNumber = hasTheNextHighNumber(result.boardGame, nextHighNumber)
        if (!hasTheNextHighNumber) return result

        result.apply {
            gameStatus = YOU_WIN
            numberToWin = nextHighNumber * 2
        }

        return result
    }

    /**
     * Searches within [boardGame] for a cell with the value [nextHighNumber]
     * **/
    private fun hasTheNextHighNumber(boardGame: MutableList<MutableList<Int>>, nextHighNumber: Int): Boolean {
        val result = boardGame.flatten().firstOrNull { number -> number == nextHighNumber }
        return result != null
    }
}