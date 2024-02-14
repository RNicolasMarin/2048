package com.example.two_zero_four_eight.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.two_zero_four_eight.R
import com.example.two_zero_four_eight.ui.screens.corners
import com.example.two_zero_four_eight.ui.theme.Green2
import com.example.two_zero_four_eight.ui.theme.White

val AppNameDefaultHeight = 60.dp
@Composable
fun AppName(
    modifier: Modifier = Modifier,
    height: Dp = AppNameDefaultHeight,
    verticalPadding: Dp = 10.dp
) {
    Box(
        modifier = modifier
            .height(height)
            .background(
                shape = RoundedCornerShape(corners),
                color = Green2
            )
    ) {
        Text(
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            text = stringResource(id = R.string.app_name),
            modifier = Modifier.padding(vertical = verticalPadding, horizontal = 20.dp),
            color = White,
        )
    }
}


