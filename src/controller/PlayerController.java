package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.BoardModel;
import view.BoardView;

public class PlayerController {

	//Fields
	@SuppressWarnings("unused")
	private BoardModel boardModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	private EventHandler<ActionEvent> eventHandler;

	boolean validTurn = false;

	//Controller for the player
	public PlayerController(final BoardModel boardModel, final BoardView boardView) {
		this.boardView = boardView;
		this.boardModel = boardModel;
		//Checks when the pass button has been pressed and switches to the other player
		this.setEventHandler(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent evt) {
				Button x = (Button) evt.getSource();    
				//User clicks on the pass button
				if (x.getId().equals(boardView.passButton.getId())) {
					//Passes to the next player if it has made a turn
					if (boardModel.safeToMove == true) {
						boardView.players[boardView.turn].successfulPlay = false;
						if (boardView.turn == 1) {
							boardView.turn = 0;
						} else {
							boardView.turn = 1;
						}
						boardModel.safeToMove = false;	
						System.out.println("It is now: " + boardView.players[boardView.turn].getName() + "'s turn!");
						boardView.changeScore();
					} else {
						//Now checks if you can still win the game or you can't make any more turn
						validTurn = false;
						//Checks through all positions on the board and then checks the 2d-array of validMoves to see, if it has a true in it - if it does you can still make a turn.
						outerloop:
						for (int row = 0; row < boardModel.getSize(); row++) {
							for (int col = 0; col < boardModel.getSize(); col++) {
								if (boardModel.getID(row, col, boardModel.getBoard()) == '.') {
									boolean[][] MOVES = new boolean[boardModel.getSize()][boardModel.getSize()];
            						MOVES = boardModel.checkIfMoveIsValid(boardModel.getBoard(), row, col, boardView.players[boardView.turn].getColor());
									for (int rowx = 0; rowx < MOVES.length; rowx++) {
										for (int colx = 0; colx < MOVES.length; colx++) {
											if (MOVES[rowx][colx] == true) {
												validTurn = true;
												break outerloop;
											}
									   }
									}
								}
							}
						}
						if (validTurn == true) {
							System.out.println("You can make a turn!");
						} else {
							System.out.println("GG");
							switchPlayer();
						}

					}
				}else  {
					System.out.println("ERROR: Unexpected ActionCommand");
				}
			}         
		});
	}

	private void switchPlayer() {
		if (boardView.turn == 1) {
			boardView.turn = 0;
		} else {
			boardView.turn = 1;
		}
		boardModel.safeToMove = false;	
		System.out.println("It is now: " + boardView.players[boardView.turn].getName() + "'s turn!");
		boardView.players[boardView.turn].successfulPlay = false;
		boardView.changeScore();
	}

	//Event handler
	public EventHandler<ActionEvent> getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}  
}
