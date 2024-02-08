package com.example.two_zero_four_eight.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.two_zero_four_eight.ui.theme.TwoZeroFourEightTheme
import com.example.two_zero_four_eight.ui.utils.DragGesturesDirectionDetector
import com.example.two_zero_four_eight.ui.utils.MovementDirection
import com.example.two_zero_four_eight.ui.utils.MovementDirection.*
import com.example.two_zero_four_eight.use_cases.DEFAULT_VALUE

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TwoZeroFourEightTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TwoZeroFourEightApp()
                }
            }
        }
    }
}

@Composable
fun TwoZeroFourEightApp() {
    var currentDirection by remember { mutableStateOf(NONE) }
    val viewModel: TwoZeroFourEightViewModel = viewModel()
    val uiState by viewModel.gameState.collectAsState()

    Box {
        Column(
            modifier = Modifier.fillMaxSize().background(Color.Gray),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Table(uiState.board, currentDirection)
            if (uiState.isGameOver) {
                Text(
                    fontSize = 20.sp,
                    text = "Game Over"
                )
            }
        }
        DragGesturesDirectionDetector(
            modifier = Modifier
                .fillMaxSize(),
            onDirectionDetected = {
                currentDirection = it
                viewModel.moveNumbers(it)
            }
        )
    }
}

/**
 * [currentDirection] allows to refresh the table. Check hot to fix this bug.
 * **/
@Composable
fun Table(tableData: List<List<Int>>, currentDirection: MovementDirection) {
    LazyColumn(
        modifier = Modifier.background(Color.Blue)
    ) {
        items(
            items = tableData,
        ) { row ->
            TableRow(rowData = row)
        }
    }
}

@Composable
fun TableRow(rowData: List<Int>) {
    LazyRow {
        items(
            items = rowData,
        ) { cellData ->
            TableCell(cellData)
        }
    }
}

@Composable
fun TableCell(cellData: Int) {
    Box(
        modifier = Modifier
            .width(100.dp)
            .height(100.dp)
            .background(color = Color.Cyan)
            .border(3.dp, Color.Gray)
            .padding(8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            fontSize = 20.sp,
            text = if (cellData == DEFAULT_VALUE) "" else cellData.toString()
        )
    }
}

