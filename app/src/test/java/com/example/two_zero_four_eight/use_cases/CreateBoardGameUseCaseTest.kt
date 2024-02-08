package com.example.two_zero_four_eight.use_cases

import com.google.common.truth.Truth.*
import org.junit.Before
import org.junit.Test

class CreateBoardGameUseCaseTest {

    private lateinit var moveNumbersUseCase: CreateBoardGameUseCase

    @Before
    fun setUp() {
        moveNumbersUseCase = CreateBoardGameUseCase()
    }

    @Test
    fun `Create 3 x 3 BoardGame`() {
        val result = moveNumbersUseCase.createBoardGame(3).flatten()
        assertThat(result.size).isEqualTo(9)

        val emptyCellsAmount = result.filter { it == DEFAULT_VALUE }.size
        assertThat(emptyCellsAmount).isEqualTo(7)

        val usedCellsAmount = result.filter { it != DEFAULT_VALUE }.size
        assertThat(usedCellsAmount).isEqualTo(2)
    }
}