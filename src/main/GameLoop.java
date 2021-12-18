package main;

import javafx.animation.AnimationTimer;
import manager.InputManager;
import manager.TimeManager;

/**
 * The game loop.
 * All looping events happen here.
 * 
 * @author Dave Jimenez
 */

public class GameLoop extends AnimationTimer {
	
	/////////////////// PROPERTIES ///////////////////
	
	private InputManager INPUT_MANAGER = InputManager.getInstance();
	private TimeManager TIME_MANAGER = TimeManager.getInstance();
	private GameStage game_stage;
	
	GameLoop (GameStage game_stage) {
		this.game_stage = game_stage;
	}
	
	/**
	 * Describe how to handle the game loop.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void handle(long NanoTime) {		
		onStartOfFrame();
		updateGameScene();
		updateInput();
		updateTime();
	}
	
	/**
	 * How to start a frame in a loop.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void onStartOfFrame() {
		INPUT_MANAGER.inputUpdate(game_stage.getGameScene().getScene());
	}
	
	/**
	 * Update game scene.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateGameScene() {
		game_stage.getGameScene().update(game_stage.getGraphicsContext());
	}
	
	/**
	 * Update time.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateTime() {
		TIME_MANAGER.updateTime(System.nanoTime());
	}
	
	/**
	 * Resets input state.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void updateInput() {
		INPUT_MANAGER.clearJustPressed();
		INPUT_MANAGER.clearJustReleased();
	}	
}

