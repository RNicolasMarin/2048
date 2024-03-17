package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.RecordValues
import com.example.two_zero_four_eight.data.model.SingleGameState
import com.example.two_zero_four_eight.data.repository.RecordRepository
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

const val DEF = DEFAULT_VALUE
const val NEXT_HIGH_NUMBER = 512
const val RECORD_NUMBER = 64
const val RECORD_SCORE = 300
class MoveNumbersUseCaseTest {

    private lateinit var moveNumbersUseCase: MoveNumbersUseCase
    private lateinit var useCase1: CombineAndMoveNumbersUseCase
    private lateinit var useCase2: AddNumberToBoardGameUseCase
    private lateinit var useCase3: IsTherePossibleMovesUseCase
    private lateinit var useCase4: HasWonTheGameUseCase
    private lateinit var useCase5: UpdateCurrentRecordsUseCase
    private lateinit var repository: RecordRepository
    private lateinit var gameState: GameState
    private lateinit var actual: GameState
    private lateinit var expected: MutableList<MutableList<Int?>>
    private lateinit var record: RecordValues

    @Before
    fun setUp() {
        gameState = GameState(
            currentState = SingleGameState(
                gameStatus = PLAYING,
                numberToWin = NEXT_HIGH_NUMBER,
                numberCurrentRecord = CurrentRecordData(currentValue = 4, recordValue = RECORD_NUMBER),
                scoreCurrentRecord = CurrentRecordData(currentValue = 10, recordValue = RECORD_SCORE),
            ),
            originalBestValues = IndividualBestValues(number = RECORD_NUMBER, score = RECORD_SCORE)
        )
        useCase1 = CombineAndMoveNumbersUseCase()
        useCase2 = AddNumberToBoardGameUseCase()
        useCase3 = IsTherePossibleMovesUseCase()
        useCase4 = HasWonTheGameUseCase()
        repository = mockk()
        useCase5 = UpdateCurrentRecordsUseCase(repository)
        moveNumbersUseCase = MoveNumbersUseCase(
            useCase1, useCase2, useCase3, useCase4, useCase5
        )
    }


    //TESTS

    /*LEFT with empty spaces left
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-4-_         8-8-_-_*/
    @Test
    fun `LEFT with empty spaces left`() = runBlocking {
        setCurrentBoard(
            row(  2,   2,  2,   2),
            row(     DEF, DEF,  4, DEF),
            row(  4, DEF,  2, DEF),
            row(  8,   4,  4, DEF)
        )

        setExpectedCurrentBoard(
            rowN(4,    4, null, null),
            rowN(4, null, null, null),
            rowN(4,    2, null, null),
            rowN(8,    8, null, null)
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 26)
        checkSideDetails()
        checkCurrentBoard( 8)
    }

    /*LEFT with possible move left
     8-4-2-2         8-4-4-x
    16-8-4-2        16-8-4-2
    16-8-4-2        16-8-4-2
    16-8-4-2        16-8-4-2*/
    @Test
    fun `LEFT with possible move left`() = runBlocking {
        setCurrentBoard(
            row(  8,  4,  2,  2),
            row( 16,  8,  4,  2),
            row( 16,  8,  4,  2),
            row( 16,  8,  4,  2)
        )

        setExpectedCurrentBoard(
            rowN(8,   4,    4, null),
            rowN(16,  8,    4,    2),
            rowN(16,  8,    4,    2),
            rowN(16,  8,    4,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 14)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0,3))
    }

    /*LEFT with no possible move left
     _-8-64-32         8-64-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT with no possible move left`() = runBlocking {
        setCurrentBoard(
            row(     DEF,  8, 64, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2)
        )

        setExpectedCurrentBoard(
            rowN(8,  64, 32, null),
            rowN(16, 32, 16,    8),
            rowN(32, 16, 64,    4),
            rowN(16, 64, 32,    2)
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 64, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0, 3))
    }

    /*LEFT with the same board
    16-8-4-_         16-8-4-_
     8-_-_-_          8-_-_-_
     _-_-_-_          _-_-_-_
     4-_-_-_          4-_-_-_*/
    @Test
    fun `LEFT with the same board`() = runBlocking {
        setCurrentBoard(
            row( 16,   8,   4, DEF),
            row(  8, DEF, DEF, DEF),
            row(     DEF, DEF, DEF, DEF),
            row(  4, DEF, DEF, DEF),
        )
        setCurrentState(currentStateCurrentNumber = 16)

        setExpectedCurrentBoard(
            rowN( 16,   8,   4, DEF),
            rowN(  8, DEF, DEF, DEF),
            rowN(     DEF, DEF, DEF, DEF),
            rowN(  4, DEF, DEF, DEF),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(5)
    }

    /*LEFT reach number to win while PLAYING
     2-2-2-2         4-4-_-_
     _-_-4-_         4-_-_-_
     4-_-2-_         4-2-_-_
     8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT reach number to win while PLAYING`() = runBlocking {
        setCurrentBoard(
            row(  2,   2,   2,   2),
            row(     DEF, DEF,   4, DEF),
            row(  4, DEF,   2, DEF),
            row(  8,   8, DEF, DEF)
        )
        setCurrentState(currentStateNumberToWin = 16)

        setExpectedCurrentBoard(
            rowN( 4,    4, null, null),
            rowN( 4, null, null, null),
            rowN( 4,    2, null, null),
            rowN(16, null, null, null)
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = YOU_WIN, numberToWin = 32, currentNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }



    /*RIGHT with empty spaces left
    2-2-2-2         _-_-4-4
    _-4-_-_         _-_-_-4
    _-2-_-4         _-_-2-4
    _-4-4-8         _-_-8-8*/
    @Test
    fun `RIGHT with empty spaces left`() = runBlocking {
        setCurrentBoard(
            row( 2, 2,   2,   2),
            row(    DEF, 4, DEF, DEF),
            row(    DEF, 2, DEF,   4),
            row(    DEF, 4,   4,   8)
        )

        setExpectedCurrentBoard(
            rowN(null, null,    4,  4),
            rowN(null, null, null,  4),
            rowN(null, null,    2,  4),
            rowN(null, null,    8,  8)
        )

        actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 26)
        checkSideDetails()
        checkCurrentBoard(8)
    }

    /*RIGHT with possible move left
     2-2-4-8         x-4-4-8
     2-4-8-16        2-4-8-16
     2-4-8-16        2-4-8-16
     2-4-8-16        2-4-8-16*/
    @Test
    fun `RIGHT with possible move left`() = runBlocking {
        setCurrentBoard(
            row( 2,  2,  4,  8),
            row( 2,  4,  8, 16),
            row( 2,  4,  8, 16),
            row( 2,  4,  8, 16),
        )

        setExpectedCurrentBoard(
            rowN(null,  4,  4,  8),
            rowN(   2,  4,  8, 16),
            rowN(   2,  4,  8, 16),
            rowN(   2,  4,  8, 16),
        )

        actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 14)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0,0))
    }

    /*RIGHT with no possible move left
       32-64- 8- _        x-32-64-8
        8-16-32-16        8-16-32-16
        4-64-16-32        4-64-16-32
        2-32-64-16        2-32-64-16*/
    @Test
    fun `RIGHT with no possible move left`() = runBlocking {
        setCurrentBoard(
            row( 32, 64,  8, DEF),
            row(  8, 16, 32,  16),
            row(  4, 64, 16,  32),
            row(  2, 32, 64,  16),
        )

        setExpectedCurrentBoard(
            rowN(null, 32, 64,  8),
            rowN(   8, 16, 32, 16),
            rowN(   4, 64, 16, 32),
            rowN(   2, 32, 64, 16)
        )

        actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 64, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0,0))
    }

    /*RIGHT with the same board
        _-4-8-16         _-4-8-16
        _-_-_-8          _-_-_-8
        _-_-_-_          _-_-_-_
        _-_-_-4          _-_-_-4*/
    @Test
    fun `RIGHT with the same board`() = runBlocking {
        setCurrentBoard(
            row(DEF,   4,   8,  16),
            row(DEF, DEF, DEF,   8),
            row(DEF, DEF, DEF, DEF),
            row(DEF, DEF, DEF,   4),
        )
        setCurrentState(currentStateCurrentNumber = 16)

        setExpectedCurrentBoard(
            rowN(DEF,   4,   8,  16),
            rowN(DEF, DEF, DEF,   8),
            rowN(DEF, DEF, DEF, DEF),
            rowN(DEF, DEF, DEF,   4),
        )

        actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(5)
    }

    /*RIGHT reach number to win while PLAYING
        2-2-2-2         _-_-4-4
        _-4-_-_         _-_-_-4
        _-2-_-4         _-_-2-4
        _-_-8-8         _-_-_-16*/
    @Test
    fun `RIGHT reach number to win while PLAYING`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF,   4, DEF, DEF),
            row(    DEF,   2, DEF,   4),
            row(    DEF, DEF,   8,   8)
        )
        setCurrentState(currentStateNumberToWin = 16)

        setExpectedCurrentBoard(
            rowN(null, null,    4,  4),
            rowN(null, null, null,  4),
            rowN(null, null,    2,  4),
            rowN(null, null, null, 16)
        )

        actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)

        checkCurrentSideDetails(status = YOU_WIN, numberToWin = 32, currentNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }



    /*UP with empty spaces left
    2-_-4-8         4-4-4-8
    2-_-_-4         4-_-2-8
    2-4-2-4         _-_-_-_
    2-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP with empty spaces left`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF,   4,   8),
            row( 2, DEF, DEF,   4),
            row( 2,   4,   2,   4),
            row( 2, DEF, DEF, DEF)
        )

        setExpectedCurrentBoard(
            rowN(   4,   4,    4,    8),
            rowN(   4, null,   2,    8),
            rowN(null, null, null, null),
            rowN(null, null, null, null)
        )

        actual = moveNumbersUseCase.moveNumbers(UP, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 26)
        checkSideDetails()
        checkCurrentBoard(8)
    }

    /*UP with possible move left
    8-16-16-16     8-16-16-16
    4- 8- 8- 8     4- 8- 8- 8
    2- 4- 4- 4     4- 4- 4- 4
    2- 2- 2- 2     x- 2- 2- 2*/
    @Test//LEFT
    fun `UP with possible move left`() = runBlocking {
        setCurrentBoard(
            row( 8, 16, 16, 16),
            row( 4,  8,  8,  8),
            row( 2,  4,  4,  4),
            row( 2,  2,  2,  2),
        )

        setExpectedCurrentBoard(
            rowN(   8, 16, 16, 16),
            rowN(   4,  8,  8,  8),
            rowN(   4,  4,  4,  4),
            rowN(null,  2,  2,  2),
        )

        actual = moveNumbersUseCase.moveNumbers(UP, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 14)
        checkSideDetails()
        checkCurrentBoard(16, Pair(3,0))
    }

    /*UP with no possible move left
     _-16-32-16          8-16-32-16
     8-32-16-64         64-32-16-64
    64-16-64-32         32-16-64-32
    32- 8- 4- 2          x- 8- 4- 2*/
    @Test//LEFT
    fun `UP with no possible move left`() = runBlocking {
        setCurrentBoard(
            row(    DEF, 16, 32, 16),
            row( 8, 32, 16, 64),
            row(64, 16, 64, 32),
            row(32,  8,  4,  2),
        )

        setExpectedCurrentBoard(
            rowN(   8, 16, 32, 16),
            rowN(  64, 32, 16, 64),
            rowN(  32, 16, 64, 32),
            rowN(null,  8,  4,  2),
        )

        actual = moveNumbersUseCase.moveNumbers(UP, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 64, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(16, Pair(3,0))
    }

    /*UP with the same board
    16-8-_-4        16-8-_-4
     8-_-_-_         8-_-_-_
     4-_-_-_         4-_-_-_
     _-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP with the same board`() = runBlocking {
        setCurrentBoard(
            row(16,   8, DEF,   4),
            row( 8, DEF, DEF, DEF),
            row( 4, DEF, DEF, DEF),
            row(    DEF, DEF, DEF, DEF),
        )
        setCurrentState(currentStateCurrentNumber = 16)

        setExpectedCurrentBoard(
            rowN( 16,   8, DEF,   4),
            rowN(  8, DEF, DEF, DEF),
            rowN(  4, DEF, DEF, DEF),
            rowN(     DEF, DEF, DEF, DEF),
        )

        actual = moveNumbersUseCase.moveNumbers(UP, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(5)
    }

    /*UP reach number to win while PLAYING
    2-_-4-8         4-4-4-16
    2-_-_-8         4-_-2-_
    2-4-2-_         _-_-_-_
    2-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP reach number to win while PLAYING`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF,   4,   8),
            row( 2, DEF, DEF,   8),
            row( 2,   4,   2, DEF),
            row( 2, DEF, DEF, DEF)
        )
        setCurrentState(currentStateNumberToWin = 16)

        setExpectedCurrentBoard(
            rowN(   4,    4,    4,   16),
            rowN(   4, null,    2, null),
            rowN(null, null, null, null),
            rowN(null, null, null, null)
        )

        actual = moveNumbersUseCase.moveNumbers(UP, gameState)

        checkCurrentSideDetails(status = YOU_WIN, numberToWin = 32, currentNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }



    /*DOWN with empty spaces left
        2-_-_-_         _-_-_-_
        2-4-2-4         _-_-_-_
        2-_-_-4         4-_-2-8
        2-_-4-8         4-4-4-8*/
    @Test//RIGHT
    fun `DOWN with empty spaces left`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF, DEF, DEF),
            row( 2,   4,   2,   4),
            row( 2, DEF, DEF,   4),
            row( 2, DEF,   4,   8),
        )

        setExpectedCurrentBoard(
            rowN(null, null, null, null),
            rowN(null, null, null, null),
            rowN(   4, null,    2,    8),
            rowN(   4,    4,    4,    8),
        )

        actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 26)
        checkSideDetails()
        checkCurrentBoard(8)
    }

    /*DOWN with possible move left
        2- 2- 2- 2     x- 2- 2- 2
        2- 4- 4- 4     4- 4- 4- 4
        4- 8- 8- 8     4- 8- 8- 8
        8-16-16-16     8-16-16-16
        */
    @Test//RIGHT
    fun `DOWN with possible move left`() = runBlocking {
        setCurrentBoard(
            row(  2,  2,  2,  2),
            row(  2,  4,  4,  4),
            row(  4,  8,  8,  8),
            row(  8, 16, 16, 16),
        )

        setExpectedCurrentBoard(
            rowN(null,  2,  2,  2),
            rowN(   4,  4,  4,  4),
            rowN(   4,  8,  8,  8),
            rowN(   8, 16, 16, 16),
        )

        actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 14)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0,0))
    }

    /*DOWN with no possible move left
        32- 8- 4- 2          x- 8- 4- 2
        64-16-64-32         32-16-64-32
         8-32-16-64         64-32-16-64
         _-16-32-16          8-16-32-16*/
    @Test//RIGHT
    fun `DOWN with no possible move left`() = runBlocking {
        setCurrentBoard(
            row(32,  8,  4,  2),
            row(64, 16, 64, 32),
            row( 8, 32, 16, 64),
            row(    DEF, 16, 32, 16),
        )

        setExpectedCurrentBoard(
            rowN(null,  8,  4,  2),
            rowN(  32, 16, 64, 32),
            rowN(  64, 32, 16, 64),
            rowN(   8, 16, 32, 16),
        )

        actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 64, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(16, Pair(0,0))
    }

    /*DOWN with the same board
        _-_-_-_         _-_-_-_
         4-_-_-_         4-_-_-_
         8-_-_-_         8-_-_-_
        16-8-_-4        16-8-_-4*/
    @Test//RIGHT
    fun `DOWN with the same board`() = runBlocking {
        setCurrentBoard(
            row(    DEF, DEF, DEF, DEF),
            row( 4, DEF, DEF, DEF),
            row( 8, DEF, DEF, DEF),
            row(16,   8, DEF,   4),
        )
        setCurrentState(currentStateCurrentNumber = 16)

        setExpectedCurrentBoard(
            rowN(    DEF, DEF, DEF, DEF),
            rowN( 4, DEF, DEF, DEF),
            rowN( 8, DEF, DEF, DEF),
            rowN(16,   8, DEF,   4),
        )

        actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(5)
    }

    /*DOWN reach number to win while PLAYING
        2-_-_-_         _-_-_-_
        2-4-2-_         _-_-_-_
        2-_-_-8         4-_-2-_
        2-_-4-8         4-4-4-16*/
    @Test//RIGHT
    fun `DOWN reach number to win while PLAYING`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF, DEF, DEF),
            row( 2,   4,   2, DEF),
            row( 2, DEF, DEF,   8),
            row( 2, DEF,   4,   8),
        )
        setCurrentState(currentStateNumberToWin = 16)

        setExpectedCurrentBoard(
            rowN(null, null, null, null),
            rowN(null, null, null, null),
            rowN(   4, null,    2, null),
            rowN(   4,    4,    4,   16)
        )

        actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)

        checkCurrentSideDetails(status = YOU_WIN, numberToWin = 32, currentNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }



    @Test
    fun `NONE with game over`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF,   4,   8),
            row( 2, DEF, DEF,   4),
            row( 2,   4,   2,   4),
            row( 2, DEF, DEF, DEF)
        )

        setExpectedCurrentBoard(
            rowN( 2, DEF,   4,   8),
            rowN( 2, DEF, DEF,   4),
            rowN( 2,   4,   2,   4),
            rowN( 2, DEF, DEF, DEF)
        )

        actual = moveNumbersUseCase.moveNumbers(NONE, gameState)

        checkCurrentSideDetails(currentNumber = 4, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(10)
    }

    @Test
    fun `NONE without game over`() = runBlocking {
        setCurrentBoard(
            row( 2, DEF,   4,   8),
            row( 2, DEF, DEF,   4),
            row( 2,   4,   2,   4),
            row( 2, DEF, DEF, DEF)
        )
        setCurrentState(currentStateStatus = GAME_OVER)

        setExpectedCurrentBoard(
            rowN( 2, DEF,   4,   8),
            rowN( 2, DEF, DEF,   4),
            rowN( 2,   4,   2,   4),
            rowN( 2, DEF, DEF, DEF)
        )

        actual = moveNumbersUseCase.moveNumbers(NONE, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 4, currentScore = 10)
        checkSideDetails()
        checkCurrentBoard(10)
    }

    /*LEFT reach number to win while PLAYING
     2-2-2-2         4-4-_-_
     _-_-4-_         4-_-_-_
     4-_-2-_         4-2-_-_
     8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT PLAYING after reach number to win`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF, DEF,   4, DEF),
            row( 4, DEF,   2, DEF),
            row( 8,   4, DEF, DEF)
        )
        setCurrentState(currentStateNumberToWin = 16, currentStateStatus = YOU_WIN)

        setExpectedCurrentBoard(
            rowN( 4,    4, null, null),
            rowN( 4, null, null, null),
            rowN( 4,    2, null, null),
            rowN( 8,    4, null, null),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = PLAYING, numberToWin = 16, currentNumber = 8, currentScore = 18)
        checkSideDetails()
        checkCurrentBoard(8)
    }




    /*LEFT increase current number keeping record number
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT increase current number keeping record number`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF, DEF,   4, DEF),
            row( 4, DEF,   2, DEF),
            row( 8,   8, DEF, DEF)
        )
        setCurrentState(currentStateCurrentNumber = 8)

        setExpectedCurrentBoard(
            rowN( 4,    4, null, null),
            rowN( 4, null, null, null),
            rowN( 4,    2, null, null),
            rowN(16, null, null, null),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }

    /*LEFT increase current number and record number
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT increase current number and record number`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF, DEF,   4, DEF),
            row( 4, DEF,   2, DEF),
            row( 8,   8, DEF, DEF)
        )
        setCurrentState(currentStateCurrentNumber = 8, currentStateRecordNumber = 8)

        setExpectedCurrentBoard(
            rowN(  4,    4, null, null),
            rowN(  4, null, null, null),
            rowN(  4,    2, null, null),
            rowN( 16, null, null, null),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 16, recordNumber = 16, currentScore = 34)
        checkSideDetails()
        checkCurrentBoard(7)
    }

    /*LEFT save new record for number
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT save new record for number`() = runBlocking {
        record = RecordValues(score = 138, number = 128, boardSize = 4)
        setCoInsertAndGetRecords()

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 64, originalBestNumber = 16)

        setExpectedCurrentBoard(
            rowN( 128,  8, 32, null),
            rowN(  16, 32, 16,    8),
            rowN(  32, 16, 64,    4),
            rowN(  16, 64, 32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 128, currentScore = 138)
        checkSideDetails(number = 16)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsInsertAndGetRecords()
    }

    /*LEFT increase current score keeping record score
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT increase current score keeping record score`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF, DEF,   4, DEF),
            row( 4, DEF,   2, DEF),
            row( 8,   4, DEF, DEF)
        )
        setCurrentState(currentStateCurrentNumber = 8, currentStateCurrentScore = 10)

        setExpectedCurrentBoard(
            rowN( 4,    4, null, null),
            rowN( 4, null, null, null),
            rowN( 4,    2, null, null),
            rowN( 8,    4, null, null),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 18)
        checkSideDetails()
        checkCurrentBoard(8)
    }

    /*LEFT increase current score and record score
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT increase current score and record score`() = runBlocking {
        setCurrentBoard(
            row( 2,   2,   2,   2),
            row(    DEF, DEF,   4, DEF),
            row( 4, DEF,   2, DEF),
            row( 8,   4, DEF, DEF)
        )
        setCurrentState(currentStateCurrentScore = 96, currentStateRecordScore = 100)

        setExpectedCurrentBoard(
            rowN( 4,    4, null, null),
            rowN( 4, null, null, null),
            rowN( 4,    2, null, null),
            rowN( 8,    4, null, null),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(currentNumber = 8, currentScore = 104, recordScore = 104)
        checkSideDetails()
        checkCurrentBoard(8)
    }

    /*LEFT save new record for score
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT save new record for score`() = runBlocking {
        record = RecordValues(score = 328, number = 128, boardSize = 4)
        setCoInsertAndGetRecords()

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 256, currentStateCurrentScore = 200, currentStateRecordScore = 300, originalBestNumber = 256, originalBestScore = 300)

        setExpectedCurrentBoard(
            rowN( 128,   8,  32, null),
            rowN(  16,  32,  16,    8),
            rowN(  32,  16,  64,    4),
            rowN(  16,  64,  32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 256, currentScore = 328, recordScore = 328)
        checkSideDetails(number = 256)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsInsertAndGetRecords()
    }




    /*LEFT erase record with lowest score and number
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and number`() = runBlocking {
        record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        setCoUpdateAndGetRecords(listOf(
            RecordValues(id = 3, score = 150, number = 4, boardSize = 4),
            RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
            RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
        ))

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 256, currentStateCurrentScore = 200, currentStateRecordScore = 300, originalBestNumber = 256, originalBestScore = 300)

        setExpectedCurrentBoard(
            rowN( 128,  8,  32, null),
            rowN(  16, 32,  16,    8),
            rowN(  32, 16,  64,    4),
            rowN(  16, 64,  32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 256, currentScore = 328, recordScore = 328)
        checkSideDetails(number = 256)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsUpdateAndGetRecords()
    }

    /*LEFT erase record with lowest score and non unique high number 1
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and non unique high number 1`() = runBlocking {
        record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        setCoUpdateAndGetRecords(listOf(
            RecordValues(id = 3, score = 150, number = 32, boardSize = 4),
            RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
            RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
        ))

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 256, currentStateCurrentScore = 200, currentStateRecordScore = 300, originalBestNumber = 256, originalBestScore = 300)

        setExpectedCurrentBoard(
            rowN( 128,  8,  32, null),
            rowN(  16, 32,  16,    8),
            rowN(  32, 16,  64,    4),
            rowN(  16, 64,  32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 256, currentScore = 328, recordScore = 328)
        checkSideDetails(number = 256)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsUpdateAndGetRecords()
    }

    /*LEFT erase record with lowest score and non unique high number 2
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and non unique high number 2`() = runBlocking {
        record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        setCoUpdateAndGetRecords(listOf(
            RecordValues(id = 3, score = 150, number = 32, boardSize = 4),
            RecordValues(id = 1, score = 200, number = 64, boardSize = 4),
            RecordValues(id = 2, score = 300, number = 32, boardSize = 4),
        ))

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 256, currentStateCurrentScore = 200, currentStateRecordScore = 300, originalBestNumber = 256, originalBestScore = 300)

        setExpectedCurrentBoard(
            rowN( 128,  8,  32, null),
            rowN(  16, 32,  16,    8),
            rowN(  32, 16,  64,    4),
            rowN(  16, 64,  32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 256, currentScore = 328, recordScore = 328)
        checkSideDetails(number = 256)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsUpdateAndGetRecords()
    }

    /*LEFT erase record with non highest number and non lowest score
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with non highest number and non lowest score`() = runBlocking {
        record = RecordValues(id = 1, score = 328, number = 128, boardSize = 4)
        setCoUpdateAndGetRecords(listOf(
            RecordValues(id = 3, score = 150, number = 64, boardSize = 4),
            RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
            RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
        ))

        setCurrentBoard(
            row( 64, 64,  8, 32),
            row( 16, 32, 16,  8),
            row( 32, 16, 64,  4),
            row( 16, 64, 32,  2),
        )
        setCurrentState(currentStateCurrentNumber = 64, currentStateRecordNumber = 256, currentStateCurrentScore = 200, currentStateRecordScore = 300, originalBestNumber = 256, originalBestScore = 300)

        setExpectedCurrentBoard(
            rowN( 128,  8,  32, null),
            rowN(  16, 32,  16,    8),
            rowN(  32, 16,  64,    4),
            rowN(  16, 64,  32,    2),
        )

        actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)

        checkCurrentSideDetails(status = GAME_OVER, currentNumber = 128, recordNumber = 256, currentScore = 328, recordScore = 328)
        checkSideDetails(number = 256)
        checkCurrentBoard(16, Pair(0,3))

        verifyCallsUpdateAndGetRecords()
    }

    //SETTERS
    private fun setCurrentBoard(vararg rows: MutableList<Int>) {
        gameState.currentState.board = rows.toMutableList()
    }

    private fun setCurrentState(
        currentStateCurrentNumber: Int? = null,
        currentStateRecordNumber: Int? = null,
        currentStateCurrentScore: Int? = null,
        currentStateRecordScore: Int? = null,
        originalBestNumber: Int? = null,
        originalBestScore: Int? = null,
        currentStateNumberToWin: Int? = null,
        currentStateStatus: GameStatus? = null
    ) = with(gameState) {
        currentStateCurrentNumber?.let {
            currentState.numberCurrentRecord.currentValue = it
        }
        currentStateRecordNumber?.let {
            currentState.numberCurrentRecord.recordValue = it
        }
        currentStateCurrentScore?.let {
            currentState.scoreCurrentRecord.currentValue = it
        }
        currentStateRecordScore?.let {
            currentState.scoreCurrentRecord.recordValue = it
        }
        currentStateNumberToWin?.let {
            currentState.numberToWin = it
        }
        currentStateStatus?.let {
            currentState.gameStatus = it
        }
        originalBestNumber?.let {
            originalBestValues.number = it
        }
        originalBestScore?.let {
            originalBestValues.score = it
        }
    }

    private fun setExpectedCurrentBoard(vararg rows: MutableList<Int?>) {
        expected = rows.toMutableList()
    }

    private fun setCoInsertAndGetRecords(records: List<RecordValues> = emptyList()) {
        coEvery { repository.insertRecord(record) } answers { }
        coEvery { repository.getRecordsForBoard(record.boardSize) } answers { records }
    }

    private fun setCoUpdateAndGetRecords(records: List<RecordValues> = emptyList()) {
        coEvery { repository.updateRecord(record) } answers { }
        coEvery { repository.getRecordsForBoard(record.boardSize) } answers { records }
    }

    //UTILS

    private fun rowN(vararg cells: Int?): MutableList<Int?> = cells.toMutableList()

    private fun row(vararg cells: Int): MutableList<Int> = cells.toMutableList()

    //ASSERTIONS

    private fun checkCurrentSideDetails(
        status: GameStatus = PLAYING, numberToWin: Int = NEXT_HIGH_NUMBER,
        currentNumber: Int, currentScore: Int,
        recordNumber: Int = RECORD_NUMBER, recordScore: Int = RECORD_SCORE
    ) {
        with(actual.currentState) {
            assertThat(gameStatus).isEqualTo(status)
            assertThat(numberToWin).isEqualTo(numberToWin)

            assertThat(numberCurrentRecord).isEqualTo(CurrentRecordData(currentNumber, recordNumber))
            assertThat(scoreCurrentRecord).isEqualTo(CurrentRecordData(currentScore, recordScore))
        }
    }

    private fun checkSideDetails(score: Int = RECORD_SCORE, number: Int = RECORD_NUMBER) {
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(score, number))
    }

    private fun checkCurrentBoard(amountNumbers: Int, thereIsNumberIn: Pair<Int, Int>? = null) {
        with(actual.currentState.board) {
            assertThat(size).isEqualTo(expected.size)
            val resultList = this.flatten()
            val expectedList = expected.flatten()

            assertThat(resultList.size).isEqualTo(expectedList.size)
            for (index in expectedList.indices) {
                if (expectedList[index] == null) continue

                assertThat(expectedList[index]).isEqualTo(resultList[index])
            }

            val actualNumbers = resultList.count { it != DEF }
            assertThat(actualNumbers).isEqualTo(amountNumbers)

            val emptyCells = resultList.count { it == DEF }
            assertThat(emptyCells).isEqualTo(expectedList.size - actualNumbers)

            thereIsNumberIn?.let {
                assertThat(this[it.first][it.second]).isNotEqualTo(DEF)
            }
        }
    }

    //VERIFICATIONS

    private fun verifyCallsInsertAndGetRecords() {
        verify {
            runBlocking {
                repository.getRecordsForBoard(record.boardSize)
                repository.insertRecord(record)
            }
        }
    }

    private fun verifyCallsUpdateAndGetRecords() {
        verify {
            runBlocking {
                repository.getRecordsForBoard(record.boardSize)
                repository.updateRecord(record)
            }
        }
    }
}