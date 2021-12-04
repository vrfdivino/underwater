package gamescene;

import java.util.ArrayList;

import component.AudioPlayer;
import constants.Assets;
import gameobject.Player;
import gui.MenuButton;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameStage;
import javafx.scene.Node;
import parentclass.GameScene;

public class Level_001 extends GameScene{
	
	// private AnchorPane root;
	private BorderPane pane;
	
	private Label label;
	private HBox statusBar;
	private MenuButton backButton;
	
	private Player player = new Player(500, 500);
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
		this.runnableObjectList.add(this.player);

	}
	
	protected void setGUIProperties() {
		
		// JUST A TEMPORARY LAYOUT
		
		label = new Label("This is the Main Game Scene!");
		this.backButton = new MenuButton(gameStage, Assets.BACK_SELECTED,  Assets.BACK_UNSELECTED,  new SplashScreen(gameStage));
		VBox metaBox = new VBox();
		metaBox.getChildren().addAll(label, this.backButton);
		
		HBox statusBar = this.buildStatusBar();
		
		metaBox.setAlignment(Pos.CENTER);
		statusBar.setAlignment(Pos.CENTER);
		HBox top = new HBox();
		top.getChildren().addAll(metaBox, statusBar);
				
		this.pane.setCenter(this.canvas);
		this.pane.setTop(top);
		// root.getChildren().add(this.pane);
		
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
		
	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		
		onStartOfFrame();
		updateObjects();	
		updateGUI();
		pane.requestFocus();
		
	}
	@Override
	protected void updateGUI() {
		// TODO Auto-generated method stub
		
		int timeElapsed = (int) TIME_MANAGER.getTimeElapsed();
		
		if (this.timeElapsed != timeElapsed) {
			--this.timeLeft;
			this.timeElapsed = timeElapsed;
		}
		
		this.timeCount.setText("0:" + this.timeLeft);
		System.out.println(this.timeLeft);
	}
}