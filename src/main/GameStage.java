package main;

import gamescene.SplashScreen;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import parentclass.GameScene;

//Only for initializing the game
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
		this.gameScene = new SplashScreen(this);	
		this.gameScene.initializeProperties();
		this.gc = this.gameScene.getGraphicsContext();
		this.gameLoop = new GameLoop(this);
		
	}
	
	public Stage getStage() {
		return stage;
	}
	
	GraphicsContext getGraphicsContext() {
		return this.gc;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
		this.stage.setTitle(GameStage.APP_NAME);
		this.stage.setScene(this.gameScene.getScene());
		
		this.stage.setResizable(false);
		
	}
	
	public GameScene getGameScene() {
		return this.gameScene;
	}
	
	public void setGameScene(GameScene gameScene) {
		this.gameScene.onExit();
		
		//Initialize new Game Scene
		this.gameScene = gameScene;
		this.gameScene.initializeProperties();
		
		this.gc = gameScene.getGraphicsContext();
		this.stage.setScene(this.gameScene.getScene());
	}
	
	void start() {
		this.stage.show();
		this.gameLoop.start();
	}
}
