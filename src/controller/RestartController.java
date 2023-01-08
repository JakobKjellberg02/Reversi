package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.BoardModel;
import view.BoardView;


public class RestartController {

	//Fields
	@SuppressWarnings("unused")
	private BoardModel boardModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	@SuppressWarnings("unused")
	private BoardController boardController;
	private EventHandler<ActionEvent> eventHandler;

	boolean validTurn = false;

	//Controller for the player
	public RestartController(final BoardModel boardModel, final BoardController boardController, final BoardView boardView) {
		this.boardView = boardView;
		this.boardModel = boardModel;
		//Checks when the pass button has been pressed and switches to the other player
		this.setEventHandler(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent evt) {
				Button x = (Button) evt.getSource();    
				//User clicks on the restart button
				if (x.getId().equals(boardView.restartButton.getId())) {
					boardModel.fillBoard();
					boardModel.startOfGame = true;
					boardView.resetBoard();
					boardView.update();
					boardController.init();
					boardController.switchTurns();
					boardView.disablePassButton();
					boardController.updateOutline();

					boardController.firstTurns = 0;
					boardController.allFirstMoves = 0;

				}
			}         
		});
	}

	//Event handler
	public EventHandler<ActionEvent> getEventHandler() {
		return eventHandler;
	}

	public void setEventHandler(EventHandler<ActionEvent> eventHandler) {
		this.eventHandler = eventHandler;
	}  
}
