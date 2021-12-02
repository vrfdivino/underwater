package main;

import javafx.animation.AnimationTimer;
import manager.InputManager;
import manager.TimeManager;

//Main Game Loop
public class GameLoop extends AnimationTimer {
	private InputManager INPUT_MANAGER = InputManager.getInstance();
	private TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	private GameStage gameStage;
	
	GameLoop (GameStage gameStage) {
		this.gameStage = gameStage;
	}
	
	@Override
	public void handle(long NanoTime) {		
		onStartOfFrame();
		updateGameScene();
		updateTime();
		updateInput();
	}
	
	private void onStartOfFrame() {
		INPUT_MANAGER.inputUpdate(gameStage.getGameScene().getScene());
	}
	
	private void updateGameScene() {
		gameStage.getGameScene().update(gameStage.getGraphicsContext());
	}
	
	private void updateTime() {
		TIME_MANAGER.updateTime(System.nanoTime());
	}
	
	private void updateInput() {
		INPUT_MANAGER.clearJustPressed();
		INPUT_MANAGER.clearJustReleased();
	}
	
}
