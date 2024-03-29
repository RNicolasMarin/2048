package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import javax.inject.Inject

class MoveNumbersUseCase @Inject constructor(
    private val useCase1: CombineAndMoveNumbersUseCase,
    private val useCase2: AddNumberToBoardGameUseCase,
    private val useCase3: IsTherePossibleMovesUseCase,
    private val useCase4: HasWonTheGameUseCase,
    private val useCase5: UpdateCurrentRecordsUseCase
) {

    /**
     * 1) Moves the numbers lines (rows or columns depending on [movementDirection]) and
     * combines the numbers that are the same and are positioned next to each other.
     *
     * 2) If there's empties cells add a new number on a random empty position.
     *
     * 3) If there's empty cells or there's something that can be combined
     * continue playing. In other case is game over.
     *
     * 4) If the status is [PLAYING] it checks if there's any cell with [gameState.numberToWin],
     * in that case changes the status to [YOU_WIN] and duplicate the [gameState.numberToWin] value.
     *
     * 5) Updates the current higher number
     * **/
    suspend fun moveNumbers(
        movementDirection: MovementDirection,
        gameState: GameState
    ): GameState {
        val previous = gameState.currentState.clone()

        //1) Move and Combine
        //If it's null there's no change on the boardGame or in isGameOver and return the original values
        var current = useCase1.combineAndMove(movementDirection, gameState) ?:
            return gameState

        //2) if there's empty cells add number
        current.board = useCase2.addNumber(current.board)

        //3) Check if there's any possible move
        current = useCase3.checkMovesToContinue(current)

        //4) Checks if it reached the nextHighNumber
        current = useCase4.checkIfHasWonTheGame(current)

        //5) Update current number
        gameState.apply {
            currentState = current
            previousState = previous
        }
        return useCase5.updateValues(gameState)
    }

}



