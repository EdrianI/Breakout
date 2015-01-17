# Breakout

This is a simple(ghetto) looking Breakout made in Java. It uses java awt and the following files;

bPlay - The "main" controlling class. This class is set up to call all of the rest of the files as needed with a single call. Create a main(as is used at the bottom of the file) and call bPlay. This is all that is needed to display/play the game.

bPlayColor - The class that creates the Brick representation that is displayed on the screen. 

bPlayGUI - Makes and displays the game Panel, paddle, ball, determines ball orientation, ball collosions, ball speed and calls the bPlayColor class to create bricks.

bPlayCtrl - Primilary just a KeyListener class that grabs user keyinput. It starts, stops, and pauses the game. It also moves the paddle left or right.

bPlayUpdate - Updates the current score and lives.
