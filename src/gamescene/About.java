package gamescene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

import component.AnimatedSprite;
import constants.Assets;
import constants.Layout;
import datatype.Vector2;
import gui.MenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.GameStage;
import parentclass.GameScene;
import constants.Content;

public class About extends GameScene {
	
	private BorderPane root;
	private VBox layout;
	private MenuButton back_button;
	private AnimatedSprite title;
	private AnimatedSprite background;
	private Label screen_title;
	
	About(GameStage gameStage) {
		
		root   = new BorderPane();
		scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		screen_title = new Label();
		
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
		
		screen_title.setTextFill(Color.web("#f1f2b6", 1.0));
		screen_title.setText(Content.ABOUT_TITLE);
		screen_title.setFont(Font.loadFont(Assets.SQUARED, 30));
		layout.getChildren().add(screen_title);
		
		for(String content: new Content().getAboutContent()) {
			Label _labelContent = new Label();
			_labelContent.setText(content);
			_labelContent.setTextFill(Color.web("#528c9f", 1.0));
			_labelContent.setFont(Font.loadFont(Assets.SQUARED, 20));
			_labelContent.setWrapText(true);
			_labelContent.setTextAlignment(TextAlignment.CENTER);
			_labelContent.setBackground(new Background(new BackgroundFill(Color.web("#f1f2b6"),new CornerRadii(5d),null)));
			_labelContent.setPadding(new Insets(10d));
			layout.getChildren().add(_labelContent);		
		}
		
		layout.getChildren().add(back_button);
		
		layout.setAlignment(Pos.CENTER);
		layout.setMaxWidth(GameStage.WINDOW_WIDTH);
		layout.setSpacing(20d);
		layout.setPadding(new Insets(50d));
		
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
