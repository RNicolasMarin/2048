package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

const val DEF = DEFAULT_VALUE
class MoveNumbersUseCaseTest {

    private lateinit var moveNumbersUseCase: MoveNumbersUseCase

    @Before
    fun setUp() {
        moveNumbersUseCase = MoveNumbersUseCase()
    }

    /*LEFT with empty spaces left
    2-2-2-2         4-4-_-_
    _-_-4-_         4-_-_-_
    4-_-2-_         4-2-_-_
    8-4-4-_         8-8-_-_*/
    @Test
    fun `LEFT with empty spaces left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,DEF,  4,DEF),
            mutableListOf(  4,DEF,  2,DEF),
            mutableListOf(  8,  4,  4,DEF)
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, LEFT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `LEFT with possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(  8,  4,  2,  2),
            mutableListOf( 16,  8,  4,  2),
            mutableListOf( 16,  8,  4,  2),
            mutableListOf( 16,  8,  4,  2),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, LEFT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `LEFT with no possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(DEF,  8, 64, 32),
            mutableListOf( 16, 32, 16,  8),
            mutableListOf( 32, 16, 64,  4),
            mutableListOf( 16, 64, 32,  2),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, LEFT, false)
        assertThat(actual.isGameOver).isEqualTo(true)

        val boardResult = actual.boardGame
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
    fun `LEFT with the same board`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, LEFT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }



    /*RIGHT with empty spaces left
    2-2-2-2         _-_-4-4
    _-4-_-_         _-_-_-4
    _-2-_-4         _-_-2-4
    _-4-4-8         _-_-8-8*/
    @Test
    fun `RIGHT with empty spaces left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(DEF,  4,DEF,DEF),
            mutableListOf(DEF,  2,DEF,  4),
            mutableListOf(DEF,  4,  4,  8)
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, RIGHT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `RIGHT with possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  4,  8),
            mutableListOf(  2,  4,  8, 16),
            mutableListOf(  2,  4,  8, 16),
            mutableListOf(  2,  4,  8, 16),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, RIGHT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
         32-64-8-_        x-32-64-8
        8-16-32-16        8-16-32-16
        4-64-16-32        4-64-16-32
        2-32-64-16        2-32-64-16*/
    @Test
    fun `RIGHT with no possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf( 32, 64,  8,DEF),
            mutableListOf(  8, 16, 32, 16),
            mutableListOf(  4, 64, 16, 32),
            mutableListOf(  2, 32, 64, 16),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, RIGHT, false)
        assertThat(actual.isGameOver).isEqualTo(true)

        val boardResult = actual.boardGame
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
    fun `RIGHT with the same board`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, RIGHT, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }



    /*UP with empty spaces left
    2-_-4-8         4-4-4-8
    2-_-_-4         4-_-2-8
    2-4-2-4         _-_-_-_
    2-_-_-_         _-_-_-_*/
    @Test//LEFT
    fun `UP with empty spaces left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,  4,  8),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,DEF)
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, UP, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `UP with possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(  8, 16, 16, 16),
            mutableListOf(  4,  8,  8,  8),
            mutableListOf(  2,  4,  4,  4),
            mutableListOf(  2,  2,  2,  2),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, UP, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `UP with no possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(DEF, 16, 32, 16),
            mutableListOf(  8, 32, 16, 64),
            mutableListOf( 64, 16, 64, 32),
            mutableListOf( 32,  8,  4,  2),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, UP, false)
        assertThat(actual.isGameOver).isEqualTo(true)

        val boardResult = actual.boardGame
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
    fun `UP with the same board`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, UP, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }



    /*DOWN with empty spaces left
        2-_-_-_         _-_-_-_
        2-4-2-4         _-_-_-_
        2-_-_-4         4-_-2-8
        2-_-4-8         4-4-4-8*/
    @Test//RIGHT
    fun `DOWN with empty spaces left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,DEF,DEF,DEF),
            mutableListOf(  2,  4,  2,  4),
            mutableListOf(  2,DEF,DEF,  4),
            mutableListOf(  2,DEF,  4,  8),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, DOWN, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `DOWN with possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf(  2,  2,  2,  2),
            mutableListOf(  2,  4,  4,  4),
            mutableListOf(  4,  8,  8,  8),
            mutableListOf(  8, 16, 16, 16),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, DOWN, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
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
    fun `DOWN with no possible move left`() {
        val boardGame = mutableListOf(
            mutableListOf( 32,  8,  4,  2),
            mutableListOf( 64, 16, 64, 32),
            mutableListOf(  8, 32, 16, 64),
            mutableListOf(DEF, 16, 32, 16),
        )

        val actual = moveNumbersUseCase.moveNumbers(boardGame, DOWN, false)
        assertThat(actual.isGameOver).isEqualTo(true)

        val boardResult = actual.boardGame
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
    fun `DOWN with the same board`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, DOWN, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }



    @Test
    fun `NONE with game over`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, NONE, false)
        assertThat(actual.isGameOver).isEqualTo(false)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }

    @Test
    fun `NONE without game over`() {
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

        val actual = moveNumbersUseCase.moveNumbers(boardGame, NONE, true)
        assertThat(actual.isGameOver).isEqualTo(true)

        val boardResult = actual.boardGame
        assertThat(boardResult).isEqualTo(expected)
    }
}