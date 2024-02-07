package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

class MoveNumbersUseCaseTest {

    private lateinit var moveNumbersUseCase: MoveNumbersUseCase

    @Before
    fun setUp() {
        moveNumbersUseCase = MoveNumbersUseCase()
    }

    @Test
    fun `LEFT 2-2-2-2 to 4-4-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(2,2,2,2),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(4,4,-1,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, LEFT)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `LEFT _-_-4-_ to 4-_-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(-1,-1,4,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(4,-1,-1,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, LEFT)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `LEFT 4-_-2-_ to 4-2-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(4,-1,2,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(4,2,-1,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, LEFT)
        assertThat(actual).isEqualTo(expected)
    }


    @Test
    fun `RIGHT 2-2-2-2 to _-_-4-4`() {
        val matrix = mutableListOf(
            mutableListOf(2,2,2,2),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(-1,-1,4,4),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, RIGHT)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `RIGHT _-4-_-_ to _-_-_-4`() {
        val matrix = mutableListOf(
            mutableListOf(-1,4,-1,-1),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(-1,-1,-1,4),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, RIGHT)
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `RIGHT _-2-_-4 to _-_-2-4`() {
        val matrix = mutableListOf(
            mutableListOf(-1, 2, -1, 4),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val expected = mutableListOf(
            mutableListOf(-1,-1,2,4),
            getNonChangingRow(),
            getNonChangingRow(),
            getNonChangingRow()
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, RIGHT)
        assertThat(actual).isEqualTo(expected)
    }



    /*
    * 2
    * 2
    * 2
    * 2
    *
    * 4
    * 4
    * -
    * -
    * */
    @Test//LEFT
    fun `UP 2-2-2-2 to 4-4-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(4,2,4,4),
            mutableListOf(8,2,8,8),
            mutableListOf(16,2,16,16),
            mutableListOf(32,2,32,32),
        )
        val expected = mutableListOf(
            mutableListOf(4,4,4,4),
            mutableListOf(8,4,8,8),
            mutableListOf(16,-1,16,16),
            mutableListOf(32,-1,32,32),
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, UP)
        assertThat(actual).isEqualTo(expected)
    }

    /*
    * -
    * -
    * 4
    * -
    *
    * 4
    * -
    * -
    * -
    * */
    @Test//LEFT
    fun `UP _-_-4-_ to 4-_-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(-1,4,4,4),
            mutableListOf(-1,8,8,8),
            mutableListOf(4,16,16,16),
            mutableListOf(-1,32,32,32),
        )
        val expected = mutableListOf(
            mutableListOf(4,4,4,4),
            mutableListOf(-1,8,8,8),
            mutableListOf(-1,16,16,16),
            mutableListOf(-1,32,32,32),
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, UP)
        assertThat(actual).isEqualTo(expected)
    }

    /*
    * 4
    * -
    * 2
    * -
    *
    * 4
    * 2
    * -
    * -
    * */
    @Test//LEFT
    fun `UP 4-_-2-_ to 4-2-_-_`() {
        val matrix = mutableListOf(
            mutableListOf(4,4,4,4),
            mutableListOf(8,8,8,-1),
            mutableListOf(16,16,16,2),
            mutableListOf(32,32,32,-1),
        )
        val expected = mutableListOf(
            mutableListOf(4,4,4,4),
            mutableListOf(8,8,8,2),
            mutableListOf(16,16,16,-1),
            mutableListOf(32,32,32,-1),
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, UP)
        assertThat(actual).isEqualTo(expected)
    }

    /*
    * 2
    * 2
    * 2
    * 2
    *
    * -
    * -
    * 4
    * 4
    * */
    @Test//RIGHT
    fun `DOWN 2-2-2-2 to _-_-4-4`() {
        val matrix = mutableListOf(
            mutableListOf(4,4,4,2),
            mutableListOf(8,8,8,2),
            mutableListOf(16,16,16,2),
            mutableListOf(32,32,32,2),
        )
        val expected = mutableListOf(
            mutableListOf(4,4,4,-1),
            mutableListOf(8,8,8,-1),
            mutableListOf(16,16,16,4),
            mutableListOf(32,32,32,4),
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, DOWN)
        assertThat(actual).isEqualTo(expected)
    }

    /*
    * -
    * 4
    * -
    * -
    *
    * -
    * -
    * -
    * 4
    * */
    @Test//RIGHT
    fun `DOWN _-4-_-_ to _-_-_-4`() {
        val matrix = mutableListOf(
            mutableListOf(4,4,-1,4),
            mutableListOf(8,8,4,8),
            mutableListOf(16,16,-1,16),
            mutableListOf(32,32,-1,32),
        )
        val expected = mutableListOf(
            mutableListOf(4,4,-1,4),
            mutableListOf(8,8,-1,8),
            mutableListOf(16,16,-1,16),
            mutableListOf(32,32,4,32)
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, DOWN)
        assertThat(actual).isEqualTo(expected)
    }

    /*
    * -
    * 2
    * -
    * 4
    *
    * -
    * -
    * 2
    * 4
    * */
    @Test//RIGHT
    fun `DOWN _-2-_-4 to _-_-2-4`() {
        val matrix = mutableListOf(
            mutableListOf(4,-1,4,4),
            mutableListOf(8,2,8,8),
            mutableListOf(16,-1,16,16),
            mutableListOf(32,4,32,32)
        )
        val expected = mutableListOf(
            mutableListOf(4,-1,4,4),
            mutableListOf(8,-1,8,8),
            mutableListOf(16,2,16,16),
            mutableListOf(32,4,32,32)
        )
        val actual = moveNumbersUseCase.moveNumbers(matrix, DOWN)
        assertThat(actual).isEqualTo(expected)
    }




    private fun getNonChangingRow(): MutableList<Int> {
        return mutableListOf(16,8,4,32)
    }
}