package com.example.two_zero_four_eight.presentation.ui.records.components

import com.example.two_zero_four_eight.R

data class SortOption(
    val sort: Sort,
    var selected: Boolean
)

enum class Sort(val text: Int) {
    NUMBER(R.string.number),
    SCORE(R.string.score)
}