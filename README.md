# RxRockPaperScissor

Android app that simulates Reactive RockPaperScissor game using RxJava2.

## About the Game
We all know how to play RockPaperScissor game.
 - If PlayerC chooses Scissor and PlayerD chooses Paper then PlayerC scores 1 point.
 - If PlayerC chooses Paper and PlayerD chooses Rock then PlayerC scores 1 point.
 - If PlayerC chooses Rock and PlayerD chooses Scissor then PlayerC scores 1 point.
 - If PlayerC and PlayerD both choose the same, then both players score half a point (ie. 0.5)
 
One player can cheat based on other player's move, based on who ever makes a move first.
 
### Rules
Assuming that players can cheat,
1. Each players should make a move after they receive a Ready signal.
2. Players then make their move. Since they cheat, they can modify their move based on the opponent's.
3. The trust-mechanism should prevent the players from cheating and increment scores accordingly.
4. Player who scores more than 10 points is declared as winner and should exit the game.
5. If both player score is same, another move is made randomly which decides the winner.
6. The scores and player moves are saved in a database.

### The code
1. The game should be scalable, that is, storing data to local storage and cloud database.
2. More rules can be added to the game.

### What I learnt?
1. Reactive programming
2. Structured Android app code
3. EventBus using RxJava
4. Communications between threads
5. Jetpack Android components (Room, ViewModel)

## Dev Info

### Prerequisite

- Download and install Java8
- Install Android Studio 3.5
- Android SDK 28.0.3

### Libraries used
1. Android Appcompat library (androidx version)
2. Android Jetpack - Architecture components (androidx version)

	- Lifecycle - Viewmodel only

	- Room persistent storage library

3. Android RecyclerView, CardView and ConstraintLayout (androidx version)
4. RxJava2 and RxAndroid (Reactive Extentions)
4. Butterknife (Field and method binding for Android views)
5. Stetho (A debug bridge for Android applications)

### Sample App

Download the Sample APK in the source (rxrps-app.apk)

