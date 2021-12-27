package gamescene;

import component.AnimatedSprite;
import component.AudioPlayer;
import component.Timer;
import constants.Assets;
import datatype.Vector2;
import gameobject.Player;
import gui.MenuButton;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.GameStage;
import parentclass.GameScene;

/**
 * The level 01 screen.
 * Inherits all the props and methods in GameScene.
 *  
 * @author Dave Jimenez, Von Divino
 */

public class Level_001 extends GameScene {
	
	/////////////////// PROPERTIES ///////////////////
	
	private static final int X_BOUND_MIN = 64;
	private static final int X_BOUND_MAX = 965;
	private static final int Y_BOUND_MIN = -730;
	private static final int Y_BOUND_MAX = 190;
	
	private AnimatedSprite background;
	private double bg_scroll_speed = 36;
	private Player player;
	
	private BorderPane pane;
	private Label timer_label;
	private Label hp_label;
	private Label score_label;
	private MenuButton back_button;	

	/**
	 * Creates a new Level_001 object.
	 * 
	 * @param gameStage The game stage.
	 * @author Dave Jimenez
	 */
	
	public Level_001(GameStage gameStage){
		
		pane = new BorderPane();
		scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		this.game_stage = gameStage;
	}
	/**
	 * Initialize other screen properties.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void initOtherProperties() {
		initPlayer();
		initGame();
		initTimer();
	}
	
	/**
	 * Initialize screen objects.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void initObjectProperties() {
		player = GAME_MANAGER.getPlayer();
	}
	
	/**
	 * Initialize screen GUI.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	protected void initGUIProperties() {
		background = new AnimatedSprite(new Image[] {new Image(Assets.BACKGROUND_002)}, 1, new Vector2(1024/2, 3072/2), new Vector2(1024, 3072));
		
		back_button = new MenuButton(game_stage,  Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(game_stage));
		VBox _metaBox = new VBox();
		_metaBox.getChildren().addAll(this.back_button);
		
		HBox _statusBar = buildStatusBar();
		
		_metaBox.setAlignment(Pos.CENTER);
		_statusBar.setAlignment(Pos.CENTER);
		HBox _top = new HBox();
		_top.getChildren().addAll(_metaBox, _statusBar);
		
		timer_label.setText("01:00");
		pane.setCenter(canvas);
		pane.setTop(_top);
	}
	
	/**
	 * A helper function to build the status bar.
	 * 
	 * @return HBox The status bar itself.
	 * @author Von Divino, Dave Jimenez
	 */
	
	private HBox buildStatusBar() {
		HBox _statusBar = new HBox();
		
		/// TIMER PANE ///
		StackPane _timer_pane = new StackPane();
		Image _timer_image = new Image(Assets.TIMER);
		ImageView _timer = new ImageView(_timer_image);
		timer_label = new Label();
		timer_label.setTextFill(Color.web("#f1f2b6", 1.0));
		timer_label.setText("00:59");
		timer_label.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(timer_label, new Insets(0, -90, 0, 0));
		_timer_pane.getChildren().add(_timer);
		_timer_pane.getChildren().add(timer_label);

		/// HP PANE ///
		StackPane _hp_pane = new StackPane();
		Image _hp_image = new Image(Assets.HP);
		ImageView _hp = new ImageView(_hp_image);
		hp_label = new Label();
		hp_label.setTextFill(Color.web("#f1f2b6", 1.0));
		hp_label.setText(String.valueOf(PLAYER_MANAGER.getHp()));
		hp_label.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(hp_label, new Insets(0, -90, 0, 0));
		_hp_pane.getChildren().add(_hp);
		_hp_pane.getChildren().add(hp_label);
		
		/// SCORE PANE ///
		StackPane _score_pane = new StackPane();
		Image _score_image = new Image(Assets.POINTS);
		ImageView _score = new ImageView(_score_image);
		this.score_label = new Label();
		score_label.setTextFill(Color.web("#f1f2b6", 1.0));
		score_label.setText(String.valueOf(PLAYER_MANAGER.getScore()));
		score_label.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(score_label, new Insets(0, -100, 0, 0));
		_score_pane.getChildren().add(_score);
		_score_pane.getChildren().add(score_label);
		
		_statusBar.getChildren().addAll(_timer_pane, _hp_pane, _score_pane);
		return _statusBar;
	}
	
	/**
	 * Initialize screen audio properties.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	protected void initAudioProperties() {
		AudioPlayer _under_pressure = new AudioPlayer(Assets.UNDER_PRESSURE, false);
		AudioPlayer _underwater = new AudioPlayer(Assets.UNDERWATER, true);
		AudioPlayer _splash = new AudioPlayer(Assets.SPLASH);
		
		AUDIO_MANAGER.addAudioPlayer("Under Pressure", _under_pressure);
		SFX_MANAGER.addAudioPlayer("Underwater", _underwater);
		SFX_MANAGER.addAudioPlayer("Splash", _splash);
		
		AUDIO_MANAGER.playAudioPlayer("Under Pressure");
		SFX_MANAGER.playAudioPlayer("Underwater");
		SFX_MANAGER.playAudioPlayer("Splash");
	}
	
	/**
	 * Trigger on exit.
	 * 
	 * @author Dave Jimenez
	 */
	
	@Override
	public void onExit() {
		AUDIO_MANAGER.stopAll();
		SFX_MANAGER.stopAll();
	}
	
	/**
	 * Update the screen.
	 * 
	 * @author Dave Jimenez, Von Divino
	 */
	
	@Override 
	public void update(GraphicsContext gc) { 	
		updateGUI();
		checkIfWon();
		limitPlayerMovement();
		pane.requestFocus();
	}
	
	/**
	 * Update GUI.
	 * 
	 * @author Von Divino
	 */
	
	@Override
	protected void updateGUI() {
		scrollBackground();
		updatePlayerStats();
	}
	
	/**
	 * Limit player movement across boundaries.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void limitPlayerMovement() {
		if (player.getPosition().x <= Level_001.X_BOUND_MIN) {
			player.setPosition(new Vector2(Level_001.X_BOUND_MIN, player.getPosition().y));
			player.setVelocity(new Vector2(0,player.getVelocity().y));
		}
		if (player.getPosition().x >= Level_001.X_BOUND_MAX) {
			player.setPosition(new Vector2(Level_001.X_BOUND_MAX, player.getPosition().y));
			player.setVelocity(new Vector2(0,player.getVelocity().y));
		}
		
		if (player.getPosition().y <= Level_001.Y_BOUND_MIN) {
			player.setPosition(new Vector2(player.getPosition().x, Level_001.Y_BOUND_MIN));
			player.setVelocity(new Vector2(player.getVelocity().x, 0));
		}
		if (player.getPosition().y >= Level_001.Y_BOUND_MAX) {
			player.setPosition(new Vector2(player.getPosition().x, Level_001.Y_BOUND_MAX));
			player.setVelocity(new Vector2(player.getVelocity().x, 0));
		}
	}
	
	/**
	 * Simulate a scrolling background effect.
	 * 
	 * @author Dave Jimenez
	 */
	
	private void scrollBackground() {
		background.getPosition().add(new Vector2(0, -bg_scroll_speed * TIME_MANAGER.getDeltaTime()));
		if (background.getPosition().y <= -565) {
			background.setPosition(new Vector2(background.getPosition().x, -565));
		}
		background.render(gc);
	}
	
	/**
	 * Initialize the game timer.
	 * This handles the update of the timer label.
	 * 
	 * @author Von Divino
	 */
	
	private void initTimer() {
		TIME_MANAGER.reset();
		Timer timer = new Timer(1);
		timer.setLoop(true);
		timer.setOnTimerTimeout(()->{
			int _time_left = (int) TIME_MANAGER.getTimeLeft();
			
			if (_time_left <= 0) 
				timer_label.setText("00:00");
			else if (_time_left >= 10) 
				timer_label.setText("00:" + _time_left);
			else 
				timer_label.setText("00:0" + _time_left);
		 });
		timer.start();
		TIME_MANAGER.addTimer(timer);
	}
	
	/**
	 * Initialize the player.
	 * This handles the player initial stats.
	 * 
	 * @author Von Divino
	 */
	
	private void initPlayer() {
		PLAYER_MANAGER.initializeStats();
	}
	
	/**
	 * Initialize the game.
	 * This handles the game objects.
	 * 
	 * @author Von Divino
	 */
	
	private void initGame() {
		GAME_MANAGER.spawnPlayer();
		GAME_MANAGER.spawnInitialEnemies();
		GAME_MANAGER.initIntervalEnemies();
		GAME_MANAGER.initBoss();
		GAME_MANAGER.initPowerups();
	}
	
	/**
	 * Update the player stats.
	 * 
	 * @author Von Divino
	 */
	
	private void updatePlayerStats() {
		hp_label.setText(String.valueOf(PLAYER_MANAGER.getHp()));
		score_label.setText(String.valueOf(PLAYER_MANAGER.getScore()));
	}
	
	/**
	 * Check if the game won or lose.
	 * 
	 * @author Von Divino, Dave Jimenez
	 */
	
	private void checkIfWon() {
		if(PLAYER_MANAGER.getHp() <= 0) {
			game_stage.setGameScene(new EndScreen(game_stage, false));
		}
		
		if((int) TIME_MANAGER.getTimeLeft() == -1) {
			game_stage.setGameScene(new EndScreen(game_stage, true));
		}
	}
} 