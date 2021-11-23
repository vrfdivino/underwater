package game; 

import javafx.stage.Stage;

public class GameStage {
	
	public static final double WINDOW_WIDTH  = 600.0d;
	public static final double WINDOW_HEIGHT = 600.0d;
	public static final String APP_NAME 	 = "CMSC 22 Mini Project";
	
	private Stage _stage;
	
	/**
	 * 
	 * Setter for object's stage.
	 * 
	 * @author Von Divino
	 * @param _stage
	 * @return void
	 */
	void setStage(Stage _stage) {
		this._stage = _stage;
		return;
	}
	
	/**
	 * 
	 * Getter for object's stage.
	 * 
	 * @author Von Divino
	 * @return void
	 */
	Stage getStage() { return this._stage; }
	
	/**
	 * 
	 * Initialize stage upon showing the window.
	 * 
	 * @author Von Divino
	 * @return void
	 */
	void initialize() {
		
		// TODO:
		
		this._stage.setTitle(GameStage.APP_NAME);
		return;
	}
	
}
