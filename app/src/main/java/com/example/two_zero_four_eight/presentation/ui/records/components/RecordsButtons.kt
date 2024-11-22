package com.example.two_zero_four_eight.presentation.ui.records.components

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
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.ShimmerPlaceholder
import com.example.two_zero_four_eight.presentation.design_system.Typographies
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction.OnButtonStateChanged
import com.example.two_zero_four_eight.presentation.ui.records.RecordsState
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.LOADING
import com.example.two_zero_four_eight.presentation_old.design_system.Green1
import com.example.two_zero_four_eight.presentation_old.design_system.Green2
import com.example.two_zero_four_eight.presentation_old.design_system.Green3
import com.example.two_zero_four_eight.presentation_old.design_system.White
import com.example.two_zero_four_eight.presentation_old.ui.records.components.FilterOption
import com.example.two_zero_four_eight.presentation_old.ui.records.components.RecordsButtonsState.*

@Composable
fun RecordsButtons(
    state: RecordsState,
    onAction: (RecordsAction) -> Unit,
    dimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier = Modifier
) {
    Column{
        Row(
            modifier = modifier
        ) {
            RecordsButton(
                text = getFilterOptionsAsText(state.filterOptions, stringResource(id = R.string.all)),
                isOpen = state.buttonsState == FILTER,
                isLoading = state.status == LOADING,
                onClick = { onAction(OnButtonStateChanged(FILTER)) },
                modifier = Modifier.weight(1f)
            )

            Spacer(modifier = Modifier.width(dimens.screenPadding))

            RecordsButton(
                text = "${stringResource(id = R.string.by)} ${stringResource(id = state.selectedSortOption.text)}",
                isOpen = state.buttonsState == SORT,
                isLoading = state.status == LOADING,
                onClick = { onAction(OnButtonStateChanged(SORT)) },
                modifier = Modifier.weight(1f)
            )
        }
        when (state.buttonsState) {
            FILTER -> {
                RecordsButtonOptions(
                    columns = 3,
                    isLoading = state.status == LOADING,
                    showTopEnd = true,
                    items = state.filterOptions,
                    isSelected = { item -> item.selected },
                    getText = { item -> "${item.size.size}X${item.size.size}" },
                    onAction = { index -> onAction(RecordsAction.OnFilterChecked(index)) }
                )
            }
            SORT -> {
                RecordsButtonOptions(
                    columns = 2,
                    isLoading = state.status == LOADING,
                    showTopStart = true,
                    items = state.sortOptions,
                    isSelected = { item -> item.selected },
                    getText = { item -> stringResource(id = item.sort.text) },
                    onAction = { index -> onAction(RecordsAction.OnSortChecked(index)) }
                )
            }
            NONE -> Unit
        }
    }
}

@Composable
fun <T>RecordsButtonOptions(
    columns: Int,
    isLoading: Boolean,
    showTopStart: Boolean = false,
    showTopEnd: Boolean = false,
    items: List<T>,
    isSelected: (T) -> Boolean,
    getText: @Composable (T) -> String,
    dimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier = Modifier,
    onAction: (Int) -> Unit,
) {
    val corners = dimens.cornerRadius
    val topStart = if (showTopStart) corners else 0.dp
    val topEnd = if (showTopEnd) corners else 0.dp

    val shape = RoundedCornerShape(
        bottomStart = corners,
        bottomEnd = corners,
        topStart = topStart,
        topEnd = topEnd
    )
    ShimmerPlaceholder(
        shape = shape,
        isLoading = isLoading,
        modifier = modifier
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            contentPadding = PaddingValues(dimens.screenPadding),
            horizontalArrangement = Arrangement.spacedBy(dimens.screenPadding),
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    shape = shape,
                    color = Green2
                )
        ) {
            itemsIndexed(items) { index, item ->
                RecordsButtonOption(
                    selected = isSelected(item),
                    text = getText(item),
                    onChecked = { onAction(index) }
                )
            }
        }
    }
}

@Composable
fun RecordsButtonOption(
    selected: Boolean,
    text: String,
    dimens: Dimens = MaterialTheme.dimens,
    typographies: Typographies = MaterialTheme.typographies,
    onChecked: () -> Unit
) {
    FilterChip(
        selected = selected,
        onClick = { onChecked() },
        label = {
            Text(
                text = text,
                color = White,
                style = typographies.text20Bold,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        },
        shape = RoundedCornerShape(dimens.cornerRadius),
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
fun RecordsButton(
    text: String,
    isOpen: Boolean,
    isLoading: Boolean,
    dimens: Dimens = MaterialTheme.dimens,
    textStyle: TextStyle = MaterialTheme.typographies.text20Bold,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {

    val topCorner = dimens.cornerRadius
    val bottomCorner = if (isOpen) 0.dp else dimens.cornerRadius

    val shape = RoundedCornerShape(
        topStart = topCorner,
        topEnd = topCorner,
        bottomStart = bottomCorner,
        bottomEnd = bottomCorner
    )

    ShimmerPlaceholder(
        shape = shape,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Column (
            modifier = Modifier
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = shape,
                        color = Green2
                    ),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Green2
                ),
                onClick = onClick,
            ) {
                Text(
                    text = text,
                    color = White,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }

            if (isOpen) {
                Spacer(
                    modifier = Modifier
                        .background(color = Green2)
                        .height(dimens.screenPadding)
                        .fillMaxWidth()
                )
            }
        }
    }

    /*
    Column (
        modifier = modifier
    ) {
        ShimmerPlaceholder(
            shape = shape,
            isLoading = isLoading,
            modifier = Modifier
        ) {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        shape = shape,
                        color = Green2
                    ),
                colors = ButtonDefaults.buttonColors().copy(
                    containerColor = Green2
                ),
                onClick = onClick,
            ) {
                Text(
                    text = text,
                    color = White,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }

        if (isOpen) {
            ShimmerPlaceholder(
                isLoading = isLoading,
                modifier = Modifier
            ) {
                Spacer(
                    modifier = Modifier
                        .background(color = Green2)
                        .height(dimens.screenPadding)
                        .fillMaxWidth()
                )
            }
        }
    }
    */
}