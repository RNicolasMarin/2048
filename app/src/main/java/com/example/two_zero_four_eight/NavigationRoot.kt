package com.example.two_zero_four_eight

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import com.example.two_zero_four_eight.domain.models.CurrentRecordData
import com.example.two_zero_four_eight.presentation.ui.game.GameAction.OnStartGame
import com.example.two_zero_four_eight.presentation.ui.game.GameScreenRoot
import com.example.two_zero_four_eight.presentation.ui.game.GameStatus.PLAYING
import com.example.two_zero_four_eight.presentation.ui.game.GameViewModel
import com.example.two_zero_four_eight.presentation.ui.menu.MenuScreenRoot
import com.example.two_zero_four_eight.presentation.ui.records.RecordsScreenRoot
import com.example.two_zero_four_eight.presentation.ui.win_or_lose.GameOverScreen
import com.example.two_zero_four_eight.presentation.ui.win_or_lose.YouWinScreen
import com.example.two_zero_four_eight.presentation_old.ui.Screen.Game
import com.example.two_zero_four_eight.presentation_old.ui.Screen.GameOver
import com.example.two_zero_four_eight.presentation_old.ui.Screen.Menu
import com.example.two_zero_four_eight.presentation_old.ui.Screen.Records
import com.example.two_zero_four_eight.presentation_old.ui.Screen.YouWin

@Composable
fun NavigationRoot(
    navController: NavHostController
) {
    val viewModel: GameViewModel = viewModel()

    val goBackFromGameOver : () -> Unit = {
        navController.popBackStack()
        viewModel.onAction(OnStartGame())
    }

    val goBackFromYouWin : () -> Unit = {
        navController.popBackStack()
        viewModel.updateCurrentGameStatus(PLAYING)
    }

    NavHost(
        navController = navController,
        startDestination = Menu
    ) {
        composable<Menu> {
            MenuScreenRoot(
                onStartGame = { size ->
                    viewModel.onAction(OnStartGame(
                        size = size,
                        deletePreviousState = true
                    ))
                    navController.navigate(Game)
                },
                onRecords = {
                    navController.navigate(Records)
                }
            )
        }
        composable<Records> {
            RecordsScreenRoot(
                onBackToMenu = {
                    navController.popBackStack()
                }
            )
        }
        composable<Game> {
            GameScreenRoot(
                viewModel = viewModel,
                onGameOver = { numbers, scores, numberToWin ->
                    navController.navigate(
                        GameOver(
                            currentScore = scores.currentValue,
                            recordScore = scores.recordValue,
                            currentNumber = numbers.currentValue,
                            recordNumber = numbers.recordValue,
                            numberToWin = numberToWin
                        )
                    )
                },
                onYouWin = { numbers, scores ->
                    navController.navigate(
                        YouWin(
                            currentScore = scores.currentValue,
                            recordScore = scores.recordValue,
                            currentNumber = numbers.currentValue,
                            recordNumber = numbers.recordValue,
                        )
                    )
                }
            )
        }
        composable<GameOver> {
            val args = it.toRoute<GameOver>()
            GameOverScreen(
                numberToShow = args.numberToWin,
                numberCurrentRecord = CurrentRecordData(args.currentNumber, args.recordNumber),
                scoreCurrentRecord = CurrentRecordData(args.currentScore, args.recordScore),
                onBackButtonPressed = goBackFromGameOver
            )
        }
        composable<YouWin> {
            val args = it.toRoute<YouWin>()
            YouWinScreen(
                numberToShow = args.currentNumber,
                numberCurrentRecord = CurrentRecordData(args.currentNumber, args.recordNumber),
                scoreCurrentRecord = CurrentRecordData(args.currentScore, args.recordScore),
                onBackButtonPressed = goBackFromYouWin
            )
        }
    }
}