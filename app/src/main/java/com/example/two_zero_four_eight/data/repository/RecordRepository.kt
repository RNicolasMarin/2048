package com.example.two_zero_four_eight.data.repository

import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.RecordValues

interface RecordRepository {

    suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues?

    suspend fun getRecordsForBoard(boardSize: Int): List<RecordValues>

    suspend fun insertRecord(record: RecordValues)

    suspend fun updateRecord(record: RecordValues)

}