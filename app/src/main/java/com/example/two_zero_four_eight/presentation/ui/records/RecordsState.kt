package com.example.two_zero_four_eight.presentation.ui.records

import com.example.two_zero_four_eight.domain.models.Record
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.*
import com.example.two_zero_four_eight.presentation.ui.records.components.FilterOption
import com.example.two_zero_four_eight.presentation.ui.records.components.RecordsButtonsState
import com.example.two_zero_four_eight.presentation.ui.records.components.RecordsButtonsState.*
import com.example.two_zero_four_eight.presentation.ui.records.components.Sort
import com.example.two_zero_four_eight.presentation.ui.records.components.Sort.*
import com.example.two_zero_four_eight.presentation.ui.records.components.SortOption

data class RecordsState(
    var buttonsState: RecordsButtonsState = NONE,
    var filterOptions: List<FilterOption> = listOf(),
    var sortOptions: List<SortOption> = listOf(),
    var selectedSortOption: Sort = NUMBER,
    var status: RecordsStatus = LOADING,
    var records: List<Record> = listOf(),
)

enum class RecordsStatus {
    LOADING,
    NO_RECORDS,
    NO_FILTERED_RECORDS,
    RECORDS
}
