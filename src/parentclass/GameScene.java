package parentclass;

import java.util.ArrayList;
import java.util.Random;

import gameobject.AnglerFish;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import main.GameStage;
import manager.AudioManager;
import manager.GameManager;
import manager.InputManager;
import manager.PlayerManager;
import manager.SFXManager;
import manager.TimeManager;
import runnableobject.RunnableObject;

public abstract class GameScene implements RunnableObject{
	//Initialize Singletons
	protected InputManager INPUT_MANAGER = InputManager.getInstance();
	protected AudioManager AUDIO_MANAGER = AudioManager.getInstance();
	protected SFXManager SFX_MANAGER = SFXManager.getInstance();
	protected GameManager GAME_MANAGER = GameManager.getInstance();
	protected PlayerManager PLAYER_MANAGER = PlayerManager.getInstance();
	protected TimeManager TIME_MANAGER = TimeManager.getInstance();
	
	//protected ArrayList<RunnableObject> runnableobject_list = new ArrayList<RunnableObject>();
	
	protected GameStage game_stage;
	protected Scene scene;
	protected GraphicsContext gc;
	protected Canvas canvas;
	
	@Override
	public void update(GraphicsContext gc) {}
	
	/**
	 * To be called at the start of update method.
	 * @author Dave
	 */
	protected void onStartOfFrame() {
		gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}
	
	/**
	 * To be called in update method
	 * @author Dave
	 */
	protected void updateObjects() {
		GAME_MANAGER.addBufferedRunnableObjectsToAdd();
		for (RunnableObject object: GAME_MANAGER.getRunnableObjects()) {
			object.update(gc);
			//System.out.println(object);
		}
	}
	
	/**
	 * To be called in update method.
	 * @author Dave
	 */
	protected abstract void updateGUI();
	
	/**
	 * Removes GameObject from the runnableobject_list if they are destroyed.
	 * To be called in update method.
	 * @author Dave
	 */
	protected void checkDestroyedObjects() {
		ArrayList<RunnableObject> toremove_list = new ArrayList<RunnableObject>(); 
		for (RunnableObject runnable_object: GAME_MANAGER.getRunnableObjects()) {
			if (runnable_object instanceof GameObject) {
				GameObject game_object = (GameObject) runnable_object;
				if (game_object.isDestroyed()) {
					toremove_list.add(runnable_object);
				}
			}
		}
		
		//Solve ConcurrentModificationException
		for (RunnableObject runnable_object: toremove_list) {
			GAME_MANAGER.getRunnableObjects().remove(runnable_object);
		}
	}
	
	/**
	 * Checks the collision for each GameObject if it collides with other GameObjects
	 * @author Dave
	 */
	protected void checkObjectCollisions() {
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
	
	/**
	 * Initializes the scene properties. Called only after switching GameScenes
	 * @author Dave
	 */
	public void initializeProperties() {
		initOtherProperties();
		initObjectProperties();
		initGUIProperties();
		initAudioProperties();
	}
	
	protected abstract void initOtherProperties();
	protected abstract void initObjectProperties();
	protected abstract void initGUIProperties();
	protected abstract void initAudioProperties();
	
	/**
	 * Do not call in update method. Called only when switching GameScenes.
	 * @author Dave
	 */
	public abstract void onExit();
	
	//Getters
	public Scene getScene(){
		return scene;
	}
	
	public GraphicsContext getGraphicsContext() {
		return gc;
	}
}
