package com.example.two_zero_four_eight.presentation.design_system.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.typographies
import com.example.two_zero_four_eight.presentation.design_system.Black
import com.example.two_zero_four_eight.presentation.design_system.Green2
import com.example.two_zero_four_eight.presentation.design_system.White

@Composable
fun WideButton(
    text: String,
    dimens: Dimens = MaterialTheme.dimens,
    textStyle: TextStyle = MaterialTheme.typographies.text24Bold,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimens.cornerRadius),
        border = BorderStroke(dimens.borderStroke, Black),
        contentPadding = PaddingValues(0.dp),
        colors = ButtonDefaults.buttonColors().copy(
            containerColor = Green2
        ),
        modifier = modifier
    ) {
        Text(
            text = text,
            color = White,
            style = textStyle,
            modifier = Modifier.padding(horizontal = dimens.innerHorizontalPadding)
        )
    }
}

@Preview(heightDp = 80, widthDp = 300)
@Composable
private fun WideButtonPreview() {
    TwoZeroFourEightTheme {
        WideButton(
            text = "Start Game",
            onClick = {},
            modifier = Modifier,
        )
    }
}