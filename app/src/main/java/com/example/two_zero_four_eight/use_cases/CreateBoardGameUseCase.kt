package com.example.two_zero_four_eight.use_cases

import kotlin.random.Random

const val DEFAULT_VALUE = -1
val NUMBER_OPTIONS = listOf(2, 2, 2, 2, 4)

class CreateBoardGameUseCase {

    fun createBoardGame(size: Int): List<List<Int>> {
        /** Initialize the matrix (for both dimensions of the size of [size]) to represent
         * the rows and columns with [DEFAULT_VALUE] for each cell as a default value **/
        val emptyMatrix =
            MutableList(size) { //rows
                MutableList(size) { //columns
                    DEFAULT_VALUE
                }
            }

        /** Adds the first number on a position and with a value randoms**/
        val number1 = getNumberToStart()
        val position1 = getPosition(emptyMatrix)
        emptyMatrix[position1.first][position1.second] = number1

        /** Adds the second number on a position and with a value randoms**/
        val number2 = getNumberToStart()
        val position2 = getPosition(emptyMatrix)
        emptyMatrix[position2.first][position2.second] = number2

        return emptyMatrix
    }

    /** It generates a random position on the axis Y and X within the matrix size.
     *  If the cell on that position has the value [DEFAULT_VALUE] returns a
     *  Pair with the position on Y and X.
     *  If it has another value it calls again to this function.
     *
     *  Since the matrix is created with all its cells on [DEFAULT_VALUE] that
     *  means that it would have at least 9 positions that would work.
     *  The only reason to this case is for when the second number is chosen
     *  (on the second call to this function a cell would have a value so this is to avoid override it)
     * **/
    private fun getPosition(matrix: List<List<Int>>): Pair<Int, Int> {
        val positionY = Random.nextInt(matrix.size)
        val positionX = Random.nextInt(matrix.size)

        val current = matrix[positionY][positionX]
        if (current == DEFAULT_VALUE) {
            return Pair(positionY, positionX)
        }
        return getPosition(matrix)
    }

    /**
     * Returns a random value from the list [NUMBER_OPTIONS] to use on the selected cell on
     * the screen while is created.
     * **/
    private fun getNumberToStart(): Int {
        val randomIndex = Random.nextInt(NUMBER_OPTIONS.size)
        return NUMBER_OPTIONS[randomIndex]
    }
}