package com.example.two_zero_four_eight.use_cases

import com.example.two_zero_four_eight.data.model.GameState

class UpdateCurrentRecordsUseCase {

    fun updateValues(gameState: GameState): GameState = with(gameState) {
        val newNumber = board.flatten().max()

        with(numberCurrentRecord) {
            if (newNumber > currentValue) {
                currentValue = newNumber
            }

            if (newNumber > recordValue) {
                recordValue = newNumber
            }
        }

        return this
    }
}