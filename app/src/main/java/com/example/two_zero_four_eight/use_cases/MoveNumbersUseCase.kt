package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

class MoveNumbersUseCase {

    fun moveNumbers(boardGame: MutableList<MutableList<Int>>, movementDirection: MovementDirection): MutableList<MutableList<Int>>? {
        val boardSize = boardGame.size
        return when (movementDirection) {
            LEFT, RIGHT -> {
                getLeftRightMove(boardSize, boardGame, movementDirection)
            }

            UP, DOWN -> {
                getUpDownMove(boardSize, boardGame, movementDirection)
            }

            else -> null
        }
    }

    /**
     * It returns the MutableList<MutableList<Int>> that represents the boardGame but with the
     * numbers combined/moved to the left or right.
     * **/
    private fun getLeftRightMove(
        boardSize: Int,
        boardGame: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): MutableList<MutableList<Int>> {
        for (rowIndex in 0..<boardSize) {

            //take a row line
            val row = boardGame[rowIndex]

            //remove all the [DEFAULT_VALUE] on the row
            for (index in row.size-1 downTo 0) {
                if (row[index] == DEFAULT_VALUE) {
                    row.removeAt(index)
                }
            }

            val newRow = mutableListOf<Int>()
            //this was thought first for the [LEFT] case, so for [RIGHT] it reverse the row
            if (movementDirection == RIGHT) {
                row.reverse()
            }

            //it combines and add each consecutive pair of numbers on the row and add without modifying the ones that are not equals
            while (row.isNotEmpty()) {
                if (row.size > 1 && row[0] == row[1]) {
                    newRow.add(row[0] * 2)
                    row.removeAt(1)
                    row.removeAt(0)
                    continue
                }
                newRow.add(row[0])
                row.removeAt(0)
            }

            //adds the missing values as the [DEFAULT_VALUE]
            for (repetition in 0..<boardSize - newRow.size) {
                newRow.add(DEFAULT_VALUE)
            }

            //reverse the row again if it's [RIGHT]
            if (movementDirection == RIGHT) {
                newRow.reverse()
            }
            boardGame[rowIndex] = newRow
        }
        return boardGame
    }

    /**
     * It returns the MutableList<MutableList<Int>> that represents the boardGame but with the
     * numbers combined/moved to up or down.
     *
     * It similar to getLeftRightMove but with some changes on the way to move on the Lists and
     * how it handle the numbers.
     * **/
    private fun getUpDownMove(
        boardSize: Int,
        boardGame: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): MutableList<MutableList<Int>> {
        for (columnIndex in 0..<boardSize) {
            val column = mutableListOf<Int>()
            //it creates what would be a column with only non default values from the current numbers on that column
            for (rowIndex in 0..<boardSize) {
                val cell = boardGame[rowIndex][columnIndex]
                if (cell != DEFAULT_VALUE) {
                    column.add(cell)
                }
            }

            val newColumn = mutableListOf<Int>()
            if (movementDirection == DOWN) {
                column.reverse()
            }
            while (column.isNotEmpty()) {
                if (column.size > 1 && column[0] == column[1]) {
                    newColumn.add(column[0] * 2)
                    column.removeAt(1)
                    column.removeAt(0)
                    continue
                }
                newColumn.add(column[0])
                column.removeAt(0)
            }

            for (repetition in 0..<boardSize - newColumn.size) {
                newColumn.add(DEFAULT_VALUE)
            }
            if (movementDirection == DOWN) {
                newColumn.reverse()
            }
            //assign the new values to the column
            for (rowIndex in 0..<boardSize) {
                boardGame[rowIndex][columnIndex] = newColumn[rowIndex]
            }
        }
        return boardGame
    }
}