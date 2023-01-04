package view;

import javax.swing.JFrame;

public class GUI{
public static void main(String[] args){
    JFrame frame=new JFrame();
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setSize(500, 500);
    frame.setVisible(true);
    frame.setTitle("Reversi");
}

}