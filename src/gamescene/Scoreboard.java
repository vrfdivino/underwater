package gamescene;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import component.AnimatedSprite;
import constants.Assets;
import constants.Layout;
import gui.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import main.GameStage;
import parentclass.GameScene;

/**
 * The Scoreboard screen.
 * Inherits all the props and methods in GameScene.
 * 
 * @author Von Divino
 */

public class Scoreboard extends GameScene {
	
	/////////////////// PROPERTIES ///////////////////
	
	private BorderPane root;
	private VBox layout;
	private MenuButton back_button;
	private AnimatedSprite title;
	private AnimatedSprite background;
	
	/**
	 * Create a new Scoreboard screen.
	 * 
	 * @param gameStage The game stage.
	 * @author Von Divino
	 */
	
	Scoreboard(GameStage gameStage) {
		root   = new BorderPane();
		scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();	
		this.game_stage = gameStage;
	}
	
	/**
	 * Initialize GUI.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void initGUIProperties() {
		
		background = Layout.STATIC_BACKGROUND;
		title = Layout.STATIC_TITLE;
		back_button = new MenuButton(game_stage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(game_stage));
		layout = new VBox();
		
		layout.getChildren().add(back_button);
		layout.setAlignment(Pos.CENTER);
		
		root.getChildren().add(canvas);		
		root.setCenter(layout);
	}


	/**
	 * Updates the screen.
	 * 
	 * @author Von Divino
	 */

	@Override
	public void update(GraphicsContext gc) { 		
		onStartOfFrame();
		updateObjects();	
		updateGUI();
		root.requestFocus();
		
	}
	
	/**
	 * Updates the GUI.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void updateGUI() {
		background.render(gc);
		title.render(gc);
	}
	
	@Override
	protected void initOtherProperties() {}
	@Override
	protected void initObjectProperties() {}
	@Override
	public void onExit() {}
	@Override
	protected void initAudioProperties() {}
}
