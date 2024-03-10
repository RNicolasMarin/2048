package com.example.two_zero_four_eight.data.model

import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.ui.DEFAULT_NUMBER_TO_WIN

data class GameState(
    var board: MutableList<MutableList<Int>> = mutableListOf(),
    var gameStatus: GameStatus = PLAYING,
    var numberToWin: Int = DEFAULT_NUMBER_TO_WIN,
    var numberCurrentRecord: CurrentRecordData = CurrentRecordData(),
    var scoreCurrentRecord: CurrentRecordData = CurrentRecordData(),
    var originalBestValues: IndividualBestValues = IndividualBestValues()
)

enum class GameStatus {
    PLAYING,
    GAME_OVER,
    YOU_WIN
}