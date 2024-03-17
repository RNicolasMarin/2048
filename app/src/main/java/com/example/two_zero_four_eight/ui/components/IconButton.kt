package com.example.two_zero_four_eight.ui.components

import androidx.compose.foundation.border
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.Green1
import com.example.two_zero_four_eight.ui.theme.Green3
import com.example.two_zero_four_eight.ui.theme.White
import com.example.two_zero_four_eight.ui.theme.dimens

@Composable
fun IconButton(
    onClick: () -> Unit,
    size: Dp,
    contentDescription: String,
    color: Color,
    modifier: Modifier = Modifier,
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .size(size)
            .border(
                shape = RoundedCornerShape(MaterialTheme.dimens.corners),
                color = Black,
                width = 1.dp
            ),
        shape = RoundedCornerShape(MaterialTheme.dimens.corners),
        contentPadding = PaddingValues(MaterialTheme.dimens.innerPadding),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green3,
            contentColor = Black,
            disabledContainerColor = Green1,
            disabledContentColor = White
        )
    ) {
        Icon(
            painter = painterResource(R.drawable.start_again),
            contentDescription = contentDescription,
            tint = color,//White,
            modifier = Modifier.fillMaxSize()
        )
    }
}