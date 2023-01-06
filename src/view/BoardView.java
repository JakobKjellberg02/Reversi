package view;
import controller.BoardController;
import controller.PlayerController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.BoardModel;
import model.PlayerModel;

public class BoardView extends Application{

  //Fields
  public static final int BOARD_SIZE = 8;
  public int turn = 0;

  //Models
  private BoardModel BOARD = new BoardModel(8);
  private PlayerModel player1 = new PlayerModel("Michael",2,'B');
  private PlayerModel player2 = new PlayerModel("Tom",2,'W');
  public PlayerModel[] players = {player1, player2};
  char[][] board_data = BOARD.initializeBoard();

  //Controller
  private BoardController boardController = new BoardController(BOARD, this);
  private PlayerController playerController = new PlayerController(BOARD, this);

  //GUI
  public Button[][] board_gui;
  public Button passButton;
  public TextField score;

  //Primary stage for the program
  @Override
  public void start(Stage primaryStage) throws Exception {

    primaryStage.setTitle("Reversi");
    BorderPane bPane = new BorderPane();

    //Center piece of the screen - The Board
    board_gui = new Button[BOARD_SIZE][BOARD_SIZE];
    GridPane gridPane = new GridPane();
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        // tilføjer knapper i hvert kvadrat
        board_gui[i][j] = new Button();
        changeColor(board_gui, BOARD, board_data, i,j);
        gridPane.add(board_gui[i][j], j,i);
        board_gui[i][j].setOnAction(boardController.getEventHandler());
      }
    }
    bPane.setCenter(gridPane); 

    //Bottom piece of the screen - Pass
    passButton = new Button();
    passButton.setText("Pass");
    passButton.setId("0");
    passButton.setOnAction(playerController.getEventHandler());
    bPane.setBottom(passButton);

    //Top piece of the screen
    score = new TextField();
    score.setText(players[turn].getName() + " " + players[turn].getPoints() + " " + players[turn].getColor());
    bPane.setTop(score); 

    //Combines it all to the scene
    Scene scene = new Scene(bPane, 500, 500);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  //Updates the colors of the board
  public void update(){
    players[0].points = 0;
    players[1].points = 0;
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        changeColor(board_gui, BOARD, board_data, i,j);
        if (BOARD.getID(i, j, board_data) == players[0].getColor()) {
          players[0].points += 1;
        } else if (BOARD.getID(i, j, board_data) == players[1].getColor()) {
          players[1].points += 1;
        }
      }
    }
    changeScore();
	}

  //Changes the display of the player 
  public void changeScore(){
    score.setText(players[turn].getName() + " " + players[turn].getPoints() + " " + players[turn].getColor());
  }

  //Main method of the GUI
  public static void main(String[] args) {
    Application.launch(args);
  }

  //Method for changing from char to a GUI color 
  public static void changeColor(Button[][] button, BoardModel BOARDMODEL, char[][] board_data, int i, int j) {
    if (BOARDMODEL.getID(i,j,board_data) == 'B') {
     button[i][j].setStyle("-fx-background-color: #000000");
    } else if (BOARDMODEL.getID(i,j,board_data) == 'W') {
     button[i][j].setStyle("-fx-background-color: #fafcfa");
    } else {
      if ((i + j) % 2 == 0) {
        button[i][j].setStyle("-fx-background-color: #023602");  
      } else {
       button[i][j].setStyle("-fx-background-color: #046e04");  
     }
    }
  }
}
