
# Tetris

### Author : Vera Kong

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

#### About
Hello! This Java project was inspired by the popularly played [Tetris game](https://en.wikipedia.org/wiki/Tetris "Tetris game") that first released in 1984. Throughout the coding process, I utilized OOP principles that I had learned in my CS3500 class at Northeastern University. The main design pattern that was used in this Tetris was the MVC pattern as shown below.

![](https://upload.wikimedia.org/wikipedia/commons/thumb/a/a0/MVC-Process.svg/1920px-MVC-Process.svg.png)

Another design choice was creating enums and classes for each Tetrominoes since each piece had a different block structure and primitive datatypes would by unwieldy to use here.

Upon starting the game, there are 3 panels you can view : the start, play, and game over screens in order.

![Start Screen](https://github.com/VKong6019/TetrisGame/blob/master/resources/start-tetris.PNG?raw=true)
![Play Screen](https://github.com/VKong6019/TetrisGame/blob/master/resources/play-screen.PNG?raw=true)
![Game Over Screen](https://github.com/VKong6019/TetrisGame/blob/master/resources/gameover-screen.PNG?raw=true)

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

#### How To Start Up Tetris
To play this Tetris game, just `git clone` this repo.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
#### There are a couple ways to start the game:

##### **!!Note!!**: Might need Java 12 downloaded since I wrote this in Java 12 :P

**The first** is opening the jar file in the folder through the command line by inputting this line into the console:
```console
java -jar Tetris.jar
```
Note: You must be in the folder to do this.

**The second** is just simply navigate into the downloaded folder and double-click the ``Tetris.bat`` file.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

#### How To Play Tetris
The goal of the game is to complete as many lines (rows) as possible to score the highest amount of amounts. The game is over when a block overfills the board (the block cannot be completely fitted).

Since I wanted to simulate a similar feeling of playing an actual Tetris game, I added music/sound fx and scoring mechanism used in most Tetris games. Unfortunately, T-spins are not implemented in this version, so the scoring guidelines only account for the amount of lines cleared.

---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

##### If ...
- **1 line is cleared : +100 pts**
- **2 lines are cleared : +300 pts**
- **3 lines are cleared : +500 pts**
- **4 lines are cleared : +800 pts**

In this game, the keyboard is mainly used to control the falling block movement.
- **Up** arrow key : Rotates the falling block 90 degrees to the right
- **Down** arrow key: Moves the falling block down a block
- **Left/Right** arrow key: Moves the falling block to the left/right
- **X** key: Holds the falling block
- **C** key: Drops the falling block all the way down

There are also mouse clicking functionality in this game with the button panel of the right side of the game panel. The buttons are mainly self-explanatory as they are labeled what their functionality is when clicked on.
