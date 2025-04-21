package com.example.two_zero_four_eight.presentation.design_system.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.presentation.design_system.dimens
import com.example.two_zero_four_eight.presentation.design_system.Black
import com.example.two_zero_four_eight.presentation.design_system.Green1
import com.example.two_zero_four_eight.presentation.design_system.Green3
import com.example.two_zero_four_eight.presentation.design_system.White

@Composable
fun IconButton(
    contentDescription: String,
    @DrawableRes iconResource: Int,
    invert: Boolean = false,
    rotate: Float = 0f,
    onClick: () -> Unit,
    dimens: Dimens = MaterialTheme.dimens,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(dimens.cornerRadius),
        border = BorderStroke(dimens.borderStroke, Black),
        contentPadding = PaddingValues(dimens.innerHorizontalPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green3,
            contentColor = Black,
            disabledContainerColor = Green1,
            disabledContentColor = White
        ),
        modifier = modifier
            .size(dimens.iconButtonSize)
    ) {
        Icon(
            painter = painterResource(iconResource),
            contentDescription = contentDescription,
            tint = White,
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer(scaleX = if (invert) -1f else 1f)
                .rotate(rotate)
        )
    }
}

@Composable
fun IconButtonPrevious(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        iconResource = R.drawable.undo_move_2,
        rotate = -45f,
        contentDescription = stringResource(id = R.string.previous_button_description),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun IconButtonNext(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        iconResource = R.drawable.undo_move_2,
        invert = true,
        rotate = -45f,
        contentDescription = stringResource(id = R.string.next_button_description),
        onClick = onClick,
        modifier = modifier
    )
}

@Composable
fun IconButtonStartAgain(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        iconResource = R.drawable.start_again,
        contentDescription = stringResource(id = R.string.start_again_button_description),
        onClick = onClick,
        modifier = modifier
    )
}

@Preview(heightDp = 45, widthDp = 45)
@Composable
private fun IconButtonPreviousPreview() {
    TwoZeroFourEightTheme {
        IconButtonPrevious(
            onClick = {},
            modifier = Modifier
        )
    }
}

@Preview(heightDp = 45, widthDp = 45)
@Composable
private fun IconButtonNextPreview() {
    TwoZeroFourEightTheme {
        IconButtonNext(
            onClick = {},
            modifier = Modifier
        )
    }
}

@Preview(heightDp = 45, widthDp = 45)
@Composable
private fun IconButtonStartAgainPreview() {
    TwoZeroFourEightTheme {
        IconButtonStartAgain(
            onClick = {},
            modifier = Modifier
        )
    }
}