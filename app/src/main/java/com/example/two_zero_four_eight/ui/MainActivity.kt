package com.example.two_zero_four_eight.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.two_zero_four_eight.ui.theme.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoZeroFourEightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var currentDirection by remember { mutableStateOf(NONE) }

                    DragGesturesDirectionDetector(
                        modifier = Modifier.fillMaxSize().background(Color.Gray),
                        onDirectionDetected = {
                            currentDirection = it
                        }
                    ) {
                        Box(modifier = Modifier
                            .fillMaxSize()) {
                            Text(
                                text = "Swipe direction: $currentDirection",
                                modifier = Modifier.align(Alignment.BottomCenter)
                            )
                        }
                    }
                }
            }
        }
    }
}

