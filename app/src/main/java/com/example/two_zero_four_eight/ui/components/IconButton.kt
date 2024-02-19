package com.example.two_zero_four_eight.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.ui.screens.corners
import com.example.two_zero_four_eight.ui.theme.Black
import com.example.two_zero_four_eight.ui.theme.Green1
import com.example.two_zero_four_eight.ui.theme.Green3
import com.example.two_zero_four_eight.ui.theme.White

val IconButtonHeight = 70.dp

@Composable
fun IconButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(IconButtonHeight)
            .width(IconButtonHeight)
            .border(
                shape = RoundedCornerShape(corners),
                color = Black,
                width = 1.dp
            ),
        shape = RoundedCornerShape(corners),
        colors = ButtonDefaults.buttonColors(
            containerColor = Green3,
            contentColor = Black,
            disabledContainerColor = Green1,
            disabledContentColor = White
        )
    ) {
        Image(
            painter = painterResource(id = R.drawable.re_start),
            contentDescription = null,
            modifier = Modifier
                .size(150.dp),
        )
    }
}