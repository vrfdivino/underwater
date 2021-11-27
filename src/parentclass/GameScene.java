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
	
	public Scene getScene(){
		return this.scene;
	}
	
	public GraphicsContext getGraphicsContext() {
		return this.gc;
	}
	
	public abstract void initializeProperties();
	
	protected void onStartOfFrame() {
		INPUT_MANAGER.inputUpdate(this.scene);
		gc.clearRect(0, 0, GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
	}
	
	public abstract void onExit();
}
