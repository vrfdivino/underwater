package gamescene;

import component.AudioPlayer;
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
import runnableobject.RunnableObject;

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
	
	public SplashScreen(GameStage gameStage){
		pane = new AnchorPane();
		scene = new Scene(pane, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		gc = canvas.getGraphicsContext2D();
		
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
		//Initialize GUI
		newGameButton = new MenuButton(gameStage, "/Menu/Sprites/Title_Texture_Button_NewGame_Selected-400.png", "/Menu/Sprites/Title_Texture_Button_NewGame_Unselected-400.png", new Level_001(gameStage));
		loadGameButton = new MenuButton(gameStage, "/Menu/Sprites/Title_Texture_Button_LoadGame_Selected-400.png", "/Menu/Sprites/Title_Texture_Button_LoadGame_Unselected-400.png", new About(gameStage));
		settingsButton = new MenuButton(gameStage, "/Menu/Sprites/Title_Texture_Button_Settings_Selected-400.png", "/Menu/Sprites/Title_Texture_Button_Settings_Unselected-400.png", new About(gameStage));
		
		volumeSlider = new Slider(0.30, 0.8, AUDIO_MANAGER.getVolume());
		volumeSlider.setMaxWidth(200);
		volumeSlider.setValue(0.5);
		
		sfxVolumeSlider = new Slider(0.30, 0.8, SFX_MANAGER.getVolume());
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
		newGameButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 300);
		loadGameButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 240);
		settingsButton.setLayout(GameStage.WINDOW_WIDTH/2 - 328/2, GameStage.WINDOW_HEIGHT - 180);
		
		volumeVBox.setLayoutX(GameStage.WINDOW_WIDTH-300);
		volumeVBox.setLayoutY(GameStage.WINDOW_HEIGHT-60);
		
		//Add to node
		pane.getChildren().add(canvas);
		pane.getChildren().add(newGameButton);
		pane.getChildren().add(loadGameButton);
		pane.getChildren().add(settingsButton);
		pane.getChildren().add(volumeVBox);
	}
	
	@Override
	protected void setAudioProperties() {
		AudioPlayer call_of_the_sea = new AudioPlayer("resources/Audio/OST_1_CallOfTheSea.mp3", true);
		
		AUDIO_MANAGER.addAudioPlayer("Main Theme", call_of_the_sea);
		AUDIO_MANAGER.setVolume(volumeScaleFactor * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue() * volumeSlider.getValue());
		SFX_MANAGER.setVolume(volumeScaleFactor * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue() * sfxVolumeSlider.getValue());
		
		//Play AudioPlayers
		AUDIO_MANAGER.playAudioPlayer("Main Theme");
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
