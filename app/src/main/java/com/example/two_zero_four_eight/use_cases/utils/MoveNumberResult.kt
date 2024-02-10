package com.example.two_zero_four_eight.use_cases.utils

data class MoveNumberResult(
    var boardGame: MutableList<MutableList<Int>>,
    var isGameOver: Boolean = false
)