package com.example.two_zero_four_eight.presentation.ui.records

import com.example.two_zero_four_eight.presentation.ui.records.components.RecordsButtonsState

sealed interface RecordsAction {

    data class OnButtonStateChanged(
        val state: RecordsButtonsState
    ) : RecordsAction

    data class OnFilterChecked(
        val position: Int
    ) : RecordsAction

    data class OnSortChecked(
        val position: Int
    ) : RecordsAction

    data object OnBackToMenu : RecordsAction
}