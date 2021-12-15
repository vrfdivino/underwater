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
	private MenuButton backButton;
	private AnimatedSprite title;
	private AnimatedSprite background;
	private Label screenTitle;
	private Label wonLabel;
	private GameDB db;
	
	public EndScreen(GameStage gameStage) {
		this.root   = new BorderPane();
		this.scene  = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc     = canvas.getGraphicsContext2D();
		this.screenTitle = new Label();
		this.wonLabel = new Label();
		this.gameStage = gameStage;
		this.db = new GameDB(Database.DEV_DB);
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
		
		this.background = Layout.STATIC_BACKGROUND;
		this.title = Layout.STATIC_TITLE;
	
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		this.layout = new VBox();
		
		screenTitle.setTextFill(Color.web("#f1f2b6", 1.0));
		screenTitle.setText("End screen");
		screenTitle.setFont(Font.loadFont(Assets.SQUARED, 30));
		this.layout.getChildren().add(screenTitle);
		
		/**
		 * 
		 * Appear only the "leader board" input if the user won,
		 * Else, just display the back button
		 * 
		 * 
		 */
		if(PLAYER_MANAGER.getIsWon()) {
			this.buildWonGUI();
		} else {
			this.buildLoseGUI();
		}
		
		this.layout.getChildren().add(this.backButton);
		
		this.layout.setAlignment(Pos.CENTER);
		this.layout.setMaxWidth(GameStage.WINDOW_WIDTH);
		this.layout.setSpacing(20d);
		this.layout.setPadding(new Insets(50d));
		
		this.root.getChildren().add(this.canvas);		
		this.root.setCenter(this.layout);
	}


	@Override 
	public void update(GraphicsContext gc) { 		
		this.onStartOfFrame();
		this.updateObjects();	
		this.updateGUI();
	}
	
	@Override
	protected void updateGUI() {
		this.background.render(gc);
		this.title.render(gc);
	}
	
	/**
	 * 
	 * Build the won GUI.
	 * 
	 * @author vondivino
	 */
	private void buildWonGUI() {
		Label label1 = new Label("Username:");
		TextField textField = new TextField();
		Button submitButton = new Button("Submit");
		submitButton.setOnAction(new EventHandler<ActionEvent>() {
		    @Override public void handle(ActionEvent e) {
		    	
		    	// get user name and commit to the database
		        if(db.connecToDb()) {
		        	db.createTable();
		        	Data p = db.insertData(new PlayerData(textField.getText()));
		        	db.closeDb();
		        }
		        textField.clear();
		        
		        // after saving redirect to the splash
		    }
		});
		HBox inputPane = new HBox();
		inputPane.getChildren().addAll(label1, textField, submitButton);
		inputPane.setSpacing(10);
		
		this.buildWonLabel("You Won!", Color.GREEN);
		this.layout.getChildren().add(inputPane);
	}
	
	/**
	 * 
	 * Build the lose GUI.
	 * 
	 * @author vondivino
	 */
	private void buildLoseGUI() {
		this.buildWonLabel("You Lose!", Color.RED);
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
		this.wonLabel.setTextFill(color);
		this.wonLabel.setText(status);
		this.wonLabel.setFont(Font.loadFont(Assets.SQUARED, 30));
		this.layout.getChildren().add(this.wonLabel);
	}
	
}
