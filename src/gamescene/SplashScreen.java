package gamescene;

import component.AnimatedSprite;
import component.AudioPlayer;
import constants.Assets;
import constants.Layout;
import datatype.Vector2;
import gameobject.Player;
import gui.MenuButton;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameStage;
import parentclass.GameScene;

public class SplashScreen extends GameScene{
	private final double volumeScaleFactor = 1f;
	private AnchorPane pane;
	
	//Game Objects
	private AnimatedSprite background;
	private AnimatedSprite title;
	
	//GUI Objects
	private Slider volumeSlider;
	private Slider sfxVolumeSlider;
	private MenuButton newGameButton;
	private MenuButton scoreboardButton;
	private MenuButton aboutButton;
	private MenuButton instructionButton;
	
	//Gui Properties
	private Vector2 title_vel = Vector2.ZERO;
	private double title_max_spd = 2;
	private Vector2 title_dir = Vector2.ZERO;
	private Vector2 point_1 = new Vector2(GameStage.WINDOW_WIDTH, 250+32);
	private Vector2 point_2 = new Vector2(GameStage.WINDOW_WIDTH, 250+24);
	
	public SplashScreen(GameStage gameStage){
		
		pane   = new AnchorPane();
		scene  = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		
		this.gameStage = gameStage;
		
	}
	
	@Override
	protected void initObjectProperties() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void initOtherProperties() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void initGUIProperties() {
		//title_vel.set(new Vector2(0, 0));
		
		
		//Backround and Title
//		background = new AnimatedSprite(new Image[] {new Image(Assets.BACKGROUND_001)}, 1, new Vector2(GameStage.WINDOW_WIDTH/2, GameStage.WINDOW_HEIGHT/2), new Vector2(1024, 1024));
		background = Layout.STATIC_BACKGROUND;
		title = new AnimatedSprite(new Image[] {new Image(Assets.TITLECARD)}, 1, new Vector2(GameStage.WINDOW_HEIGHT/2, -186), new Vector2(720, 364));
		
		//Buttons
		newGameButton  = new MenuButton(gameStage, Assets.NEW_GAME_SELECTED,  Assets.NEW_GAME_PRESSED, Assets.NEW_GAME_UNSELECTED,  new Level_001(gameStage));
		scoreboardButton = new MenuButton(gameStage, Assets.SCOREBOARD_SELECTED, Assets.SCOREBOARD_PRESSED, Assets.SCOREBOARD_UNSELECTED, new Scoreboard(gameStage));
		aboutButton       = new MenuButton(gameStage,  Assets.ABOUT_SELECTED, Assets.ABOUT_PRESSED, Assets.ABOUT_UNSELECTED,        new About(gameStage));
		instructionButton = new MenuButton(gameStage,  Assets.INSTRUCTION_SELECTED, Assets.INSTRUCTION_PRESSED, Assets.INSTRUCTION_UNSELECTED,  new Instruction(gameStage));
		
		volumeSlider    = new Slider(0.30, 0.8, AUDIO_MANAGER.getVolume());
		sfxVolumeSlider = new Slider(0.30, 0.8, SFX_MANAGER.getVolume());
		
		volumeSlider.setMaxWidth(200);
		volumeSlider.setValue(0.5);
		
		sfxVolumeSlider.setMaxWidth(200);
		sfxVolumeSlider.setValue(0.5);
		
		VBox volume = new VBox(new Label("Volume"), this.volumeSlider);
		VBox sfxVolume = new VBox(new Label("Sound Effects"), this.sfxVolumeSlider);
		HBox volumeSettings = new HBox(volume, sfxVolume);
		VBox volumeVBox = new VBox(new Label("Volume Settings"), volumeSettings);
		
		volume.setAlignment(Pos.CENTER);
		sfxVolume.setAlignment(Pos.CENTER);
		volumeVBox.setAlignment(Pos.CENTER);
		
		//Set up GUI Layout
		newGameButton.setLayout(GameStage.WINDOW_WIDTH/2 - 360/2, GameStage.WINDOW_HEIGHT - 360);
		scoreboardButton.setLayout(GameStage.WINDOW_WIDTH/2 - 360/2, GameStage.WINDOW_HEIGHT - 300);
		aboutButton.setLayout(GameStage.WINDOW_WIDTH/2 - 360/2, GameStage.WINDOW_HEIGHT - 180);
		instructionButton.setLayout(GameStage.WINDOW_WIDTH/2 - 360/2, GameStage.WINDOW_HEIGHT - 240);
		
		volumeVBox.setLayoutX(GameStage.WINDOW_WIDTH-300);
		volumeVBox.setLayoutY(GameStage.WINDOW_HEIGHT-60);
		
		//Add to node
		pane.getChildren().add(canvas);
		pane.getChildren().add(newGameButton);
		pane.getChildren().add(scoreboardButton);
		pane.getChildren().add(instructionButton);
		pane.getChildren().add(aboutButton);
		pane.getChildren().add(volumeVBox);
	}
	
	@Override
	protected void initAudioProperties() {
		
		String musicThemeName = "Main Theme";
		
		AudioPlayer call_of_the_sea = new AudioPlayer(Assets.CALL_OF_THE_SEA, true);
		
		AUDIO_MANAGER.addAudioPlayer(musicThemeName, call_of_the_sea);
		AUDIO_MANAGER.setVolume(volumeScaleFactor * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue());
		SFX_MANAGER.setVolume(volumeScaleFactor * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue());
		
		//Play AudioPlayers
		AUDIO_MANAGER.playAudioPlayer(musicThemeName);
		
	}
	
	@Override
	public void onExit() {
		
		if (newGameButton.isClicked()) {
			AUDIO_MANAGER.stopAll();
		}
		
	}
	
	@Override //Write all logic for the scene here
	public void update(GraphicsContext gc) { 		
		onStartOfFrame();
		updateGUI();
		updateObjects();	
		pane.requestFocus();
		
	}
	
	protected void updateGUI() {
		background.render(gc);
		animateTitle();
		
		volumeSlider.valueProperty().addListener(
				(Observable observable) -> {
				//Using x^4 as input for volume. This allows for a linear loudness experience
				AUDIO_MANAGER.setVolume(volumeScaleFactor * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue()); 
				SFX_MANAGER.setVolume(volumeScaleFactor * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue());
			}
		);
	}
	
	private void animateTitle() {
		if (title.getPosition().y > point_1.y)	title_dir = Vector2.UP;
		if (title.getPosition().y < point_2.y) 	title_dir = Vector2.DOWN;

		title_vel.moveTowards(title_vel, new Vector2(title_max_spd * title_dir.x, title_max_spd * title_dir.y), 5 * TIME_MANAGER.getDeltaTime());
		title.getPosition().add(title_vel);
		title.render(gc);
	}
}
