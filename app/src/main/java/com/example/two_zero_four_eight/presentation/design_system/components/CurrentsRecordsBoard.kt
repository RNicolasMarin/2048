package com.example.two_zero_four_eight.presentation.design_system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.domain.models.CurrentRecordData
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.Size
import com.example.two_zero_four_eight.presentation.design_system.Typographies
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.getMeasureResult
import com.example.two_zero_four_eight.presentation.design_system.heightSize
import com.example.two_zero_four_eight.presentation.design_system.toSize
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.design_system.Green1
import com.example.two_zero_four_eight.presentation.design_system.Green3
import com.example.two_zero_four_eight.presentation.design_system.White
import com.example.two_zero_four_eight.presentation.design_system.shimmerEffect
import kotlin.math.max

@Composable
fun CurrentsRecordsBoard(
    dataNumber: CurrentRecordData,
    dataScore: CurrentRecordData,
    isLoading: Boolean,
    dimens: Dimens = MaterialTheme.dimens,
    typographies: Typographies = MaterialTheme.typographies,
    modifier: Modifier = Modifier
) {
    val measurer = rememberTextMeasurer()

    val nameNumber = stringResource(id = R.string.current_record_number_label)
    val nameScore = stringResource(id = R.string.current_record_score_label)

    val nameStyle = typographies.text16Bold
    val nameNumberResult = measurer.getMeasureResult(nameNumber, nameStyle)
    val nameScoreResult = measurer.getMeasureResult(nameScore, nameStyle)

    val titleStyle = typographies.text14SemiBold
    val titleCurrentResult = measurer.getMeasureResult(stringResource(id = R.string.current_label), titleStyle)
    val titleRecordResult = measurer.getMeasureResult(stringResource(id = R.string.record_label), titleStyle)

    val biggerTitleWidth = max(titleCurrentResult.size.width, titleRecordResult.size.width).toSize()

    val valueStyle = typographies.text16Bold
    val valueResult = measurer.getMeasureResult("9999999", valueStyle)

    val spaceHeight = (dimens.smallPadding / 2).toSize()
    val spaceWidth = (dimens.screenPadding / 2).toSize()
    val padding = (dimens.smallPadding).toSize()

    val biggerNameWidth = max(nameNumberResult.size.width, nameScoreResult.size.width).toSize()

    val biggerSectionWidth = max(biggerTitleWidth.px, valueResult.size.width).toSize()

    val height = padding + titleCurrentResult.heightSize() + spaceHeight + valueResult.heightSize() + padding
    val width = padding + biggerNameWidth + spaceWidth + biggerSectionWidth + spaceWidth + biggerSectionWidth + padding

    val sizes = CurrentAndRecordSizes(
        padding = padding,
        sectionWidth = biggerSectionWidth,
        nameWidth = biggerNameWidth,
        spaceHeight = spaceHeight,
        spaceWidth = spaceWidth,
    )

    Column(
        modifier = modifier
    ) {
        if (isLoading) {
            CurrentAndRecordShimmer(height = height.dp, width = width.dp)

            Spacer(modifier = Modifier.height(dimens.screenPadding))

            CurrentAndRecordShimmer(height = height.dp, width = width.dp)

        } else {
            CurrentAndRecord(
                data = dataNumber,
                name = nameNumber,
                sizes = sizes,
                titleStyle = titleStyle,
                valueStyle = valueStyle,
            )

            Spacer(modifier = Modifier.height(dimens.screenPadding))

            CurrentAndRecord(
                data = dataScore,
                name = nameScore,
                sizes = sizes,
                titleStyle = titleStyle,
                valueStyle = valueStyle,
            )
        }
    }

}

@Composable
fun CurrentAndRecordShimmer(
    height: Dp,
    width: Dp,
    dimens: Dimens = MaterialTheme.dimens
) {
    Box(
        modifier = Modifier
            .height(height)
            .width(width)
            .shimmerEffect(RoundedCornerShape(dimens.cornerRadius))
    )
}

@Composable
fun CurrentAndRecord(
    data: CurrentRecordData,
    name: String,
    sizes: CurrentAndRecordSizes,
    dimens: Dimens = MaterialTheme.dimens,
    titleStyle: TextStyle,
    valueStyle: TextStyle,
    typographies: Typographies = MaterialTheme.typographies,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .background(
                color = Green3,
                shape = RoundedCornerShape(dimens.cornerRadius)
            )
            .padding(sizes.padding.dp)
    ) {
        Text(
            text = name,
            color = Green1,
            style = typographies.text16Bold,
            modifier = Modifier.width(sizes.nameWidth.dp)
        )

        Spacer(modifier = Modifier.width(sizes.spaceWidth.dp))

        CurrentAndRecordSection(
            title = stringResource(id = R.string.current_label),
            value = data.currentValue,
            titleStyle = titleStyle,
            valueStyle = valueStyle,
            spaceHeight = sizes.spaceHeight.dp,
            modifier = Modifier.width(sizes.sectionWidth.dp)
        )

        Spacer(modifier = Modifier.width(sizes.spaceWidth.dp))

        CurrentAndRecordSection(
            title = stringResource(id = R.string.record_label),
            value = data.recordValue,
            titleStyle = titleStyle,
            valueStyle = valueStyle,
            spaceHeight = sizes.spaceHeight.dp,
            modifier = Modifier.width(sizes.sectionWidth.dp)
        )
    }
}

@Composable
fun CurrentAndRecordSection(
    title: String,
    value: Int,
    titleStyle: TextStyle,
    valueStyle: TextStyle,
    spaceHeight: Dp,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = title,
            color = Green1,
            style = titleStyle
        )

        Spacer(modifier = Modifier.height(spaceHeight))

        Text(
            text = value.toString(),
            color = White,
            style = valueStyle
        )
    }
}

data class CurrentAndRecordSizes(
    val padding: Size,
    val sectionWidth: Size,
    val nameWidth: Size,
    val spaceHeight: Size,
    val spaceWidth: Size,
)
