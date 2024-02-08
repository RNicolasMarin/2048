package com.example.two_zero_four_eight.use_cases

import kotlin.random.Random

val NUMBER_OPTIONS = listOf(2, 2, 2, 2, 4)

class AddNumberToBoardGameUseCase {

    /**
     * It receives the [boardGame], tries to get a random position that's empty
     * (the cell has the value [DEFAULT_VALUE]). If there's not position return the
     * original [boardGame] without any changes.
     *
     * If there's a position gets a random number from [NUMBER_OPTIONS] and adds it
     * to [boardGame] on the selected position to later return [boardGame].
     * **/
    fun addNumber(boardGame: MutableList<MutableList<Int>>): MutableList<MutableList<Int>> {
        val position = getAvailablePosition(boardGame) ?: return boardGame
        val number = getNumber()

        boardGame[position.first][position.second] = number

        return boardGame
    }

    /**It receives the [boardGame], iterate first for its rows and then the cells on each cell
     * and for each cell that has the value of [DEFAULT_VALUE] adds to a list a Pair<Int, Int> with
     * the index on the Y axis and the X axis.
     *
     * If that list is empty (all cells on boardGame have numbers on them) then return null.
     *
     * If not it would return randomly one of the pairs added to the list of available cells.
     *
     * **/
    private fun getAvailablePosition(boardGame: MutableList<MutableList<Int>>): Pair<Int, Int>? {
        val lineSize = boardGame.size
        val availableCells = mutableListOf<Pair<Int, Int>>()

        for (rowIndex in 0..<lineSize) {
            for (cellIndex in 0..<lineSize) {
                val cell = boardGame[rowIndex][cellIndex]
                if (cell == DEFAULT_VALUE) {
                    availableCells.add(Pair(rowIndex, cellIndex))
                }
            }
        }

        if (availableCells.isEmpty()) {
            return null
        }

        val position = Random.nextInt(availableCells.size)
        return availableCells[position]
    }

    /**
     * Returns a random value from the list [NUMBER_OPTIONS] to use on the selected cell on
     * the board game.
     * **/
    private fun getNumber(): Int {
        val randomIndex = Random.nextInt(NUMBER_OPTIONS.size)
        return NUMBER_OPTIONS[randomIndex]
    }

}