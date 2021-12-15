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
	private MenuButton back_button;
	private AnimatedSprite title;
	private AnimatedSprite background;
	
	Scoreboard(GameStage gameStage) {
		
		root   = new BorderPane();
		scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		
		this.game_stage = gameStage;
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
		
		background = Layout.STATIC_BACKGROUND;
		title = Layout.STATIC_TITLE;
		back_button = new MenuButton(game_stage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(game_stage));
		layout = new VBox();
		
		layout.getChildren().add(back_button);
		layout.setAlignment(Pos.CENTER);
		
		root.getChildren().add(canvas);		
		root.setCenter(layout);
	}


	@Override
	protected void initAudioProperties() {
		// TODO Auto-generated method stub

	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		onStartOfFrame();
		updateObjects();	
		updateGUI();
		root.requestFocus();
		
	}
	
	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub
		background.render(gc);
		title.render(gc);

	}
	
	@Override
	public void onExit() {
		// TODO Auto-generated method stub

	}
	
}
