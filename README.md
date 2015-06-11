# NineSquareCardPuzzle

This is a Java program I wrote to find solutions to a Where's Wally (a.k.a. "Where's Waldo" in the US) nine square card puzzle.

![where_is_wally](https://cloud.githubusercontent.com/assets/4344677/8003732/4f787b8a-0ba4-11e5-96d9-dcdd92adb678.jpg)

Each of the nine cards features the top or bottom halves of four characters: Wally (Waldo), Wanda, Wizard Whitebeard, or Woof.  To solve the puzzle you must place all 9 cards in a square so that all of the neighbouring sides have matching tops and bottoms.  There are nine cards and each card has four possible orientations so the number of possible layouts to search is 9! x 4<sup>9</sup> = 95,126,814,720 layouts!  

Each of the cards is modelled by a [Card](https://github.com/Tominator2/NineSquareCardPuzzle/blob/master/Card.java) class where each side of the card contains a picture ([Pic.java](https://github.com/Tominator2/NineSquareCardPuzzle/blob/master/Pic.java)) that represents one half (either the head 'H' or tail 'T') of one of the characters: 

  * 'WY' for Wally 
  * 'WA' for Wanda
  * 'WW' for Wizard Whitebeard
  * 'WF' for Woof.  

The [Puzzle](https://github.com/Tominator2/NineSquareCardPuzzle/blob/master/Puzzle.java) class creates the nine cards, solves the puzzle, and outputs [the solutions](https://github.com/Tominator2/NineSquareCardPuzzle/blob/master/solutions.txt) to standard oputput. The program accepts an optional `-d` command line flag that prints out some debugging information while the code runs, e.g.

`  java Puzzle -d`
