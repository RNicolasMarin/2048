
# 2048

An Android Compose application that replicates the [2048](https://play.google.com/store/apps/details?id=com.androbaby.game2048) game. This game involves moving numbers on the screen to combine consecutive equal numbers.

![Logo](https://github.com/RNicolasMarin/2048/assets/69260571/5c5398ad-d731-4c0c-b995-4b509e36e726)

## Features

- Creating a new Board Game
- Making movements
- Displaying results (You Win/Game Over)
- Showing Current/Record Number and Score
- Undoing a movement
- Supporting different screen sizes
- Using an adaptive app icon

**Creating a new Board Game**:
When the app starts or the restart button is pressed:
- The app creates and displays a BoardGame on the screen. Cells on the board are initialized either as empty or containing values of 2 or 4.
- The app loads the record number and score from the database and displays them on the screen.
- During these processes, a Shimmer Effect is displayed on the BoardGame, as well as on the score and number boards.

These actions are performed by the **CreateBoardGameUseCase** class.

[video 2.webm](https://github.com/RNicolasMarin/2048/assets/69260571/1b6aef1e-bdce-4f04-a912-404a6ffb97f3)


**Making movements**:
To make a game movement, you need to touch the screen and swipe in one of the following directions: LEFT, RIGHT, UP, or DOWN. This action is detected by the **DragGesturesDirectionDetector** class, which then passes the direction to the viewmodel. The viewmodel utilizes the **MoveNumbersUseCase** class to execute the movement. The movement process involves the following steps/use cases:

- **CombineAndMoveNumbersUseCase**: This use case moves the numbers within the rows or columns, depending on the direction of the swipe, and combines adjacent numbers that are identical. It also increments the score for each combination of numbers.
- **AddNumberToBoardGameUseCase**: If there are empty cells on the board, this use case adds a new number to a random empty position.
- **IsTherePossibleMovesUseCase**: This use case checks if there are any empty cells or if there are any possible number combinations remaining. If so, the game continues; otherwise, it triggers a *GAME_OVER* status.
- **HasWonTheGameUseCase**: If the game status is *PLAYING*, this use case checks if any cell contains the *numberToWin*. If so, it changes the status to *YOU_WIN* and doubles the *numberToWin* value.
- **UpdateCurrentRecordsUseCase**: This use case updates the current and record numbers in memory. If the game is over and a new record for the number or score has been achieved, it is saved in the database.

[video 3.webm](https://github.com/RNicolasMarin/2048/assets/69260571/50627c55-9261-45fa-9bf3-f312740b6854)

**Displaying results (You Win/Game Over)**:
There are 2 cases in which the app shows a message depending on each situation:
- **YOU_WIN**: when the current number reaches the *numberToWin*, it displays the message "You win" until you make another move. Clarification: In the actual app, *numberToWin* would be initialized to 2048, but for simplification purposes, it is set to 16 here.

[video 4 1.webm](https://github.com/RNicolasMarin/2048/assets/69260571/c3381b50-d558-4f4b-8abd-9abe1dbdcf60)

- **GAME_OVER**: when there are no moves left to make, the message "Game over" appears.

[video 4 2.webm](https://github.com/RNicolasMarin/2048/assets/69260571/db9cac9b-d2c2-4612-92a3-fcfba3542168)

**Showing Current/Record Number and Score**:
When the BoardGame is created, it shows the current number and scores on 0, and retrieves the record number and score with the best values from the database (the database saves a table with records that includes the number and score of a match that achieved the best value at the moment).
On each move, the app checks if there's a higher number to replace the current number or if the current number surpasses the record number to replace it. Additionally, for each combination of numbers, it increments the current score, and if the current score is higher than the record score, it replaces it.

[video 5.webm](https://github.com/RNicolasMarin/2048/assets/69260571/8d78dedd-eca0-4746-b7f7-968b814c2d78)

**Undoing a movement**:
The app handles the game state with the *GameState* class. This class contains two instances of the *SingleGameState* class: one for the current state of the game and the other for the previous state, which starts as null. With every move, the current state is copied to the previousState property, and then the changes from the movement are applied to the current state.
When the Undo Move button (single arrow button) is pressed, it performs the opposite action. The value for the previous state is assigned to the current state, replacing what is currently shown on the screen, and the previousState is then set to null. This allows for only one undo action.

[video 6.webm](https://github.com/RNicolasMarin/2048/assets/69260571/1c9a5e89-3234-42e5-b515-a0a513a6ff35)

**Supporting different screen sizes**: 
The app displays different designs for *Portrait*, *Landscape* and *Split-screen* modes. It also utilizes the *calculateWindowSizeClass* method along with the *WindowSizeClass* to determine the dimensions and typography needed for the specific window size.

[video 7 1.webm](https://github.com/RNicolasMarin/2048/assets/69260571/66ebe197-860a-4087-ba6d-c95fa0902a49)

[video 7 2.webm](https://github.com/RNicolasMarin/2048/assets/69260571/dc2eb0b1-d1e0-4e3b-a2da-32eb2d200135)


**Using an adaptive app icon**: 
The app allows users to use either the color chosen by the user or from the wallpaper to determine the icon's color on the launcher.

<p float="left">
  <img src="https://github.com/RNicolasMarin/2048/assets/69260571/d4872af4-7b8f-4afb-9133-292ac6cd2824" width="350" alt="Screenshot"/>

  <img src="https://github.com/RNicolasMarin/2048/assets/69260571/127735cb-f67d-46c3-956a-c74e7e805b20" width="350" alt="Screenshot"/>
</p>

## Built with

* **Kotlin**: programming language used to build the app.
* **Compose**: modern declarative Toolkit for creating the app's UI.
* **MVVM**: architecture pattern the app is built with.
* **Viewmodel**: to keep the data shown on the screen.
* **StateFlow**: used on the viewmodel to keep the data that is observed for the composables and it’ll change.
* **Hilt**: library used to apply the dependency injection at the time of instantiating some classes.
* **Use cases**: the core logic for the game is content on a set of use case classes.
* **Repositories**: there's a repository class used as a layer of abstraction at the moment to access the db.
* **ROOM**: library used to manage the access to the local SQL database.
* **Coroutines**: used on suspend functions for some use cases that access the database.
* **SplashScreen**: library used to show the app icon on a screen when it is starting. It includes different colors for dark and light mode.
* **JUnit**: library used for Unit Testing on use cases.
* **Truth**: library used for assertions on unit tests.
* **Mockk**: library used for mocking dependencies from the classes under tests.
* **CompositionLocalProvider**: used to have available some values without need to pass them down through each Composable.
* **.kts**: Kotlin script, an alternative file type for writing the build.gradle file.

## Lessons Learned
What I learned from this project was how to build a Compose Android App on my own. It is based on an existing [2048](https://play.google.com/store/apps/details?id=com.androbaby.game2048) app, so my main focus was analyzing and replicating most of its existing behavior. I also added some extra details that I wanted to try, either related to this specific app's functionality or to Android development in general (such as being written in Compose, using Adaptive Icons, configuring different screens, etc).

One particular challenge I faced was detecting user movements. I created a Composable that could detect the direction of the moves across the entire screen. However, when I added the BoardGame to the screen, touch detection did not work for that part. To solve this issue, I placed a Box component that held the Composable detecting moves and any buttons on the same level. On another level, I placed the rest of the Composables, which were purely visual with no direct user interactions.


## Run Locally

Clone the project

```bash
  git clone https://github.com/RNicolasMarin/2048.git
```

**Run App**:

Open the cloned project on Android Studio and click on "Run" after selecting a physical device or emulator.

**Run Tests**:

Open the cloned project on Android Studio, right click on the "com (test)" folder, then click on "Run 'Tests in 'com".


## Roadmap
As for features planned for the next version, I'll include:
- "Game Over" and "You Win" screens
- Different BoardGame sizes.
- Records screen
- Dark/Light Mode and Material You


## Feedback

If you have any feedback, please reach out to me at [LinkedIn](https://www.linkedin.com/in/rnicolasmarin/).
