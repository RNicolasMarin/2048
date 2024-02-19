package com.example.two_zero_four_eight.use_cases

import javax.inject.Inject

const val DEFAULT_VALUE = -1

class CreateBoardGameUseCase @Inject constructor(
    private val useCase: AddNumberToBoardGameUseCase
) {

    /** Initialize the BoardGame matrix (for both dimensions of the size of [size]) to represent
     * the rows and columns with [DEFAULT_VALUE] for each cell as a default value
     *
     * Then adds the first and second number to the boardGame and returns it.
     * **/
    fun createBoardGame(size: Int): MutableList<MutableList<Int>> {
        var boardGame = MutableList(size) { //rows
            MutableList(size) { //cells
                DEFAULT_VALUE
            }
        }

        boardGame = useCase.addNumber(boardGame)
        boardGame = useCase.addNumber(boardGame)

        return boardGame
    }
}