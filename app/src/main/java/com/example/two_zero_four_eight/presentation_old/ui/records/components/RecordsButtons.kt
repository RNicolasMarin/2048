package com.example.two_zero_four_eight.presentation_old.ui.records.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.presentation_old.design_system.Green1
import com.example.two_zero_four_eight.presentation_old.design_system.Green2
import com.example.two_zero_four_eight.presentation_old.design_system.Green3
import com.example.two_zero_four_eight.presentation_old.design_system.White
import com.example.two_zero_four_eight.presentation_old.design_system.dimensOld
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction.*
import com.example.two_zero_four_eight.presentation.ui.records.RecordsState
import com.example.two_zero_four_eight.presentation_old.ui.records.components.RecordsButtonsState.*

@Composable
fun RecordsButtonsOld(
    state: RecordsState,
    onAction: (RecordsAction) -> Unit,
    modifier: Modifier = Modifier
) {

    Column {
        Row(modifier = modifier) {
            RecordsButtonOld(
                text = getFilterOptionsAsText(state.filterOptions, stringResource(id = R.string.all)),
                open = state.buttonsState == FILTER,
                onClick = { onAction(OnButtonStateChanged(FILTER)) },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(12.dp))
            RecordsButtonOld(
                text = "${stringResource(id = R.string.by)} ${stringResource(id = state.selectedSortOption.text)}",
                open = state.buttonsState == SORT,
                onClick = { onAction(OnButtonStateChanged(SORT)) },
                modifier = Modifier.weight(1f)
            )
        }
        when (state.buttonsState) {
            FILTER -> {
                RecordsButtonOptionsOld(
                    columns = 3,
                    showTopEnd = true,
                    items = state.filterOptions,
                    isSelected = { item -> item.selected },
                    getText = { item -> "${item.size.size}X${item.size.size}" },
                    onAction = { index -> onAction(OnFilterChecked(index)) }
                )
            }
            SORT -> {
                RecordsButtonOptionsOld(
                    columns = 2,
                    showTopStart = true,
                    items = state.sortOptions,
                    isSelected = { item -> item.selected },
                    getText = { item -> stringResource(id = item.sort.text) },
                    onAction = { index -> onAction(OnSortChecked(index)) }
                )
            }
            NONE -> Unit
        }
    }
}

@Composable
fun <T>RecordsButtonOptionsOld(
    columns: Int,
    showTopStart: Boolean = false,
    showTopEnd: Boolean = false,
    items: List<T>,
    isSelected: (T) -> Boolean,
    getText: @Composable (T) -> String,
    onAction: (Int) -> Unit,
) {
    val corners = MaterialTheme.dimensOld.corners

    LazyVerticalGrid(
        columns = GridCells.Fixed(columns),
        contentPadding = PaddingValues(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .fillMaxWidth()
            .background(
                shape = RoundedCornerShape(
                    bottomStart = corners,
                    bottomEnd = corners,
                    topStart = if (showTopStart) corners else 0.dp,
                    topEnd = if (showTopEnd) corners else 0.dp
                ),
                color = Green2
            )
    ) {
        itemsIndexed(items) { index, item ->
            RecordsButtonOptionOld(
                selected = isSelected(item),
                text = getText(item),
                onChecked = { onAction(index) }
            )
        }
    }
}

@Composable
fun RecordsButtonOptionOld(
    selected: Boolean,
    text: String,
    onChecked: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = { onChecked() },
        label = {
            Text(
                text = text,
                color = White,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(5.dp),
        colors = FilterChipDefaults.filterChipColors().copy(
            containerColor = Green3,
            selectedContainerColor = Green1
        )
    )
}

fun getFilterOptionsAsText(options: List<FilterOption>, all: String): String {
    var result = ""
    options.filter { it.selected }.forEach {
        result += "${it.size.size}X${it.size.size}, "
    }

    if (result.isEmpty()) return all

    return result.substring(0, result.length - 2)
}

@Composable
fun RecordsButtonOld(
    text: String,
    open: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val corners = MaterialTheme.dimensOld.corners

    Column (
        modifier = modifier
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = RoundedCornerShape(
                        topStart = corners,
                        topEnd = corners,
                        bottomStart = if (open) 0.dp else corners,
                        bottomEnd = if (open) 0.dp else corners
                    ),
                    color = Green2
                ),
            onClick = onClick,
        ) {
            Text(
                text = text,
                color = White,
                style = MaterialTheme.typography.titleLarge,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier
            )
        }
        if (open) {
            Spacer(
                modifier = Modifier
                    .background(color = Green2)
                    .height(10.dp)
                    .fillMaxWidth()
            )
        }
    }
}

enum class RecordsButtonsState {
    FILTER, SORT, NONE
}