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
				//User clicks on the restart button and restarts the game
				if (x.getId().equals(boardView.restartButton.getId())) {
					//Clears the board in model
					boardModel.fillBoard();
					boardModel.startOfGame = true;
					//Clears the board in view
					boardView.resetBoard();
					boardView.update();
					//Initializes the controller
					boardController.init();
					boardController.switchTurns();
					boardView.disablePassButton();
					boardController.updateOutline();

					//Make sure like everything is like if you just start the program without exiting
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
