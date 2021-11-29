package main;

import gamescene.SplashScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import parentclass.GameScene;

/**
 * Handles Stage and Scene Management
 * @author Dave
 *
 */
public class GameStage {			
	
	public static final double WINDOW_WIDTH = 1000;
	public static final double WINDOW_HEIGHT = 1000;
	public static final String APP_NAME = "Under Pressure";
	public static final double JAVA_FPS = 60.0;
	
	private Stage stage;
	private GameScene gameScene;
	private GraphicsContext gc;
	
	private GameLoop gameLoop;
	
	GameStage() {
		gameScene = new SplashScreen(this);	
		gameScene.initializeProperties();
		gc = this.gameScene.getGraphicsContext();
		gameLoop = new GameLoop(this);	
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public GameScene getGameScene() {
		return gameScene;
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
		this.stage = stage;
		this.stage.setTitle(GameStage.APP_NAME);
		this.stage.setScene(this.gameScene.getScene());
		
		this.stage.setResizable(false);	
	}
	
	/**
	 * Sets the GameScene. Used for switching between Scenes.
	 * @author Dave
	 * @param gameScene GameScene to switch with
	 */
	public void setGameScene(GameScene gameScene) {
		this.gameScene.onExit();
		
		//Initialize new Game Scene
		this.gameScene = gameScene;
		this.gameScene.initializeProperties();
		
		this.gc = gameScene.getGraphicsContext();
		this.stage.setScene(gameScene.getScene());
	}
	
	void start() {
		stage.show();
		gameLoop.start();
	}
}
