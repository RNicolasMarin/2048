package com.example.two_zero_four_eight.data.repository

import com.example.two_zero_four_eight.data.local.daos.RecordDao
import com.example.two_zero_four_eight.data.model.IndividualBestValues

class RecordRepositoryImpl(
    private val dao: RecordDao
): RecordRepository {

    override suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues? {
        return dao.getIndividualBestValues(boardSize)
    }
}