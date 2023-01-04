package view;

import javax.swing.*;
import java.awt.*;

public class GUI {
  private static final int BOARD_SIZE = 8;
  private JButton[][] board;

  public GUI() {
    // laver board
    board = new JButton[BOARD_SIZE][BOARD_SIZE];
    JPanel panel = new JPanel(new GridLayout(BOARD_SIZE, BOARD_SIZE));
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        // tilføjer knapper i hvert kvadrat
        board[i][j] = new JButton();
        board[i][j].setPreferredSize(new Dimension(50, 50));
        panel.add(board[i][j]);
      }
    }

    // laver frame
    JFrame frame = new JFrame("Reversi");
    frame.add(panel, BorderLayout.CENTER);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.pack();
    frame.setVisible(true);
  }

  public static void main(String[] args) {
    new GUI();
  }
}
