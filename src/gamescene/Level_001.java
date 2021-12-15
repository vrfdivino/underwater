package gamescene;

import java.util.Random;

import component.AnimatedSprite;
import component.AudioPlayer;
import constants.Assets;
import datatype.Vector2;
import gameobject.AnglerFish;
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

public class Level_001 extends GameScene{
	
	private BorderPane pane;
	
	//GUI Elements
	private AnimatedSprite background;
	private double bg_scroll_speed = 36;

	private MenuButton backButton;
	
	private Label timeCount;
	private Label hpLabel;
	private Label scoreLabel;
	
	private Player player = new Player(100, -450);

	public Level_001(GameStage gameStage){
		this.pane = new BorderPane();
		this.scene = new Scene(this.pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gameStage = gameStage;
	}
	
	@Override
	protected void initOtherProperties() {
		GAME_MANAGER.reset();
		PLAYER_MANAGER.reset();
	}
	
	@Override
	protected void initObjectProperties() {
		runnableObjectList.add(player);
		spawnInitialEnemies();
	}
	
	@Override
	protected void initGUIProperties() {
		
		background = new AnimatedSprite(new Image[] {new Image(Assets.BACKGROUND_002)}, 1, new Vector2(1024/2, 3072/2), new Vector2(1024, 3072));
		
		this.backButton = new MenuButton(gameStage,  Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		VBox metaBox = new VBox();
		metaBox.getChildren().addAll(this.backButton);
		
		HBox statusBar = buildStatusBar();
		
		metaBox.setAlignment(Pos.CENTER);
		statusBar.setAlignment(Pos.CENTER);
		HBox top = new HBox();
		top.getChildren().addAll(metaBox, statusBar);
				
		this.pane.setCenter(this.canvas);
		this.pane.setTop(top);
		
	}
	
	private HBox buildStatusBar() {
		
		HBox statusBar = new HBox();
		
		/**
		 * 
		 * This is the timer pane.
		 * 
		 */
		StackPane timerPane = new StackPane();
		Image timerImage = new Image(Assets.TIMER);
		ImageView timer = new ImageView(timerImage);
		timeCount = new Label();
		timeCount.setTextFill(Color.web("#f1f2b6", 1.0));
		timeCount.setText("01:00");
		timeCount.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(timeCount, new Insets(0, -90, 0, 0));
		timerPane.getChildren().add(timer);
		timerPane.getChildren().add(timeCount);

		
		/**
		 * 
		 * This is the HP pane.
		 * 
		 * 
		 */
		StackPane hpPane = new StackPane();
		Image hpImage = new Image(Assets.HP);
		ImageView hp = new ImageView(hpImage);
		this.hpLabel = new Label();
		hpLabel.setTextFill(Color.web("#f1f2b6", 1.0));
		hpLabel.setText(String.valueOf(PLAYER_MANAGER.getHp()));
		hpLabel.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(hpLabel, new Insets(0, -90, 0, 0));
		hpPane.getChildren().add(hp);
		hpPane.getChildren().add(hpLabel);
		
		/**
		 * 
		 * This is the score (fish killed) pane.
		 * 
		 * 
		 */
		StackPane scorePane = new StackPane();
		Image scoreImage = new Image(Assets.POINTS);
		ImageView score = new ImageView(scoreImage);
		this.scoreLabel = new Label();
		scoreLabel.setTextFill(Color.web("#f1f2b6", 1.0));
		scoreLabel.setText(String.valueOf(PLAYER_MANAGER.getFishKilled()));
		scoreLabel.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(scoreLabel, new Insets(0, -100, 0, 0));
		scorePane.getChildren().add(score);
		scorePane.getChildren().add(scoreLabel);
		
		statusBar.getChildren().addAll(timerPane, hpPane, scorePane);
		return statusBar;
	}
	
	@Override
	protected void initAudioProperties() {
		AudioPlayer under_pressure = new AudioPlayer(Assets.UNDER_PRESSURE, false);
		AudioPlayer underwater = new AudioPlayer(Assets.UNDERWATER, true);
		AudioPlayer splash = new AudioPlayer(Assets.SPLASH);
		
		AUDIO_MANAGER.addAudioPlayer("Under Pressure", under_pressure);
		SFX_MANAGER.addAudioPlayer("Underwater", underwater);
		SFX_MANAGER.addAudioPlayer("Splash", splash);
		
		AUDIO_MANAGER.playAudioPlayer("Under Pressure");
		SFX_MANAGER.playAudioPlayer("Underwater");
		SFX_MANAGER.playAudioPlayer("Splash");
	}
	
	@Override
	public void onExit() {
		AUDIO_MANAGER.stopAll();
		SFX_MANAGER.stopAll();
	}
	
	@Override 
	public void update(GraphicsContext gc) { 		
		onStartOfFrame();
		updateGUI();
		updateObjects();
		limitPlayerMovement();
		updateTimer();
		checkObjectCollisions();
		checkDestroyedObjects();
		spawnEnemy();
		checkIfEndGame();
		pane.requestFocus();
		
	}
	@Override
	protected void updateGUI() {
		scrollBackground();
		updateTimerLabel();
		updateHpLabel();
		updateScoreLabel();
	}
	
	private void limitPlayerMovement() {
		if (player.getPosition().x <= 64) {
			player.setPosition(new Vector2(64, player.getPosition().y));
			player.setVelocity(new Vector2(0,player.getVelocity().y));
		}
		if (player.getPosition().x >= 965) {
			player.setPosition(new Vector2(965, player.getPosition().y));
			player.setVelocity(new Vector2(0,player.getVelocity().y));
		}
		
		if (player.getPosition().y <= -610) {
			player.setPosition(new Vector2(player.getPosition().x, -610));
			player.setVelocity(new Vector2(player.getVelocity().x, 0));
		}
		if (player.getPosition().y >= 155) {
			player.setPosition(new Vector2(player.getPosition().x, 155));
			player.setVelocity(new Vector2(player.getVelocity().x, 0));
		}
		
	}
	
	private void scrollBackground() {
		background.getPosition().add(new Vector2(0, -bg_scroll_speed * TIME_MANAGER.getDeltaTime()));
		if (background.getPosition().y <= -565) {
			background.setPosition(new Vector2(background.getPosition().x, -565));
		}
		background.render(gc);
	}
	
	private void updateTimerLabel() {
		
		int timeLeft = GAME_MANAGER.getTimeLeft();
		
		if (timeLeft <= 0) {
			this.timeCount.setText("00:00");
		}else if (timeLeft >= 10) {
			this.timeCount.setText("00:" + timeLeft);
		} else {
			this.timeCount.setText("00:0" + timeLeft);
		}
		
	}
	
	private void spawnEnemy() {
		if(GAME_MANAGER.doSpawn()) {
			GAME_MANAGER.spawnEnemy(runnableObjectList);
		}
	}
	
	private void updateHpLabel() {
		hpLabel.setText(String.valueOf(PLAYER_MANAGER.getHp()));
	}
	
	private void spawnInitialEnemies() {
		Random r = new Random();
		for(int i = 0; i < 7; i++) {
			int x = r.nextInt(800) + 200;
			int y =r.nextInt(400) + 200;
			runnableObjectList.add(new AnglerFish(x,y));
		}
	}
	
	private void checkIfEndGame() {
		if(GAME_MANAGER.getTimeLeft() <= 0 || PLAYER_MANAGER.getHp() <= 0) {
			if(PLAYER_MANAGER.getStrength() <= 0 || PLAYER_MANAGER.getHp() <= 0) {
				PLAYER_MANAGER.setIsWon(false);	
			} else {
				PLAYER_MANAGER.setIsWon(true);
			}
			gameStage.setGameScene(new EndScreen(gameStage));
		}
	}
	
	private void updateScoreLabel() {
		
	}
} 