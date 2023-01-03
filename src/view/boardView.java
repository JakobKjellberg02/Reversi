package view;

import model.boardModel;

public class boardView {

    public static void main(String[] args) {
        boardModel BOARD = new boardModel(8);
        char[][] board = BOARD.initializeBoard();
        printBoard(board);
        System.out.println("");
        BOARD.turn(4, 5, 'B');
        printBoard(board);
        BOARD.turn(5, 3, 'W');
        printBoard(board);
        BOARD.turn(3, 2, 'B');
        printBoard(board);
    }

    private  static void printBoard(char[][] b) {
        int size = b.length;
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                 System.out.print((b[row][col]));
            }
            System.out.println();
        }
    }



}