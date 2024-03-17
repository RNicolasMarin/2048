package com.example.two_zero_four_eight.data.model

import com.example.two_zero_four_eight.data.model.GameStatus.*
import com.example.two_zero_four_eight.ui.DEFAULT_NUMBER_TO_WIN
import com.example.two_zero_four_eight.use_cases.copy

data class GameState(
    var currentState: SingleGameState = SingleGameState(),
    var previousState: SingleGameState? = null,
    var originalBestValues: IndividualBestValues = IndividualBestValues()
)

data class SingleGameState(
    var board: MutableList<MutableList<Int>> = mutableListOf(),
    var gameStatus: GameStatus = PLAYING,
    var numberToWin: Int = DEFAULT_NUMBER_TO_WIN,
    var numberCurrentRecord: CurrentRecordData = CurrentRecordData(),
    var scoreCurrentRecord: CurrentRecordData = CurrentRecordData()
) {
    fun clone(): SingleGameState {
        return copy(
            board = board.copy(),
            gameStatus = gameStatus,
            numberToWin = numberToWin,
            numberCurrentRecord = numberCurrentRecord.copy(),
            scoreCurrentRecord = scoreCurrentRecord.copy()
        )
    }
}

enum class GameStatus {
    PLAYING,
    GAME_OVER,
    YOU_WIN
}