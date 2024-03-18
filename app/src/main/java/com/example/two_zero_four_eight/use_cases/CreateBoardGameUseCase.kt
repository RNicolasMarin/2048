package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.SingleGameState
import com.example.two_zero_four_eight.data.repository.RecordRepository
import com.example.two_zero_four_eight.ui.DEFAULT_NUMBER_TO_WIN
import kotlinx.coroutines.delay
import javax.inject.Inject

const val DEFAULT_VALUE = -1

class CreateBoardGameUseCase @Inject constructor(
    private val useCase: AddNumberToBoardGameUseCase,
    private val repository: RecordRepository
) {

    /** Initialize the BoardGame matrix (for both dimensions of the size of [size]) to represent
     * the rows and columns with [DEFAULT_VALUE] for each cell as a default value
     *
     * Then adds the first and second number to the boardGame and returns it.
     * **/
    suspend fun createBoardGame(previousState: SingleGameState, size: Int): GameState {
        var boardGame = MutableList(size) { //rows
            MutableList(size) { //cells
                DEFAULT_VALUE
            }
        }

        boardGame = useCase.addNumber(boardGame)
        boardGame = useCase.addNumber(boardGame)

        delay(200)

        val individualBestValues = repository.getIndividualBestValues(size) ?: IndividualBestValues()

        return GameState(
            previousState = if (previousState.board.isEmpty()) null else previousState,
            currentState = SingleGameState(
                board = boardGame,
                gameStatus = PLAYING,
                numberToWin = DEFAULT_NUMBER_TO_WIN,
                numberCurrentRecord = CurrentRecordData(recordValue = individualBestValues.number),
                scoreCurrentRecord = CurrentRecordData(recordValue = individualBestValues.score),
            ),
            originalBestValues = individualBestValues
        )
    }
}