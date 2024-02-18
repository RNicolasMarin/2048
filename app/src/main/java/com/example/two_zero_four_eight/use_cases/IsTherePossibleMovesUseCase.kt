package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.model.GameStatus.*
import com.example.two_zero_four_eight.use_cases.utils.MoveNumberResult

class IsTherePossibleMovesUseCase {

    /**
     * It receives [boardGame] and checks:
     *
     * 1) If there's empty cells, keep playing.
     * 2) if there's something that can be combined (two consecutive cells
     * vertically or horizontally with the same number), keep playing.
     * 3) Otherwise is game over
     * **/
    fun checkMovesToContinue(
        boardGame: MutableList<MutableList<Int>>,
        numberToWin: Int
    ): MoveNumberResult {
        val playingBoardGame = MoveNumberResult(boardGame, numberToWin =  numberToWin)
        val boardSize = boardGame.size

        for (rowIndex in 0..<boardSize) {

            //take a row line
            val row = boardGame[rowIndex]
            //if the first cell is the default value (there's empty cells) keep playing
            if (row[0] == DEFAULT_VALUE)
                return playingBoardGame

            for (index in 0..<row.size - 1) {
                val currentCell = row[index]
                val nextCell = row[index + 1]

                //if the next cell is the default value (there's empty cells)
                //or is the same value as the current cell keep playing
                if (nextCell == DEFAULT_VALUE || currentCell == nextCell)
                    return playingBoardGame

            }
        }

        //check the same but for column lines instead of row lines
        for (columnIndex in 0..<boardSize) {

            val firstCell = boardGame[0][columnIndex]
            if (firstCell == DEFAULT_VALUE)
                return playingBoardGame

            for (rowIndex in 0..<boardSize - 1) {
                val currentCell = boardGame[rowIndex][columnIndex]
                val nextCell = boardGame[rowIndex + 1][columnIndex]

                if (nextCell == DEFAULT_VALUE || currentCell == nextCell)
                    return playingBoardGame
            }
        }

        //At this point there's no possible next move so returns GAME_OVER as the status
        return MoveNumberResult(
            boardGame = boardGame,
            gameStatus = GAME_OVER,
            numberToWin = numberToWin
        )
    }
}