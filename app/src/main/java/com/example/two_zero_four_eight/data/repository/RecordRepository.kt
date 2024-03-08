package com.example.two_zero_four_eight.data.repository

import com.example.two_zero_four_eight.data.model.IndividualBestValues

interface RecordRepository {

    suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues?
}