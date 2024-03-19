package com.example.two_zero_four_eight.ui.components.boardgame

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.data.model.CurrentRecordData
import com.example.two_zero_four_eight.ui.components.AppName
import com.example.two_zero_four_eight.ui.components.CurrentRecord
import com.example.two_zero_four_eight.ui.theme.dimens

/**
 * It renders the top part of the screen on Portrait mode or the top of the left side of the screen on Landscape mode.
 * **/
@Composable
fun BoardGameTop(
    singlePartHeight: Dp,
    dataNumber: CurrentRecordData,
    dataScore: CurrentRecordData,
    isLoading: Boolean,
    modifier: Modifier = Modifier
) {
    val outerPadding = MaterialTheme.dimens.outerPadding
    Column(
        modifier = modifier.fillMaxWidth()
            .padding(
                start = outerPadding,
                end = outerPadding,
                top = outerPadding
            ),
    ) {
        AppName(
            modifier = Modifier.height(singlePartHeight - outerPadding)
        )
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.height(outerPadding))

            CurrentRecord(
                data = dataNumber,
                name = stringResource(id = R.string.current_record_number_label),
                isLoading = isLoading,
                modifier = Modifier
                    .widthIn(
                        min = MaterialTheme.dimens.currentRecordWidthMin,
                        max = MaterialTheme.dimens.currentRecordWidthMax
                    )
                    .height(singlePartHeight - outerPadding)
            )

            Spacer(modifier = Modifier.height(outerPadding / 2))

            CurrentRecord(
                data = dataScore,
                name = stringResource(id = R.string.current_record_score_label),
                isLoading = isLoading,
                modifier = Modifier
                    .widthIn(
                        min = MaterialTheme.dimens.currentRecordWidthMin,
                        max = MaterialTheme.dimens.currentRecordWidthMax
                    )
                    .height(singlePartHeight - outerPadding),
            )
        }
    }
}