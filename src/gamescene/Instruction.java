package gamescene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import component.AnimatedSprite;
import constants.Assets;
import constants.Layout;
import gui.MenuButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import main.GameStage;
import parentclass.GameScene;
import constants.Content;

/**
 * The Instruction screen.
 * Inherits all the props and methods in GameScene.
 * 
 * @author Von Divino
 */

public class Instruction extends GameScene {
	
	/////////////////// PROPERTIES ///////////////////
	
	private BorderPane root;
	private VBox layout;
	private MenuButton back_button;
	private AnimatedSprite background;
	private Label screen_title;
	
	/**
	 * Create a new Instruction screen.
	 * 
	 * @param gameStage The game stage.
	 * @author Von Divino
	 */
	
	Instruction(GameStage gameStage) {
		root   = new BorderPane();
		scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		screen_title = new Label();
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
		back_button = new MenuButton(game_stage, 
				Assets.BACK_SELECTED, 
				Assets.BACK_PRESSED,  
				Assets.BACK_UNSELECTED,  
				new SplashScreen(game_stage));
		layout = new VBox();
		
		screen_title.setTextFill(Color.web("#2f325d", 1.0));
		screen_title.setText("Instructions");
		screen_title.setFont(Font.loadFont(Assets.SQUARED, 48));
		layout.getChildren().add(screen_title);
		
		int _idx = 0;
		for(String content: new Content().getInstructionContent()) {
			Label _label_content = new Label();
			_label_content.setText(content);
			_label_content.setTextFill(Color.web("#528c9f", 1.0));
			_label_content.setFont(Font.loadFont(Assets.SQUARED, _idx % 2 == 0 ? 24 : 16));
			_label_content.setWrapText(true);
			_label_content.setTextAlignment(TextAlignment.CENTER);
			_label_content.setBackground(new Background(new BackgroundFill(Color.web("#f1f2b6"),new CornerRadii(5d),null)));
			_label_content.setPadding(new Insets(10d));
			this.layout.getChildren().add(_label_content);
			++_idx;
		}
		
		layout.getChildren().add(back_button);
		
		layout.setAlignment(Pos.CENTER);
		layout.setMaxWidth(GameStage.WINDOW_WIDTH);
		layout.setSpacing(20d);
		layout.setPadding(new Insets(50d));
		
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
	}
	
	@Override
	public void onExit() {}
	@Override
	protected void initOtherProperties() {}
	@Override
	protected void initObjectProperties() {}
	@Override
	protected void initAudioProperties() {}
}
