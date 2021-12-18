package gamescene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

import java.util.ArrayList;

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
import services.Data;
import services.Database;
import services.GameDB;
import services.PlayerData;

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
	private AnimatedSprite background;
	private GameDB db;
	private ArrayList<PlayerData> players;
	private Label screen_title;
	
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
		game_stage = gameStage;
		db     = new GameDB(Database.PROD_DB);
		screen_title = new Label();
	}
	
	/**
	 * Initialize GUI.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void initGUIProperties() {
		background = Layout.STATIC_BACKGROUND;
		back_button = new MenuButton(game_stage, Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(game_stage));
		layout = new VBox();
		
		screen_title.setTextFill(Color.web("#2f325d", 1.0));
		screen_title.setText("Scoreboard");
		screen_title.setFont(Font.loadFont(Assets.SQUARED, 48));
		layout.getChildren().add(screen_title);
		layout.getChildren().add(buildPlayers());
		layout.getChildren().add(back_button);
		layout.setAlignment(Pos.CENTER);
		layout.setSpacing(20);
		
		root.getChildren().add(canvas);		
		root.setCenter(layout);
	}
	

	/**
	 * Initialize database query.
	 * 
	 * @author Von Divino
	 */
	@Override
	protected void initOtherProperties() {
		if(db.connectToDb()) {
        	db.createTable();
			players = new ArrayList<PlayerData>();
			for(Data d: db.retrieveAllData()) {
				PlayerData player = d.getPlayerData();
				players.add(player);
			}
			db.closeDb();
		}
	}
	
	private VBox buildPlayers() {
		VBox _player_box = new VBox();
		for(PlayerData player: players) {
			Label _player_label = new Label();
			_player_label.setText(player.getName() + " - " + player.getScore() + " pts");
			_player_label.setTextFill(Color.web("#528c9f", 1.0));
			_player_label.setFont(Font.loadFont(Assets.SQUARED,16));
			_player_label.setWrapText(true);
			_player_label.setTextAlignment(TextAlignment.CENTER);
			_player_label.setBackground(new Background(new BackgroundFill(Color.web("#f1f2b6"),new CornerRadii(5d),null)));
			_player_label.setPadding(new Insets(10d));
			_player_box.getChildren().add(_player_label);
		}
		_player_box.setSpacing(10d);
		_player_box.setAlignment(Pos.CENTER);
		return _player_box;
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
	}
	
	/**
	 * Trigger on exit.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	public void onExit() {
		db.closeDb();
	}
	
	@Override
	protected void initObjectProperties() {}
	@Override
	protected void initAudioProperties() {}
}
