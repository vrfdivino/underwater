package gamescene;

import java.util.Random;

import component.AnimatedSprite;
import component.AudioPlayer;
import component.Timer;
import constants.Assets;
import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.Player;
import gameobject.Projectile;
import gameobject.SpikeBall;
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
import manager.GameManager;
import parentclass.GameScene;

public class Level_001 extends GameScene{
	
	private AnimatedSprite background;
	private double bg_scroll_speed = 36;
	private Player player;
	private AnglerFish boss;
	
	private BorderPane pane;
	private Label timer_label;
	private Label hp_label;
	private Label score_label;
	private MenuButton back_button;	
	private Timer timer;

	public Level_001(GameStage gameStage){
		
		pane = new BorderPane();
		scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		this.game_stage = gameStage;
	}
	
	@Override
	protected void initOtherProperties() {
		TIME_MANAGER.resetTimeElapsed();
		GAME_MANAGER.reset();
		PLAYER_MANAGER.reset();
		initSpawner();
		initBossSpawner();
	}
	
	@Override
	protected void initObjectProperties() {
		player = new Player(100, -450);
		boss = new AnglerFish(1200, 400);
		Projectile projectile = new Projectile(player);
		runnable_object_list.add(player);
		runnable_object_list.add(projectile);
		spawnInitialEnemies();
	}
	
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
				
		pane.setCenter(canvas);
		pane.setTop(_top);
		
	}
	
	/**
	 * 
	 * A helper function to build the status bar.
	 * 
	 * @return
	 * @author vondivino, dave
	 */
	private HBox buildStatusBar() {
		
		HBox _statusBar = new HBox();
		
		/// TIMER PANE ///
		StackPane _timer_pane = new StackPane();
		Image _timer_image = new Image(Assets.TIMER);
		ImageView _timer = new ImageView(_timer_image);
		timer_label = new Label();
		timer_label.setTextFill(Color.web("#f1f2b6", 1.0));
		timer_label.setText("01:00");
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
		score_label.setText(String.valueOf(PLAYER_MANAGER.getFishKilled()));
		score_label.setFont(Font.loadFont(Assets.SQUARED, 30));
		StackPane.setMargin(score_label, new Insets(0, -100, 0, 0));
		_score_pane.getChildren().add(_score);
		_score_pane.getChildren().add(score_label);
		
		_statusBar.getChildren().addAll(_timer_pane, _hp_pane, _score_pane);
		return _statusBar;
	}
	
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
		
		checkObjectCollisions();
		checkDestroyedObjects();
		
//		spawnEnemy();
//		spawnBoss();
		reloadProjectile();
		
		checkIfEndGame();
		
		limitPlayerMovement();
		
		pane.requestFocus();
		System.out.println(PLAYER_MANAGER.getHp());
		
	}
	@Override
	protected void updateGUI() {
		
		scrollBackground();
		updateTimerLabel();
		updateHPLabel();
		updateScoreLabel();
		
	}
	
	/**
	 * 
	 * @author dave
	 */
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
	
	/**
	 * 
	 * @author dave
	 */
	private void scrollBackground() {
		background.getPosition().add(new Vector2(0, -bg_scroll_speed * TIME_MANAGER.getDeltaTime()));
		if (background.getPosition().y <= -565) {
			background.setPosition(new Vector2(background.getPosition().x, -565));
		}
		background.render(gc);
	}
	
	/**
	 * 
	 * Update the timer in the status bar.
	 * 
	 * @author vondivino
	 */
	private void updateTimerLabel() {
		int _time_left = GAME_MANAGER.getTimeLeft();
		
		if (_time_left <= 0) {
			timer_label.setText("00:00");
		}else if (_time_left >= 10) {
			timer_label.setText("00:" + _time_left);
		} else {
			timer_label.setText("00:0" + _time_left);
		}
	}
	
	/**
	 * 
	 * TODO:
	 * Implement this one if the enemy is available.
	 * 
	 * @author vondivino
	 */
	private void spawnEnemy() {
		if(GAME_MANAGER.getSpawn()) {
			GAME_MANAGER.spawnEnemy(runnable_object_list);
		}
	}
	
	/**
	 * 
	 * Update the HP in the status bar.
	 * 
	 * @author vondivino
	 */
	private void updateHPLabel() {
		hp_label.setText(String.valueOf(PLAYER_MANAGER.getHp()));
	}
	

	/**
	 * 
	 * Check if the game should be ended.
	 * 
	 * @author vondivino
	 */
	private void checkIfEndGame() {
//		if(GAME_MANAGER.getTimeLeft() <= -1 || PLAYER_MANAGER.getHp() <= 0 || PLAYER_MANAGER.getStrength() <= 0) {
//			if(PLAYER_MANAGER.getStrength() <= 0 || PLAYER_MANAGER.getHp() <= 0) {
//				PLAYER_MANAGER.setIsWon(false);	
//			} else {
//				PLAYER_MANAGER.setIsWon(true);
//			}
//			game_stage.setGameScene(new EndScreen(game_stage));
//		}
	}
	
	/**
	 * 
	 * TODO:
	 * Implement once the player can shoot enemies.
	 * 
	 * @author vondivino
	 */
	private void updateScoreLabel() {
		
	}
	
	/**
	 * 
	 * Spawn boss.
	 * 
	 * @author vondivino
	 */
	private void spawnBoss() {
		if(GAME_MANAGER.getTimeLeft() ==( 60 - 30) && !GAME_MANAGER.getSpawnBoss()) {
			GAME_MANAGER.spawnBoss(runnable_object_list);
			GAME_MANAGER.setSpawnBoss(true);
		} 
	}
	
	private void initTimer() {
		timer = new Timer(1);
		timer.setLoop(true);
		timer.onTimerTimeout(()->{
		    GAME_MANAGER.setTimeLeft(-1);
		 });
		timer.start();
		TIME_MANAGER.addTimer(timer);
	}
	
	
	/************** FINAL ****/
	
	
	
	/**
	 * Spawn initial small fish enemies.
	 * 
	 * @author Von Divino
	 */
	private void spawnInitialEnemies() {
		GAME_MANAGER.spawnInitialEnemies(runnable_object_list);
	}
	
	private void initSpawner() {
		Timer spawner = new Timer(3);
		spawner.setLoop(true);
		spawner.onTimerTimeout(()->{
		    for(int i = 0; i < 3; ++i) GAME_MANAGER.spawnEnemy(runnable_object_list);
		 });
		spawner.start();
		TIME_MANAGER.addTimer(spawner);
	}
	
	private void initBossSpawner() {
		Timer boss_spawner = new Timer(5);
		boss_spawner.setLoop(true);
		boss_spawner.onTimerTimeout(()->{
			if(boss.getCanRelease()) {
			    SpikeBall spike_ball = new SpikeBall(boss);
			    runnable_object_list.add(spike_ball);
			    releaseSpikeBall(spike_ball);
			    boss.setCanRelease(false);
			}
		    if(!boss.getSpawn()) {
		    	runnable_object_list.add(boss);
		    	boss.setSpawn(true);
		    }
		 });
		boss_spawner.start();
		TIME_MANAGER.addTimer(boss_spawner);
	}
	
	
	private void reloadProjectile() {
		if(player.getCanReload() == true) {
			Projectile new_projectile = new Projectile(player);
			runnable_object_list.add(new_projectile);
			player.setCanReload(false);
		}
	}
	
	private void releaseSpikeBall(SpikeBall sb) {
		Timer releaser = new Timer(5);
		releaser.setLoop(true);
		releaser.onTimerTimeout(()->{
			if(boss.getDir() < 0) {
				sb.setRelease(true);
				boss.setCanRelease(true);
			}
		 });
		releaser.start();
		TIME_MANAGER.addTimer(releaser);
	}
	
} 