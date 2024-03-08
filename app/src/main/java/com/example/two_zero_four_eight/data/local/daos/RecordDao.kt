package com.example.two_zero_four_eight.data.local.daos

import androidx.room.Dao
import androidx.room.Query
import com.example.two_zero_four_eight.data.model.IndividualBestValues

@Dao
interface RecordDao {

    //SELECT (SELECT MAX(score) FROM records WHERE boardSize = 3) AS score, (SELECT MAX(number) FROM records WHERE boardSize = 3) AS number;
    //SELECT MAX(score) AS highest_score FROM records WHERE boardSize = 3;
    //SELECT MAX(number) AS highest_number FROM records WHERE boardSize = 3;
    @Query("SELECT (SELECT MAX(score) FROM records WHERE boardSize = :boardSize) AS score, (SELECT MAX(number) FROM records WHERE boardSize = :boardSize) AS number;")
    suspend fun getIndividualBestValues(boardSize: Int): IndividualBestValues?
}