package com.example.two_zero_four_eight.model

import com.example.two_zero_four_eight.model.GameStatus.*
import com.example.two_zero_four_eight.ui.DEFAULT_NUMBER_TO_WIN

data class GameState(
    var board: MutableList<MutableList<Int>> = mutableListOf(),
    var gameStatus: GameStatus = PLAYING,
    var numberToWin: Int = DEFAULT_NUMBER_TO_WIN,
    var numberCurrentRecord: CurrentRecordData = CurrentRecordData(),
)

enum class GameStatus {
    PLAYING,
    GAME_OVER,
    YOU_WIN
}