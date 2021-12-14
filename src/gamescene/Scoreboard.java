package gamescene;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import component.AnimatedSprite;
import constants.Assets;
import constants.Layout;
import datatype.Vector2;
import gui.MenuButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Shape;
import main.GameStage;
import parentclass.GameScene;

public class Scoreboard extends GameScene {
	
	private BorderPane root;
	private VBox layout;
	private MenuButton backButton;
	private AnimatedSprite title;
	private AnimatedSprite background;
	
	Scoreboard(GameStage gameStage) {
		
		this.root   = new BorderPane();
		this.scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc     = canvas.getGraphicsContext2D();
		
		this.gameStage = gameStage;
	}
	
	@Override
	protected void initOtherProperties() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void initObjectProperties() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void initGUIProperties() {
		
		this.background = Layout.STATIC_BACKGROUND;
		this.title = Layout.STATIC_TITLE;
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		this.layout = new VBox();
		
		this.layout.getChildren().add(this.backButton);
		this.layout.setAlignment(Pos.CENTER);
		
		this.root.getChildren().add(this.canvas);		
		this.root.setCenter(this.layout);
	}


	@Override
	protected void initAudioProperties() {
		// TODO Auto-generated method stub

	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		this.onStartOfFrame();
		this.updateObjects();	
		this.updateGUI();
		root.requestFocus();
		
	}
	
	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub
		this.background.render(gc);
		this.title.render(gc);

	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}
	
}
