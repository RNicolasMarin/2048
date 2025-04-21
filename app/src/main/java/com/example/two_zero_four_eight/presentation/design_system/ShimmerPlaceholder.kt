package com.example.two_zero_four_eight.presentation.design_system

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalDensity

@Composable
fun ShimmerPlaceholder(
    shape: Shape = RectangleShape,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    var size by remember { mutableStateOf<Size?>(null) }

    SubcomposeLayout { constraints ->
        // Subcompose the content
        val placeables = subcompose("content", content).map { it.measure(constraints) }

        // Measure the height of the first placeable
        val heightPx = if (placeables.isNotEmpty()) placeables[0].height else 0
        val widthPx = if (placeables.isNotEmpty()) placeables[0].width else 0

        size = Size(
            height = heightPx.toFloat(),
            width = widthPx.toFloat()
        )

        // Return zero-sized layout since we are not drawing anything
        layout(0, 0) {}
    }

    when {
        isLoading && size == null -> { }
        isLoading && size != null -> {
            Box(
                modifier = modifier
                    .size(
                        width = with(LocalDensity.current) { size!!.width.toDp() },
                        height = with(LocalDensity.current) { size!!.height.toDp() }
                    )
                    .shimmerEffect(shape)
            )
        }
        else -> {
            Box(
                modifier = modifier
            ) {
                content()
            }
        }
    }

    /*
    var contentSize by remember { mutableStateOf<IntSize?>(null) }

    //null & loading -> try to draw -> onSizeChanged -> set content size
    //non null & loading -> draw shimmer effect on that size
    //non null & !loading -> draw content on that size (or ignoring size)

    when {
        isLoading && contentSize == null -> {
            Box(
                modifier = modifier.onSizeChanged { contentSize = it }
            ) {
                content()
            }
        }
        isLoading && contentSize != null -> {
            Box(
                modifier = modifier
                    .size(
                        width = with(LocalDensity.current) { contentSize!!.width.toDp() },
                        height = with(LocalDensity.current) { contentSize!!.height.toDp() }
                    )
                    .shimmerEffect(shape)
            )
        }
        else -> {
            Box(
                modifier = modifier
            ) {
                content()
            }
        }
    }
    */
}