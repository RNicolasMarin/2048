package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.GameState

class UpdateCurrentRecordsUseCase {

    fun updateValues(gameState: GameState): GameState = with(gameState) {
        val newNumber = board.flatten().max()

        if (newNumber > numberCurrentRecord.currentValue) {
            numberCurrentRecord.currentValue = newNumber
        }

        return this
    }
}