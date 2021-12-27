package main;

import java.util.ArrayList;

import javafx.animation.AnimationTimer;
import manager.GameManager;
import manager.InputManager;
import manager.TimeManager;
import parentclass.GameObject;
import runnableobject.RunnableObject;

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
	private GameManager GAME_MANAGER = GameManager.getInstance();
	
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
		updateTime();
		updateObjects();
		
		updateObjectCollisions();
		updateDestroyedObjects();
		
		updateInput();
	}
	
	/**
	 * How to start a frame in a loop.
	 * 
	 * @author Dave Jimenez
	 */	
	private void onStartOfFrame() {
		INPUT_MANAGER.inputUpdate(game_stage.getGameScene().getScene());
		game_stage.getGraphicsContext().clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
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
	
	/**
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected void updateObjects() {
		GAME_MANAGER.addBufferedRunnableObjectsToAdd();
		for (RunnableObject object: GAME_MANAGER.getRunnableObjects()) {
			object.update(game_stage.getGraphicsContext());
		}
	}
	
	/**
	 * Removes GameObject from the runnableobject_list if they are destroyed.
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected void updateDestroyedObjects() {
		ArrayList<RunnableObject> toremove_list = new ArrayList<RunnableObject>(); 
		for (RunnableObject runnable_object: GAME_MANAGER.getRunnableObjects()) {
			if (runnable_object instanceof GameObject) {
				GameObject game_object = (GameObject) runnable_object;
				if (game_object.isDestroyed()) {
					toremove_list.add(runnable_object);
				}
			}
		}
		
		for (RunnableObject runnable_object: toremove_list) {
			GAME_MANAGER.getRunnableObjects().remove(runnable_object);
		}
	}
	
	/**
	 * Checks the collision for each GameObject if it collides with other GameObjects.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected void updateObjectCollisions() {
		for (RunnableObject runnable_object: GAME_MANAGER.getRunnableObjects()) {
			if (runnable_object instanceof GameObject) {
				GameObject game_object = (GameObject) runnable_object;
				for (RunnableObject another_object: GAME_MANAGER.getRunnableObjects()) {
					GameObject other = (GameObject) another_object;
					if (other != game_object) {
						game_object.collidesWith(other);
					}
				}
			}
		}
	}
}

