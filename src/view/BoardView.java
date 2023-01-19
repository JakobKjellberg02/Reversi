package view;
import java.util.Random;

import controller.BoardController;
import controller.PlayerController;
import controller.RestartController;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import model.BoardModel;
import model.PlayerModel;
import java.util.ArrayList;
import java.util.List;
import java.awt.Point;

public class BoardView extends Application{

  //Fields
  public static final int BOARD_SIZE = 8;
  public int turn = new Random().nextInt(1 - 0 + 1) + 0;
  public int startingTurn = turn;
  public List<Point> knownTurns = new ArrayList<>();

  //Models
  private BoardModel boardModel = new BoardModel(8);
  private PlayerModel player1 = new PlayerModel("Sort",0,'B');
  private PlayerModel player2 = new PlayerModel("Hvid",0,'W');
  public PlayerModel[] players = {player1, player2};

  //Controller
  private BoardController boardController = new BoardController(boardModel, this);
  private PlayerController playerController = new PlayerController(boardModel, this);
  private RestartController restartController = new RestartController(boardModel, boardController, this);

  //GUI
  public Button[][] board_gui;
  public Button passButton;
  public TextField score;
  public Button restartButton;

  //Primary stage for the program
  @Override
  public void start(Stage primaryStage) throws Exception {


    primaryStage.setTitle("Reversi");
    BorderPane bPane = new BorderPane();

    //Center piece of the screen - The Board
    board_gui = new Button[BOARD_SIZE][BOARD_SIZE];
    GridPane gridPane = new GridPane();
  
    //Show grid lines
  gridPane.setGridLinesVisible(true);
  //for loop that adds buttons to the board
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        // tilføjer knapper i hvert kvadrat
        board_gui[i][j] = new Button();
        board_gui[i][j].setPrefSize(60, 60);
        changeColor(board_gui, boardModel, boardModel.getBoard(), i,j);
        gridPane.add(board_gui[i][j], j,i);
        board_gui[i][j].setOnAction(boardController.getEventHandler());
        
      }
    }

    //Start of game
    boardController.init();
    boardController.updateOutline();
    bPane.setCenter(gridPane); 

    //Bottom piece of the screen - Pass
    passButton = new Button();
    passButton.setText("Pass");
    passButton.setId("0");
    passButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
    passButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    passButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    passButton.setOnAction(playerController.getEventHandler());
    

    //Top piece of the screen
    score = new TextField();
    score.setText(players[turn].getName() + " - Brikker: " + players[turn].getPoints());
    bPane.setTop(score); 
    //Changes the font
    score.setFont(Font.font("Times New Roman", FontWeight.BOLD, 20));
    //Adds a background color
    score.setBackground(new Background(new BackgroundFill(Color.PERU, CornerRadii.EMPTY, Insets.EMPTY)));
    //Adds a border
    score.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    //Change the alignment of the text
    score.setAlignment(Pos.CENTER);

    //restart button bottom right
    restartButton= new Button();
    restartButton.setText("Restart");
    restartButton.setId("1");
    restartButton.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));
    restartButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
    restartButton.setBorder(new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
    restartButton.setOnAction(restartController.getEventHandler());
    
    //sets color of the bPane
    bPane.setStyle("-fx-background-color: gray;");

    //Combines it all to the scene
    Scene scene = new Scene(bPane, 480, 555);
    primaryStage.setScene(scene);
    //makes the window nonsizable
    primaryStage.setResizable(false);
    primaryStage.show();
    //adds pass and restart buttons
    HBox buttonsBox = new HBox();
    buttonsBox.getChildren().addAll(passButton, restartButton);
    bPane.setBottom(buttonsBox); // Add the buttonsbox to the bottom of the BorderPane
    passButton.setDisable(true);
  }

  //Updates the colors of the board
  public void update(){
    //Clears the board from oranges circles
    boardController.clearPossibleMoves();
    players[0].points = 0;
    players[1].points = 0;
    //Counts the bricks and assigns it to the respactable user
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        List<Point> coordinates = new ArrayList<>();
        coordinates = boardModel.checkIfMoveIsValid(boardModel.getBoard(), i, j, players[turn].getColor());
        changeColor(board_gui, boardModel, boardModel.getBoard(), i,j);
        if (boardModel.getID(i, j, boardModel.getBoard()) == players[0].getColor()) {
          players[0].points += 1;
        } else if (boardModel.getID(i, j, boardModel.getBoard()) == players[1].getColor()) {
          players[1].points += 1;
        }

        //Places a orange cirlce if the user can make a move there
        if (coordinates.isEmpty() != true && boardModel.startOfGame == false) {
          if ((i + j) % 2 == 0 && boardModel.getID(i, j, boardModel.getBoard()) == '.') {
            Circle circleG = new Circle(7);
            circleG.setFill(Paint.valueOf("#FFD700"));
            board_gui[i][j].setGraphic(circleG);
            board_gui[i][j].setStyle("-fx-background-color: #023602");  
            knownTurns.add(new Point(i,j));
          } else if(boardModel.getID(i, j, boardModel.getBoard()) == '.') {
            Circle circleG = new Circle(7);
            circleG.setFill(Paint.valueOf("#FFD700"));
            board_gui[i][j].setGraphic(circleG);
            board_gui[i][j].setStyle("-fx-background-color: #046e04");  
            knownTurns.add(new Point(i,j));
          }
        }

      }
    }
    //Writes to journal if it isn't the start of the game
    if (boardModel.startOfGame == false) {
      boardModel.writeToFile(boardController.path);
    }
    changeScore();
	}

  //Disables the pass button
  public void disablePassButton() {
    passButton.setDisable(true);
  }

  //Changes the display of the player 
  public void changeScore(){
    score.setText(players[turn].getName() + " - Brikker: " + players[turn].getPoints());
  }

  //Main method of the GUI
  public static void main(String[] args) {
    Application.launch(args);
  }

  //Resets board visuals
  public void resetBoard() {
    for (int i = 0; i < BOARD_SIZE; i++) {
      for (int j = 0; j < BOARD_SIZE; j++) {
        board_gui[i][j].setGraphic(null);
      }
    }
  }

  //Method for changing from char to a GUI color 
  public static void changeColor(Button[][] button, BoardModel boardModel, char[][] board_data, int i, int j) {
    //if statement that creates circles
    if (boardModel.getID(i,j,board_data) == 'B') {
       Circle circleB = new Circle(22);
       circleB.setFill(Paint.valueOf("#000000"));
       circleB.setEffect(new DropShadow(20, Color.BLACK));
       button[i][j].setGraphic(circleB);
    } else if (boardModel.getID(i,j,board_data) == 'W') {
      Circle circleW = new Circle(22);
      circleW.setFill(Paint.valueOf("#fafcfa"));
      circleW.setEffect(new DropShadow(20, Color.BLACK));
      button[i][j].setGraphic(circleW);
    } 
    //if statement that paint the boards background color
      if ((i + j) % 2 == 0) {
        button[i][j].setStyle("-fx-background-color: #023602");  
      } else {
       button[i][j].setStyle("-fx-background-color: #046e04");  
     } 
  }
}