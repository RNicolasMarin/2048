package com.example.two_zero_four_eight.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf

@Composable
fun ProvideScreenConfig(
    dimens: Dimens,
    isPortrait: Boolean,
    isBothCompact: Boolean,
    content: @Composable () -> Unit
) {
    val dimensSet = remember { dimens }
    val isPortraitSet = remember { isPortrait }
    val isBothCompactSet = remember { isBothCompact }
    CompositionLocalProvider(
        LocalAppDimens provides dimensSet,
        LocalAppIsPortrait provides isPortraitSet,
        LocalAppIsBothCompact provides isBothCompactSet,
        content = content
    )
}

private val LocalAppDimens = staticCompositionLocalOf {
    CompactDimens
}
private val LocalAppIsPortrait = staticCompositionLocalOf {
    true
}
private val LocalAppIsBothCompact = staticCompositionLocalOf {
    false
}

val MaterialTheme.dimens: Dimens
    @Composable
    get() = LocalAppDimens.current

val MaterialTheme.isPortrait: Boolean
    @Composable
    get() = LocalAppIsPortrait.current

val MaterialTheme.isBothCompact: Boolean
    @Composable
    get() = LocalAppIsBothCompact.current