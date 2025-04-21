package com.example.two_zero_four_eight.presentation.design_system.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.design_system.Green2
import com.example.two_zero_four_eight.presentation.design_system.White

@Composable
fun LabelText(
    text: String,
    dimens: Dimens = MaterialTheme.dimens,
    textStyle: TextStyle = MaterialTheme.typographies.text24Bold,
    modifier: Modifier = Modifier
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .background(
                shape = RoundedCornerShape(dimens.cornerRadius),
                color = Green2
            )
    ) {
        Text(
            text = text,
            color = White,
            style = textStyle,
            modifier = Modifier
                .padding(horizontal = dimens.innerHorizontalPadding2)
        )
    }
}

@Preview(heightDp = 80, widthDp = 300)
@Composable
private fun LabelTextPreview() {
    TwoZeroFourEightTheme {
        LabelText(
            text = "3X3",
            modifier = Modifier,
        )
    }
}