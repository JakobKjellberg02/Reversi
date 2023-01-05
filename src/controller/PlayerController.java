package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import model.PlayerModel;
import view.BoardView;

public class PlayerController {

    @SuppressWarnings("unused")
	private PlayerModel playerModel;
	@SuppressWarnings("unused")
	private BoardView boardView;
	private EventHandler<ActionEvent> eventHandler;

	public PlayerController(final PlayerModel playerModel, final BoardView boardView) {
		this.playerModel = playerModel;
		this.boardView = boardView;
		this.setEventHandler(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent evt) {
				Button x = (Button) evt.getSource();
					    
				if (x.getId().equals(boardView.passButton.getId())) {
					if (boardView.turn == 1) {
						boardView.turn = 0;
					} else {
						boardView.turn = 1;
					}
					boardView.changeScore();
				}else  {
					System.out.println("ERROR: Unexpected ActionCommand");
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
