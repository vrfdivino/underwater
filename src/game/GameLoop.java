package game;

import javafx.stage.Stage;

public class GameLoop {
	
	private GameStage _gameStage;
	
	public GameLoop() {
		this._gameStage = new GameStage();
	}
	
	/**
	 * 
	 * Setter for object's gameStage's stage.
	 * 
	 * @author Von Divino
	 * @param _stage
	 * @return void
	 * 
	 */
	public void setStage(Stage stage) {
		this._gameStage.setStage(stage);
		return;
	}
	
	/**
	 * 
	 * Start the application instance.
	 * 
	 * @author Von Divino
	 * @return void
	 * 
	 */
	public void startApp() {
		this._gameStage.initialize();
		this._gameStage.getStage().show();
		return;
	}
}
