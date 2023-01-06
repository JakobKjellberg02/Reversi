package controller;

import javafx.event.*;
import model.BoardModel;
import view.BoardView;

public class BoardController  {

	//Fields
	@SuppressWarnings("unused")
	private BoardModel boardModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	private EventHandler<ActionEvent> eventHandler;

	//Controller for the board
	public BoardController(final BoardModel boardModel, final BoardView boardView) {
		this.boardModel = boardModel;
		this.boardView = boardView;

		this.setEventHandler(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {		

		//Looks at what button has been touched at the gui and does two things.
				for (int row = 0; row < BoardView.BOARD_SIZE; row++) {
					for (int col = 0; col < BoardView.BOARD_SIZE; col++) {
						if (evt.getSource() == boardView.board_gui[row][col]) {
							//Makes the turn from which the button has been pressed on the grid
							if (boardModel.safeToMove = true) {
								boardModel.turn(row, col, boardView.players[boardView.turn].getColor());
							//Updates our GUI
							boardView.update();		
							}		    
					    }
				    }
			    }
			}
		});
	}

	//EventHandler
	public EventHandler<ActionEvent> getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}
}
	