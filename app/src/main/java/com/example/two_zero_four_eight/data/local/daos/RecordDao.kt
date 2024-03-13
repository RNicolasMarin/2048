package com.example.two_zero_four_eight.data.local.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.two_zero_four_eight.data.model.IndividualBestValues
import com.example.two_zero_four_eight.data.model.RecordValues

@Dao
interface RecordDao {

    @Query("SELECT (SELECT MAX(score) FROM records WHERE boardSize = :boardSize) AS score, (SELECT MAX(number) FROM records WHERE boardSize = :boardSize) AS number;")
    suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues?

    @Query("SELECT * FROM records WHERE boardSize = :boardSize ORDER BY score, number ASC")
    suspend fun getRecordsForBoard(boardSize: Int): List<RecordValues>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecord(record: RecordValues)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateRecord(record: RecordValues)
}