package parentclass;

import java.util.ArrayList;
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

/**
 * The class which all the game scnees inherits.
 * 
 * @author Dave Jimenez
 *
 */

public abstract class GameScene implements RunnableObject{
	
	/////////////////// PROPERTIES ///////////////////
	
	protected InputManager INPUT_MANAGER = InputManager.getInstance();
	protected AudioManager AUDIO_MANAGER = AudioManager.getInstance();
	protected SFXManager SFX_MANAGER = SFXManager.getInstance();
	protected GameManager GAME_MANAGER = GameManager.getInstance();
	protected PlayerManager PLAYER_MANAGER = PlayerManager.getInstance();
	protected TimeManager TIME_MANAGER = TimeManager.getInstance();
	protected GameStage game_stage;
	protected Scene scene;
	protected GraphicsContext gc;
	protected Canvas canvas;
	
	/**
	 * This should be called in every scenes.
	 * 
	 */
	
	@Override
	public void update(GraphicsContext gc) {}
	
	/**
	 * To be called at the start of update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected void onStartOfFrame() {
		gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}
	
	/**
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected void updateObjects() {
		GAME_MANAGER.addBufferedRunnableObjectsToAdd();
		for (RunnableObject object: GAME_MANAGER.getRunnableObjects()) {
			object.update(gc);
		}
	}
	
	/**
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected abstract void updateGUI();
	
	/**
	 * Removes GameObject from the runnableobject_list if they are destroyed.
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
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
		
		for (RunnableObject runnable_object: toremove_list) {
			GAME_MANAGER.getRunnableObjects().remove(runnable_object);
		}
	}
	
	/**
	 * Checks the collision for each GameObject if it collides with other GameObjects.
	 * 
	 * @author Dave Jimenez
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
	 * Initializes the scene properties. Called only after switching GameScenes.
	 * 
	 * @author Dave Jimenez
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
	 * 
	 * @author Dave Jimenez
	 */
	
	public abstract void onExit();
	
	/////// GETTERS ///////
	
	public Scene getScene() {return scene;}
	public GraphicsContext getGraphicsContext() {return gc;}
}
