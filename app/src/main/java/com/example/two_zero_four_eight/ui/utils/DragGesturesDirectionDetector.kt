package com.example.two_zero_four_eight.ui.utils

import android.util.Log
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

const val DragGesturesTag = "DragGestures"
/**
 * This composable function allows to detect long touching or drag movements and
 * in which direction they are.
 *
 * For this it creates a Box a base wrapper composable and it uses [modifier] as its modifier,
 * to which apply the [Modifier.pointerInput] method with a [PointerInputScope.detectDragGestures]
 * to detect the interactions with the screen.
 *
 * [PointerInputScope.detectDragGestures] receives 4 callback functions.
 *
 * The first one is onDragStart. This is called on the first touch when a drag move occurs
 * and it's used to save the position in which the drag move started.
 *
 * The next two callbacks are onDragEnd (called when the drag move ends by not continuing touching the screen)
 * and onDragCancel (called when the drag move is interrupted for another gesture).
 * For both of this methods if [resetAtTheEnd] is true it would put startPosition and
 * currentDirection to their defaults values and call [onDirectionDetected] with the value
 * [NONE]
 *
 * And the last callback is onDrag. This is called for each position while the drag move
 * is being made. With this callback this composable (only if the currentDirection
 * is different of [NONE]) compare currentPosition with startPosition to see if there's a difference
 * equal or higher to [difference] between the Xs and Ys coordinates to determine in which direction
 * the drag move is being made and returns that using the [onDirectionDetected]
 *
 * **/
@Composable
fun DragGesturesDirectionDetector(
    modifier: Modifier = Modifier,
    resetAtTheEnd: Boolean = true,
    difference: Float = 40f,
    onDirectionDetected: (MovementDirection) -> Unit
) {

    var startPosition by remember { mutableStateOf(Offset(0f, 0f)) }
    var currentDirection = NONE

    Box(modifier = modifier.pointerInput(Unit) {
        detectDragGestures(
            { offset ->
                Log.d(DragGesturesTag, "onDragStart: $currentDirection")
                startPosition = offset
            },
            {
                Log.d(DragGesturesTag, "onDragEnd: $currentDirection")
                if (!resetAtTheEnd) return@detectDragGestures

                startPosition = Offset(0f, 0f)
                currentDirection = NONE
                onDirectionDetected(currentDirection)
            },
            {
                Log.d(DragGesturesTag, "onDragCancel")
                if (!resetAtTheEnd) return@detectDragGestures

                startPosition = Offset(0f, 0f)
                currentDirection = NONE
                onDirectionDetected(currentDirection)
            },
        ) { change, dragAmount ->

            Log.d(DragGesturesTag, "onDrag -> change: $change, dragAmount: $dragAmount")
            if (currentDirection != NONE) return@detectDragGestures

            val currentPosition = change.position

            val newDirection = getNewDirection(currentPosition, startPosition, difference)
            if (newDirection == NONE) return@detectDragGestures

            currentDirection = newDirection
            onDirectionDetected(currentDirection)
        }
    })
}

fun getNewDirection(current: Offset, start: Offset, difference: Float) =
    if (current.x - start.x >= difference) {
        RIGHT
    } else if (start.x - current.x >= difference) {
        LEFT
    } else if (current.y - start.y >= difference) {
        DOWN
    } else if (start.y - current.y >= difference) {
        UP
    } else {
        NONE
    }
