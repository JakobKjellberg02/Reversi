package controller;

import javafx.event.*;
import model.BoardModel;
import view.BoardView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.awt.Point;
import java.text.SimpleDateFormat;

public class BoardController  {

	//Fields
	@SuppressWarnings("unused")
	private BoardModel boardModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	private EventHandler<ActionEvent> eventHandler;

	public List<Point> firstMoves = new ArrayList<>();
	public int firstTurns = 0;
	public int allFirstMoves = 0;
	public String path;

	//Controller for the board
	public BoardController(final BoardModel boardModel, final BoardView boardView) {
		this.boardModel = boardModel;
		this.boardView = boardView;

		this.setEventHandler(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {		
				//If-statement for start of every game
				if (boardModel.startOfGame == true) {
					for (int row = 0; row < BoardView.BOARD_SIZE; row++) {
						for (int col = 0; col < BoardView.BOARD_SIZE; col++) {
							if (evt.getSource() == boardView.board_gui[row][col]) {
								//Detects if the player has played two times
								if (firstTurns < 2) {
									for (int i = 0; i < firstMoves.size(); i++) {
										if (row == firstMoves.get(i).x && col == firstMoves.get(i).y) {
											boardModel.place(row, col, boardView.players[boardView.turn].getColor());
											//Styling the placed brick's background
											if ((firstMoves.get(i).x + firstMoves.get(i).y) % 2 == 0) {
												boardView.board_gui[i][firstMoves.get(i).y].setStyle("-fx-background-color: #023602");  
											} else {
												boardView.board_gui[i][firstMoves.get(i).y].setStyle("-fx-background-color: #046e04");  
											}
											firstMoves.remove(i);
											firstTurns += 1;
											allFirstMoves += 1;
											//Updates score and outline
											boardView.update();	
											updateOutline();
										}
									}

									//If four pieces has been placed the game begins
									if (allFirstMoves == 4) {
										boardModel.startOfGame = false;
										switchTurns();
										boardView.passButton.setDisable(false);
										//Creates a new journal
										Date date = new Date() ;
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss") ;
										path = dateFormat.format(date) +".txt";
										boardModel.writeToFile(path);
										break;
									}

									//The player can only place two pieces at the beginning
									if (firstTurns == 2) {
										switchTurns();
										firstTurns = 0;	
									}
									
								} 
							}
						}
					}		
				} else {
		//Looks at what button has been touched at the gui and does two things.
					for (int row = 0; row < BoardView.BOARD_SIZE; row++) {
						for (int col = 0; col < BoardView.BOARD_SIZE; col++) {
							if (evt.getSource() == boardView.board_gui[row][col]) {
								//Makes the turn from which the button has been pressed on the grid
								if (boardModel.safeToMove = true) {
									boardModel.turn(row, col, boardView.players[boardView.turn].getColor());
								//Updates our GUI
								if (boardModel.switchPlayer == true) {
									switchTurns();
								}
								boardView.update();		
								}	
								//Error messages if you don't know how to play the game :P
								if (boardModel.errorMessage.isEmpty() != true) {
									boardView.score.setText(boardModel.errorMessage);
								}	    
							}
						}
					}
				}
			}
		});
	}


	//Hard-coded points for the initialization
	public void init() {
		firstMoves.clear();
		boardModel.fillBoard();
		firstMoves.add(new Point(3, 3));
		firstMoves.add(new Point(3, 4));
		firstMoves.add(new Point(4, 3));
		firstMoves.add(new Point(4, 4));
	}

	//Makes a red outline around the pieces that you can place at the beginning
	public void updateOutline() {
		for (int i = 0; i < firstMoves.size(); i++) {
			if ((firstMoves.get(i).x + firstMoves.get(i).y) % 2 == 0) {
				boardView.board_gui[firstMoves.get(i).x][firstMoves.get(i).y].setStyle("-fx-border-color: red; -fx-background-color: #023602");  
			} else {
				boardView.board_gui[firstMoves.get(i).x][firstMoves.get(i).y].setStyle("-fx-border-color: red; -fx-background-color: #046e04");  
			}
		}
	}

	//Method for clearing the board from orange circles
	public void clearPossibleMoves() {
		for (int i = 0; i < boardView.knownTurns.size(); i++) {
			if ((boardView.knownTurns.get(i).x + boardView.knownTurns.get(i).y) % 2 == 0) {
				boardView.board_gui[boardView.knownTurns.get(i).x][boardView.knownTurns.get(i).y].setGraphic(null);
				boardView.board_gui[boardView.knownTurns.get(i).x][boardView.knownTurns.get(i).y].setStyle("-fx-background-color: #023602");  
			} else {
				boardView.board_gui[boardView.knownTurns.get(i).x][boardView.knownTurns.get(i).y].setGraphic(null);
				boardView.board_gui[boardView.knownTurns.get(i).x][boardView.knownTurns.get(i).y].setStyle("-fx-background-color: #046e04");  
			}
		}
		boardView.knownTurns.clear();
	}

	//Switches player's turn
	public void switchTurns() {
		if (boardView.turn == 1) {
			boardView.turn = 0;
		} else {
			boardView.turn = 1;
		}
		boardView.changeScore();
	}

	//EventHandler
	public EventHandler<ActionEvent> getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}
}
	