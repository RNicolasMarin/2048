package com.example.two_zero_four_eight.use_cases.utils

sealed class MoveNumberResult(
    open var boardGame: MutableList<MutableList<Int>>
) {

    data class KeepPlaying(
        override var boardGame: MutableList<MutableList<Int>>
    ): MoveNumberResult(boardGame)

    data class GameOver(
        override var boardGame: MutableList<MutableList<Int>>
    ): MoveNumberResult(boardGame)
}