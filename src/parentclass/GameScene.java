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
	
	@Override
	public void update(GraphicsContext gc) {}
	
	/**
	 * To be called in update method.
	 * 
	 * @author Dave Jimenez
	 */
	
	protected abstract void updateGUI();
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
