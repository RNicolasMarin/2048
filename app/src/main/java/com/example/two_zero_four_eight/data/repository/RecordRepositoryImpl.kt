package com.example.two_zero_four_eight.data.repository

import com.example.two_zero_four_eight.data.local.daos.RecordDao
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.RecordValues

class RecordRepositoryImpl(
    private val dao: RecordDao
): RecordRepository {

    override suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues? {
        return dao.getIndividualBestValues(boardSize)
    }

    override suspend fun getRecordsForBoard(boardSize: Int): List<RecordValues> {
        return dao.getRecordsForBoard(boardSize)
    }

    override suspend fun insertRecord(record: RecordValues) {
        dao.insertRecord(record)
    }

    override suspend fun updateRecord(record: RecordValues) {
        dao.updateRecord(record)
    }

}