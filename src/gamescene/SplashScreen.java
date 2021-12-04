package gamescene;

import component.AudioPlayer;
import constants.Assets;
import gameobject.Player;
import gui.MenuButton;
import javafx.beans.Observable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import main.GameStage;
import parentclass.GameScene;

public class SplashScreen extends GameScene{
	private final double volumeScaleFactor = 1f;
	private AnchorPane pane;
	
	//Game Objects
	private Player player = new Player(500, 500);
	
	//GUI Objects
	private Slider volumeSlider;
	private Slider sfxVolumeSlider;
	private MenuButton newGameButton;
	private MenuButton loadGameButton;
	private MenuButton settingsButton;
	private MenuButton aboutButton;
	private MenuButton instructionButton;
	
	public SplashScreen(GameStage gameStage){
		
		pane   = new AnchorPane();
		scene  = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc     = canvas.getGraphicsContext2D();
		
		this.gameStage = gameStage;
		
	}
	
	@Override
	public void initializeProperties(){
		setObjectProperties();
		setGUIProperties();
		setAudioProperties();
	}
	
	@Override
	protected void setObjectProperties() {
		runnableObjectList.add(player);
	}
	
	@Override
	protected void setGUIProperties() {
		
		
		newGameButton  = new MenuButton(gameStage, Assets.NEW_GAME_SELECTED,  Assets.NEW_GAME_UNSELECTED,  new Level_001(gameStage));
		loadGameButton = new MenuButton(gameStage, Assets.LOAD_GAME_SELECTED, Assets.LOAD_GAME_UNSELECTED, new About(gameStage));
		settingsButton = new MenuButton(gameStage, Assets.SETTINGS_SELECTED,  Assets.SETTINGS_UNSELECTED,  new About(gameStage));
		
		// TODO: Change texture for about and instruction buttons
		aboutButton       = new MenuButton(gameStage, Assets.ABOUT_SELECTED,        Assets.ABOUT_UNSELECTED,        new About(gameStage));
		instructionButton = new MenuButton(gameStage, Assets.INSTRUCTION_SELECTED,  Assets.INSTRUCTION_UNSELECTED,  new Instruction(gameStage));
		
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
		newGameButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 360);
		loadGameButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 300);
		aboutButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 240);
		instructionButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 180);
		settingsButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 120);
		
		volumeVBox.setLayoutX(GameStage.WINDOW_WIDTH-300);
		volumeVBox.setLayoutY(GameStage.WINDOW_HEIGHT-60);
		
		//Add to node
		pane.getChildren().add(canvas);
		pane.getChildren().add(newGameButton);
		pane.getChildren().add(loadGameButton);
		pane.getChildren().add(aboutButton);
		pane.getChildren().add(instructionButton);
		pane.getChildren().add(settingsButton);
		pane.getChildren().add(volumeVBox);
		
	}
	
	@Override
	protected void setAudioProperties() {
		
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
		updateObjects();	
		updateGUI();
		pane.requestFocus();
		
	}
	
	protected void updateGUI() {
		volumeSlider.valueProperty().addListener(
				(Observable observable) -> {
				//Using x^4 as input for volume. This allows for a linear loudness experience
				AUDIO_MANAGER.setVolume(volumeScaleFactor * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue()); 
				SFX_MANAGER.setVolume(volumeScaleFactor * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue());
			}
		);
	}
}
