package com.example.two_zero_four_eight.ui.theme

import android.app.Activity
import android.content.res.Configuration
import android.content.res.Configuration.*
import android.os.Build
import android.util.Log
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass.Companion
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Compact
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass.Companion.Medium
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.two_zero_four_eight.ui.MainActivity

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
@Composable
fun TwoZeroFourEightTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    activity: Activity = LocalContext.current as MainActivity,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    val window = calculateWindowSizeClass(activity = activity)
    val config = LocalConfiguration.current

    val isBothCompact = window.heightSizeClass == Companion.Compact && window.widthSizeClass == Compact
    val isPortrait = getIsPortrait(config, isBothCompact)

    val appDimes = getAppDimens(window, config, isPortrait)
    val appTypography = getAppTypography(window, config, isPortrait)

    ProvideScreenConfig(appDimes, isPortrait, isBothCompact) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = appTypography,
            content = content
        )
    }
}

fun getIsPortrait(config: Configuration, isBothCompact: Boolean): Boolean {
    val isPortrait = config.orientation == ORIENTATION_PORTRAIT
    if (isBothCompact) {
        //to show correctly the orientation on split screens
        return !isPortrait
    }
    return isPortrait
}

fun getAppDimens(window: WindowSizeClass, config: Configuration, isPortrait: Boolean): Dimens {
    Log.d("getAppDimens", "config.screenWidthDp: ${config.screenWidthDp}, config.screenHeightDp: ${config.screenHeightDp}")
    return if (isPortrait) {
        when (window.widthSizeClass) {
            Compact -> {

                val width = config.screenWidthDp
                when {
                    width <= 360 -> {
                        Log.d("getAppDimens", "Dimen: CompactSmallDimens")
                        CompactSmallDimens
                    }
                    width < 480 -> {// 599 -> {//361..480
                        Log.d("getAppDimens", "Dimen: CompactMediumDimens")
                        CompactMediumDimens
                    }
                    else -> {//481..599
                        Log.d("getAppDimens", "Dimen: CompactDimens")
                        CompactDimens
                    }
                }
            }

            Medium -> {
                Log.d("getAppDimens", "Dimen: MediumDimens")
                MediumDimens
            }

            else -> {
                Log.d("getAppDimens", "Dimen: ExpandedDimens")
                ExpandedDimens
            }
        }
    } else {
        when (window.heightSizeClass) {
            WindowHeightSizeClass.Compact -> {

                val height = config.screenHeightDp
                when {
                    height <= 360 -> {
                        Log.d("getAppDimens", "Dimen: CompactSmallDimens")
                        CompactSmallDimens
                    }
                    height < 480 -> {
                        Log.d("getAppDimens", "Dimen: CompactMediumDimens")
                        CompactMediumDimens
                    }
                    else -> {
                        Log.d("getAppDimens", "Dimen: CompactDimens")
                        CompactDimens
                    }
                }
            }

            WindowHeightSizeClass.Medium -> {
                Log.d("getAppDimens", "Dimen: MediumDimens")
                MediumDimens
            }

            else -> {
                Log.d("getAppDimens", "Dimen: ExpandedDimens")
                ExpandedDimens
            }
        }
    }
}

fun getAppTypography(window: WindowSizeClass, config: Configuration, isPortrait: Boolean): Typography {
    return if (isPortrait) {
        when (window.widthSizeClass) {
            Compact -> {
                val width = config.screenWidthDp
                when {
                    width <= 360 -> CompactSmallTypography
                    width < 480 -> CompactMediumTypography
                    else -> CompactTypography
                }
            }

            Medium -> {
                MediumTypography
            }

            else -> {
                ExpandedTypography
            }
        }
    } else {
        when (window.heightSizeClass) {
            WindowHeightSizeClass.Compact -> {
                val height = config.screenHeightDp
                when {
                    height <= 360 -> CompactSmallTypography
                    height < 480 -> CompactMediumTypography
                    else -> CompactTypography
                }
            }

            WindowHeightSizeClass.Medium -> {
                MediumTypography
            }

            else -> {
                ExpandedTypography
            }
        }
    }

}
