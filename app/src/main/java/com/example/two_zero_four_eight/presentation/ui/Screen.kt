package com.example.two_zero_four_eight.presentation.ui

import kotlinx.serialization.Serializable

sealed class Screen {

    @Serializable
    data object Menu: Screen()

    @Serializable
    data object Records: Screen()

    @Serializable
    data object Game: Screen()

    @Serializable
    data class YouWin(
        val currentScore: Int,
        val recordScore: Int,
        val currentNumber: Int,
        val recordNumber: Int,
    ): Screen()

    @Serializable
    data class GameOver(
        val currentScore: Int,
        val recordScore: Int,
        val currentNumber: Int,
        val recordNumber: Int,
        val numberToWin: Int
    ): Screen()

}