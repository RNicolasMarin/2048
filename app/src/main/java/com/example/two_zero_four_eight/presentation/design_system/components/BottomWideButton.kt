package com.example.two_zero_four_eight.presentation.design_system.components

import android.content.res.Configuration
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import com.example.two_zero_four_eight.presentation.design_system.Dimens
import com.example.two_zero_four_eight.presentation.design_system.dimens

@Composable
fun BottomWideButton(
    @StringRes id: Int,
    goBackFromYouWin : () -> Unit,
    dimens: Dimens = MaterialTheme.dimens,
    configuration: Configuration = LocalConfiguration.current,
    modifier: Modifier = Modifier
) {
    val minWidth = min(configuration.screenWidthDp.dp, configuration.screenHeightDp.dp)

    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center
    ) {
        WideButton(
            text = stringResource(id = id),
            modifier = modifier
                .widthIn(
                    max = minWidth - dimens.screenPadding * 4
                )
                .fillMaxWidth(),
            onClick = goBackFromYouWin
        )
    }
}