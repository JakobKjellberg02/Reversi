package model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.awt.Point;

public class BoardModel {
    //Fields for the board model
    int SIZE;
    char[][] BOARD;
    public boolean safeToMove = false;

    //Constructor
    public BoardModel(int SIZE) {
        this.SIZE = SIZE;
        this.BOARD = new char[SIZE][SIZE];
    }

    //Initializes the start of the board but it is for an 8x8
    public char[][] initializeBoard() {
        //Fills the entire board with '.'
        for (char[] row: BOARD) {
            Arrays.fill(row, '.');    
        }
        //Center pieces
        BOARD[3][3] = 'W';
        BOARD[3][4] = 'B';
        BOARD[4][3] = 'B';
        BOARD[4][4] = 'W';
        return BOARD;
    }

    //Method for checking if move is valid
    public List<Point> checkIfMoveIsValid(char board[][], int current_x, int current_y, char my_color) {
        int x = current_x;
        int y = current_y;

        //ArrayList with coordinates
        List<Point> coordinates = new ArrayList<>();

        //Detects enemy's color
        char enemy_color;
        if (my_color == 'W') {
            enemy_color = 'B';
        } else {
            enemy_color = 'W';
        }

        //dx and dy works like a compass so it moves diagonal, horizontal and vertical
        for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    //Variables and potential list
                    int foundEnemy = 0;
                    boolean foundPotentialMove = false;
                    List<Point> potentialCoordinates = new ArrayList<>();

                    //Assigns elements to variables
                    x = current_x;
                    y = current_y;
                    
                    //checks all directions
                    while (x < 0 || y < 0 || x < SIZE || y < SIZE) {
                        x += dx;
                        y += dy;
                        //Out of bounds or own position it will break out of loop
                        if (x < 0 || y < 0 || x > SIZE -1  || y > SIZE -1 || (dx == 0 && dy == 0)) {
                            break;
                        }
                        foundEnemy = checkFoundEnemy(x, y, enemy_color);
                        //If empty or your own brick is found it will also break
                        if (foundEnemy == 0) {
                            break;
                        //Break out if found ally immediately
                        } else if (foundEnemy == 2 && foundPotentialMove == false) {
                            break;
                        //Adds positions of bricks you can flip 
                        } else if (foundEnemy == 2 && foundPotentialMove == true) {
                            for (int i = 0; i < potentialCoordinates.size(); i++) {
                                coordinates.add(new Point(potentialCoordinates.get(i).x, potentialCoordinates.get(i).y));
                            }
                            //Breaks out of loop if it sees an ally again after only seeing white
                            break;
                        } else {
                             //The player can maybe make a move so we note that
                            potentialCoordinates.add(new Point(x,y));
                            foundPotentialMove = true;
                        }

                    }
                }
        }
        //Returns the positions in a list
        return coordinates;
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
        List<Point> coordinatesCheck = new ArrayList<>();
        //Detects first if you put brick on a brick
        if (getID(x,y,BOARD) != '.') {
            System.out.println("NOT VALID");
        } else {
             //Places the color
            BOARD[x][y] = my_color;
            coordinatesCheck = checkIfMoveIsValid(BOARD, x, y, my_color);
            safeToMove = false;
            for (int i = 0; i < coordinatesCheck.size(); i++) {
            }
            //If it doesn't find any valid moves, it will send an illegal move message
            if (coordinatesCheck.isEmpty() == true) {
                BOARD[x][y] = '.';
                System.out.println("Illegal move!");
            } else {
                for (int i = 0; i < coordinatesCheck.size(); i++) {
                    safeToMove = true;
                    place(coordinatesCheck.get(i).x, coordinatesCheck.get(i).y, my_color);
                }
            }
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

    //Gets the ID of the position
    public char getID(int x, int y,char[][] board) {
        return board[x][y]; 
    }

    //Place the brick 
    public void place(int x, int y, char my_color) {
        BOARD[x][y] = my_color;
    }
}