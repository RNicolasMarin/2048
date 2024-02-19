package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

class CombineAndMoveNumbersUseCase {

    /**
     * If [movementDirection] was [NONE] (shouldn't apply changes, it's an extra case) or
     * if even after applying a move the boardGame is the same would return null.
     * In other case would return the the boardGame with the move applied.
     * **/
    fun combineAndMove(
        boardGameOriginal: MutableList<MutableList<Int>>,
        movementDirection: MovementDirection
    ): MutableList<MutableList<Int>>? {
        val boardSize = boardGameOriginal.size
        val boardGame = boardGameOriginal.copy()

        val boardGameAfterMove = when (movementDirection) {
            LEFT, RIGHT -> {
                getLeftRightMove(boardSize, boardGame, movementDirection)
            }

            UP, DOWN -> {
                getUpDownMove(boardSize, boardGame, movementDirection)
            }

            NONE -> boardGame
        }

        if (movementDirection == NONE) return null
        return if (boardGameOriginal.isTheSameBoard(boardGameAfterMove)) null else boardGameAfterMove
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
        for (rowIndex in 0..< boardSize) {

            //it creates what would be a row with only non default values from the current numbers on that row
            val row = mutableListOf<Int>()
            for (columnIndex in 0..< boardSize) {
                val cell = boardGame[rowIndex][columnIndex]
                if (cell != DEFAULT_VALUE) {
                    row.add(cell)
                }
            }

            //row line after combining numbers
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

            //it creates what would be a column with only non default values from the current numbers on that column
            val column = mutableListOf<Int>()
            for (rowIndex in 0..<boardSize) {
                val cell = boardGame[rowIndex][columnIndex]
                if (cell != DEFAULT_VALUE) {
                    column.add(cell)
                }
            }

            //column line after combining numbers
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

/**
 * Method added to copy the boardGame that I use without changing
 * how I handle the change on the cells and avoiding a problem referencing memory.
 * **/
fun MutableList<MutableList<Int>>.copy(): MutableList<MutableList<Int>> {
    val list = mutableListOf<MutableList<Int>>()
    for (rowIndex in 0..<size) {
        val subList = mutableListOf<Int>()
        for (cellIndex in 0..<size) {
            subList.add(this[rowIndex][cellIndex])
        }
        list.add(subList)
    }
    return list
}

/**
 * It checks cell by cell if the original boardGame and the new one
 * have the same cells on the same positions.
 * **/
fun MutableList<MutableList<Int>>.isTheSameBoard(other: MutableList<MutableList<Int>>): Boolean {
    for (rowIndex in 0..<size) {
        for (cellIndex in 0..<size) {
            if (this[rowIndex][cellIndex] != other[rowIndex][cellIndex]) {
                return false
            }
        }
    }
    return true
}