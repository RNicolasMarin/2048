package com.example.two_zero_four_eight.presentation.ui.records

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.domain.models.Record
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.MultiDevicePreview
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.PORTRAIT
import com.example.two_zero_four_eight.presentation.design_system.ScreenContentOrientation.SIMPLIFIED
import com.example.two_zero_four_eight.presentation.design_system.ShimmerPlaceholder
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.Typographies
import com.example.two_zero_four_eight.presentation.design_system.components.BottomWideButton
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.screenContentOrientation
import com.example.two_zero_four_eight.presentation.design_system.toPx
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.ui.records.RecordsAction.OnBackToMenu
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.LOADING
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.NO_FILTERED_RECORDS
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.NO_RECORDS
import com.example.two_zero_four_eight.presentation.ui.records.RecordsStatus.RECORDS
import com.example.two_zero_four_eight.presentation.ui.records.components.RecordsButtons
import com.example.two_zero_four_eight.presentation_old.design_system.Black
import com.example.two_zero_four_eight.presentation_old.design_system.Green3
import com.example.two_zero_four_eight.presentation_old.design_system.Green5
import com.example.two_zero_four_eight.presentation_old.design_system.Green7

@Composable
fun RecordsScreenRoot(
    onBackToMenu: () -> Unit,
    viewModel: RecordsViewModel = hiltViewModel()
) {
    RecordsScreen(
        state = viewModel.state,
        onAction = { action ->
            when (action) {
                is OnBackToMenu -> onBackToMenu()
                else -> Unit
            }
            viewModel.onAction(action)
        },
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
fun RecordsScreen(
    state: RecordsState,
    dimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier = Modifier,
    onAction: (RecordsAction) -> Unit
) {

    val bottom = getBottomPadding(state.status)
    val top = getTopPadding(state.status)
    val startEnd = getStartEndPadding(state.status)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Green7)
            .padding(
                top = top,
                bottom = bottom,
                start = startEnd,
                end = startEnd
            )
    ) {
        if (state.status == NO_FILTERED_RECORDS) {
            RecordsScreenNoRecordsMessage(
                modifier = Modifier.fillMaxSize()
            )
        }
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (state.status == NO_RECORDS) {
                RecordsScreenNoRecordsMessage()

                BottomWideButton(
                    id = R.string.go_to_menu,
                    goBackFromYouWin = { onAction(OnBackToMenu) }
                )
            } else {
                RecordsButtons(
                    state = state,
                    onAction = onAction,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(modifier = Modifier.height(dimens.screenPadding * 2))

                if (state.status == LOADING || state.status == RECORDS) {
                    var itemCount by remember { mutableIntStateOf(0) }

                    val itemContent: @Composable () -> Unit = {
                        RecordCard(isLoading = true, record = Record(0, 0, 0))
                    }

                    BoxWithConstraints {
                        val heightPx = maxHeight.toPx()

                        SubcomposeLayout { constraints ->
                            // Subcompose the content
                            val placeables = subcompose("content", itemContent).map { it.measure(constraints) }

                            // Measure the height of the first placeable
                            val itemHeightPx = if (placeables.isNotEmpty()) placeables[0].height.toFloat() else 0f

                            itemCount = (heightPx / itemHeightPx).toInt()

                            // Return zero-sized layout since we are not drawing anything
                            layout(0, 0) {}
                        }

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(dimens.screenPadding),
                            modifier = Modifier.fillMaxSize()
                        ) {
                            when (state.status) {
                                LOADING -> {
                                    items(itemCount) {
                                        itemContent()
                                    }
                                }
                                RECORDS -> {
                                    items(state.records.size) {index ->
                                        RecordCard(
                                            isLoading = false,
                                            record = state.records[index]
                                        )
                                    }
                                }
                                else -> Unit
                            }
                        }
                    }
                }
            }
        }
    }

}

@Composable
fun RecordsScreenNoRecordsMessage(
    dimens: Dimens = MaterialTheme.dimens,
    typographies: Typographies = MaterialTheme.typographies,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = dimens.screenPadding
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.no_records_title),
            color = Black,
            style = typographies.text40Bold,
            textAlign = TextAlign.Center,
        )

        Spacer(modifier = Modifier.height(dimens.screenPadding2))

        Text(
            text = stringResource(id = R.string.no_records_message),
            color = Black,
            style = typographies.text24Regular,
            textAlign = TextAlign.Center,
        )
    }
}


@Composable
fun RecordCard(
    isLoading : Boolean,
    record: Record,
    dimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(dimens.cornerRadius)
    ShimmerPlaceholder(
        shape = shape,
        isLoading = isLoading,
        modifier = modifier
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Green5,
                    shape = shape
                )
                .padding(
                    vertical = dimens.innerHorizontalPadding,
                    horizontal = dimens.innerHorizontalPadding * 2
                )
        ) {
            RecordCardColumnSection(
                labelId = R.string.score,
                value = record.score
            )

            Spacer(modifier = Modifier.width(dimens.screenPadding))

            RecordCardColumnSection(
                labelId = R.string.number,
                value = record.number
            )

            Spacer(modifier = Modifier.width(dimens.screenPadding))

            RecordCardColumnSection(
                labelId = R.string.size,
                value = record.boardSize
            )
        }
    }
}

@Composable
fun RecordCardColumnSection(
    @StringRes labelId: Int,
    value: Int,
    dimens: Dimens = MaterialTheme.dimens,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        RecordCardLabel(
            text = stringResource(id = labelId)
        )

        Spacer(modifier = Modifier.height(dimens.screenPadding))

        RecordCardValue(
            text = value.toString()
        )
    }
}

@Composable
fun RecordCardLabel(
    text: String,
    typographies: Typographies = MaterialTheme.typographies,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Green3,
        style = typographies.text16Bold,
        modifier = modifier
    )
}

@Composable
fun RecordCardValue(
    text: String,
    typographies: Typographies = MaterialTheme.typographies,
    modifier: Modifier = Modifier
) {
    Text(
        text = text,
        color = Black,
        style = typographies.text16Bold,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Composable
fun getBottomPadding(
    status: RecordsStatus,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    dimens: Dimens = MaterialTheme.dimens,
): Dp {
    return when {
        screenContentOrientation == PORTRAIT && status == NO_RECORDS -> dimens.screenPaddingBottom1
        else -> dimens.screenPadding
    }
}

@Composable
fun getTopPadding(
    status: RecordsStatus,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    dimens: Dimens = MaterialTheme.dimens,
): Dp {
    return when {
        screenContentOrientation == PORTRAIT && status == NO_RECORDS -> dimens.screenPaddingTop1
        else -> dimens.screenPadding
    }
}

@Composable
fun getStartEndPadding(
    status: RecordsStatus,
    screenContentOrientation: ScreenContentOrientation = MaterialTheme.screenContentOrientation,
    dimens: Dimens = MaterialTheme.dimens,
): Dp {
    return when {
        screenContentOrientation == SIMPLIFIED && status == NO_RECORDS -> dimens.screenPadding * 2
        else -> dimens.screenPadding
    }
}

@MultiDevicePreview
@Composable
private fun RecordsScreenLoadingPreview() {
    TwoZeroFourEightTheme {
        RecordsScreen(
            state = RecordsState(
                status = LOADING
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun RecordsScreenNoRecordsPreview() {
    TwoZeroFourEightTheme {
        RecordsScreen(
            state = RecordsState(
                status = NO_RECORDS
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun RecordsScreenNoFilteredRecordsPreview() {
    TwoZeroFourEightTheme {
        RecordsScreen(
            state = RecordsState(
                status = NO_FILTERED_RECORDS
            ),
            onAction = {}
        )
    }
}

@MultiDevicePreview
@Composable
private fun RecordsScreenRecordsPreview() {
    TwoZeroFourEightTheme {
        RecordsScreen(
            state = RecordsState(
                status = RECORDS,
                records = (1..5).map { Record(
                    score = 16000,
                    number = 512,
                    boardSize = 3
                ) }
            ),
            onAction = {}
        )
    }
}