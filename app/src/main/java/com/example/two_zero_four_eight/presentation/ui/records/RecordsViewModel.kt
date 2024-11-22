package com.example.two_zero_four_eight.presentation.ui.records

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.two_zero_four_eight.domain.models.BoardSize
import com.example.two_zero_four_eight.domain.repositories.RecordRepository
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction.*
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.*
import com.example.two_zero_four_eight.presentation_old.ui.records.components.FilterOption
import com.example.two_zero_four_eight.presentation_old.ui.records.components.RecordsButtonsState
import com.example.two_zero_four_eight.presentation_old.ui.records.components.RecordsButtonsState.*
import com.example.two_zero_four_eight.presentation_old.ui.records.components.Sort
import com.example.two_zero_four_eight.presentation_old.ui.records.components.Sort.*
import com.example.two_zero_four_eight.presentation_old.ui.records.components.SortOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecordsViewModel @Inject constructor(
    private val repository: RecordRepository
): ViewModel() {

    var state by mutableStateOf(RecordsState())
        private set

    private var allFilterOptionNumbers: List<Int> = emptyList()

    init {
        val filterOptions = BoardSize.entries.map { FilterOption(it, false) }
        val sortOptions   = Sort.entries.map { SortOption(it, it == NUMBER) }

        allFilterOptionNumbers = filterOptions.map { it.size.size }

        viewModelScope.launch {
            state = state.copy(
                filterOptions = filterOptions,
                sortOptions = sortOptions
            )
            loadRecords(filterOptions, state.selectedSortOption)
        }
    }

    private suspend fun loadRecords(filterOptions: List<FilterOption>, sort: Sort) {
        val filters = if (filterOptions.isEmpty()) allFilterOptionNumbers else filterOptions.map { it.size.size }
        val records = when (sort) {
            NUMBER -> repository.getRecordsWithSizesAndSortedByNumber(filters)
            SCORE -> repository.getRecordsWithSizesAndSortedByScore(filters)
        }

        val status = when {
            records.isEmpty() && filters == allFilterOptionNumbers -> NO_RECORDS
            records.isEmpty() && filters != allFilterOptionNumbers -> NO_FILTERED_RECORDS
            else -> RECORDS
        }

        delay(500)

        state = state.copy(
            status = status,
            records = records
        )
    }

    fun onAction(action: RecordsAction) = viewModelScope.launch {
        when (action) {
            is OnFilterChecked -> {
                val filterOptions = state.filterOptions.mapIndexed { index, filterOption ->
                    val selected = filterOption.selected
                    filterOption.copy(
                        selected = if (index == action.position) !selected else selected
                    )
                }
                state = state.copy(
                    filterOptions = filterOptions,
                    status = LOADING
                )
                loadRecords(filterOptions.filter { it.selected }, state.selectedSortOption)
            }

            is OnSortChecked -> {
                val sortOptions = state.sortOptions
                val option = sortOptions[action.position]

                if (option.selected) return@launch

                state = state.copy(
                    sortOptions = sortOptions.mapIndexed { index, sortOption ->
                        sortOption.copy(
                            selected = index == action.position
                        )
                    },
                    selectedSortOption = option.sort,
                    status = LOADING
                )
                loadRecords(state.filterOptions.filter { it.selected }, option.sort)
            }

            is OnButtonStateChanged -> {
                when (action.state) {
                    FILTER -> changeStateToNoneOrSpecificState(FILTER)
                    SORT -> changeStateToNoneOrSpecificState(SORT)
                    NONE -> Unit
                }
            }

            else -> Unit
        }
    }

    private fun changeStateToNoneOrSpecificState(buttonState: RecordsButtonsState) {
        val newState = if (state.buttonsState == buttonState) NONE else buttonState
        state = state.copy(
            buttonsState = newState
        )
    }

}