package gamescene;

import component.AnimatedSprite;
import component.AudioPlayer;
import constants.Assets;
import datatype.Vector2;
import gameobject.AnglerFish;
import gameobject.Player;
import gui.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameStage;
import parentclass.GameScene;

public class Level_001 extends GameScene{
	
	private BorderPane pane;
	
	//GUI Elements
	private AnimatedSprite background;
	private double bg_scroll_speed = 36;
	
	private HBox statusBar;
	private MenuButton backButton;
	
	private Player player = new Player(100, -450);
	private AnglerFish enemy = new AnglerFish(400, 200);
	private int timeLeft = 60;
	private Label timeCount;
	private int timeElapsed;
	

	public Level_001(GameStage gameStage){
		
		// this.root = new AnchorPane();
		this.pane = new BorderPane();
		this.scene = new Scene(this.pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gameStage = gameStage;
	}
	
	public void initializeProperties(){
		
		setObjectProperties();
		setGUIProperties();
		setAudioProperties();
	}
	
	protected void setObjectProperties() {
		runnableObjectList.add(player);
		runnableObjectList.add(enemy);
		
	}
	
	protected void setGUIProperties() {
		
		background = new AnimatedSprite(new Image[] {new Image(Assets.BACKGROUND_002)}, 1, new Vector2(1024/2, 3072/2), new Vector2(1024, 3072));
		
		// JUST A TEMPORARY LAYOUT
		
		this.backButton = new MenuButton(gameStage,  Assets.BACK_SELECTED, Assets.BACK_PRESSED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		VBox metaBox = new VBox();
		metaBox.getChildren().addAll(this.backButton);
		
		HBox statusBar = this.buildStatusBar();
		
		metaBox.setAlignment(Pos.CENTER);
		statusBar.setAlignment(Pos.CENTER);
		HBox top = new HBox();
		top.getChildren().addAll(metaBox, statusBar);
				
		this.pane.setCenter(this.canvas);
		this.pane.setTop(top);
		
	}
	
	private HBox buildStatusBar() {
		
		HBox statusBar = new HBox();
		
		Label timeLabel = new Label("Time: ");
		this.timeCount = new Label("1:00");
		HBox timeBox = new HBox();
		timeBox.getChildren().addAll(timeLabel, this.timeCount);
		
		Label scoreLabel = new Label("Score: ");
		Label scoreCount = new Label("999");
		HBox scoreBox = new HBox();
		scoreBox.getChildren().addAll(scoreLabel, scoreCount);
		
		Label strengthLabel = new Label("Strength: ");
		Label strengthCount = new Label("140");
		HBox strengthBox = new HBox();
		strengthBox.getChildren().addAll(strengthLabel, strengthCount);
		
		statusBar.getChildren().addAll(timeBox, scoreBox, strengthBox);
		return statusBar;

	}
	
	protected void setAudioProperties() {
		
		AudioPlayer under_pressure = new AudioPlayer(Assets.UNDER_PRESSURE, false);
		AudioPlayer underwater = new AudioPlayer(Assets.UNDERWATER, true);
		
		AUDIO_MANAGER.addAudioPlayer("Under Pressure", under_pressure);
		SFX_MANAGER.addAudioPlayer("Underwater", underwater);
		
		AUDIO_MANAGER.playAudioPlayer("Under Pressure");
		SFX_MANAGER.playAudioPlayer("Underwater");
		
	}
	
	@Override
	public void onExit() {
		AUDIO_MANAGER.stopAll();
		SFX_MANAGER.stopAll();
	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		
		onStartOfFrame();
		updateGUI();
		updateObjects();
		limitPlayerMovement();
		
		checkObjectCollisions();
		
		pane.requestFocus();
		
	}
	@Override
	protected void updateGUI() {
		updateTimer();
		scrollBackground();
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
		
		//System.out.println(player.getPosition().x);
		
	}
	
	private void scrollBackground() {
		background.getPosition().add(new Vector2(0, -bg_scroll_speed * TIME_MANAGER.getDeltaTime()));
		if (background.getPosition().y <= -565) {
			background.setPosition(new Vector2(background.getPosition().x, -565));
		}
		background.render(gc);
	}
	
	private void updateTimer() {
		int timeElapsed = (int) TIME_MANAGER.getTimeElapsed();
		
		if (this.timeElapsed != timeElapsed) {
			--this.timeLeft;
			this.timeElapsed = timeElapsed;
		}
		
		this.timeCount.setText("0:" + this.timeLeft);
		//System.out.println(this.timeLeft);
	}
}