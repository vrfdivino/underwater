package gamescene;

import component.AudioPlayer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import main.GameStage;
import parentclass.GameScene;

public class Level_001 extends GameScene{
	private AnchorPane root;
	private BorderPane pane;
	
	private Label label;

	public Level_001(GameStage gameStage){
		this.root = new AnchorPane();
		this.pane = new BorderPane();
		this.scene = new Scene(root, GameStage.WINDOW_WIDTH,GameStage.WINDOW_HEIGHT);
		this.canvas = new Canvas(GameStage.WINDOW_WIDTH, GameStage.WINDOW_HEIGHT);
		this.gc = canvas.getGraphicsContext2D();
		this.gameStage = gameStage;
	}
	
	public void initializeProperties(){
		//Insert Layout
		setObjectProperties();
		setGUIProperties();
		setAudioProperties();
	}
	
	private void setObjectProperties() {

	}
	
	private void setGUIProperties() {
		label = new Label("This is the Main Game Scene!");
		label.setAlignment(Pos.CENTER);
		
		this.pane.setRight(label);
		this.root.getChildren().add(this.pane);
	}
	
	private void setAudioProperties() {
		AudioPlayer under_pressure = new AudioPlayer("resources/Audio/OST_2_UnderPressure.mp3", false);
		AudioPlayer underwater = new AudioPlayer("resources/Audio/SFX_1_Underwater.mp3", true);
		
		AUDIO_MANAGER.addAudioPlayer("Under Pressure", under_pressure);
		SFX_MANAGER.addAudioPlayer("Underwater", underwater);
		
		AUDIO_MANAGER.playAudioPlayer("Under Pressure");
		SFX_MANAGER.playAudioPlayer("Underwater");
	}
	
	@Override
	public void onExit() {
		
	}
	
	@Override
	public void update(GraphicsContext gc) {					//Write all logic for this scene here
		this.onStartOfFrame();
	}
}