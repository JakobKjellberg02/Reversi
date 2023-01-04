package model;

import java.util.Arrays;

public class boardModel {
    //Fields for the board model
    int SIZE;
    char[][] BOARD;

    //Constructor
    public boardModel(int SIZE) {
        this.SIZE = SIZE;
        this.BOARD = new char[SIZE][SIZE];
    }

    //Initializes the start of the board but it is for an 8x8
    public char[][] initializeBoard() {
        //Fills the entire board with '.'
        for (char[] row: BOARD) {
            Arrays.fill(row, '.');    
        }
        BOARD[3][3] = 'W';
        BOARD[3][4] = 'B';
        BOARD[4][3] = 'B';
        BOARD[4][4] = 'W';
        return BOARD;
    }

    //Method for checking if move is valid
    public boolean[][] checkIfMoveIsValid(char board[][], int current_x, int current_y, char my_color) {
        int x = current_x;
        int y = current_y;
        boolean[][] listOfMoves = new boolean[SIZE][SIZE];

        char enemy_color;
        if (my_color == 'W') {
            enemy_color = 'B';
        } else {
            enemy_color = 'W';
        }


        //System.out.println("enemy" + enemy_color);
        //dx and dy works like a compass so it moves diagonal, horizontal and vertical
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {

                int foundEnemy = 0;
                boolean foundPotentialMove = false;
                char[][] potentialMoves = new char[SIZE][SIZE];

                x = current_x;
                y = current_y;

                //checks all directions
                while (x != 0 || y != 0 || x < SIZE || y < SIZE) {
                    //System.out.println("x: " + x + " y:" + y);
                    //System.out.println("dx: " + dx + "dy: " + dy);
                    x += dx;
                    y += dy;
                    //Out of bounds or own position it will break out of loop
                    if (x == 0 || y == 0 || x > SIZE -1 || y > SIZE -1 || (dx == 0 && dy == 0)) {
                        break;
                    }
                    //If empty or your own brick is found it will also break
                    foundEnemy = checkFoundEnemy(x, y, enemy_color);
                    if (foundEnemy == 0) {
                        break;
                    }
                    if (foundEnemy == 2 && foundPotentialMove == false) {
                        break;
                    //Adds positions of bricks you can flip 
                    } else if (foundEnemy == 2 && foundPotentialMove == true) {
                        for (int row = 0; row < potentialMoves.length; row++) {
                            for (int col = 0; col < potentialMoves.length; col++) {
                               if (potentialMoves[row][col] != 0) {
                                    //System.out.println("color: " + my_color + "move: " + row + col);
                                    listOfMoves[row][col] = true; 
                               }
                            }
                         }
                    } 
                    else {
                        potentialMoves[x][y] = my_color;
                        foundPotentialMove = true;
                    }

                }
            }
        }
        return listOfMoves;
    }

    //Check element on board. 0 is empty, 1 is enemy and 2 is ally(you)
    public int checkFoundEnemy(int x, int y, char enemy_color) {
        if (BOARD[x][y] == enemy_color) {
            return 1;
        } else if (BOARD[x][y] == '.'){
            return 0;
        } else {
            return 2;
        }
    }

    //Method for player's turn
    public void turn(int x, int y, char my_color) {
        //Places the color
        BOARD[x][y] = my_color;
        boolean[][] MOVES = new boolean[SIZE][SIZE];
        MOVES = checkIfMoveIsValid(BOARD, x, y, my_color);
        boolean safeToMove = false;
        //If it doesn't find any valid moves, it will send an illegal move message
        for (int row = 0; row < MOVES.length; row++) {
            for (int col = 0; col < MOVES.length; col++) {
               if (MOVES[row][col] == true) {
                    safeToMove = true;
                    place(row, col, my_color);
               }
            }
         }
         
        if (safeToMove == false) {
            BOARD[x][y] = '.';
            System.out.println("Illegal move!");
        } 
    }

    //Gets the size of board
    public int getSize(){
        return this.SIZE;
    }

    //Gets the board
    public char[][] getBoard(){
        return BOARD;
    }

    //Place the brick 
    public void place(int x, int y, char my_color) {
        BOARD[x][y] = my_color;
    }
}