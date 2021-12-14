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
	
	protected ArrayList<RunnableObject> runnableObjectList = new ArrayList<RunnableObject>();
	
	protected GameStage gameStage;
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
		for (RunnableObject object: runnableObjectList) {
			// update speed of AnglerFish randomly
			// below condition is not final, it should be random, not every spawn
			// just a simulation
			if(object instanceof AnglerFish && GAME_MANAGER.doSpawn()) {
				Random r = new Random();
				((AnglerFish) object).setSpeed(r.nextInt(125) + r.nextInt(500));
			}
			
			object.update(gc);
		}
	}
	
	/**
	 * To be called in update method.
	 * @author Dave
	 */
	protected abstract void updateGUI();
	
	/**
	 * Removes GameObject from the runnableObjectList if they are destroyed.
	 * To be called in update method.
	 * @author Dave
	 */
	protected void checkDestroyedObjects() {
		ArrayList<RunnableObject> toRemoveList = new ArrayList<RunnableObject>(); 
		for (RunnableObject runnableObject: runnableObjectList) {
			if (runnableObject instanceof GameObject) {
				GameObject gameObject = (GameObject) runnableObject;
				if (gameObject.isDestroyed()) {
					toRemoveList.add(runnableObject);
				}
			}
		}
		
		//Solve ConcurrentModificationException
		for (RunnableObject runnableObject: toRemoveList) {
			runnableObjectList.remove(runnableObject);
		}
	}
	
	/**
	 * Checks the collision for each GameObject if it collides with other GameObjects
	 * @author Dave
	 */
	protected void checkObjectCollisions() {
		for (RunnableObject runnableObject: runnableObjectList) {
			if (runnableObject instanceof GameObject) {
				GameObject gameObject = (GameObject) runnableObject;
				for (RunnableObject anotherObject: runnableObjectList) {
					GameObject other = (GameObject) anotherObject;
					if (other != gameObject) {
						//System.out.println(gameObject + " COLLIDING WITH " + other);
						gameObject.collidesWith(other);
					}
				}
			}
		}
		
		//System.out.println("End of Check \n");
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
	
	/**
	 * 
	 */
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
	
	protected void updateTimer() {
		GAME_MANAGER.updateTimer((int) TIME_MANAGER.getTimeElapsed());
	}
	
}
