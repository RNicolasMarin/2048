package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.RecordValues
import com.example.two_zero_four_eight.data.repository.RecordRepository
import javax.inject.Inject

class UpdateCurrentRecordsUseCase @Inject constructor(
    private val repository: RecordRepository
) {

    private lateinit var gameState: GameState

    suspend fun updateValues(state: GameState): GameState = with(state) {
        gameState = this

        updateNumber()

        val createNewRecord = shouldCreateNewRecord(numberCurrentRecord, originalBestValues.number)
                || shouldCreateNewRecord(scoreCurrentRecord, originalBestValues.score)

        saveNewRecordIfNeeded(createNewRecord)

        return this
    }

    private suspend fun saveNewRecordIfNeeded(createNewRecord: Boolean) = with(gameState) {
        if (!createNewRecord) return

        val newRecordValues = RecordValues(
            score = scoreCurrentRecord.recordValue,
            number = numberCurrentRecord.recordValue,
            boardSize = gameState.board.size
        )
        repository.insertRecord(newRecordValues)
    }

    private fun shouldCreateNewRecord(
        currentRecord: CurrentRecordData,
        originalValue: Int
    ): Boolean = with(gameState) {
        if (gameStatus != GAME_OVER) return false

        return currentRecord.recordValue > originalValue
    }

    private fun updateNumber() = with(gameState.numberCurrentRecord) {
        val newNumber = gameState.board.flatten().max()

        if (newNumber > currentValue) {
            currentValue = newNumber
        }

        if (newNumber > recordValue) {
            recordValue = newNumber
        }
    }
}