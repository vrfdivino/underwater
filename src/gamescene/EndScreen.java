package gamescene;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.TextField;
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
import javafx.scene.paint.Paint;
import javafx.scene.shape.Box;
import javafx.scene.shape.Shape;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import main.GameStage;
import parentclass.GameScene;
import services.Data;
import services.Database;
import services.GameDB;
import services.PlayerData;
import constants.Content;

public class EndScreen extends GameScene {
	private BorderPane root;
	private VBox layout;
	private MenuButton back_button;
	private AnimatedSprite title;
	private AnimatedSprite background;
	private Label screen_title;
	private Label wonLabel;
	private GameDB db;
	
	public EndScreen(GameStage gameStage) {
		root   = new BorderPane();
		scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		screen_title = new Label();
		wonLabel = new Label();
		
		this.game_stage = gameStage;
		
		db = new GameDB(Database.DEV_DB);
	}
	
	@Override
	protected void initOtherProperties() {}
	@Override
	protected void initObjectProperties() {}
	@Override
	protected void initAudioProperties() {}
	@Override
	public void onExit() {}

	@Override
	protected void initGUIProperties() {
		
		background = Layout.STATIC_BACKGROUND;
		title = Layout.STATIC_TITLE;
	
		back_button = new MenuButton(game_stage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(game_stage));
		layout = new VBox();
		
		screen_title.setTextFill(Color.web("#f1f2b6", 1.0));
		screen_title.setText("End screen");
		screen_title.setFont(Font.loadFont(Assets.SQUARED, 30));
		layout.getChildren().add(screen_title);
		
		/**
		 * 
		 * Appear only the "leader board" input if the user won,
		 * Else, just display the back button
		 * 
		 * 
		 */
		if(PLAYER_MANAGER.getIsWon()) {
			buildWonGUI();
		} else {
			buildLoseGUI();
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
	public void update(GraphicsContext gc) { 		
		onStartOfFrame();
		updateObjects();	
		updateGUI();
	}
	
	@Override
	protected void updateGUI() {
		background.render(gc);
		title.render(gc);
	}
	
	/**
	 * 
	 * Build the won GUI.
	 * 
	 * @author vondivino
	 */
	private void buildWonGUI() {
		Label _label1 = new Label("Username:");
		TextField _textField = new TextField();
		Button _submitButton = new Button("Submit");
		
		_submitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	// get user name and commit to the database
		        if(db.connecToDb()) {
		        	db.createTable();
		        	Data p = db.insertData(new PlayerData(_textField.getText()));
		        	db.closeDb();
		        }
		        _textField.clear();
		        
		        // after saving redirect to the splash
		    }
		});
		HBox _inputPane = new HBox();
		_inputPane.getChildren().addAll(_label1, _textField, _submitButton);
		_inputPane.setSpacing(10);
		
		buildWonLabel("You Won!", Color.GREEN);
		layout.getChildren().add(_inputPane);
	}
	
	/**
	 * 
	 * Build the lose GUI.
	 * 
	 * @author vondivino
	 */
	private void buildLoseGUI() {
		buildWonLabel("You Lose!", Color.RED);
	}
	
	/**
	 * 
	 * A wrapper method to build the won label.
	 * 
	 * @param status
	 * @param color
	 * @author vondivino
	 */
	private void buildWonLabel(String status, Paint color) {
		wonLabel.setTextFill(color);
		wonLabel.setText(status);
		wonLabel.setFont(Font.loadFont(Assets.SQUARED, 30));
		layout.getChildren().add(wonLabel);
	}
	
}
