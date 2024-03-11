package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.repository.RecordRepository
import com.google.common.truth.Truth.*
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class CreateBoardGameUseCaseTest {

    private lateinit var moveNumbersUseCase: CreateBoardGameUseCase
    private lateinit var addNumberToBoardGameUseCase: AddNumberToBoardGameUseCase
    private lateinit var repository: RecordRepository

    @Before
    fun setUp() {
        addNumberToBoardGameUseCase = AddNumberToBoardGameUseCase()
        repository = mockk()
        moveNumbersUseCase = CreateBoardGameUseCase(addNumberToBoardGameUseCase, repository)
    }

    @Test
    fun `Create 3 x 3 BoardGame with record`() = runBlocking {
        val individualBestValues = IndividualBestValues(number = 64, score = 400)
        coEvery { repository.getIndividualBestValues(3) } answers {
            individualBestValues
        }
        val state = moveNumbersUseCase.createBoardGame(3)
        val boardResult = state.board.flatten()

        with(boardResult) {
            assertThat(size).isEqualTo(9)

            val emptyCellsAmount = filter { it == DEFAULT_VALUE }.size
            assertThat(emptyCellsAmount).isEqualTo(7)

            val usedCellsAmount = filter { it != DEFAULT_VALUE }.size
            assertThat(usedCellsAmount).isEqualTo(2)
        }

        with(state) {
            assertThat(numberCurrentRecord).isEqualTo(CurrentRecordData(recordValue = 64))
            assertThat(scoreCurrentRecord).isEqualTo(CurrentRecordData(recordValue = 400))
            assertThat(originalBestValues).isEqualTo(individualBestValues)
        }

        verify {
            runBlocking {
                repository.getIndividualBestValues(3)
            }
        }
    }

    @Test
    fun `Create 3 x 3 BoardGame without record`() = runBlocking {
        coEvery { repository.getIndividualBestValues(3) } answers {
            null
        }
        val state = moveNumbersUseCase.createBoardGame(3)
        val boardResult = state.board.flatten()

        with(boardResult) {
            assertThat(size).isEqualTo(9)

            val emptyCellsAmount = filter { it == DEFAULT_VALUE }.size
            assertThat(emptyCellsAmount).isEqualTo(7)

            val usedCellsAmount = filter { it != DEFAULT_VALUE }.size
            assertThat(usedCellsAmount).isEqualTo(2)
        }

        with(state) {
            assertThat(numberCurrentRecord).isEqualTo(CurrentRecordData())
            assertThat(scoreCurrentRecord).isEqualTo(CurrentRecordData())
            assertThat(originalBestValues).isEqualTo(IndividualBestValues())
        }

        verify {
            runBlocking {
                repository.getIndividualBestValues(3)
            }
        }
    }

}