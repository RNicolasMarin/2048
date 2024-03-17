package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.data.model.GameState
import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.data.model.RecordValues
import com.example.two_zero_four_eight.data.repository.RecordRepository
import javax.inject.Inject

class UpdateCurrentRecordsUseCase @Inject constructor(
    private val repository: RecordRepository,
    private val maxSavedRecords: Int = 2
) {

    private lateinit var gameState: GameState

    suspend fun updateValues(state: GameState): GameState = with(state) {
        gameState = this

        updateNumber()

        val createNewRecord = shouldCreateNewRecord(currentState.numberCurrentRecord, originalBestValues.number)
                || shouldCreateNewRecord(currentState.scoreCurrentRecord, originalBestValues.score)

        saveNewRecordIfNeeded(createNewRecord)

        return this
    }

    private suspend fun saveNewRecordIfNeeded(createNewRecord: Boolean) = with(gameState.currentState) {
        if (!createNewRecord) return

        val newRecordValues = RecordValues(
            score = scoreCurrentRecord.currentValue,
            number = numberCurrentRecord.currentValue,
            boardSize = board.size
        )

        //get the records for that size
        val records = repository.getRecordsForBoard(board.size)

        //if there's less than 10 records (0-9 inclusive) add the new record
        if (records.size < maxSavedRecords) {
            repository.insertRecord(newRecordValues)
        } else {
            //check a record that is not needed anymore, use its id on the new record and updated on the db
            val id = getIrrelevantRecord(records).id
            newRecordValues.id = id
            repository.updateRecord(newRecordValues)
        }
    }

    private fun getIrrelevantRecord(records: List<RecordValues>): RecordValues {
        val lowestNumber = records.minBy { it.number }.number

        for (recordLowestScore in records) {
            //if the record with lowest score also has the lowest number overwrite it
            if (recordLowestScore.number == lowestNumber) {
                return recordLowestScore
            }

            //if there's any record with the same number or higher overwrite the record with lowest score
            val numbersEqualOrHigherToLowestNumber = records.count { it.number >= recordLowestScore.number }
            if (numbersEqualOrHigherToLowestNumber > 1) {
                return recordLowestScore
            }
        }

        return records.first()
    }

    private fun shouldCreateNewRecord(
        currentRecord: CurrentRecordData,
        originalValue: Int
    ): Boolean = with(gameState.currentState) {
        if (gameStatus != GAME_OVER) return false

        return currentRecord.recordValue > originalValue
    }

    private fun updateNumber() = with(gameState.currentState.numberCurrentRecord) {
        val newNumber = gameState.currentState.board.flatten().max()

        if (newNumber > currentValue) {
            currentValue = newNumber
        }

        if (newNumber > recordValue) {
            recordValue = newNumber
        }
    }
}