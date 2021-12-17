package main;

import javafx.animation.AnimationTimer;
import manager.InputManager;
import manager.TimeManager;

//Main Game Loop
public class GameLoop extends AnimationTimer {
	private InputManager INPUT_MANAGER = InputManager.getInstance();
	private TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	private GameStage game_stage;
	
	GameLoop (GameStage game_stage) {
		this.game_stage = game_stage;
	}
	
	@Override
	public void handle(long NanoTime) {		
		onStartOfFrame();
		updateGameScene();
		updateInput();
		updateTime();
	}
	
	private void onStartOfFrame() {
		INPUT_MANAGER.inputUpdate(game_stage.getGameScene().getScene());
	}
	
	private void updateGameScene() {
		game_stage.getGameScene().update(game_stage.getGraphicsContext());
	}
	
	private void updateTime() {
		TIME_MANAGER.updateTime(System.nanoTime());
	}
	
	private void updateInput() {
		INPUT_MANAGER.clearJustPressed();
		INPUT_MANAGER.clearJustReleased();
	}
	
}

