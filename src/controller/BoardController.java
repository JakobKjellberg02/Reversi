package controller;

import javafx.event.*;
import model.BoardModel;
import view.BoardView;

public class BoardController  {

	@SuppressWarnings("unused")
	private BoardModel boardModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	private EventHandler<ActionEvent> eventHandler;

	public BoardController(final BoardModel boardModel, final BoardView boardView) {
		this.boardModel = boardModel;
		this.boardView = boardView;

		this.setEventHandler(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent evt) {		

				int size = boardModel.getSize();
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                 System.out.print(boardModel.getID(row, col, boardModel.getBoard()));
            }
            System.out.println();
        }


				for (int row = 0; row < BoardView.BOARD_SIZE; row++) {
					for (int col = 0; col < BoardView.BOARD_SIZE; col++) {
						if (evt.getSource() == boardView.board_gui[row][col]) {
							boardModel.turn(row, col, boardView.players[boardView.turn].getColor());
							boardView.update();		    
					    }
				    }
			    }

        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                 System.out.print(boardModel.getID(row, col, boardModel.getBoard()));
            }
            System.out.println();
        }

			}
		});
	}

	public EventHandler<ActionEvent> getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}
}
	