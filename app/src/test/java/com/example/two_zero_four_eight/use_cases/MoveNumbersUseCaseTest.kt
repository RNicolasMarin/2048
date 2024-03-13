package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.RecordValues
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

    @Before
    fun setUp() {
        gameState = GameState(
            gameStatus = PLAYING,
            numberToWin = NEXT_HIGH_NUMBER,
            numberCurrentRecord = CurrentRecordData(currentValue = 4, recordValue = RECORD_NUMBER),
            scoreCurrentRecord = CurrentRecordData(currentValue = 10, recordValue = RECORD_SCORE),
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


    /*LEFT with empty spaces left
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-4-_         8-8-_-_*/
    @Test
    fun `LEFT with empty spaces left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  4,  4,DEF)
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(26, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(8)
        assertThat(boardResult[3][1]).isEqualTo(8)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*LEFT with possible move left
     8-4-2-2         8-4-4-x
    16-8-4-2        16-8-4-2
    16-8-4-2        16-8-4-2
    16-8-4-2        16-8-4-2*/
    @Test
    fun `LEFT with possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  8,  4,  2,  2),
            mutableListOf( 16,  8,  4,  2),
            mutableListOf( 16,  8,  4,  2),
            mutableListOf( 16,  8,  4,  2),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(14, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(8)
        assertThat(boardResult[0][1]).isEqualTo(4)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16,  8,  4,  2))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 16,  8,  4,  2))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16,  8,  4,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*LEFT with no possible move left
     _-8-64-32         8-64-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT with no possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(DEF,  8, 64, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(64, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(8)
        assertThat(boardResult[0][1]).isEqualTo(64)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*LEFT with the same board
    16-8-4-_         16-8-4-_
     8-_-_-_          8-_-_-_
     _-_-_-_          _-_-_-_
     4-_-_-_          4-_-_-_*/
    @Test
    fun `LEFT with the same board`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf( 16,  8,  4,DEF),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
        )
        val expected = mutableListOf(
            mutableListOf( 16,  8,  4,DEF),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
        )

        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    /*LEFT reach number to win while PLAYING
     2-2-2-2         4-4-_-_
     _-_-4-_         4-_-_-_
     4-_-2-_         4-2-_-_
     8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT reach number to win while PLAYING`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  8,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberToWin = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(YOU_WIN)
        assertThat(actual.numberToWin).isEqualTo(32)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(16)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }



    /*RIGHT with empty spaces left
    2-2-2-2         _-_-4-4
    _-4-_-_         _-_-_-4
    _-2-_-4         _-_-2-4
    _-4-4-8         _-_-8-8*/
    @Test
    fun `RIGHT with empty spaces left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,  4,DEF,DEF),
            mutableListOf(DEF,  2,DEF,  4),
            mutableListOf(DEF,  4,  4,  8)
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(26, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isEqualTo(4)

        assertThat(boardResult[1][3]).isEqualTo(4)

        assertThat(boardResult[2][2]).isEqualTo(2)
        assertThat(boardResult[2][3]).isEqualTo(4)

        assertThat(boardResult[3][2]).isEqualTo(8)
        assertThat(boardResult[3][3]).isEqualTo(8)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*RIGHT with possible move left
     2-2-4-8         x-4-4-8
     2-4-8-16        2-4-8-16
     2-4-8-16        2-4-8-16
     2-4-8-16        2-4-8-16*/
    @Test
    fun `RIGHT with possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  4,  8),
            mutableListOf(  2,  4,  8, 16),
            mutableListOf(  2,  4,  8, 16),
            mutableListOf(  2,  4,  8, 16),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(14, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][3]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)
        assertThat(boardResult[0][0]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf(  2,  4,  8, 16))
        assertThat(boardResult[2]).isEqualTo(mutableListOf(  2,  4,  8, 16))
        assertThat(boardResult[3]).isEqualTo(mutableListOf(  2,  4,  8, 16))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*RIGHT with no possible move left
       32-64- 8- _        x-32-64-8
        8-16-32-16        8-16-32-16
        4-64-16-32        4-64-16-32
        2-32-64-16        2-32-64-16*/
    @Test
    fun `RIGHT with no possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf( 32, 64,  8,DEF),
            mutableListOf(  8, 16, 32, 16),
            mutableListOf(  4, 64, 16, 32),
            mutableListOf(  2, 32, 64, 16),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(64, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][3]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(64)
        assertThat(boardResult[0][1]).isEqualTo(32)
        assertThat(boardResult[0][0]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf(  8, 16, 32, 16))
        assertThat(boardResult[2]).isEqualTo(mutableListOf(  4, 64, 16, 32))
        assertThat(boardResult[3]).isEqualTo(mutableListOf(  2, 32, 64, 16))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*RIGHT with the same board
        _-4-8-16         _-4-8-16
        _-_-_-8          _-_-_-8
        _-_-_-_          _-_-_-_
        _-_-_-4          _-_-_-4*/
    @Test
    fun `RIGHT with the same board`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(DEF,  4,  8, 16),
            mutableListOf(DEF,DEF,DEF,  8),
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,  4),
        )
        val expected = mutableListOf(
            mutableListOf(DEF,  4,  8, 16),
            mutableListOf(DEF,DEF,DEF,  8),
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,  4),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    /*RIGHT reach number to win while PLAYING
        2-2-2-2         _-_-4-4
        _-4-_-_         _-_-_-4
        _-2-_-4         _-_-2-4
        _-_-8-8         _-_-_-16*/
    @Test
    fun `RIGHT reach number to win while PLAYING`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,  4,DEF,DEF),
            mutableListOf(DEF,  2,DEF,  4),
            mutableListOf(DEF,DEF,  8,  8)
        )
        gameState.apply {
            board = boardGame
            numberToWin = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(RIGHT, gameState)
        assertThat(actual.gameStatus).isEqualTo(YOU_WIN)
        assertThat(actual.numberToWin).isEqualTo(32)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isEqualTo(4)

        assertThat(boardResult[1][3]).isEqualTo(4)

        assertThat(boardResult[2][2]).isEqualTo(2)
        assertThat(boardResult[2][3]).isEqualTo(4)

        assertThat(boardResult[3][3]).isEqualTo(16)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }



    /*UP with empty spaces left
    2-_-4-8         4-4-4-8
    2-_-_-4         4-_-2-8
    2-4-2-4         _-_-_-_
    2-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP with empty spaces left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(UP, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(26, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isEqualTo(8)

        assertThat(boardResult[1][0]).isEqualTo(4)
        assertThat(boardResult[1][2]).isEqualTo(2)
        assertThat(boardResult[1][3]).isEqualTo(8)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*UP with possible move left
    8-16-16-16     8-16-16-16
    4- 8- 8- 8     4- 8- 8- 8
    2- 4- 4- 4     4- 4- 4- 4
    2- 2- 2- 2     x- 2- 2- 2*/
    @Test//LEFT
    fun `UP with possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  8, 16, 16, 16),
            mutableListOf(  4,  8,  8,  8),
            mutableListOf(  2,  4,  4,  4),
            mutableListOf(  2,  2,  2,  2),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(UP, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(14, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0]).isEqualTo(mutableListOf(  8, 16, 16, 16))
        assertThat(boardResult[1]).isEqualTo(mutableListOf(  4,  8,  8,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf(  4,  4,  4,  4))

        assertThat(boardResult[3][0]).isNotEqualTo(DEF)
        assertThat(boardResult[3][1]).isEqualTo(2)
        assertThat(boardResult[3][2]).isEqualTo(2)
        assertThat(boardResult[3][3]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*UP with no possible move left
     _-16-32-16          8-16-32-16
     8-32-16-64         64-32-16-64
    64-16-64-32         32-16-64-32
    32- 8- 4- 2          x- 8- 4- 2*/
    @Test//LEFT
    fun `UP with no possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(DEF, 16, 32, 16),
            mutableListOf(  8, 32, 16, 64),
            mutableListOf( 64, 16, 64, 32),
            mutableListOf( 32,  8,  4,  2),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(UP, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(64, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0]).isEqualTo(mutableListOf(  8, 16, 32, 16))
        assertThat(boardResult[1]).isEqualTo(mutableListOf( 64, 32, 16, 64))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64, 32))

        assertThat(boardResult[3][0]).isNotEqualTo(DEF)
        assertThat(boardResult[3][1]).isEqualTo(8)
        assertThat(boardResult[3][2]).isEqualTo(4)
        assertThat(boardResult[3][3]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*UP with the same board
    16-8-_-4        16-8-_-4
     8-_-_-_         8-_-_-_
     4-_-_-_         4-_-_-_
     _-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP with the same board`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf( 16,  8,DEF,  4),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,DEF),
        )
        val expected = mutableListOf(
            mutableListOf( 16,  8,DEF,  4),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
            mutableListOf(DEF,DEF,DEF,DEF),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(UP, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    /*UP reach number to win while PLAYING
    2-_-4-8         4-4-4-16
    2-_-_-8         4-_-2-_
    2-4-2-_         _-_-_-_
    2-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP reach number to win while PLAYING`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  8),
            mutableListOf(  2,  4,  2,DEF),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberToWin = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(UP, gameState)
        assertThat(actual.gameStatus).isEqualTo(YOU_WIN)
        assertThat(actual.numberToWin).isEqualTo(32)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isEqualTo(16)

        assertThat(boardResult[1][0]).isEqualTo(4)
        assertThat(boardResult[1][2]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }



    /*DOWN with empty spaces left
        2-_-_-_         _-_-_-_
        2-4-2-4         _-_-_-_
        2-_-_-4         4-_-2-8
        2-_-4-8         4-4-4-8*/
    @Test//RIGHT
    fun `DOWN with empty spaces left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,DEF,DEF),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,DEF,  4,  8),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(26, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[3][0]).isEqualTo(4)
        assertThat(boardResult[3][1]).isEqualTo(4)
        assertThat(boardResult[3][2]).isEqualTo(4)
        assertThat(boardResult[3][3]).isEqualTo(8)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][2]).isEqualTo(2)
        assertThat(boardResult[2][3]).isEqualTo(8)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*DOWN with possible move left
        2- 2- 2- 2     x- 2- 2- 2
        2- 4- 4- 4     4- 4- 4- 4
        4- 8- 8- 8     4- 8- 8- 8
        8-16-16-16     8-16-16-16
        */
    @Test//RIGHT
    fun `DOWN with possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(  2,  4,  4,  4),
            mutableListOf(  4,  8,  8,  8),
            mutableListOf(  8, 16, 16, 16),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(14, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[3]).isEqualTo(mutableListOf(  8, 16, 16, 16))
        assertThat(boardResult[2]).isEqualTo(mutableListOf(  4,  8,  8,  8))
        assertThat(boardResult[1]).isEqualTo(mutableListOf(  4,  4,  4,  4))

        assertThat(boardResult[0][0]).isNotEqualTo(DEF)
        assertThat(boardResult[0][1]).isEqualTo(2)
        assertThat(boardResult[0][2]).isEqualTo(2)
        assertThat(boardResult[0][3]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*DOWN with no possible move left
        32- 8- 4- 2          x- 8- 4- 2
        64-16-64-32         32-16-64-32
         8-32-16-64         64-32-16-64
         _-16-32-16          8-16-32-16*/
    @Test//RIGHT
    fun `DOWN with no possible move left`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf( 32,  8,  4,  2),
            mutableListOf( 64, 16, 64, 32),
            mutableListOf(  8, 32, 16, 64),
            mutableListOf(DEF, 16, 32, 16),
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(64, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[3]).isEqualTo(mutableListOf(  8, 16, 32, 16))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 64, 32, 16, 64))
        assertThat(boardResult[1]).isEqualTo(mutableListOf( 32, 16, 64, 32))

        assertThat(boardResult[0][0]).isNotEqualTo(DEF)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(4)
        assertThat(boardResult[0][3]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)
    }

    /*DOWN with the same board
        _-_-_-_         _-_-_-_
         4-_-_-_         4-_-_-_
         8-_-_-_         8-_-_-_
        16-8-_-4        16-8-_-4*/
    @Test//RIGHT
    fun `DOWN with the same board`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf( 16,  8,DEF,  4),
        )
        val expected = mutableListOf(
            mutableListOf(DEF,DEF,DEF,DEF),
            mutableListOf(  4,DEF,DEF,DEF),
            mutableListOf(  8,DEF,DEF,DEF),
            mutableListOf( 16,  8,DEF,  4),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    /*DOWN reach number to win while PLAYING
        2-_-_-_         _-_-_-_
        2-4-2-_         _-_-_-_
        2-_-_-8         4-_-2-_
        2-_-4-8         4-4-4-16*/
    @Test//RIGHT
    fun `DOWN reach number to win while PLAYING`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,DEF,DEF),
            mutableListOf(  2,  4,  2,DEF),
            mutableListOf(  2,DEF,DEF,  8),
            mutableListOf(  2,DEF,  4,  8),
        )
        gameState.apply {
            board = boardGame
            numberToWin = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(DOWN, gameState)
        assertThat(actual.gameStatus).isEqualTo(YOU_WIN)
        assertThat(actual.numberToWin).isEqualTo(32)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[3][0]).isEqualTo(4)
        assertThat(boardResult[3][1]).isEqualTo(4)
        assertThat(boardResult[3][2]).isEqualTo(4)
        assertThat(boardResult[3][3]).isEqualTo(16)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][2]).isEqualTo(2)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }



    @Test
    fun `NONE with game over`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        val expected = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        gameState.board = boardGame

        val actual = moveNumbersUseCase.moveNumbers(NONE, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(4, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    @Test
    fun `NONE without game over`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        val expected = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            gameStatus = GAME_OVER
        }

        val actual = moveNumbersUseCase.moveNumbers(NONE, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(4, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(10, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult).isEqualTo(expected)
    }

    /*LEFT reach number to win while PLAYING
     2-2-2-2         4-4-_-_
     _-_-4-_         4-_-_-_
     4-_-2-_         4-2-_-_
     8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT PLAYING after reach number to win`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  4,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberToWin = 16
            gameStatus = YOU_WIN
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(16)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(18, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(8)
        assertThat(boardResult[3][1]).isEqualTo(4)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }




    /*LEFT increase current number keeping record number
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT increase current number keeping record number`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  8,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 8
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(16)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }

    /*LEFT increase current number and record number
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-8-_-_        16-_-_-_*/
    @Test
    fun `LEFT increase current number and record number`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  8,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(8, 8)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(16, 16))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(34, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(16)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(7)
    }

    /*LEFT save new record for number
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT save new record for number`() = runBlocking {
        val record = RecordValues(score = 138, number = 128, boardSize = 4)
        coEvery { repository.insertRecord(record) } answers { }
        coEvery { repository.getRecordsForBoard(4) } answers { emptyList() }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 64)
            originalBestValues.number = 16
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 128))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(138, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 16))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.insertRecord(record)
            }
        }
    }


    /*LEFT increase current score keeping record score
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT increase current score keeping record score`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  4,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord.currentValue = 8
            scoreCurrentRecord.currentValue = 10
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(18, RECORD_SCORE))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(8)
        assertThat(boardResult[3][1]).isEqualTo(4)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*LEFT increase current score and record score
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-_-_         8-4-_-_*/
    @Test
    fun `LEFT increase current score and record score`() = runBlocking {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  4,DEF,DEF)
        )
        gameState.apply {
            board = boardGame
            scoreCurrentRecord = CurrentRecordData(96, 100)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(PLAYING)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(8, RECORD_NUMBER))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(104, 104))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, RECORD_NUMBER))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)
        assertThat(boardResult[0][0]).isEqualTo(4)
        assertThat(boardResult[0][1]).isEqualTo(4)

        assertThat(boardResult[1][0]).isEqualTo(4)

        assertThat(boardResult[2][0]).isEqualTo(4)
        assertThat(boardResult[2][1]).isEqualTo(2)

        assertThat(boardResult[3][0]).isEqualTo(8)
        assertThat(boardResult[3][1]).isEqualTo(4)

        val numbers = boardResult.flatten().count { it != DEF }
        assertThat(numbers).isEqualTo(8)
    }

    /*LEFT save new record for score
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT save new record for score`() = runBlocking {
        val record = RecordValues(score = 328, number = 128, boardSize = 4)
        coEvery { repository.insertRecord(record) } answers { }
        coEvery { repository.getRecordsForBoard(4) } answers { emptyList() }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 256)
            scoreCurrentRecord = CurrentRecordData(200, 300)
            originalBestValues = IndividualBestValues(number = 256, score = 300)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 256))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(328, 328))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 256))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.insertRecord(record)
            }
        }
    }




    /*LEFT erase record with lowest score and number
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and number`() = runBlocking {
        val record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        coEvery { repository.getRecordsForBoard(4) } answers {
            listOf(
                RecordValues(id = 3, score = 150, number = 4, boardSize = 4),
                RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
                RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
            )
        }
        coEvery { repository.updateRecord(record) } answers { }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 256)
            scoreCurrentRecord = CurrentRecordData(200, 300)
            originalBestValues = IndividualBestValues(number = 256, score = 300)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 256))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(328, 328))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 256))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.updateRecord(record)
            }
        }
    }

    /*LEFT erase record with lowest score and non unique high number 1
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and non unique high number 1`() = runBlocking {
        val record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        coEvery { repository.getRecordsForBoard(4) } answers {
            listOf(
                RecordValues(id = 3, score = 150, number = 32, boardSize = 4),
                RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
                RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
            )
        }
        coEvery { repository.updateRecord(record) } answers { }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 256)
            scoreCurrentRecord = CurrentRecordData(200, 300)
            originalBestValues = IndividualBestValues(number = 256, score = 300)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 256))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(328, 328))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 256))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.updateRecord(record)
            }
        }
    }

    /*LEFT erase record with lowest score and non unique high number 2
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with lowest score and non unique high number 2`() = runBlocking {
        val record = RecordValues(id = 3, score = 328, number = 128, boardSize = 4)
        coEvery { repository.getRecordsForBoard(4) } answers {
            listOf(
                RecordValues(id = 3, score = 150, number = 32, boardSize = 4),
                RecordValues(id = 1, score = 200, number = 64, boardSize = 4),
                RecordValues(id = 2, score = 300, number = 32, boardSize = 4),
            )
        }
        coEvery { repository.updateRecord(record) } answers { }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 256)
            scoreCurrentRecord = CurrentRecordData(200, 300)
            originalBestValues = IndividualBestValues(number = 256, score = 300)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 256))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(328, 328))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 256))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.updateRecord(record)
            }
        }
    }

    /*LEFT erase record with non highest number and non lowest score
    64-64- 8-32      128- 8-32-x
    16-32-16-8        16-32-16-8
    32-16-64-4        32-16-64-4
    16-64-32-2        16-64-32-2*/
    @Test
    fun `LEFT erase record with non highest number and non lowest score`() = runBlocking {
        val record = RecordValues(id = 1, score = 328, number = 128, boardSize = 4)
        coEvery { repository.getRecordsForBoard(4) } answers {
            listOf(
                RecordValues(id = 3, score = 150, number = 64, boardSize = 4),
                RecordValues(id = 1, score = 200, number = 32, boardSize = 4),
                RecordValues(id = 2, score = 300, number = 16, boardSize = 4),
            )
        }
        coEvery { repository.updateRecord(record) } answers { }

        val boardGame = mutableListOf(
            mutableListOf( 64, 64,  8, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )
        gameState.apply {
            board = boardGame
            numberCurrentRecord = CurrentRecordData(64, 256)
            scoreCurrentRecord = CurrentRecordData(200, 300)
            originalBestValues = IndividualBestValues(number = 256, score = 300)
        }

        val actual = moveNumbersUseCase.moveNumbers(LEFT, gameState)
        assertThat(actual.gameStatus).isEqualTo(GAME_OVER)
        assertThat(actual.numberToWin).isEqualTo(NEXT_HIGH_NUMBER)

        assertThat(actual.numberCurrentRecord).isEqualTo(CurrentRecordData(128, 256))
        assertThat(actual.scoreCurrentRecord).isEqualTo(CurrentRecordData(328, 328))
        assertThat(actual.originalBestValues).isEqualTo(IndividualBestValues(RECORD_SCORE, 256))

        val boardResult = actual.board
        assertThat(boardResult.size).isEqualTo(4)

        assertThat(boardResult[0][0]).isEqualTo(128)
        assertThat(boardResult[0][1]).isEqualTo(8)
        assertThat(boardResult[0][2]).isEqualTo(32)
        assertThat(boardResult[0][3]).isNotEqualTo(DEF)

        assertThat(boardResult[1]).isEqualTo(mutableListOf( 16, 32, 16,  8))
        assertThat(boardResult[2]).isEqualTo(mutableListOf( 32, 16, 64,  4))
        assertThat(boardResult[3]).isEqualTo(mutableListOf( 16, 64, 32,  2))

        val numbers = boardResult.flatten().count { it == DEF }
        assertThat(numbers).isEqualTo(0)

        verify {
            runBlocking {
                repository.getRecordsForBoard(4)
                repository.updateRecord(record)
            }
        }
    }

}