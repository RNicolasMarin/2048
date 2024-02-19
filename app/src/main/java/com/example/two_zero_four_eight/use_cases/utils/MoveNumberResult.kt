package com.example.two_zero_four_eight.use_cases.utils

import com.example.two_zero_four_eight.model.GameStatus
import com.example.two_zero_four_eight.model.GameStatus.*

data class MoveNumberResult(
    var boardGame: MutableList<MutableList<Int>>,
    var gameStatus: GameStatus = PLAYING,
    var numberToWin: Int
)