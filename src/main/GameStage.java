package main;

import constants.Assets;
import gamescene.SplashScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import manager.GameManager;
import manager.TimeManager;
import parentclass.GameScene;

/**
 * Handles Stage and Scene Management
 * @author Dave
 *
 */
public class GameStage {			
	
	public static final double WINDOW_WIDTH = 1024;
	public static final double WINDOW_HEIGHT = 1024;
	public static final String APP_NAME = "Under Pressure";
	
	private Stage stage;
	private GameScene game_scene;
	private GraphicsContext gc;
	
	private GameLoop game_loop;
	
	GameStage() {
		game_scene = new SplashScreen(this);
//		game_scene = new EndScreen(this); // for testing of the end screen only
		game_scene.initializeProperties();
		gc = this.game_scene.getGraphicsContext();
		game_loop = new GameLoop(this);	
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public GameScene getGameScene() {
		return game_scene;
	}
	
	GraphicsContext getGraphicsContext() {
		return gc;
	}
	
	/**
	 * Sets the stage. Should only be called once in Main
	 * @param stage
	 * @author Dave
	 */
	public void setStage(Stage stage) {
		stage.getIcons().add(new Image(Assets.ICON));
		stage.setTitle(GameStage.APP_NAME);
		stage.setScene(this.game_scene.getScene());
		stage.setResizable(false);	
		
		this.stage = stage;
	}
	
	/**
	 * Sets the game_scene. Used for switching between Scenes.
	 * @author Dave
	 * @param game_scene game_scene to switch with
	 */
	public void setGameScene(GameScene game_scene) {
		this.game_scene.onExit();
		GameManager.getInstance().getRunnableObjects().clear();
		TimeManager.getInstance().clearTimerList();
		//Initialize new Game Scene
		game_scene.initializeProperties();
		gc = game_scene.getGraphicsContext();
		stage.setScene(game_scene.getScene());
		
		this.game_scene = game_scene;
	}
	
	void start() {
		stage.show();
		game_loop.start();
	}
}
